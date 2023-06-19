package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.model.Player;
import com.example.mvt_tracker.service.MVPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class MVPServiceImpl implements MVPService {
    private final PlayerDao playerDao;
    private static final String ERROR_MESSAGE = "No MVP player found";

    public MVPServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player getMVP() {
        List<Player> players = playerDao.getAllPlayers();
        Player mvp = calculateMVP(players);

        if (mvp == null) {
            log.error(ERROR_MESSAGE);
            throw new NoSuchElementException(ERROR_MESSAGE);
        }

        return mvp;
    }

    private Player calculateMVP(List<Player> players) {
        return players.stream()
                .max(Comparator.comparingInt(Player::getRatingPoints))
                .orElse(null);
    }
}
