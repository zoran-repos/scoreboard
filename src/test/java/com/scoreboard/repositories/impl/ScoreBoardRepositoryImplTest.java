package com.scoreboard.repositories.impl;

import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardRepositoryImplTest {

    private ScoreBoardRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new ScoreBoardRepositoryImpl();
    }

    @Test
    void addGame() {

        Team homeTeam = new Team(1, "Home Team");
        Team awayTeam = new Team(2, "Away Team");
        Game game = new Game(1,homeTeam,awayTeam);
        repository.addGame(game);
        assertEquals(1, repository.getAllGames().size());
    }

    @Test
    void getGame() {
        Team homeTeam = new Team(1, "Home Team");
        Team awayTeam = new Team(2, "Away Team");
        Game game = new Game(1,homeTeam,awayTeam);
        repository.addGame(game);

        Game retrievedGame = repository.getGame(homeTeam, awayTeam);
        assertNotNull(retrievedGame);
        assertEquals(game, retrievedGame);
    }

    @Test
    void findGameByTeam() {
        Team awayTeam = new Team(2, "Away Team");
        Game game = new Game(1, new Team(1, "Home Team"), awayTeam);
        repository.addGame(game);

        Game retrievedGame = repository.findGameByTeam(awayTeam);
        assertNotNull(retrievedGame);
        assertEquals(game, retrievedGame);
    }

    @Test
    void deleteGame() {
        Team homeTeam = new Team(1, "Home Team");
        Team awayTeam = new Team(2, "Away Team");
        Game game = new Game(1,homeTeam,awayTeam);
        repository.addGame(game);

        repository.deleteGame(homeTeam, awayTeam);
        assertEquals(0, repository.getAllGames().size());
    }

    @Test
    void getAllGames() {
        Game game1 = new Game(1, new Team(1, "HomeTeam1"), new Team(2, "AwayTeam1"));
        Game game2 = new Game(2, new Team(3, "HomeTeam2"), new Team(4, "AwayTeam2"));
        repository.addGame(game1);
        repository.addGame(game2);

        List<Game> games = repository.getAllGames();
        assertEquals(2, games.size());
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    @Test
    void getAllTeams() {
        Team team1 = new Team(1, "Team1");
        Team team2 = new Team(2, "Team2");
        repository.addTeam(team1);
        repository.addTeam(team2);

        List<Team> teams = repository.getAllTeams();
        assertEquals(2, teams.size());
        assertTrue(teams.contains(team1));
        assertTrue(teams.contains(team2));
    }

    @Test
    void updateGame() {
        Game game = new Game(1, new Team(1, "HomeTeam1"), new Team(2, "AwayTeam1"));
        repository.addGame(game);

        Game updatedGame = new Game(1, new Team(1, "UpdatedHomeTeam"), new Team(2, "UpdatedAwayTeam"));
        updatedGame.setId(game.getId());

        repository.updateGame(updatedGame);

        Game retrievedGame = repository.getGame(updatedGame.getHomeTeam(), updatedGame.getAwayTeam());
        assertNotNull(retrievedGame);
        assertEquals(updatedGame, retrievedGame);
    }
}
