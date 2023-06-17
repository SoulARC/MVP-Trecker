package com.example.mvt_tracker.dao;

import com.example.mvt_tracker.model.Player;

import java.util.List;

public interface PlayerDao {
    void save(String nickname, Player player);

    Player findPlayerByNickname(String nickname);

    void editRatingPointsByNickname(String nickname, int ratingPoints);

    List<Player> getAllPlayers();

    void deleteAll();
}
