package com.matchmaking.helper;

import com.matchmaking.model.Match;
import com.matchmaking.model.Player;

import java.util.Set;

import static com.matchmaking.model.MatchResult.WON;
import static java.lang.Math.round;

/*
 * This class is a simple implementation of a rating system which only looks at the winning ratio of a player.
 * However, in reality, more robust scoring mechanism should be used.
 * One example can be: http://en.wikipedia.org/wiki/Glicko_rating_system
 * or http://en.wikipedia.org/wiki/Elo_rating_system
 */
public class WinningRatioBasedRatingHelper implements RatingHelper {

    private double defaultRating;
    private static final double roundingFactor = 10000;

    public WinningRatioBasedRatingHelper(double defaultRating) {
        this.defaultRating = defaultRating;
    }

    public void initRating(Player player) {
        player.setRating(calculateWinningRatio(player));
    }

    public void
    updateRatingAfterMatch(Match match) {
        if (WON.equals(match.getResult())) {
            updateWinnersRating(match.getTeam1());
            updateLosersRating(match.getTeam2());
        } else {
            updateWinnersRating(match.getTeam2());
            updateLosersRating(match.getTeam1());
        }
    }

    private void updateWinnersRating(Iterable<Player> team) {
        team.forEach((player) -> {
            player.increaseWins();
            player.setRating(calculateWinningRatio(player));
        });
    }

    private void updateLosersRating(Set<Player> team) {
        team.forEach((player) -> {
            player.increaseLosses();
            player.setRating(calculateWinningRatio(player));
        });
    }

    private double calculateWinningRatio(Player player) {
        long losses = player.getLosses();
        long wins = player.getWins();
        long total = losses + wins;
        if (losses >= 0 && wins >= 0 && total > 0) {
            return round((double) wins / total * roundingFactor) / roundingFactor;
        } else {
            return defaultRating;
        }
    }
}
