package com.matchmaking.model;

import java.util.Set;

public class Match {

    private final Set<Player> team1;
    private final Set<Player> team2;
    private MatchResult result;

    public Match(Set<Player> team1, Set<Player> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public Set<Player> getTeam1() {
        return team1;
    }

    public Set<Player> getTeam2() {
        return team2;
    }

    /**
     * <p>
     * The result of a match. WON means team1 won. LOST means team1 lost.
     * </p>
     */
    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

}
