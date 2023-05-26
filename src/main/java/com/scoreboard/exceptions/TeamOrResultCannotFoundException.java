package com.scoreboard.exceptions;

public class TeamOrResultCannotFoundException extends RuntimeException  {
    private static final long serialVersionUID = -6775052970854268457L;

    public TeamOrResultCannotFoundException() {
        super("Invalid teams or result");
    }
}
