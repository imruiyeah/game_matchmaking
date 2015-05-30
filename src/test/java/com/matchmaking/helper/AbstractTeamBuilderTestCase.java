package com.matchmaking.helper;

import com.matchmaking.helper.TeamBuilder;
import com.matchmaking.model.Match;
import com.matchmaking.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractTeamBuilderTestCase {

    TeamBuilder teamBuilder;

    @Before
    public void before(){
        initBuilder();
    }

    @Test
    public void testShouldReturnNullWhenPlayersIsNotValid() throws Exception {
        Match match = teamBuilder.splitPlayersIntoMatch(null);
        assertEquals("Should return null if players list is null", null, match);

        match = teamBuilder.splitPlayersIntoMatch(new ArrayList<Player>());
        assertEquals("Should return null if players list is empty", null, match);

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("player1", 40, 60, 0.4));
        players.add(new Player("player2", 50, 50, 0.5));
        players.add(new Player("player2", 10, 90, 0.1));
        match = teamBuilder.splitPlayersIntoMatch(players);
        assertEquals("Should return null if players list has odd number of players", null, match);
    }

    abstract protected void initBuilder();
}