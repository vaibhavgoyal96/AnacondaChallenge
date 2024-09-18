# Rock, Paper, Scissors API Documentation

## Base URL
```http://localhost:8080```


## Endpoints

### 1. Play a Game

**URL**: `/api/game/play`

**Method**: `GET`

**Description**: Play a Rock, Paper, Scissors game between two players or between a player and the computer. If `player2Name` is not provided, the computer will generate a random move.

**Parameters**:
- `player1Move` (required): The move of Player 1 (rock, paper, scissors).
- `player2Move` (optional): The move of Player 2 (rock, paper, scissors). If not provided, the computer will generate a move.
- `player1Name` (required): The name of Player 1.
- `player2Name` (optional): The name of Player 2. If not provided, the computer plays.

**Example URL**:

```http://localhost:8080/api/game/play?player1Move=rock&player1Name=Vaibhav```


**Response**:
```json
{
  "player1Name": "Vaibhav",
  "player2Name": "Computer",
  "player1Move": "rock",
  "player2Move": "scissors",
  "player1Score": {
    "wins": 3,
    "losses": 1,
    "ties": 2
  },
  "player2Score": {
    "wins": 1,
    "losses": 3,
    "ties": 2
  }
}
```

### 2. Get Player's Match Summary

**URL**: `/api/game/player/{playerName}/matches`

**Method**: `GET`

**Description**: Get the match summary for a specific player, showing their performance (wins, losses, ties) against all opponents.

**Parameters**:
- `playerName` (required): The name of the player.

**Example URL**:

```http://localhost:8080/api/game/player/Dibyo/matches```


**Response**:
```json
{
  "Vaibhav": "2 wins, 1 losses, 1 ties",
  "Rakesh": "1 wins, 0 losses, 0 ties"
}
```

### 3. Get All Players' Scores (Leaderboard)

**URL**: `/api/game/scores`

**Method**: `GET`

**Description**: Get the total wins of all players. Can be used to create a leaderboard.


**Example URL**:

```http://localhost:8080/api/game/scores```


**Response**:
```json
{
  "Vaibhav": 4,
  "Dibyo": 2,
  "Rakesh": 1
}

```

### 4. H2 Database Console

**URL**: `/h2-console`

**Method**: `GET`

**Description**: Access the H2 in-memory database console to view or manage the data.

**Example URL**:

```http://localhost:8080/h2-console```


**Credentials**:
```json
{
  "Username": "sa",
  "Password": "password"
}

```
