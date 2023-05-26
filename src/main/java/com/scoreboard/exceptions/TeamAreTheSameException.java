package com.scoreboard.exceptions;

public class TeamAreTheSameException extends RuntimeException {
    private static final long serialVersionUID = -6775052970854268457L;

    public TeamAreTheSameException() {
        super("The same team cannot be in both home team and away team");
    }
}
