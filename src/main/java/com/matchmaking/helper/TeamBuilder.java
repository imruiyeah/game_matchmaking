package com.matchmaking.helper;

import com.matchmaking.model.Match;
import com.matchmaking.model.Player;

import java.util.List;

public interface TeamBuilder {
    /**
     * <p>
     * Split a list of players into a match of two teams.
     * Return null if the number of players is odd.
     * </p>
     */
    Match splitPlayersIntoMatch(List<Player> players);

}
