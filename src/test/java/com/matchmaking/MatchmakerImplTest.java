package com.matchmaking;

import com.matchmaking.MatchmakerImpl;
import com.matchmaking.helper.PlayerFinder;
import com.matchmaking.helper.TeamBuilder;
import com.matchmaking.model.Match;
import com.matchmaking.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MatchmakerImplTest {

    MatchmakerImpl matchmaker;
    PlayerFinder playerFinder;
    TeamBuilder teamBuilder;


    @Before
    public void before() {
        playerFinder = mock(PlayerFinder.class);
        teamBuilder = mock(TeamBuilder.class);
        matchmaker = new MatchmakerImpl(playerFinder, teamBuilder);
        assertEquals("Should not have any players at first", 0, matchmaker.getPlayers().size());
    }

    @Test
    public void testEnterMatchmakingShouldAddPlayerToThePlayerList() throws Exception {
        Player player1 = new Player("a player", 50, 50, 0.5);
        matchmaker.enterMatchmaking(player1);
        assertEquals("Should have only 1 player in the list", 1, matchmaker.getPlayers().size());
        assertTrue("Should have the player who just entered", matchmaker.getPlayers().contains(player1));

        Player player2 = new Player("another player", 40, 60, 0.4);
        matchmaker.enterMatchmaking(player2);
        assertEquals("Should have 2 players in the list", 2, matchmaker.getPlayers().size());
        assertTrue("Should still have the old player", matchmaker.getPlayers().contains(player1));
        assertTrue("Should have the player who just entered", matchmaker.getPlayers().contains(player2));
    }

    @Test
    public void testEnterMatchmakingShouldNotAddPlayerToThePlayerListIfHeAlreadyEntered() throws Exception {
        Player player1 = new Player("a player", 50, 50, 0.5);
        matchmaker.enterMatchmaking(player1);
        matchmaker.enterMatchmaking(player1);
        assertEquals("Should still have only 1 player in the list", 1, matchmaker.getPlayers().size());
        assertTrue("Should still have the old player", matchmaker.getPlayers().contains(player1));
    }

    @Test
    public void testFindMatchShouldReturnNullIfThereAreNotEnoughPlayers() throws Exception {
        matchmaker.enterMatchmaking(new Player("player1", 50, 50, 0.5));
        matchmaker.enterMatchmaking(new Player("player2", 50, 50, 0.5));
        matchmaker.enterMatchmaking(new Player("player3", 50, 50, 0.5));
        matchmaker.enterMatchmaking(new Player("player4", 50, 50, 0.5));
        matchmaker.enterMatchmaking(new Player("player5", 50, 50, 0.5));
        assertEquals("Should return null when there are not enough players", null, matchmaker.findMatch(3));
        verify(playerFinder, never()).findSimilarPlayers(Matchers.any(Player.class), Matchers.anyListOf(Player.class), Matchers.anyInt());
        verify(teamBuilder, never()).splitPlayersIntoMatch(Matchers.anyListOf(Player.class));
    }

    @Test
    public void testFindMatchShouldReturnNullIfThereAreNotEnoughSimilarPlayers() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", 50, 50, 0.5));
        players.add(new Player("player2", 50, 50, 0.5));
        players.add(new Player("player3", 50, 50, 0.5));
        players.add(new Player("player4", 50, 50, 0.5));
        players.add(new Player("player5", 50, 50, 0.5));
        players.add(new Player("player6", 50, 50, 0.5));
        players.add(new Player("player7", 50, 50, 0.5));
        players.add(new Player("player8", 50, 50, 0.5));
        players.add(new Player("player9", 50, 50, 0.5));
        players.forEach(matchmaker::enterMatchmaking);

        // only return 4 similar players, which is not enough for a match
        when(playerFinder.findSimilarPlayers(players.get(0), players.subList(1, players.size() - 1), 5)).thenReturn(players.subList(1, 5));
        assertEquals("Should return null when there are not enough players", null, matchmaker.findMatch(3));

        List<Player> actualPlayers = matchmaker.getPlayers();
        int size = players.size();
        assertEquals("Should have the same number of players in the list", size, actualPlayers.size());
        assertEquals("Should move the first player to the end of the list", players.get(0), actualPlayers.get(size - 1));
        assertEquals("Should shift the rest of players forward", players.subList(1, size), actualPlayers.subList(0, size - 1));
    }

    @Test
    public void testFindMatchShouldReturnAMatchIfSimilarPlayersCanBeFound() throws Exception {
        List<Player> players = new ArrayList<>();
        Player player0 = new Player("player0", 50, 50, 0.5);
        Player player1 = new Player("player1", 50, 50, 0.5);
        Player player2 = new Player("player2", 50, 50, 0.5);
        Player player3 = new Player("player3", 50, 50, 0.5);
        Player player4 = new Player("player3", 50, 50, 0.5);
        players.add(player0);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.forEach(matchmaker::enterMatchmaking);

        List<Player> similarPlayers = players.subList(1, 4);
        Set<Player> team1 = new HashSet<>(Arrays.asList(player0, player1));
        Set<Player> team2 = new HashSet<>(Arrays.asList(player2, player3));
        Match resultedMatch = new Match(team1, team2);

        when(playerFinder.findSimilarPlayers(player0, Arrays.asList(player1, player2, player3, player4), 3)).thenReturn(similarPlayers);
        when(teamBuilder.splitPlayersIntoMatch(similarPlayers)).thenReturn(resultedMatch);
        assertEquals("Should return a match when there are enough similar players", resultedMatch, matchmaker.findMatch(2));

        List<Player> actualPlayers = matchmaker.getPlayers();
        assertEquals("Should remove the matched players and have only 1 player in the list", 1, actualPlayers.size());
        assertTrue("Should have player4 in the list", actualPlayers.contains(player4));
    }

}