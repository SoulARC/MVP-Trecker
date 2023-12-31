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
public class BasketballResultServiceImpl implements ResultService {
    private final PlayerDao playerDao;
    private final PlayerDataValidator namingValidator;
    private Map<String, Integer> teamScore;

    private static final int NICK_NAME_INDEX = 1;
    private static final int TEAM_NAME_INDEX = 3;
    private static final int SCORED_POINTS_INDEX = 4;
    private static final int REBOUNDS_INDEX = 5;
    private static final int ASSISTS_INDEX = 6;
    private static final int MULTIPLIER_SCORED_POINT = 2;
    private static final int WINNER_BONUS = 10;
    private static final int BASKETBALL_ROW_COUNT = 7;

    public BasketballResultServiceImpl(
            PlayerDao playerDao, PlayerDataValidator namingValidator
    ) {
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
        return Games.BASKETBALL.name();
    }

    @Override
    private Optional<String> getWinningTeam(Map<String, Integer> teamScores) {
        return teamScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    @Override
    private void formatValidation(String[] inputRow) {
        if (inputRow.length != BASKETBALL_ROW_COUNT || namingValidator.namingValidation(inputRow)) {
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
            int score = Integer.parseInt(playerData[SCORED_POINTS_INDEX]) * MULTIPLIER_SCORED_POINT;
            int rebounds = Integer.parseInt(playerData[REBOUNDS_INDEX]);
            int assists = Integer.parseInt(playerData[ASSISTS_INDEX]);
            int totalPoints = score + rebounds + assists;
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
            int score = Integer.parseInt(playerData[SCORED_POINTS_INDEX]) * MULTIPLIER_SCORED_POINT;
            int rebounds = Integer.parseInt(playerData[REBOUNDS_INDEX]);
            int assists = Integer.parseInt(playerData[ASSISTS_INDEX]);
            int totalPoints = score + rebounds + assists;
            teamScore.put(teamName, teamScore.getOrDefault(teamName, 0) + totalPoints);
        }
        return teamScore;
    }
}
