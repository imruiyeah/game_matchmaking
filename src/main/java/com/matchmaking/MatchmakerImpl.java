package com.matchmaking;

import com.matchmaking.helper.PlayerFinder;
import com.matchmaking.helper.TeamBuilder;
import com.matchmaking.model.Match;
import com.matchmaking.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The matchmaking implementation that you will write.
 */
public class MatchmakerImpl implements Matchmaker {

    private PlayerFinder playerFinder;
    private TeamBuilder matchBuilder;
    private List<Player> players;

    public MatchmakerImpl(PlayerFinder playerFinder, TeamBuilder matchBuilder) {
        players = new ArrayList<Player>();
        this.playerFinder = playerFinder;
        this.matchBuilder = matchBuilder;
    }

    public Match findMatch(int playersPerTeam) {
        int playersRequired = 2 * playersPerTeam;
        int playerSize = players.size();
        if (playerSize < playersRequired) {
            return null;
        }
        Player firstPlayer = players.remove(0);
        List<Player> selectedPlayers = playerFinder.findSimilarPlayers(firstPlayer, players, playersRequired - 1);
        if (selectedPlayers == null || selectedPlayers.size() != playersRequired - 1) {
            // Add the player back to the end of the waiting list.
            // this can be enhanced to have more robust retrying logic to optimize the waiting time.
            players.add(firstPlayer);
            return null;
        }
        players.removeAll(selectedPlayers);
        selectedPlayers.add(firstPlayer);
        return matchBuilder.splitPlayersIntoMatch(selectedPlayers);
    }

    public void enterMatchmaking(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

}
