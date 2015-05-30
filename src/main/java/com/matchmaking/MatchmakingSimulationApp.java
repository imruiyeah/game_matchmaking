package com.matchmaking;

import com.matchmaking.helper.RatingHelper;
import com.matchmaking.model.Match;
import com.matchmaking.model.Player;
import com.matchmaking.simulator.MatchSimulator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MatchmakingSimulationApp {

    public static int testCounter = 2000;
    public static int playersPerTeam = 3;

    /**
     * <p>
     * A sample app to simulate the matchmaking process.
     * matchmaking.xml can be modified to configure different PlayerFinder and TeamBuilder for the Matchmaker.
     * This app can potentially used to find out the best matchmaking strategy as well as the parameters required.
     * </p>
     */
    public static void main(String[] args) {

        System.out.println("*************************************");
        System.out.println("*       Matchmaking Simulator       *");
        System.out.println("*          by Darren Yang           *");
        System.out.println("*************************************");

        ApplicationContext context = new ClassPathXmlApplicationContext("matchmaking.xml");
        Matchmaker matchmaker = (Matchmaker) context.getBean("matchmaker");
        RatingHelper ratingHelper = (RatingHelper) context.getBean("winningRatioBasedRatingHelper");
        MatchSimulator simulator = (MatchSimulator) context.getBean("simpleMatchSimulator");
        List<Player> players = SampleData.getPlayers();
        System.out.println("Running " + testCounter + " times for " + players.size() + " players to join " + playersPerTeam + "v" + playersPerTeam + " games.");

        players.forEach(matchmaker::enterMatchmaking);
        while (testCounter > 0) {
            Match match = matchmaker.findMatch(playersPerTeam);
            if (null != match) {
                simulator.predictMatchResult(match);
                ratingHelper.updateRatingAfterMatch(match);
            }
            testCounter--;
        }

        /**
         * After all the tests finished, here we can check the winning ratio of each matches to see the performance of a specific matchmaking strategy.
         * For example, we can calculate the average winning ratio of all the players to verify if it is close to 0.5:
         */
        System.out.println("Simulation completed!");

    }
}

