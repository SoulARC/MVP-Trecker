package com.example.mvt_tracker.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ResultService {
    void calculateResult(List<String[]> gameData);

    String getGame();

    Optional<String> getWinningTeam(Map<String, Integer> teamScores);

    void validateFormat(String[] inputRow);

    void calculatePlayerPoints(List<String[]> gameData, String winningTeam);

    Map<String, Integer> calculateTeamPoints(List<String[]> gameData);
}
