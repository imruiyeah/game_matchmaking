package com.matchmaking.helper;


import com.matchmaking.model.Match;
import com.matchmaking.model.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PositionBasedTeamBuilder extends AbstractTeamBuilder {

    /**
     * <p>
     * Split the players into two teams based on their ratings such that:
     * the one with the highest rating and the one with the lowest rating are in the same group
     * the one with the second-highest rating and the one with the second-lowest rating are in the same group
     * etc.
     * </p>
     */
    public Match buildMatch(List<Player> players) {
        List<Player> sortedPlayers = players.stream()
                .sorted((player1, player2) -> Double.compare(player1.getRating(), player2.getRating()))
                .collect(Collectors.toList());

        Set<Player> team1 = new HashSet<>();
        Set<Player> team2 = new HashSet<>();
        int teamSize = players.size() / 2;
        boolean isOddTeam = 1 == teamSize % 2;
        for (int i = 0; i < teamSize; i++) {
            if (isOddTeam && i == teamSize - 1) {
                addHeadAndTail(sortedPlayers, i, team1, team2);
            } else if (0 == i % 2) {
                addHeadAndTail(sortedPlayers, i, team1, team1);
            } else {
                addHeadAndTail(sortedPlayers, i, team2, team2);
            }
        }
        return new Match(team1, team2);
    }

    private void addHeadAndTail(List<Player> players, int currentIndex, Set<Player> teamForHead, Set<Player> teamForTail) {
        teamForHead.add(players.get(currentIndex));
        teamForTail.add(players.get(players.size() - currentIndex - 1));
    }

}
