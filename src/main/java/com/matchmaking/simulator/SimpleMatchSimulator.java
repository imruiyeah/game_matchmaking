package com.matchmaking.simulator;

import com.matchmaking.model.Match;
import com.matchmaking.model.MatchResult;
import com.matchmaking.model.Player;

import java.util.Random;
import java.util.Set;

/**
 * <p>
 * Generate simple match result based on the average player ratings
 * </p>
 */
public class SimpleMatchSimulator implements MatchSimulator {

    private double winningOffset;
    private int winOddAboveOffset;
    private int winOddWithinOffset;
    private int winOddBelowOffset;

    public SimpleMatchSimulator(double winningOffset, int winOddAboveOffset, int winOddWithinOffset, int winOddBelowOffset) {
        this.winningOffset = winningOffset;
        this.winOddAboveOffset = winOddAboveOffset;
        this.winOddWithinOffset = winOddWithinOffset;
        this.winOddBelowOffset = winOddBelowOffset;
    }

    public void predictMatchResult(Match match) {
        double team1Rating = getTeamTotalRating(match.getTeam1());
        double team2Rating = getTeamTotalRating(match.getTeam2());

        if (team1Rating > team2Rating * (1 + winningOffset)) {
            match.setResult(generateResult(winOddAboveOffset));
        } else if (team1Rating < team2Rating * (1 - winningOffset)) {
            match.setResult(generateResult(winOddBelowOffset));
        } else {
            match.setResult(generateResult(winOddWithinOffset));
        }
    }

    private double getTeamTotalRating(Set<Player> team) {
        return team.stream().mapToDouble(Player::getRating).sum();
    }

    private MatchResult generateResult(int oddToWin) {
        Random random = new Random();
        int result = random.nextInt(100);
        return result <= oddToWin ? MatchResult.WON : MatchResult.LOST;
    }
}
