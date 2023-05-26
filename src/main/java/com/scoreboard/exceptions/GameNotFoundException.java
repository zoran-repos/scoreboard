package com.scoreboard.exceptions;

public class GameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6775052970854268457L;

    public GameNotFoundException() {
        super("Game not found");
    }
}
