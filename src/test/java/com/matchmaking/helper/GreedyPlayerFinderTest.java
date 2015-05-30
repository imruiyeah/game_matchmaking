package com.matchmaking.helper;

import com.matchmaking.helper.GreedyPlayerFinder;
import com.matchmaking.model.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GreedyPlayerFinderTest {

    GreedyPlayerFinder playerFinder;
    static List<Player> playersPool;

    @BeforeClass
    public static void beforeClass() {
        playersPool = new ArrayList<Player>();
        playersPool.add(new Player("Player0", 961, 658, 0.5936));
        playersPool.add(new Player("Player1", 852, 179, 0.8264));
        playersPool.add(new Player("Player2", 942, 951, 0.4976));
        playersPool.add(new Player("Player3", 675, 546, 0.5528));
        playersPool.add(new Player("Player4", 235, 8, 0.9671));
        playersPool.add(new Player("Player5", 754, 378, 0.6661));
        playersPool.add(new Player("Player6", 910, 258, 0.7791));
        playersPool.add(new Player("Player7", 647, 310, 0.6761));
        playersPool.add(new Player("Player8", 331, 599, 0.3559));
        playersPool.add(new Player("Player9", 212, 112, 0.6543));
    }

    @Test
    public void testShouldFindSimilarPlayersBasedOnOffset() throws Exception {
        Player player = new Player("new player", 820, 430, 0.656);
        playerFinder = new GreedyPlayerFinder(0.2);
        List<Player> similarPlayers = playerFinder.findSimilarPlayers(player, playersPool, 5);
        assertEquals("Should find 5 similar players", 5, similarPlayers.size());
        assertTrue("Should contain Player0 when offset is 0.2", similarPlayers.contains(playersPool.get(0)));
        assertTrue("Should contain Player3 when offset is 0.2", similarPlayers.contains(playersPool.get(3)));
        assertTrue("Should contain Player5 when offset is 0.2", similarPlayers.contains(playersPool.get(5)));
        assertTrue("Should contain Player6 when offset is 0.2", similarPlayers.contains(playersPool.get(6)));
        assertTrue("Should contain Player7 when offset is 0.2", similarPlayers.contains(playersPool.get(7)));

        playerFinder = new GreedyPlayerFinder(0.4);
        similarPlayers = playerFinder.findSimilarPlayers(player, playersPool, 5);
        assertEquals("Should find 5 similar players", 5, similarPlayers.size());
        assertTrue("Should contain Player0 when offset is 0.4", similarPlayers.contains(playersPool.get(0)));
        assertTrue("Should contain Player1 when offset is 0.4", similarPlayers.contains(playersPool.get(1)));
        assertTrue("Should contain Player2 when offset is 0.4", similarPlayers.contains(playersPool.get(2)));
        assertTrue("Should contain Player3 when offset is 0.4", similarPlayers.contains(playersPool.get(3)));
        assertTrue("Should contain Player5 when offset is 0.4", similarPlayers.contains(playersPool.get(5)));
    }

    @Test
    public void testShouldReturnRequiredNumberOfSimilarPlayers() throws Exception {
        Player player = new Player("new player", 820, 430, 0.656);
        playerFinder = new GreedyPlayerFinder(0.4);
        List<Player> similarPlayers = playerFinder.findSimilarPlayers(player, playersPool, 3);
        assertEquals("Should find 3 similar players", 3, similarPlayers.size());
        assertTrue("Should contain Player0", similarPlayers.contains(playersPool.get(0)));
        assertTrue("Should contain Player1", similarPlayers.contains(playersPool.get(1)));
        assertTrue("Should contain Player2", similarPlayers.contains(playersPool.get(2)));
    }

    @Test
    public void testShouldReturnEmptyListIfNoSimilarPlayersFound() throws Exception {
        Player player = new Player("new player", 100, 900, 0.1);
        playerFinder = new GreedyPlayerFinder(0.02);
        List<Player> similarPlayers = playerFinder.findSimilarPlayers(player, playersPool, 5);
        assertEquals("Should return empty list when no player qualifies the condition", 0, similarPlayers.size());
    }
}