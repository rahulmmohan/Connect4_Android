[![Build Status](https://travis-ci.org/rahulmmohan/Connect4_Android.svg?branch=master)](https://travis-ci.org/rahulmmohan/Connect4_Android)
 
# Connect4 <a style="margin-bottom: 0;" href='https://play.google.com/store/apps/details?id=info.overrideandroid.connect4&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height="50px"/></a><a style="margin-bottom: 0;" href='https://appetize.io/embed/0an4vqrp545hnkjma9q7g7h47m?device=nexus5&scale=75&orientation=portrait&osVersion=7.0'><img alt='appetize.io' src='https://appetize.io/images/logo1_colored_background_padded_extra.png' height="50px"/></a>
 
Connect Four is a two-player connection game in which the players first choose a color and then take turns dropping colored discs from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the next available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs.
 
### Get from google play
Get the app from [Google Playstore](https://play.google.com/store/apps/details?id=info.overrideandroid.connect4)
 
### Run on appetize.io
Run app on the online emulators using [appetize.io](https://appetize.io/embed/0an4vqrp545hnkjma9q7g7h47m?device=nexus5&scale=75&orientation=portrait&osVersion=7.0)
 
### Download APK
[Download APK](https://github.com/rahulmmohan/Connect4_Android/blob/master/app/app-release.apk) from here and install on devices or emulators.
 
## GamePlay
 
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/ezgif-2-355bc88733.gif" width="300px" />
 
The two players alternate turns dropping one of their discs at a time into an unfilled column, until one of the a player, achieves four discs in a row, diagonally, and wins. If the game board fills before either player achieves four in a row, then the game is a draw.
 
## The AI Move
 
### Minimax with Alpha-Beta-pruning
Alpha-Beta-pruning is an optimization technique for minimax algorithm. It reduces the computation time by a huge factor. This allows us to search much faster and even go into deeper levels in the game tree. It cuts off branches in the game tree which need not be searched because there already exists a better move available. It is called Alpha-Beta pruning because it passes 2 extra parameters in the minimax function, namely alpha and beta.

#### Algorithm

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Alpha-Beta-Pruning.png" width="400px" />
 
* The initial call starts from A. The value of alpha here is -INFINITY and the value of beta is +INFINITY. These values are passed down to subsequent nodes in the tree. At A the maximizer must choose max of B and C, so A calls B first
* At B it the minimizer must choose min of D and E and hence calls D first.
* At D, it looks at its left child which is a leaf node. This node returns a value of 3. Now the value of alpha at D is max( -INF, 3) which is 3.
* To decide whether it is worth looking at its right node or not, it checks the condition beta<=alpha. This is false since beta = +INF and alpha = 3. So it continues the search.
* D now looks at its right child which returns a value of 5.At D, alpha = max(3, 5) which is 5. Now the value of node D is 5
* D returns a value of 5 to B. At B, beta = min( +INF, 5) which is 5. The minimizer is now guaranteed a value of 5 or lesser. B now calls E to see if he can get a lower value than 5.
* At E the values of alpha and beta is not -INF and +INF but instead -INF and 5 respectively, because the value of beta was changed at B and that is what B passed down to E
* Now E looks at its left child which is 6. At E, alpha = max(-INF, 6) which is 6. Here the condition becomes true. beta is 5 and alpha is 6. So beta<=alpha is true. Hence it breaks and E returns 6 to B
* Note how it did not matter what the value of E‘s right child is. It could have been +INF or -INF, it still wouldn’t matter, We never even had to look at it because the minimizer was guaranteed a value of 5 or lesser. So as soon as the maximizer saw the 6 he knew the minimizer would never come this way because he can get a 5 on the left side of B. This way we didn’t have to look at that 9 and hence saved computation time.
* E returns a value of 6 to B. At B, beta = min( 5, 6) which is 5.The value of node B is also 5
 
So far this is how our game tree looks. The 9 is crossed out because it was never computed.
 
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Alpha-Beta-Pruning-2.png" width="400px" />
 
* B returns 5 to A. At A, alpha = max( -INF, 5) which is 5. Now the maximizer is guaranteed a value of 5 or greater. A now calls C to see if it can get a higher value than 5.
* At C, alpha = 5 and beta = +INF. C calls F
* At F, alpha = 5 and beta = +INF. F looks at its left child which is a 1. alpha = max( 5, 1) which is still 5.
* F looks at its right child which is a 2. Hence the best value of this node is 2. Alpha still remains 5
* F returns a value of 2 to C. At C, beta = min( +INF, 2). The condition beta <= alpha becomes false as beta = 2 and alpha = 5. So it breaks and it doesn't even have to compute the entire sub-tree of G.
* The intuition behind this break off is that, at C the minimizer was guaranteed a value of 2 or lesser. But the maximizer was already guaranteed a value of 5 if he choose B. So why would the maximizer ever choose C and get a value less than 2 ? Again you can see that it did not matter what those last 2 values were. We also saved a lot of computation by skipping a whole sub tree.
* C now returns a value of 2 to A. Therefore the best value at A is max( 5, 2) which is a 5.
* Hence the optimal value that the maximizer can get is 5

<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Alpha-Beta-Pruning-3.png" width="400px" />
 
#### Implementation
 
To implement this, we need some data structure that represents the entire state of a game, So we can use a 2D array with 6 rows and 7 columns. Then we need to able to generate all the valid moves that a player can make from a given state. For connect 4 just go through each column and if it isn't full then it is a valid move. For each valid move, generate a new board state that represents the board after making that move, and then the algorithm gets recursive. We look at all the valid moves possible from after that state.
 
We want to keep track of the depth we have searched to and terminate the search after it has gone down a few levels. When it reaches the maximum depth, or one of the players has won, need have a function that evaluates the board, and gives a numeric estimate of how good the board is for player 1. If player 1 won, return an extremely high value. If player 2 won, return an extremely low value.
 
```
 private Move chooseMove(int player, int opponent,
                            int alpha, int beta, int depth) {
        Move best = new Move(-1, player == Player.PLAYER2 ? alpha : beta);
        // go from left to right until you find a non-full column
        for (int i = 0; i < 7; i++) {
            if (boardLogic.columnHeight(i) > 0) {
                // add a counter to that column, then check for win-condition
                boardLogic.placeMove(i, player);
                // score this move and all its children
                int score = 0;
                if (boardLogic.checkMatch(i, boardLogic.columnHeight(i))) {
                    // this move is a winning move for the player
                    score = player == Player.PLAYER2 ? 1 : -1;
                } else if (depth != 1) {
                    // this move wasn't a win or a draw, so go to the next move
                    score = chooseMove(opponent, player, alpha, beta,
                            depth - 1).getScore();
                }
                boardLogic.undoMove(i);
                // if this move beats this player's best move so far, record it
                if (player == Player.PLAYER2 && score > best.getScore()) {
                    best = new Move(i, score);
                    alpha = score;
                } else if (player == Player.PLAYER1 && score < best.getScore()) {
                    best = new Move(i, score);
                    beta = score;
                }
                // don't continue with this branch, we've already found better
                if (alpha >= beta) {
                    return best;
                }
            }
        }
        return best;
    }
```
 
The idea is that two scores are passed around in the search. The first one is alpha, which is the best score that can be forced by some means. Anything worth less than this is of no use, because there is a strategy that is known to result in a score of alpha. Anything less than or equal to alpha is no improvement.
 
The second score is beta. Beta is the worst-case scenario for the opponent. It's the worst thing that the opponent has to endure, because it's known that there is a way for the opponent to force a situation no worse than beta, from the opponent's point of view. If the search finds something that returns a score of beta or better, it's too good, so the side to move is not going to get a chance to use this strategy.
 
When searching moves, each move searched returns a score that has some relation to alpha and beta, and the relation is very important and might mean that the search can stop and return a value.
 
If a move results in a score that was less than or equal to alpha, it was just a bad move and it can be forgotten about, since, as I stated a few paragraphs ago, there is known to be a strategy that gets the moving side a position valued at alpha.
 
If a move results in a score that is greater than or equal to beta, this whole node is trash, since the opponent is not going to let the side to move to achieve this position, because there is some choice the opponent can make that will avoid it. So, if we find something with a score of beta or better, it has been proven that this whole node is not going to happen, so the rest of the legal moves do not have to be searched.
 
If a move results in a score that is greater than alpha, but less than beta, this is the move that the side to move is going to plan to play, unless something changes later on. So alpha is increased to reflect this new value.
 
The more you give the algorithm depth levels, the more it can predict the best play in the future. So, I made the easy level have 4 levels of recursion (checks for the computer turn, the player next and the computer next) and the normal have 7 levels, and the hard have 10 levels of recursion to make better estimates while having longer processing time.
 
 
## Architecture
When we googling for **"clean code"** we can find a name **Uncle Bob** is: he formulated the First Five Principles of Object Oriented Design (which were later given acronym S.O.L.I.D), was among original authors of Manifesto for Agile Software Development. Since Uncle Bob unconditionally states that the only way to move fast is to keep the code clean, and since Android does not encourage us to write clean code, it is our job as professional software developers to find ways to keep our code clean at all times.
 
Proper **MVC architecture** implementations have the following characteristics:
 
* Readable and maintainable code
* Modular code which provides high degree of flexibility
* More testable code (especially in context of unit testing)
* Code which is fun to work with
 
The above characteristics are generally associated with “clean code”. Therefore, following Uncle Bob’s reasoning, adoption of  **MVC architecture** allows me to “go faster” with this project.
 
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/Screen%20Shot%202017-06-04%20at%207.32.00%20PM.png" width ="300px"/>
 
## App Workflow and Screenshots
 
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/app_flow_diagram.png" />
 
The versions of the Connect 4 Android app consists of three UI screens: 
 
### Splash Screen
Splash screen is a simple screen that shows the app logo and name to the user, the screen visible on each startup of the app for a two second.
 
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/splash.png" width=200px/>
 
### Game Menu Screen
Game menu screen offer options to change the game play mode. The different options available on the menu are:
* Play with- The user can choose either play with the smart phone or a different player(friend) in the same phone.
* Choose color disc- The game keys are two color discs- Red and Yellow, This option allows the user to select the color disc.
* First turn- When play with computer or friend the first move is default by user. this option allows to switch the first move.
* Difficulty- This option is only available when we play with computer. This difficulty level of the game.
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/menu1.png" width=200px/>                               <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/menu2.png" width=200px/>
 
### Game play Screen 
Game play screen bring a board with 6 rows and 7 column, the user can drop a disc into any non full column by tapping on the cells. Game play screen will also show the winner status. From this window user can either stop or restart the current play. Stopping the play will bring user to the menu screen.
 
<img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/gameplay1.png" width=200px/>                               <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/gameplay2.png" width=200px/>                               <img src="https://github.com/rahulmmohan/Connect4_Android/blob/master/Files/gameplay3.png" width=200px/>
 
 
## Open in Android Studio
To open one of this code in Android Studio, begin by checking out to the master branch, and then open the **Connect4_Android/** directory in Android Studio. The following series of steps illustrate how to open this.
 
Clone the repository:
 
```
git clone https://github.com/rahulmmohan/Connect4_Android.git
```
 
1. Open Android Studio and launch the Android SDK manager from it (Tools | Android | SDK Manager)
1. Check that these two components are installed and updated to the latest version. Install or upgrade
   them if necessary.
   1. *Android SDK Platform Tools*
   2. *Android Support Library*
1. Return to Android Studio and select *Import Project*
1. Select "Import from existing model - Gradle"
1. Compile and run.
 
## TO DO
 
1. Multiplayer support: User can compete with other player by using bluetooth or wifi.
1. Achievements and leaderboard support.
1. Tablet devices support.
1. Android Wear support.
 
 ## See my other projects
 ### Whirly bird
 A simple helicopter game, based on the Spectacular flappy bird game built with the libgdx.
 [Google Playstore](https://play.google.com/store/apps/details?id=games.factory72.whirly_8)
 
 ### Override Android
 Android development tutorial blog - www.overrideandroid.info
 
