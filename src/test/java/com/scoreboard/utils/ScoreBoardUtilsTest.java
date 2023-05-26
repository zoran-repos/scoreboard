package com.scoreboard.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;

public class ScoreBoardUtilsTest {

    @Test
    public void testAreValidTeams_ValidTeams_ReturnsTrue() {

        Team homeTeam = new Team(1, "Home Team");
        Team awayTeam = new Team(2, "Away Team");


        boolean result = ScoreBoardUtils.areValidTeams(homeTeam, awayTeam);


        Assertions.assertTrue(result);
    }

    @Test
    public void testAreValidTeams_NullHomeTeam_ReturnsFalse() {

        Team awayTeam = new Team(2, "Away Team");


        boolean result = ScoreBoardUtils.areValidTeams(null, awayTeam);


        Assertions.assertFalse(result);
    }

    @Test
    public void testAreValidTeams_NullAwayTeam_ReturnsFalse() {

        Team homeTeam = new Team(1, "Home Team");


        boolean result = ScoreBoardUtils.areValidTeams(homeTeam, null);


        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidTeam_ValidTeam_ReturnsTrue() {

        Team team = new Team(1, "Team");


        boolean result = ScoreBoardUtils.isValidTeam(team);


        Assertions.assertTrue(result);
    }

    @Test
    public void testIsValidTeam_NullTeam_ReturnsFalse() {

        Team team = null;


        boolean result = ScoreBoardUtils.isValidTeam(team);


        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidResult_ValidResult_ReturnsTrue() {

        int homeScore = 2;
        int awayScore = 1;


        boolean result = ScoreBoardUtils.isValidResult(homeScore, awayScore);


        Assertions.assertTrue(result);
    }

    @Test
    public void testIsValidResult_NegativeScores_ReturnsFalse() {

        int homeScore = -1;
        int awayScore = 2;


        boolean result = ScoreBoardUtils.isValidResult(homeScore, awayScore);


        Assertions.assertFalse(result);
    }

    @Test
    public void testGetTotalScore_ValidGame_ReturnsTotalScore() {

        Game game = new Game(1, new Team(1, "Home Team"), new Team(2, "Away Team"));
        game.setHomeScore(2);
        game.setAwayScore(1);


        int result = ScoreBoardUtils.getTotalScore(game);


        Assertions.assertEquals(3, result);
    }


}
