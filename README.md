# Falling Words - Babbel Code Challenge

![Alt text](screenshots/in_game.png?raw=true "In game screen")

Word guessing game (code challenge)  showcasing coding an UX skills.
The application is written in Kotlin using Clean Architecture.

#  App Structure

- MVVM is used as an architecture
- ViewModel is the presentation layer, which exposes observable live data.
- The ViewModel communicates with the data layer, in this case GameManager and HighScoreRepository
- Coroutines are used for async jobs
- Dagger Hilt is used for dependency injection.

# Unit Testing

- GameViewModel, GameManager and  HighScoreRepository are unit tested.

# Tests missing \[ 🚧 Missing ⛏👷🚧 \]

- Due to time constraints no UI tests were written
- Tests for WordsRepository and QuestionFactory are missing

# Possible improvements

- Finish the game when the user goes through all the questions.
- Better design for correct/wrong button.
- Cloud storage for the high score.
- Technically, find a better way to implement the countdown.

# Time management

The project was finished in roughly 8 hours:

- 2 hours project setup and data layer creation
- 3 hours creating game logic + UI
- 0.5 hours bug hunting and polishing UI
- 2 hours unit tests
- 0.5 hours preparing the README file


