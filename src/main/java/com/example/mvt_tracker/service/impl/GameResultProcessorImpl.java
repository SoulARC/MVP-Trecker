package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.service.GameResultProcessor;
import com.example.mvt_tracker.service.ResultService;
import com.example.mvt_tracker.service.enums.Games;
import com.example.mvt_tracker.util.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class GameResultProcessorImpl implements GameResultProcessor {
    private static final int GAME_NAME_INDEX = 0;
    private final CSVReader csvReader;
    private final Map<String, ResultService> resultServiceMap;

    public GameResultProcessorImpl(
            CSVReader csvReader,
            @Qualifier("basketballResultServiceImpl") ResultService basketballResultService,
            @Qualifier("handballResultServiceImpl") ResultService handballResultService
    ) {
        this.csvReader = csvReader;
        this.resultServiceMap = new HashMap<>();
        resultServiceMap.put(Games.BASKETBALL.name(), basketballResultService);
        resultServiceMap.put(Games.HANDBALL.name(), handballResultService);
    }

    @Override
    public void gameStrategy(String filePath) throws IncorrectFormatException {
        List<String[]> inputData = csvReader.readData(filePath);
        String gameName = inputData.get(GAME_NAME_INDEX)[GAME_NAME_INDEX];
        inputData.remove(GAME_NAME_INDEX);
        ResultService resultService = resultServiceMap.get(gameName);
        if (resultService != null) {
            resultService.calculateResult(inputData);
        }
        else {
            String errorMessage = String.format("Incorrect game name: %s", gameName);
            log.error(errorMessage);
            throw new IncorrectFormatException(errorMessage);
        }
    }
}
