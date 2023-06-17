package com.example.mvt_tracker.util.impl;

import com.example.mvt_tracker.util.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CSVReaderImpl implements CSVReader {
    private static final String SEPARATOR = ";";

    @Override
    public List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(SEPARATOR);
                data.add(fields);
            }
        } catch (IOException e) {
            log.error("Error reading data from file: {}", filePath, e);
        }

        return data;
    }
}
