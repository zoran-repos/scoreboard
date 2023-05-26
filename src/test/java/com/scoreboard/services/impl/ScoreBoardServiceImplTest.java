package com.scoreboard.services.impl;

import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;
import com.scoreboard.exceptions.GameNotFoundException;
import com.scoreboard.exceptions.TeamAlreadyPlayingException;
import com.scoreboard.exceptions.TeamAreTheSameException;
import com.scoreboard.exceptions.TeamOrResultCannotFoundException;
import com.scoreboard.repositories.ScoreBoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreBoardServiceImplTest {

    private ScoreBoardRepository repository;
    private ScoreBoardServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(ScoreBoardRepository.class);
        service = new ScoreBoardServiceImpl(repository);
    }

    @Test
    void startGame_ValidTeams_ShouldCreateGame() throws TeamAlreadyPlayingException, TeamAreTheSameException {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = new Team(2, "AwayTeam");

        when(repository.findGameByTeam(homeTeam)).thenReturn(null);
        when(repository.findGameByTeam(awayTeam)).thenReturn(null);
        when(repository.getAllTeams()).thenReturn(new ArrayList<>());

        Game game = service.startGame(homeTeam, awayTeam);

        assertNotNull(game);
        assertEquals(homeTeam, game.getHomeTeam());
        assertEquals(awayTeam, game.getAwayTeam());
        verify(repository, times(1)).addGame(game);
        verify(repository, times(2)).addTeam(any());
    }

    @Test
    void startGame_TeamAlreadyPlaying_ShouldThrowTeamAlreadyPlayingException() throws TeamAlreadyPlayingException, TeamAreTheSameException {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = new Team(2, "AwayTeam");

        when(repository.findGameByTeam(homeTeam)).thenReturn(new Game(1, homeTeam, awayTeam));

        assertThrows(TeamAlreadyPlayingException.class, () -> service.startGame(homeTeam, awayTeam));
        verify(repository, never()).addGame(any());
    }

    @Test
    void startGame_TeamAreTheSame_ShouldThrowTeamAreTheSameException() throws TeamAlreadyPlayingException, TeamAreTheSameException {
        Team team = new Team(1, "Team");

        assertThrows(TeamAreTheSameException.class, () -> service.startGame(team, team));
        verify(repository, never()).addGame(any());
    }

    @Test
    void finishGame_ValidTeams_ShouldDeleteGame() {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = new Team(2, "AwayTeam");

        service.finishGame(homeTeam, awayTeam);

        verify(repository, times(1)).deleteGame(homeTeam, awayTeam);
    }

    @Test
    void updateScore_ValidTeamsAndResults_ShouldUpdateGameScore() throws TeamOrResultCannotFoundException, GameNotFoundException {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = new Team(2, "AwayTeam");
        int homeScore = 3;
        int awayScore = 2;
        Game game = new Game(1, homeTeam, awayTeam);

        when(repository.getGame(homeTeam, awayTeam)).thenReturn(game);

        service.updateScore(homeTeam, awayTeam, homeScore, awayScore);

        assertEquals(homeScore, game.getHomeScore());
        assertEquals(awayScore, game.getAwayScore());
        verify(repository, times(1)).updateGame(game);
    }

    @Test
    void updateScore_InvalidTeamsOrResults_ShouldThrowTeamOrResultCannotFoundException() {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = new Team(2, "AwayTeam");
        int homeScore = 3;
        int awayScore = -1;

        assertThrows(TeamOrResultCannotFoundException.class, () -> service.updateScore(homeTeam, awayTeam, homeScore, awayScore));
        verify(repository, never()).updateGame(any());
    }

    @Test
    void updateScore_GameNotFound_ShouldThrowGameNotFoundException() {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = new Team(2, "AwayTeam");
        int homeScore = 3;
        int awayScore = 2;

        when(repository.getGame(homeTeam, awayTeam)).thenReturn(null);

        assertThrows(GameNotFoundException.class, () -> service.updateScore(homeTeam, awayTeam, homeScore, awayScore));
        verify(repository, never()).updateGame(any());
    }

    @Test
    void getSummary_ShouldReturnSortedGameList() {
        List<Game> games = new ArrayList<>();
        Game game1 = new Game(1, new Team(1, "Team1"), new Team(2, "Team2"));
        game1.setHomeScore(3);
        game1.setAwayScore(2);
        game1.setInitDate(new Date());
        games.add(game1);

        Game game2 = new Game(2, new Team(3, "Team3"), new Team(4, "Team4"));
        game2.setHomeScore(2);
        game2.setAwayScore(2);
        game2.setInitDate(new Date(System.currentTimeMillis() - 1000));
        games.add(game2);

        when(repository.getAllGames()).thenReturn(games);

        List<Game> summary = service.getSummary();

        assertEquals(2, summary.size());
        assertEquals(game1, summary.get(0));
        assertEquals(game2, summary.get(1));
        verify(repository, times(1)).getAllGames();
    }
    @Test
    void startGame_InvalidTeams_ShouldThrowIllegalArgumentException()  {
        Team homeTeam = new Team(1, "HomeTeam");
        Team awayTeam = null;

        assertThrows(IllegalArgumentException.class, () -> service.startGame(homeTeam, awayTeam));
        verify(repository, never()).addGame(any());
    }

    @Test
    void getSummary_NoGames_ShouldReturnEmptyList() {
        when(repository.getAllGames()).thenReturn(new ArrayList<>());

        List<Game> summary = service.getSummary();

        assertNotNull(summary);
        assertTrue(summary.isEmpty());
        verify(repository, times(1)).getAllGames();
    }
}
