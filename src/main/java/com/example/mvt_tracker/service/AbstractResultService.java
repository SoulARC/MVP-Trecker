package com.example.mvt_tracker.service;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractResultService {

    public String getWinningTeam(Map<String, Integer> teamScores) {
        Optional<Map.Entry<String, Integer>> maxEntry = teamScores.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        return maxEntry.map(Map.Entry::getKey).orElse(null);
    }
}
