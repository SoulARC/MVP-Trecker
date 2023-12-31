package com.example.mvt_tracker.service;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.service.impl.BasketballResultServiceImpl;
import org.junit.Before;
import org.junit.Test;


import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class BasketballResultServiceImplTest {
    private BasketballResultServiceImpl basketballResultService;

    @Before
   public void setUp() {

        PlayerDataValidator formatValidatorMock = mock(PlayerDataValidator.class);
        basketballResultService = new BasketballResultServiceImpl(
                mock(PlayerDao.class),
                formatValidatorMock
        );
    }

    @Test
    public void calculateTeamPoints_ok() {
        List<String[]> gameData = new ArrayList<>();
        gameData.add(new String[]{ "player 1", "nick1", "4", "Team A", "10", "2", "7" });
        gameData.add(new String[]{ "player 2", "nick2", "8", "Team A", "0", "10", "0" });
        gameData.add(new String[]{ "player 3", "nick3", "15", "Team A", "15", "10", "4" });
        gameData.add(new String[]{ "player 4", "nick4", "16", "Team B", "20", "0", "0" });
        gameData.add(new String[]{ "player 5", "nick5", "23", "Team B", "4", "7", "7" });
        gameData.add(new String[]{ "player 6", "nick6", "42", "Team B", "8", "10", "0" });

        Map<String, Integer> teamScores = basketballResultService.calculateTeamPoints(gameData);

        assertEquals(83, teamScores.get("Team A"));
        assertEquals(88, teamScores.get("Team B"));
    }

    @Test
    public void getWinningTeam_ok() {
        Map<String, Integer> teamScores = new HashMap<>();
        teamScores.put("Team A", 83);
        teamScores.put("Team B", 88);
        teamScores.put("Team C", 76);

        String winningTeam = basketballResultService.getWinningTeam(teamScores).get();

        assertEquals("Team B", winningTeam);
    }
}

