package com.scoreboard.services.impl;

import java.util.Date;
import java.util.List;

import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;
import com.scoreboard.exceptions.GameNotFoundException;
import com.scoreboard.exceptions.TeamAlreadyPlayingException;
import com.scoreboard.exceptions.TeamAreTheSameException;
import com.scoreboard.exceptions.TeamOrResultCannotFoundException;
import com.scoreboard.repositories.ScoreBoardRepository;
import com.scoreboard.services.ScoreBoardService;
import com.scoreboard.utils.ScoreBoardUtils;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    private final ScoreBoardRepository scoreBoardRepository;

    public ScoreBoardServiceImpl(ScoreBoardRepository repository) {
        this.scoreBoardRepository = repository;
    }

    @Override
    public Game startGame(Team homeTeam, Team awayTeam) {
        int idGame = findNumberOfGames()+1;
        int idTeam = findNumberOfTeams()+1;

        if (!ScoreBoardUtils.areValidTeams(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Teams cannot be null and must have mandatory fields present");
        }
        if (homeTeam.getId().equals(awayTeam.getId())) {
            throw new TeamAreTheSameException();
        }
        if (isTeamAlreadyPlaying(homeTeam) || isTeamAlreadyPlaying(awayTeam)) {
            throw new TeamAlreadyPlayingException();
        }

        Game game = new Game(idGame, homeTeam, awayTeam);
        game.setInitDate(new Date());
        scoreBoardRepository.addGame(game);
        addTeamsFromGame(homeTeam, idTeam);
        return game;
    }

    private void addTeamsFromGame(Team homeTeam, int idTeam) {
        Team team1 = new Team(idTeam, homeTeam.getName());
        Team team2 = new Team(idTeam +1, homeTeam.getName());
        scoreBoardRepository.addTeam(team1);
        scoreBoardRepository.addTeam(team2);
    }
    private boolean isTeamAlreadyPlaying(Team team) {
        return scoreBoardRepository.findGameByTeam(team) != null;
    }

    private int findNumberOfGames() {
        return scoreBoardRepository.getAllGames().size();
    }
    private int findNumberOfTeams() {
        return scoreBoardRepository.getAllTeams().size();
    }

    @Override
    public void finishGame(Team homeTeam, Team awayTeam) {
        if (!ScoreBoardUtils.areValidTeams(homeTeam, awayTeam)) {
            return;
        }
        this.scoreBoardRepository.deleteGame(homeTeam, awayTeam);
    }

    @Override
    public void updateScore(Team homeTeam, Team awayTeam, int homeScore, int awayScore)  {
        if (!ScoreBoardUtils.areValidTeams(homeTeam, awayTeam) || !ScoreBoardUtils.isValidResult(homeScore, awayScore)) {
            throw new TeamOrResultCannotFoundException();
        }
        Game game = this.scoreBoardRepository.getGame(homeTeam, awayTeam);

        if (game == null) {
            throw new GameNotFoundException();
        }

        game.setHomeScore(homeScore);
        game.setAwayScore(awayScore);
        this.scoreBoardRepository.updateGame(game);
    }

    @Override
    public List<Game> getSummary() {
        List<Game> list = this.scoreBoardRepository.getAllGames();
        list.sort((game1, game2) -> {
            if (ScoreBoardUtils.getTotalScore(game1) > ScoreBoardUtils.getTotalScore(game2)) {
                return -1;
            } else if (ScoreBoardUtils.getTotalScore(game1) < ScoreBoardUtils.getTotalScore(game2)) {
                return 1;
            }
            return game1.getInitDate().before(game2.getInitDate()) ? 1 : -1;
        });
        return list;
    }

}
