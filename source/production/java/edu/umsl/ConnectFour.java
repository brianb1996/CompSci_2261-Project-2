/*
* Brian Bredahl
* 7/7/2020
* Computer Science 2261 - Object Oriented Programming
* Project 2: Connect Four
*
*
* Connect Four is a two-player board game in which the players alternately drop colored disks into a seven-
* column, six-row vertically suspended grid. The object of the game is to connect four same-colored disks in
* a row, column, or diagonal before your opponent can do so. Please see http://www.ludoteka.com/connect-4.html
* (Links to an external site.)
*
*The program should prompt two players to drop a red or yellow disk alternately in a column of their choosing
* (i.e. 0-6). Whenever a disk is dropped, the program should display the board on the console and determine the
* status of the game (win, draw, or continue). Here is a sample run:
*
*  0   1   2   3   4   5   6
*
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*-------------------------
*
*Please choose a column (0-6) to drop the RED disk:  0 // where the user entered 0
*
*  0   1   2   3   4   5   6
*
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|  R  |    |    |    |     |    |    |
*-----------------------------
*
*Please choose a column (0-6) to drop the YELLOW disk:  0 // where the user 2 also entered 0
*
*  0   1   2   3   4   5   6
*
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|     |    |    |    |     |    |    |
*|  Y  |    |    |    |     |    |    |
*|  R  |    |    |    |     |    |    |
*-----------------------------
*
*Please choose a column (0-6) to drop the RED disk:  1 // where the user 1 entered 1
*
*  0   1   2   3   4   5   6
*
*|     |     |    |    |     |    |    |
*|     |     |    |    |     |    |    |
*|     |     |    |    |     |    |    |
*|     |     |    |    |     |    |    |
*|  Y  |     |    |    |     |    |    |
*|  R  |  R  |    |    |     |    |    |
* -----------------------------
*
* Please choose a column (0-6) to drop the YELLOW disk: ....
*
*
* ... And so on until a user (either Red or Yellow) connects 4 of their characters.
* The program must contain:
* - User input.
* - A multidimensional array as the game board.
* - Winning conditions: 4 Horizontal pieces of the same kind ,4 vertical pieces of the same kind , or 4 diagonal pieces of the same kind.
* - Ensure your program does not terminate if there is bad input and can recover gracefully.
*
* */

package edu.umsl;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.String;







public class ConnectFour {

