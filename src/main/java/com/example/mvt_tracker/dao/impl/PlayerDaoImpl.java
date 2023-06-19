package com.example.mvt_tracker.dao.impl;

import com.example.mvt_tracker.dao.PlayerDao;
import com.example.mvt_tracker.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class PlayerDaoImpl implements PlayerDao {
    private final Map<String, Player> players = new HashMap<>();

    @Override
    public void save(String nickname, Player player) {
        players.put(nickname, player);
        log.info("Player {} is added", nickname);
    }

    @Override
    public Player findPlayerByNickname(String nickname) {
        return players.get(nickname);
    }

    @Override
    public void editRatingPointsByNickname(String nickname, int ratingPoints) {
        Player player = players.get(nickname);
        if (player != null) {
            player.setRatingPoints(ratingPoints);
        }
        else {
            throw new IllegalArgumentException("Player not found with nickname: " + nickname);
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        return players.values().stream().toList();
    }

    @Override
    public void deleteAll() {
        players.clear();
    }
}
