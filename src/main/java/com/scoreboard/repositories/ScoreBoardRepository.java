package com.scoreboard.repositories;

import java.util.List;

import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;

public interface ScoreBoardRepository {

    void addGame(Game game);

    Game getGame(Team homeTeam, Team awayTeam);

    Game findGameByTeam(Team team);

    void deleteGame(Team homeTeam, Team awayTeam);

    List<Game> getAllGames();
    List<Team> getAllTeams();
    void addTeam(Team team);
    void updateGame(Game game);

}