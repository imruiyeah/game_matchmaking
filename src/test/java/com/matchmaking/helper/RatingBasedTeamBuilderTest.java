package com.matchmaking.helper;

import com.matchmaking.helper.RatingBasedTeamBuilder;
import com.matchmaking.model.Match;
import com.matchmaking.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RatingBasedTeamBuilderTest {

    RatingBasedTeamBuilder teamBuilder;

    @Before
    public void before(){
        teamBuilder = new RatingBasedTeamBuilder();
    }

    @Test
    public void testShouldSplitPlayersIntoMatchWhenEachTeamHasOddPlayers() throws Exception {
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Player0", 80, 20, 0.8));
        players.add(new Player("Player1", 40, 60, 0.4));
        players.add(new Player("Player2", 20, 80, 0.2));
        players.add(new Player("Player3", 25, 75, 0.25));
        players.add(new Player("Player4", 15, 85, 0.15));
        players.add(new Player("Player5", 30, 70, 0.3));

        Match match = teamBuilder.splitPlayersIntoMatch(players);

        Set<Player> team1 = match.getTeam1();
        assertEquals("team1 should have 3 players", 3, team1.size());
        assertTrue("player0 should be in team1", team1.contains(players.get(0)));
        assertTrue("player2 should be in team1", team1.contains(players.get(2)));
        assertTrue("player4 should be in team1", team1.contains(players.get(4)));

        Set<Player> team2 = match.getTeam2();
        assertEquals("team2 should have 3 players", 3, team2.size());
        assertTrue("player1 should be in team2", team2.contains(players.get(1)));
        assertTrue("player3 should be in team2", team2.contains(players.get(3)));
        assertTrue("player5 should be in team2", team2.contains(players.get(5)));
    }

}