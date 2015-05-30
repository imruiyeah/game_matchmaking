package com.matchmaking.helper;

import com.matchmaking.model.Player;

import java.util.List;

public interface PlayerFinder {

    /**
     * <p>
     * Find a number of players from the pool who are similar to the given player.
     * </p>
     *
     * @param currentPlayer   a given player.
     * @param playerPool      a pool of players to search from.
     * @param numberOfPlayers the number of players needed to be found from the pool.
     * @return a list of similar players.
     */
    List<Player> findSimilarPlayers(Player currentPlayer, List<Player> playerPool, int numberOfPlayers);

}
