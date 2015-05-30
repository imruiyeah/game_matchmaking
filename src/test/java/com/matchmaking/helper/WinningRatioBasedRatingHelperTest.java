package com.matchmaking.helper;

import com.matchmaking.helper.WinningRatioBasedRatingHelper;
import com.matchmaking.model.Match;
import com.matchmaking.model.MatchResult;
import com.matchmaking.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;

public class WinningRatioBasedRatingHelperTest {

    WinningRatioBasedRatingHelper ratingHelper;

    @Before
    public void before(){
        ratingHelper = new WinningRatioBasedRatingHelper(0.5);
    }

    @Test
    public void testInitRatingShouldCalculateTheCorrectInitialRating() throws Exception {
        Player player = new Player("a player", 40, 60, 0);
        ratingHelper.initRating(player);
        assertEquals("Should set the player's rating to winning ratio", 0.4, player.getRating());

        player = new Player("a player", 30, 70, 0);
        ratingHelper.initRating(player);
        assertEquals("Should set the player's rating to winning ratio", 0.3, player.getRating());

        player = new Player("a player", 30, -70, 0);
        ratingHelper.initRating(player);
        assertEquals("Should set the player's rating to default one if loss is invalid", 0.5, player.getRating());

        player = new Player("a player", -30, 70, 0);
        ratingHelper.initRating(player);
        assertEquals("Should set the player's rating to default one if win is invalid", 0.5, player.getRating());
    }

    @Test
    public void testShouldUpdateRatingAfterMatch() throws Exception {
        Player player1 = new Player("player1", 89, 10, 0.899);
        Player player2 = new Player("player2", 79, 20, 0.798);
        Player player3 = new Player("player3", 50, 49, 0.505);
        Player player4 = new Player("player4", 60, 39, 0.606);

        Match match = new Match(new HashSet<>(Arrays.asList(player1, player2)), new HashSet<>(Arrays.asList(player3, player4)));
        match.setResult(MatchResult.WON);
        ratingHelper.updateRatingAfterMatch(match);

        assertEquals("Should update player1's rating", 0.9, player1.getRating());
        assertEquals("Should update player2's rating", 0.8, player2.getRating());
        assertEquals("Should update player3's rating", 0.5, player3.getRating());
        assertEquals("Should update player4's rating", 0.6, player4.getRating());
    }
}