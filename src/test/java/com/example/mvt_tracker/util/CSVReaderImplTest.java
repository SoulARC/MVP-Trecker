package com.example.mvt_tracker.util;

import com.example.mvt_tracker.util.impl.CSVReaderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVReaderImplTest {

    private CSVReaderImpl csvReader;
    private String filePath;

    @BeforeEach
    public void setUp() {
        csvReader = new CSVReaderImpl();
        filePath = "CSVReaderTest.csv";
    }

    @Test
    public void readValidFile_ok() throws IOException {
        createCSVFile(filePath, "player 1;nick1;4;Team A;10;2;7\nplayer 2;nick2;8;Team A;0;10;0");

        List<String[]> data = csvReader.readData(filePath);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(2, data.size());
    }

    @Test
    public void readEmptyFile_ok() throws IOException {
        createCSVFile(filePath, "");

        List<String[]> data = csvReader.readData(filePath);

        Assertions.assertNotNull(data);
        Assertions.assertTrue(data.isEmpty());
    }

    @Test
    public void readHandballFile_ok() throws IOException {
        String content = "HANDBALL\n" +
                "player 1;nick1;4;Team A;0;20\n" +
                "player 2;nick2;8;Team A;15;20\n" +
                "player 3;nick3;15;Team A;10;20";

        createCSVFile(filePath, content);

        List<String[]> data = csvReader.readData(filePath);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(4, data.size());
        Assertions.assertEquals("HANDBALL", data.get(0)[0]);
        Assertions.assertEquals("player 1", data.get(1)[0]);
        Assertions.assertEquals("nick1", data.get(1)[1]);
        Assertions.assertEquals("4", data.get(1)[2]);
        Assertions.assertEquals("Team A", data.get(1)[3]);
        Assertions.assertEquals("0", data.get(1)[4]);
        Assertions.assertEquals("20", data.get(1)[5]);
    }

    @Test
    public void readBasketballFile_ok() throws IOException {
        String content = "BASKETBALL\n" +
                "player 1;nick1;4;Team A;10;2;7\n" +
                "player 2;nick2;8;Team A;0;10;0";

        createCSVFile(filePath, content);

        List<String[]> data = csvReader.readData(filePath);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(3, data.size());
        Assertions.assertEquals("BASKETBALL", data.get(0)[0]);
        Assertions.assertEquals("player 1", data.get(1)[0]);
        Assertions.assertEquals("nick1", data.get(1)[1]);
        Assertions.assertEquals("4", data.get(1)[2]);
        Assertions.assertEquals("Team A", data.get(1)[3]);
        Assertions.assertEquals("10", data.get(1)[4]);
        Assertions.assertEquals("2", data.get(1)[5]);
        Assertions.assertEquals("7", data.get(1)[6]);
    }

    private void createCSVFile(String filePath, String content) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
    }
}
