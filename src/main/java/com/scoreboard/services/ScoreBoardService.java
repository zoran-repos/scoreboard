package com.scoreboard.services;

import java.util.List;

import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;
import com.scoreboard.exceptions.GameNotFoundException;
import com.scoreboard.exceptions.TeamAlreadyPlayingException;
import com.scoreboard.exceptions.TeamAreTheSameException;
import com.scoreboard.exceptions.TeamOrResultCannotFoundException;

public interface ScoreBoardService {

    Game startGame(Team homeTeam, Team awayTeam) throws TeamAlreadyPlayingException, TeamAreTheSameException;

    void finishGame(Team homeTeam, Team awayTeam);

    void updateScore(Team homeTeam, Team awayTeam, int homeScore, int awayScore) throws TeamOrResultCannotFoundException, GameNotFoundException;

    List<Game> getSummary();

}
