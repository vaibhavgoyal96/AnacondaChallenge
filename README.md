
# Rock, Paper, Scissors API (Backend)

This project implements a Rock, Paper, Scissors game where two players can play against each other or a player can play against the computer. The results are stored in an H2 in-memory database, and a simple leaderboard can be generated based on player scores.


## Software Tools Used

- **Java**: The core language used to develop the game logic and API.
- **Spring Boot**: Framework used to create the REST API.
- **H2 Database**: In-memory database used to store game results. Accessible through the H2 console.
- **Maven**: Dependency management and build tool.
- **Postman**: API testing tool (optional).

## How to Run

1. Clone the repository.
2. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA).
3. Run the `RockPaperScissorsApplication` class to start the Spring Boot application.
4. Access the API through `http://localhost:8080`.

## Demo
- [Link](https://drive.google.com/file/d/1Or-DtZpnKvKJ0L3oaDhdicGL2XRXy80t/view?usp=drive_link)
## H2 Database Settings

- **Username**: `sa`
- **Password**: `password`
- To access the database, navigate to `http://localhost:8080/h2-console`.

## API Documentation

For detailed API usage, see the [API Documentation](api.md).