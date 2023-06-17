package com.example.mvt_tracker.service;

import com.example.mvt_tracker.exception.IncorrectFormatException;

public interface GameResultProcessor {
    void gameStrategy(String filePath) throws IncorrectFormatException;
}
