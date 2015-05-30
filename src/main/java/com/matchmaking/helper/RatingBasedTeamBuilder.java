package com.matchmaking.helper;


import com.matchmaking.model.Match;
import com.matchmaking.model.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RatingBasedTeamBuilder extends AbstractTeamBuilder {

    /**
     * <p>
     * Split the players into two teams with the most balanced total rating for each team.
     * </p>
     */
    protected Match buildMatch(List<Player> players) {
        Set<Player> team1 = new HashSet<>();
        Set<Player> team2 = new HashSet<>();
        double ratingSum1 = 0;
        double ratingSum2 = 0;
        int totalSize = players.size();
        int teamSize = totalSize / 2;

        List<Player> sortedPlayers = players.stream()
                .sorted((player1, player2) -> Double.compare(player2.getRating(), player1.getRating()))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            if (teamSize == team1.size()) {
                team2.addAll(sortedPlayers.subList(i, totalSize));
                break;
            }
            if (teamSize == team2.size()) {
                team1.addAll(sortedPlayers.subList(i, totalSize));
                break;
            }
            if (ratingSum1 <= ratingSum2) {
                team1.add(player);
                ratingSum1 += player.getRating();
            } else {
                team2.add(player);
                ratingSum2 += player.getRating();
            }
        }
        return new Match(team1, team2);
    }

}
