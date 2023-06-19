package com.example.mvt_tracker;

import com.example.mvt_tracker.model.Player;
import com.example.mvt_tracker.service.GameResultProcessor;
import com.example.mvt_tracker.service.MVPService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MvtTrackerApplication implements CommandLineRunner {
    private final GameResultProcessor gameResultProcessor;
    private final MVPService mvpService;
    private static final String HANDBALL_PATH = "hball.csv";
    private static final String BASKETBALL_PATH = "bball.csv";

    public MvtTrackerApplication(GameResultProcessor gameResultProcessor, MVPService mvpService) {
        this.gameResultProcessor = gameResultProcessor;
        this.mvpService = mvpService;
    }
    public static void main(String[] args) {
        SpringApplication.run(MvtTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        gameResultProcessor.gameStrategy(HANDBALL_PATH);
        gameResultProcessor.gameStrategy(BASKETBALL_PATH);

        Player mvp = mvpService.getMVP();
        System.out.println("MVP: " + mvp.getNickname());
        System.out.println("SCORE: " + mvp.getRatingPoints());
    }
}









