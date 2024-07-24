# Game Betting App

A simple app which lets a player place bet and determine the win based on the bet amount and a number passed to the server.


## Technologies Used

**Server:** Java 8, Spring Boot (2.4.5), Open API


**Build Tool:** Gradle (7.x)
## Running contact list app locally

Clone the repository to a directory and run the below commands.

```bash
  a.  git clone https://github.com/abhilash28abhi/betting-game.git
  b.  cd betting-game
  c.  Run the app using either of the 2 options
        1.  ./gradlew bootrun
        2.  java -jar <path of the generated project jar>
```

Access the application from browser at :
```bash
http://localhost:8090
```
Access the API documentation at :
```bash
http://localhost:8090/swagger-ui.html/
```

## Build
To build the spring boot app, run the below gradle command

```
./gradlew clean build
```