package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.model.Player;
import com.example.mvt_tracker.service.AbstractResultService;
import com.example.mvt_tracker.service.HandballResultService;
import com.example.mvt_tracker.util.FormatValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HandballResultServiceImpl extends AbstractResultService implements HandballResultService {
    private final PlayerDao playerDao;
    private final FormatValidator formatValidator;
    private Map<String, Integer> teamScore;

    private static final int NICK_NAME_INDEX = 1;
    private static final int TEAM_NAME_INDEX = 3;
    private static final int GOAL_MADE_INDEX = 4;
    private static final int GOAL_RECEIVED_INDEX = 5;
    private static final int MULTIPLIER_GOAL_MADE_POINT = 2;
    private static final int WINNER_BONUS = 10;

    public HandballResultServiceImpl(PlayerDao playerDao, FormatValidator formatValidator) {
        this.playerDao = playerDao;
        this.formatValidator = formatValidator;
    }

    @Override
    public void calculateResult(List<String[]> gameData) {
        teamScore = calculateTeamPoints(gameData);
        String winningTeam = getWinningTeam(teamScore);
        calculatePlayerPoints(gameData, winningTeam);
    }

    public void calculatePlayerPoints(List<String[]> gameData, String winningTeam) {
        for (String[] playerData : gameData) {
            String teamName = playerData[TEAM_NAME_INDEX];
            String nickname = playerData[NICK_NAME_INDEX];
            int goalsMade = Integer.parseInt(playerData[GOAL_MADE_INDEX]) * MULTIPLIER_GOAL_MADE_POINT;
            int goalsReceived = Integer.parseInt(playerData[GOAL_RECEIVED_INDEX]);
            int totalPoints = goalsMade - goalsReceived;

            if (teamName.equals(winningTeam)) {
                totalPoints += WINNER_BONUS;
            }

            Player player = playerDao.findPlayerByNickname(nickname);
            if (player != null) {
                int ratingPoints = player.getRatingPoints() + totalPoints;
                playerDao.editRatingPointsByNickname(nickname, ratingPoints);
            }
            else {
                player = new Player(nickname, totalPoints);
                playerDao.save(nickname, player);
            }
        }
    }

    public Map<String, Integer> calculateTeamPoints(List<String[]> gameData) {
        Map<String, Integer> teamScore = new HashMap<>();
        for (String[] playerData : gameData) {
            try {
                formatValidator.isValidHandballFormat(playerData);
                String teamName = playerData[TEAM_NAME_INDEX];
                int goalMade = Integer.parseInt(playerData[GOAL_MADE_INDEX]) * MULTIPLIER_GOAL_MADE_POINT;
                int goalReceived = Integer.parseInt(playerData[GOAL_RECEIVED_INDEX]);
                int totalPoints = goalMade + goalReceived;
                teamScore.put(teamName, teamScore.getOrDefault(teamName, 0) + totalPoints);
            } catch (IncorrectFormatException e) {
                log.error("Invalid format: {}", e.getMessage());
                playerDao.deleteAll();
            }
        }

        return teamScore;
    }
}
