package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.service.GameResultProcessor;
import com.example.mvt_tracker.service.ResultService;
import com.example.mvt_tracker.util.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameResultProcessorImpl implements GameResultProcessor {
    private static final int GAME_NAME_INDEX = 0;
    private final CSVReader csvReader;
    private final Map<String, ResultService> resultServiceMap;

    public GameResultProcessorImpl(
            CSVReader csvReader,
            List<ResultService> resultServices
    ) {
        this.csvReader = csvReader;
        this.resultServiceMap = resultServices.stream()
                .collect(Collectors.toMap(ResultService::getGame, Function.identity()));
    }

    @PostConstruct
    public void initialize() {
        if (resultServiceMap.isEmpty()) {
            throw new IllegalStateException("No ResultService implementations found");
        }
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
