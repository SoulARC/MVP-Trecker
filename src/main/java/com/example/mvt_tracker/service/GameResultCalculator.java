package com.example.mvt_tracker.service;

import java.util.List;
import java.util.Map;

public interface GameResultCalculator {
    void calculatePlayerPoints(List<String[]> gameData, String winningTeam);

    Map<String, Integer> calculateTeamPoints(List<String[]> gameData);
}
