package com.example.mvt_tracker.service.impl;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.model.Player;
import com.example.mvt_tracker.service.MVPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class MVPServiceImpl implements MVPService {
    private final PlayerDao playerDao;

    public MVPServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player getMVP() {
        List<Player> players = playerDao.getAllPlayers();
        Player mvp = calculateMVP(players);

        if (mvp == null) {
            log.error("No MVP player found");
            throw new IllegalStateException("No MVP player found.");
        }

        return mvp;
    }

    private Player calculateMVP(List<Player> players) {
        return players.stream()
                .max(Comparator.comparingInt(Player::getRatingPoints))
                .orElse(null);
    }
}
