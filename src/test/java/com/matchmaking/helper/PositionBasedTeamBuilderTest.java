package com.matchmaking.helper;

import com.matchmaking.helper.PositionBasedTeamBuilder;
import com.matchmaking.model.Match;
import com.matchmaking.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class PositionBasedTeamBuilderTest extends  AbstractTeamBuilderTestCase {

    protected void initBuilder() {
        teamBuilder = new PositionBasedTeamBuilder();
    }

    @Test
    public void testShouldSplitPlayersIntoMatchWhenEachTeamHasOddPlayers() throws Exception {
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Player0", 961, 658, 0.5936));
        players.add(new Player("Player1", 942, 951, 0.4976));
        players.add(new Player("Player2", 675, 546, 0.5528));
        players.add(new Player("Player3", 754, 378, 0.6661));
        players.add(new Player("Player4", 647, 310, 0.6761));
        players.add(new Player("Player5", 656, 544, 0.5467));

        Match match = teamBuilder.splitPlayersIntoMatch(players);
        Set<Player> team1 = match.getTeam1();
        assertEquals("team1 should have 3 players", 3, team1.size());
        assertTrue("player1 should be in team1", team1.contains(players.get(1)));
        assertTrue("player2 should be in team1", team1.contains(players.get(2)));
        assertTrue("player4 should be in team1", team1.contains(players.get(4)));

        Set<Player> team2 = match.getTeam2();
        assertEquals("team2 should have 3 players", 3, team2.size());
        assertTrue("player0 should be in team2", team2.contains(players.get(0)));
        assertTrue("player3 should be in team2", team2.contains(players.get(3)));
        assertTrue("player5 should be in team2", team2.contains(players.get(5)));
    }

    @Test
    public void testShouldSplitPlayersIntoMatchWhenEachTeamHasEvenPlayers() throws Exception {
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Player0", 961, 658, 0.5936));
        players.add(new Player("Player1", 942, 951, 0.4976));
        players.add(new Player("Player2", 675, 546, 0.5528));
        players.add(new Player("Player3", 754, 378, 0.6661));
        players.add(new Player("Player4", 647, 310, 0.6761));
        players.add(new Player("Player5", 656, 544, 0.5467));
        players.add(new Player("Player6", 572, 596, 0.4897));
        players.add(new Player("Player7", 565, 241, 0.701));

        Match match = teamBuilder.splitPlayersIntoMatch(players);
        Set<Player> team1 = match.getTeam1();
        assertEquals("team1 should have 4 players", 4, team1.size());
        assertTrue("player3 should be in team2", team1.contains(players.get(3)));
        assertTrue("player5 should be in team2", team1.contains(players.get(5)));
        assertTrue("player6 should be in team2", team1.contains(players.get(6)));
        assertTrue("player7 should be in team2", team1.contains(players.get(7)));

        Set<Player> team2 = match.getTeam2();
        assertEquals("team2 should have 4 players", 4, team2.size());
        assertTrue("player0 should be in team1", team2.contains(players.get(0)));
        assertTrue("player1 should be in team1", team2.contains(players.get(1)));
        assertTrue("player2 should be in team1", team2.contains(players.get(2)));
        assertTrue("player4 should be in team1", team2.contains(players.get(4)));
    }

    @Test
    public void testShouldSplitPlayersIntoMatchWhenSomePlayersHaveTheSameRating() throws Exception {
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Player0", 754, 378, 0.6661));
        players.add(new Player("Player1", 942, 951, 0.4976));
        players.add(new Player("Player2", 675, 546, 0.5528));
        players.add(new Player("Player3", 754, 378, 0.6661));
        players.add(new Player("Player4", 647, 310, 0.6761));
        players.add(new Player("Player5", 656, 544, 0.5467));
        players.add(new Player("Player6", 572, 596, 0.4897));
        players.add(new Player("Player7", 565, 241, 0.701));

        Match match = teamBuilder.splitPlayersIntoMatch(players);
        Set<Player> team1 = match.getTeam1();
        assertEquals("team1 should have 4 players", 4, team1.size());
        assertTrue("player3 should be in team2", team1.contains(players.get(3)));
        assertTrue("player5 should be in team2", team1.contains(players.get(5)));
        assertTrue("player6 should be in team2", team1.contains(players.get(6)));
        assertTrue("player7 should be in team2", team1.contains(players.get(7)));

        Set<Player> team2 = match.getTeam2();
        assertEquals("team2 should have 4 players", 4, team2.size());
        assertTrue("player0 should be in team1", team2.contains(players.get(0)));
        assertTrue("player1 should be in team1", team2.contains(players.get(1)));
        assertTrue("player2 should be in team1", team2.contains(players.get(2)));
        assertTrue("player4 should be in team1", team2.contains(players.get(4)));
    }

}