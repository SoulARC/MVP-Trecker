package com.example.mvt_tracker.util;

import java.util.List;

public interface CSVReader {
    List<String[]> readData(String filePath);
}
