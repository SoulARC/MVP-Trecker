package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.service.GameResultProcessor;
import com.example.mvt_tracker.service.BasketballResultService;
import com.example.mvt_tracker.service.HandballResultService;
import com.example.mvt_tracker.util.CSVReader;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.mvt_tracker.service.enums.Games.BASKETBALL;
import static com.example.mvt_tracker.service.enums.Games.HANDBALL;

@Service
public class GameResultProcessorImpl implements GameResultProcessor {
    private static final int gameIndex = 0;
    private final CSVReader csvReader;
    private final BasketballResultService basketballResultService;
    private final HandballResultService handballResultService;

    public GameResultProcessorImpl(
            CSVReader csvReader, BasketballResultService basketballResultService,
            HandballResultService handballResultService
    ) {
        this.csvReader = csvReader;
        this.basketballResultService = basketballResultService;
        this.handballResultService = handballResultService;
    }

    @Override
    public void gameStrategy(String filePath) throws IncorrectFormatException {
        List<String[]> inputData = csvReader.readData(filePath);
        String gameName = inputData.get(gameIndex)[gameIndex];
        inputData.remove(gameIndex);
        if (BASKETBALL.equals(gameName)) {
            basketballResultService.calculateResult(inputData);
        }
        else if (HANDBALL.equals(gameName)) {
            handballResultService.calculateResult(inputData);
        }
        else {
            throw new IncorrectFormatException(
                    String.format("Incorrect game name %s", gameName));
        }
    }
}
