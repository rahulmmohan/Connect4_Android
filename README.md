# Connect4
Connect Four is a two-player connection game in which the players first choose a color and then take turns dropping colored discs from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the next available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs.
## GamePlay
![Connect 4 gameplay](https://github.com/altercation/solarized/raw/master/img/solarized-palette.png)

The animation demonstrates Connect Four gameplay where the first player begins by dropping his/her yellow disc into the center column of the game board. The two players then alternate turns dropping one of their discs at a time into an unfilled column, until the second player, with red discs, achieves four discs in a row, diagonally, and wins. If the game board fills before either player achieves four in a row, then the game is a draw.

## Architecture
When we googling for **"clean code"** we can find a name **Uncle Bob** is: he formulated the First Five Principles of Object Oriented Design (which were later given acronym S.O.L.I.D), was among original authors of Manifesto for Agile Software Development. Since Uncle Bob unconditionally states that the only way to move fast is to keep the code clean, and since Android does not encourage us to write clean code, it is our job as professional software developers to find ways to keep our code clean at all times.

Proper **MVC architecture** implementations have the following characteristics:

* Readable and maintainable code
* Modular code which provides high degree of flexibility
* More testable code (especially in context of unit testing)
* Code which is fun to work with

The above characteristics are generally associated with “clean code”. Therefore, following Uncle Bob’s reasoning, adoption of  **MVC architecture** allows me to “go faster” with this project.
![MVC](https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Screen%20Shot%202017-06-04%20at%207.32.00%20PM.png)


## App Workflow and Screenshots

The versions of the Connect 4 Android app consists of three UI screens: 
### Spash 
Splash screen is simple screen that shows the app logo and name to the user, the screen visible on each startup of th app for a two second.

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/splash.png" width=200px/>

### Game Menu
Used to select the game mode - User can play against a Computer or a friend, can choose the color disc,first turn and        the dificulty when playing with computer.
### Game play 
game play goes here.
