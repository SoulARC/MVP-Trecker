package com.example.mvt_tracker.service;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.service.enums.Games;
import com.example.mvt_tracker.service.impl.GameResultProcessorImpl;
import com.example.mvt_tracker.util.CSVReader;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameResultProcessorImplTest {

    @Mock
    private CSVReader csvReader;

    @Mock
    private ResultService basketballResultService;

    @Mock
    private ResultService handballResultService;

    private final GameResultProcessorImpl gameResultProcessor;

    public GameResultProcessorImplTest() {
        MockitoAnnotations.openMocks(this);
        gameResultProcessor = new GameResultProcessorImpl(csvReader, basketballResultService, handballResultService);
    }

    @Test
    void gameStrategyHasValidGameName_ok() throws IncorrectFormatException {
        String filePath = "data.csv";
        String gameName = Games.BASKETBALL.name();
        String[] gameNameRow = {gameName};
        when(csvReader.readData(filePath)).thenReturn( new ArrayList<>(Collections.singleton(gameNameRow)));

        gameResultProcessor.gameStrategy(filePath);

        verify(basketballResultService, times(1)).calculateResult(anyList());
        verify(handballResultService, never()).calculateResult(anyList());
    }

    @Test
    void gameStrategyHasInvalidGameName_notOk() {
        String filePath = "data.csv";
        String gameName = "InvalidGame";
        String[] gameNameRow = {gameName};
        when(csvReader.readData(filePath)).thenReturn( new ArrayList<>(Collections.singleton(gameNameRow)));

        assertThrows(IncorrectFormatException.class, () -> gameResultProcessor.gameStrategy(filePath));

        verify(basketballResultService, never()).calculateResult(anyList());
        verify(handballResultService, never()).calculateResult(anyList());
    }
}
