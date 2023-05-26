package com.scoreboard;

import com.scoreboard.entities.Game;
import com.scoreboard.entities.Team;
import com.scoreboard.repositories.impl.ScoreBoardRepositoryImpl;
import com.scoreboard.services.ScoreBoardService;
import com.scoreboard.services.impl.ScoreBoardServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoard {

    private final ScoreBoardService scoreBoardService;

    public ScoreBoard() {
        this.scoreBoardService = new ScoreBoardServiceImpl(new ScoreBoardRepositoryImpl());
    }

    public Game startGame(Team homeTeam, Team awayTeam){
        return this.scoreBoardService.startGame(homeTeam, awayTeam);
    }

    public void finishGame(Team homeTeam, Team awayTeam) {
        this.scoreBoardService.finishGame(homeTeam, awayTeam);
    }

    public void updateScore(Team homeTeam, Team awayTeam, int homeScore, int awayScore){
        this.scoreBoardService.updateScore(homeTeam, awayTeam, homeScore, awayScore);
    }

    public List<Game> getSummary() {
        return this.scoreBoardService.getSummary();
    }

    public String getSummaryAsString() {
        return this.getSummary().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
