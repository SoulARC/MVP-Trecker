package com.example.mvt_tracker.service;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.service.impl.GameResultProcessorImpl;
import com.example.mvt_tracker.util.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class GameResultProcessorImplTest {
    @Mock
    private CSVReader csvReader;

    @Mock
    private ResultService resultService;

    private GameResultProcessorImpl gameResultProcessor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        List<ResultService> resultServices = Collections.singletonList(resultService);
        gameResultProcessor = new GameResultProcessorImpl(csvReader, resultServices);
    }

    @Test(expected = IncorrectFormatException.class)
    public void gameStrategyInvalidGameName_noyOk() throws IncorrectFormatException {
        String filePath = "file.csv";
        String invalidGameName = "InvalidGame";
        String[][] inputData = {
                { invalidGameName },
                { "data1", "data2" },
                { "data3", "data4" }
        };

        when(csvReader.readData(filePath)).thenReturn(new ArrayList<>(Arrays.asList(inputData)));

        gameResultProcessor.gameStrategy(filePath);
    }

    @Test(expected = IllegalStateException.class)
    public void noResultServiceImplementations_notOk() {
        List<ResultService> emptyResultServices = Collections.emptyList();
        GameResultProcessorImpl processor = new GameResultProcessorImpl(csvReader, emptyResultServices);
        processor.initialize();
    }
}