    // main method
    public static void main(String[] args) {
        int columnSelect = -1;                  // used to store the users input for the column they selected to play a game piece
        int playerAlternate = 0;                // used to alternate between the players
        char player1 = 'R';                     // sets player one to the char R for a red game piece
        char player2 = 'Y';                     // sets player two to the char Y for the yellow game piece
        char current;                           // used store the current players game piece
        boolean badInput, winner;               // bad input is used to return true if the users input is not a integer from 0 to 6, winner is used to store true if one of the players has won
        String message = "Error invalid input\t Please enter a Integer from 0 to 6";
        Scanner inputError = new Scanner(System.in);                    // scanner object used to store invaild inputs in the input buffer
        System.out.println("This is a game of Connect Four\nPlayer 1 is Red\tPlayer 2 is Yellow");

        //outermost do-while loop used to play more games if desired
        do {
            char[][] board = new char[6][7];        //array used to hold the "game piece"

            //second level do-while loop used to continue the game till a winner is decided or the game board is full
            do {
                current = (playerAlternate++ % 2 == 0) ? player1 : player2;         //ternary operator used to alternate between players
                System.out.println(screenOutput(board));

                // innermost do-while loop used to check for valid input and send messages to the user if their input is invalid and a new input needs to be input
                do {
                    badInput = false;

                    // try catch statement used to catch exception for any input that is not a int
                    try {
                        if (current == player1) {
                            System.out.print("Player 1 ");
                        } else {
                            System.out.print("Player 2 ");
                        }
                        columnSelect = userInput();                     // call to the userInput method
                        if (columnSelect < 0 || columnSelect > 6) {     // if the users input is a integer that is not from 0 to 6 this if statement will execute
                            System.out.println(message);
                            badInput = true;

                        } else if (board[0][columnSelect] == 'R' || board[0][columnSelect] == 'Y') {            //if the user selects a column that is full this if statement will execute
                            System.out.println("That column is already full\tPlease select another column");
                            badInput = true;
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println(message);
                        badInput = true;
                        inputError = new Scanner(System.in);        //used to set inputError to a new Scanner object to clear the input buffer
                    }
                } while (badInput);
                pieceDrop(board, columnSelect, current);            // call to the piece drop method
                winner = winnerCheck(board);                        // sets winner equal to the value returned by the winnerCheck method

            } while (!winner && !fullBoard(board));                 // if their is no winner and the board isnt full the game will continue

            System.out.println(screenOutput(board));
            if (winner) {                                           // if the game has a winner
                if (current == player1) {                           // used to display the winner of the game
                    System.out.println(" Player 1 has Won! ");
                } else {
                    System.out.println(" Player 2 has Won! ");
                }
            }else{                                                  // if the game has no winner
                System.out.println("The game has been tied!");
            }
        }while(playAgain());

    }

    //screenOutput method, used to display the game board with and without pieces
    public static String screenOutput(char[][] GameBoard){
        String output = "  0   1   2   3   4   5   6\n";                    // displays 0 to 6 across top of game board
        String line;                                                        // used to store each game spot as a string
        String dash = "|";                                                  // dash used to separate each column
        String empty = "   ";                                               // if the game spot is empty this string is used

        //for loop used to increment through each row
        for(int r = 0; r < 6; r++ ){
            // for loop used to increment through each column
            for(int c = 0; c < 7; c++){
                if(GameBoard[r][c] == 'R' || GameBoard[r][c] == 'Y'){       // if a spot hold a game piece the game piece will be shown on the board
                    line = dash + " " + GameBoard[r][c] + " ";
                }else{
                    line = dash + empty;                                    // if the spot on the game board is empty then nothing will be displayed in its spot
                }
                output = output.concat(line);                               // concatenates each game spot into one string for output
            }
            output = output.concat("|\n");                                  // concatenates a escape new line character on the end of each row
        }
        return output;
    }

    //pieceDrop method - used to determine what row the piece will end up in when a column has been selected
    public static void pieceDrop(char[][] GameBoard, int column, char player){
        System.out.println("\n" + player);
        int rowCount = 0;
        //while loop used to determine where the game piece will be on the game board
        while(rowCount < 6 && (GameBoard[rowCount][column] != 'R' && GameBoard[rowCount][column] != 'Y')){
            rowCount++;
        }
        GameBoard[--rowCount][column] = player;
    }

    //userInput method - used to accept input from the user for the column they wish to play a game piece
    public static int userInput(){
        System.out.print("Enter the Column Number: ");
        Scanner userInput = new Scanner(System.in);
        return userInput.nextInt();
    }

    //winnerCheck method - used tp check if the game has a winner, returns true if there is a winner
    public static boolean winnerCheck(char[][] gameBoard){
        int win = 0;
        int tempR;
        int tempC;
        int r;
        int c;

        // for loop used to check for a horizontal win
        for( r = 0; r < 6; r++ ) {
            for ( c = 1; c < 7; c++) {
                if (gameBoard[r][c - 1] == gameBoard[r][c]) {
                    if(gameBoard[r][c] == 'R' || gameBoard[r][c] =='Y'){
                        win++;
                    }
                    if(win == 3){
                        return true;
                    }
                }else{
                  win = 0;
                }
            }
        }

        // for loop used to check for vertical win
        for ( c = 0; c < 7; c++) {
            for( r = 1; r < 6; r++ ) {
                if (gameBoard[r-1][c] == gameBoard[r][c]) {
                    if(gameBoard[r][c] == 'R' || gameBoard[r][c] =='Y'){
                        win++;
                    }
                    if(win == 3){
                        return true;
                    }
                }else{
                    win = 0;
                }
            }
        }

        // for loop used to partial check for diagonal wins for row 1 to 3 in this direction\
        for( r = 1; r < 4; r++) {
            for (c = 1, tempR = r; tempR < 6 && c < 7; tempR++, c++) {
                if (gameBoard[tempR - 1][c - 1] == gameBoard[tempR][c]) {
                    if (gameBoard[tempR][c] == 'R' || gameBoard[tempR][c] == 'Y') {
                        win++;
                    }
                    if (win == 3) {
                        return true;
                    }
                } else {
                    win = 0;
                }
            }
        }

        // for loop used to partial check for diagonal wins for column 2 to 4 in this direction\
        for( c = 2; c < 5; c++) {
            for (r = 1, tempC = c; r < 6 && tempC < 7; r++, tempC++) {
                if (gameBoard[r - 1][tempC - 1] == gameBoard[r][tempC]) {
                    if (gameBoard[r][tempC] == 'R' || gameBoard[r][tempC] == 'Y') {
                        win++;
                    }
                    if (win == 3) {
                        return true;
                    }
                } else {
                    win = 0;
                }
            }
        }

        // for loop used to partial check for diagonal wins for row 3 to 1 in this direction/
        for( r = 3; r > 0; r--){
            for( c = 5, tempR = r ; c >= 0 && tempR < 6; tempR++ ,c-- ){
                if(gameBoard[tempR-1][c+1] == gameBoard[tempR][c]){
                    if (gameBoard[tempR][c] == 'R' || gameBoard[tempR][c] == 'Y') {
                        win++;
                    }
                    if (win == 3) {
                        return true;
                    }
                } else {
                    win = 0;
                }
            }
        }

        // for loop used to partial check for diagonal wins for column 4 to 2 in this direction/
        for( c = 4; c > 1; c--){
            for( r = 1, tempC = c; r < 6 && tempC > 0; r++, tempC--){
                if(gameBoard[r-1][tempC+1] == gameBoard[r][tempC]){
                    if (gameBoard[r][tempC] == 'R' || gameBoard[r][tempC] == 'Y') {
                        win++;
                    }
                    if (win == 3) {
                        return true;
                    }
                } else {
                    win = 0;
                }
            }
        }
        return false;
    }

    //fullBoard method used to check if the board has been filled, returns true if there are no available place to play a game piece
    public static boolean fullBoard(char[][] gameBoard){
        // only the first row of each column need to be checked because the
        int count = 0;
        for(int c = 0; c < 7; c++){
            if(gameBoard[0][c]== 'R' || gameBoard[0][c]=='Y'){
                count ++;
            }
        }
        return (count == 7);
    }

    //playAgain method, gets input from the user to determine if another game will be played.
    public static boolean playAgain(){
        String input;
        Scanner play = new Scanner(System.in);
        System.out.println("Would you like to play again?\n Enter [ Y ] to play again\t\tEnter Anything else to quit\n");
        System.out.print("Enter your response: ");
        input = play.nextLine();
        return (input.length() == 1 && (input.equals("Y") || input.equals("y")));
    }
}
