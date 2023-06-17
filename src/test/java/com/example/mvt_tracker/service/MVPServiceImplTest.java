package com.example.mvt_tracker.service;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.model.Player;
import com.example.mvt_tracker.service.impl.MVPServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MVPServiceImplTest {
    @Test
    public void testGetMVP_ok() {
        Player mvpPlayer = new Player("John", 100);
        List<Player> players = new ArrayList<>();
        players.add(new Player("Alice", 80));
        players.add(mvpPlayer);
        players.add(new Player("Bob", 90));

        PlayerDao playerDao = Mockito.mock(PlayerDao.class);
        Mockito.when(playerDao.getAllPlayers()).thenReturn(players);

        MVPService mvpService = new MVPServiceImpl(playerDao);

        Player result = mvpService.getMVP();

        Assertions.assertEquals(mvpPlayer, result);
    }

    @Test
    public void testGetMVPWhenPlayersExist_ok() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("John", 100));
        players.add(new Player("Mike", 150));
        players.add(new Player("Sarah", 120));

        PlayerDao playerDao = Mockito.mock(PlayerDao.class);
        Mockito.when(playerDao.getAllPlayers()).thenReturn(players);

        MVPService mvpService = new MVPServiceImpl(playerDao);

        Player result = mvpService.getMVP();
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mike", result.getNickname());
        Assertions.assertEquals(150, result.getRatingPoints());
    }

    @Test
    public void testGetMVPWhenNoPlayers_notOk() {
        List<Player> players = new ArrayList<>();

        PlayerDao playerDao = Mockito.mock(PlayerDao.class);
        Mockito.when(playerDao.getAllPlayers()).thenReturn(players);

        MVPService mvpService = new MVPServiceImpl(playerDao);

        Assertions.assertThrows(IllegalStateException.class, mvpService::getMVP);
    }
}
