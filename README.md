# Scoreboard Library

The Scoreboard Library is a Java library that provides functionality for managing game scores and generating summaries of game results.

## Features

- Start a new game with home and away teams.
- Finish an ongoing game.
- Update the score of a game.
- Get a summary of all games played.

## Usage

1. Add the Scoreboard Library as a dependency in your Maven project.

```xml
<dependency>
    <groupId>com.scoreboard</groupId>
    <artifactId>scoreboard-lib</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. Create an instance of the ScoreBoard class to manage the game scores.

```bash
       ScoreBoard scoreBoard = new ScoreBoard();

// Start a new game
        Team homeTeam = new Team(1, "Home Team");
        Team awayTeam = new Team(2, "Away Team");
        Game game = scoreBoard.startGame(1, homeTeam, awayTeam);

// Update the score
        scoreBoard.updateScore(homeTeam, awayTeam, 3, 2);

// Finish a game
        scoreBoard.finishGame(homeTeam, awayTeam);

// Get the summary of all games
        List<Game> summary = scoreBoard.getSummaryAsString();
```


## Check functionality with CliRunner

1. Use mvn command for creating .jar file

```bash
mvn clean install
``` 

2. Go in target folder and run 

```bash
java -jar .\scoreboard-lib-1.0.0.jar
``` 

- Start a new game
```java
--- Menu ---
1. Start game
2. Finish game
3. Update score
4. Exit
Enter your choice: 1
Enter home team name: Brasil
Enter away team name: Mexico
Game started successfully.

--- Scoreboard ---
Brasil 0 - 0 Mexico

```
- Update score
```java
--- Menu ---
1. Start game
2. Finish game
3. Update score
4. Exit
Enter your choice: 3
Enter home team name: Brasil
Enter away team name: Mexico
Enter home team score: 1
Enter away team score: 0
Score updated successfully.

--- Scoreboard ---
Brasil 1 - 0 Mexico

```
- Start a new game
```java
--- Menu ---
1. Start game
2. Finish game
3. Update score
4. Exit
Enter your choice: 1
Enter home team name: France
Enter away team name: Ireland
Game started successfully.

--- Scoreboard ---
Brasil 1 - 0 Mexico
France 0 - 0 Ireland

```
- Finish game
```java
--- Menu ---
1. Start game
2. Finish game
3. Update score
4. Exit
Enter your choice: 2
Enter home team name: France
Enter away team name: Ireland
Game finished successfully.

--- Scoreboard ---
Brasil 1 - 0 Mexico
```
