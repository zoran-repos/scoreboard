package com.scoreboard.exceptions;

public class TeamAlreadyPlayingException extends RuntimeException  {
    private static final long serialVersionUID = -6775052970854268457L;

    public TeamAlreadyPlayingException() {
        super("The team is already playing");
    }
}
