package com.example.mvt_tracker.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ResultService {
    void calculateResult(List<String[]> gameData);

    Optional<String> getWinningTeam(Map<String, Integer> teamScores);
}
