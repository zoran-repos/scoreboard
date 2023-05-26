package com.scoreboard.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import com.scoreboard.repositories.ScoreBoardRepository;
import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;

public class ScoreBoardRepositoryImpl implements ScoreBoardRepository {

    private final List<Game> games = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();

    @Override
    public void addGame(Game game) {
        games.add(game);
    }

    @Override
    public Game getGame(Team homeTeam, Team awayTeam) {
        return games.stream()
                .filter(game -> game.getHomeTeam().getName().equals(homeTeam.getName())
                        && game.getAwayTeam().getName().equals(awayTeam.getName()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Game findGameByTeam(Team team) {
        return games.stream()
                .filter(game -> game.getAwayTeam().getName().equals(team.getName()) || game.getHomeTeam().getName().equals(team.getName()))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void deleteGame(Team homeTeam, Team awayTeam) {
        games.removeIf(game -> game.getHomeTeam().getName().equals(homeTeam.getName()) &&
                game.getAwayTeam().getName().equals(awayTeam.getName()));
    }


    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }
    @Override
    public List<Team> getAllTeams() {
        return new ArrayList<>(teams);
    }
    @Override
    public void addTeam(Team team) {
        teams.add(team);
    }

    @Override
    public void updateGame(Game game) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId() == game.getId()) {
                games.set(i, game);
                break;
            }
        }
    }

}