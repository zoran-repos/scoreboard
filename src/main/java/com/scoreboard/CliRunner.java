package com.scoreboard;


import com.scoreboard.entities.Team;
import com.scoreboard.exceptions.GameNotFoundException;
import com.scoreboard.exceptions.TeamAlreadyPlayingException;
import com.scoreboard.exceptions.TeamAreTheSameException;
import com.scoreboard.exceptions.TeamOrResultCannotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliRunner {
    private static ScoreBoard scoreBoard;
    private static Scanner scanner;

    public static void main(String[] args){
        scoreBoard = new ScoreBoard();
        scanner = new Scanner(System.in);

        displayResults();

        String choice;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Start game");
            System.out.println("2. Finish game");
            System.out.println("3. Update score");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    startGame();
                    break;
                case "2":
                    finishGame();
                    break;
                case "3":
                    updateScore();
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            displayResults();
        } while (true);
    }

    private static void displayResults() {
        System.out.println("\n--- Scoreboard ---");
        System.out.println(scoreBoard.getSummaryAsString());
    }

    private static void startGame() {
        List<Team> teams = userInputAndSetTeams();
        try {
            scoreBoard.startGame(teams.get(0), teams.get(1));
            System.out.println("Game started successfully.");
        } catch (TeamAlreadyPlayingException e) {
            System.out.println("One of the teams is already playing.");
        } catch (TeamAreTheSameException e) {
            throw new RuntimeException(e);
        }
    }

    private static void finishGame() {
        List<Team> teams = userInputAndSetTeams();

        scoreBoard.finishGame(teams.get(0), teams.get(1));
        System.out.println("Game finished successfully.");
    }

    private static void updateScore() throws TeamOrResultCannotFoundException, GameNotFoundException {
        List<Team> teams = userInputAndSetTeams();

        System.out.print("Enter home team score: ");
        int homeScore = scanner.nextInt();

        System.out.print("Enter away team score: ");
        int awayScore = scanner.nextInt();

        scoreBoard.updateScore(teams.get(0), teams.get(1), homeScore, awayScore);
        System.out.println("Score updated successfully.");
    }
    private static List<Team> userInputAndSetTeams() {
        System.out.print("Enter home team name: ");
        String homeTeamName = scanner.nextLine();

        System.out.print("Enter away team name: ");
        String awayTeamName = scanner.nextLine();
        List<Team> teamsFromGame = new ArrayList<>();
        Team homeTeam = new Team(1, homeTeamName);
        Team awayTeam = new Team(2, awayTeamName);
        teamsFromGame.add(homeTeam);
        teamsFromGame.add(awayTeam);
        return teamsFromGame;
    }
}
