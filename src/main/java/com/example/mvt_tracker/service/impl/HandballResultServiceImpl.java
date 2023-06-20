package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.model.Player;
import com.example.mvt_tracker.service.ResultService;
import com.example.mvt_tracker.service.enums.Games;
import com.example.mvt_tracker.service.PlayerDataValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class HandballResultServiceImpl implements ResultService {
    private final PlayerDao playerDao;
    private final PlayerDataValidator namingValidator;
    private Map<String, Integer> teamScore;

    private static final int NICK_NAME_INDEX = 1;
    private static final int TEAM_NAME_INDEX = 3;
    private static final int GOAL_MADE_INDEX = 4;
    private static final int GOAL_RECEIVED_INDEX = 5;
    private static final int MULTIPLIER_GOAL_MADE_POINT = 2;
    private static final int WINNER_BONUS = 10;
    public static final int HANDBALL_ROW_COUNT = 6;

    public HandballResultServiceImpl(PlayerDao playerDao, PlayerDataValidator namingValidator) {
        this.playerDao = playerDao;
        this.namingValidator = namingValidator;
    }

    @Override
    public void calculateResult(List<String[]> gameData) {
        teamScore = calculateTeamPoints(gameData);
        Optional<String> winningTeam = getWinningTeam(teamScore);
        winningTeam.ifPresent(team -> calculatePlayerPoints(gameData, team));
    }

    @Override
    public String getGame() {
        return Games.HANDBALL.name();
    }

    @Override
    private Optional<String> getWinningTeam(Map<String, Integer> teamScores) {
        return teamScores.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    @Override
    private void formatValidation(String[] inputRow) {
        if (inputRow.length != HANDBALL_ROW_COUNT || namingValidator.namingValidation(inputRow)) {
            try {
                throw new IncorrectFormatException("The file has an incorrect format");
            } catch (IncorrectFormatException e) {
                log.error("Invalid format: {}", e.getMessage());
                playerDao.deleteAll();
            }
        }
    }

    @Override
    private void calculatePlayerPoints(List<String[]> gameData, String winningTeam) {
        for (String[] playerData : gameData) {
            String teamName = playerData[TEAM_NAME_INDEX];
            String nickname = playerData[NICK_NAME_INDEX];
            int goalsMade = Integer.parseInt(playerData[GOAL_MADE_INDEX]) * MULTIPLIER_GOAL_MADE_POINT;
            int goalsReceived = Integer.parseInt(playerData[GOAL_RECEIVED_INDEX]);
            int totalPoints = goalsMade - goalsReceived;
            totalPoints += teamName.equals(winningTeam)
                           ? WINNER_BONUS
                           : 0;

            Player player = playerDao.findPlayerByNickname(nickname);
            int ratingPoints = (player != null)
                               ? player.getRatingPoints() + totalPoints
                               : totalPoints;
            playerDao.saveOrUpdatePlayer(new Player(nickname, ratingPoints));
        }
    }

    @Override
    private Map<String, Integer> calculateTeamPoints(List<String[]> gameData) {
        Map<String, Integer> teamScore = new HashMap<>();
        for (String[] playerData : gameData) {
            formatValidation(playerData);
            String teamName = playerData[TEAM_NAME_INDEX];
            int goalMade = Integer.parseInt(playerData[GOAL_MADE_INDEX]) * MULTIPLIER_GOAL_MADE_POINT;
            int goalReceived = Integer.parseInt(playerData[GOAL_RECEIVED_INDEX]);
            int totalPoints = goalMade + goalReceived;
            teamScore.put(teamName, teamScore.getOrDefault(teamName, 0) + totalPoints);
        }
        return teamScore;
    }
}
