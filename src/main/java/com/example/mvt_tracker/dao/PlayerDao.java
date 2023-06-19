package com.example.mvt_tracker.dao;

import com.example.mvt_tracker.model.Player;

import java.util.List;

public interface PlayerDao {
    Player findPlayerByNickname(String nickname);

    List<Player> getAllPlayers();

    void deleteAll();

    void saveOrUpdatePlayer(Player player);
}
