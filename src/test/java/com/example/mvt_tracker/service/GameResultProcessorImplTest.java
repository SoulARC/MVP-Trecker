package com.example.mvt_tracker.service;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.service.impl.GameResultProcessorImpl;
import com.example.mvt_tracker.util.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

public class GameResultProcessorImplTest {
    private static final String TEST_FILE_PATH = "CSVReaderTest.csv";

    @Test
    public void testGameStrategyWhenGameNameIsBasketball_ok() throws IncorrectFormatException {
        CSVReader csvReader = Mockito.mock(CSVReader.class);
        BasketballResultService basketballResultService = Mockito.mock(BasketballResultService.class);
        HandballResultService handballResultService = Mockito.mock(HandballResultService.class);

        GameResultProcessorImpl gameResultProcessor = new GameResultProcessorImpl(
                csvReader, basketballResultService, handballResultService);

        List<String[]> inputData = new ArrayList<>();
        String[] gameInfo = { "BASKETBALL" };
        inputData.add(gameInfo);

        Mockito.when(csvReader.readData(anyString())).thenReturn(inputData);

        gameResultProcessor.gameStrategy(TEST_FILE_PATH);

        Mockito.verify(basketballResultService, Mockito.times(1)).calculateResult(Mockito.anyList());
        Mockito.verify(handballResultService, Mockito.times(0)).calculateResult(Mockito.anyList());
    }

    @Test
    public void testGameStrategyWhenGameNameIsHandball_ok() throws IncorrectFormatException {
        CSVReader csvReader = Mockito.mock(CSVReader.class);
        BasketballResultService basketballResultService = Mockito.mock(BasketballResultService.class);
        HandballResultService handballResultService = Mockito.mock(HandballResultService.class);

        GameResultProcessorImpl gameResultProcessor = new GameResultProcessorImpl(
                csvReader, basketballResultService, handballResultService);

        List<String[]> inputData = new ArrayList<>();
        String[] gameInfo = { "HANDBALL" };
        inputData.add(gameInfo);

        Mockito.when(csvReader.readData(anyString())).thenReturn(inputData);

        gameResultProcessor.gameStrategy(TEST_FILE_PATH);

        Mockito.verify(basketballResultService, Mockito.times(0)).calculateResult(Mockito.anyList());
        Mockito.verify(handballResultService, Mockito.times(1)).calculateResult(Mockito.anyList());
    }

    @Test
    public void testGameStrategyWhenGameNameIsInvalid_notOk() {
        CSVReader csvReader = Mockito.mock(CSVReader.class);
        BasketballResultService basketballResultService = Mockito.mock(BasketballResultService.class);
        HandballResultService handballResultService = Mockito.mock(HandballResultService.class);

        GameResultProcessorImpl gameResultProcessor = new GameResultProcessorImpl(
                csvReader, basketballResultService, handballResultService);

        List<String[]> inputData = new ArrayList<>();
        String[] gameInfo = { "invalidGame" };
        inputData.add(gameInfo);

        Mockito.when(csvReader.readData(anyString())).thenReturn(inputData);

        Assertions.assertThrows(IncorrectFormatException.class, () -> {
            gameResultProcessor.gameStrategy(TEST_FILE_PATH);
        });

        Mockito.verify(basketballResultService, Mockito.times(0)).calculateResult(Mockito.anyList());
        Mockito.verify(handballResultService, Mockito.times(0)).calculateResult(Mockito.anyList());
    }
}
