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

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/app_flow_diagram.png" />

The versions of the Connect 4 Android app consists of three UI screens: 
### Spash Screen
Splash screen is a simple screen that shows the app logo and name to the user, the screen visible on each startup of th app for a two second.

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/splash.png" width=200px/>

### Game Menu Screen
Game menu screen offer options to change the game play mode. The different options avaliable on the menu are:
* Play with- The user can choose either play with the smart phone or a different player(friend) in the same phone.
* Choose color disc- The game keys are two color discs- Red and Yellow, This option allows the user to select the color disc.
* Firt turn- When play with computer or friend the first move is default by user. this option allows to switch the first move.
* Difficulty- This option is only available when we play with computer. This difficulty level of the game.
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/menu1.png" width=200px/>                               <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/menu2.png" width=200px/>

### Game play Screen 
Game play screen bring a board with 6 rows and 7 colum, the user can drop a disc in to any non full colum by taping on the cells. Game play screen will also show the winner status. From this window useer can either stop or resart the current play.Stoping the play will bring user to the menu screen.

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/gameplay1.png" width=200px/>                               <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/gameplay2.png" width=200px/>                               <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/gameplay3.png" width=200px/>
