# Connect4
Connect Four is a two-player connection game in which the players first choose a color and then take turns dropping colored discs from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the next available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs.
## GamePlay
![Connect 4 gameplay](https://github.com/altercation/solarized/raw/master/img/solarized-palette.png)

The animation demonstrates Connect Four gameplay where the first player begins by dropping his/her yellow disc into the center column of the game board. The two players then alternate turns dropping one of their discs at a time into an unfilled column, until the second player, with red discs, achieves four discs in a row, diagonally, and wins. If the game board fills before either player achieves four in a row, then the game is a draw.

## The AI Move

### Minimax with Alpha-Beta-pruning
Alpha-Beta-pruning is an optimization technique for minimax algorithm. It reduces the computation time by a huge factor. This allows us to search much faster and even go into deeper levels in the game tree. It cuts off branches in the game tree which need not be searched because there already exists a better move available. It is called Alpha-Beta pruning because it passes 2 extra parameters in the minimax function, namely alpha and beta.

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Alpha-Beta-Pruning.png" width="300px" />

* The initial call stars from A. The value of alpha here is -INFINITY and the value of beta is +INFINITY. These values are passed down to subsequent nodes in the tree. At A the maximizer must choose max of B and C, so A calls B first
* At B it the minimizer must choose min of D and E and hence calls D first.
* At D, it looks at its left child which is a leaf node. This node returns a value of 3. Now the value of alpha at D is max( -INF, 3) which is 3.
* To decide whether its worth looking at its right node or not, it checks the condition beta<=alpha. This is false since beta = +INF and alpha = 3. So it continues the search.
* D now looks at its right child which returns a value of 5.At D, alpha = max(3, 5) which is 5. Now the value of node D is 5
* D returns a value of 5 to B. At B, beta = min( +INF, 5) which is 5. The minimizer is now guaranteed a value of 5 or lesser. B now calls E to see if he can get a lower value than 5.
* At E the values of alpha and beta is not -INF and +INF but instead -INF and 5 respectively, because the value of beta was changed at B and that is what B passed down to E
* Now E looks at its left child which is 6. At E, alpha = max(-INF, 6) which is 6. Here the condition becomes true. beta is 5 and alpha is 6. So beta<=alpha is true. Hence it breaks and E returns 6 to B
* Note how it did not matter what the value of E‘s right child is. It could have been +INF or -INF, it still wouldn’t matter, We never even had to look at it because the minimizer was guaranteed a value of 5 or lesser. So as soon as the maximizer saw the 6 he knew the minimizer would never come this way because he can get a 5 on the left side of B. This way we dint have to look at that 9 and hence saved computation time.
* E returns a value of 6 to B. At B, beta = min( 5, 6) which is 5.The value of node B is also 5

So far this is how our game tree looks. The 9 is crossed out because it was never computed.

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Alpha-Beta-Pruning-2.png" width="300px" />              <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Alpha-Beta-Pruning-3.png" width="300px" />

* B returns 5 to A. At A, alpha = max( -INF, 5) which is 5. Now the maximizer is guaranteed a value of 5 or greater. A now calls C to see if it can get a higher value than 5.
* At C, alpha = 5 and beta = +INF. C calls F
* At F, alpha = 5 and beta = +INF. F looks at its left child which is a 1. alpha = max( 5, 1) which is still 5.
F looks at its right child which is a 2. Hence the best value of this node is 2. Alpha still remains 5
F returns a value of 2 to C. At C, beta = min( +INF, 2). The condition beta <= alpha becomes false as beta = 2 and alpha = 5. So it breaks and it dose not even have to compute the entire sub-tree of G.
The intuition behind this break off is that, at C the minimizer was guaranteed a value of 2 or lesser. But the maximizer was already guaranteed a value of 5 if he choose B. So why would the maximizer ever choose C and get a value less than 2 ? Again you can see that it did not matter what those last 2 values were. We also saved a lot of computation by skipping a whole sub tree.
C now returns a value of 2 to A. Therefore the best value at A is max( 5, 2) which is a 5.
Hence the optimal value that the maximizer can get is 5
This is how our final game tree looks like. As you can see G has been crossed out as it was never computed.

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
