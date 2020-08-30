import java.util.Scanner;

public class TicTacToe {
  private static String[] board;

  public static void main(String[] args) {
    board = new String[9];
    Scanner in = new Scanner(System.in);
    generateEmptyBoard();
    AIPlayer ai = new AIPlayer("O", false, board);
    System.out.println("Welcome to TicTacNo. Good luck.");
    System.out.println("X will go first.");
    printBoard();
    int numInput;

    // While a winner has not been detected
    while (detectWinner().equals("none")) {
      // If it is the AI's turn, it makes its move and indicates a change in turn.
      if (ai.isTurn()) {
        ai.move();
        System.out.println();
        System.out.println("The A.I. has made its move.");
        System.out.println("The board is now: ");
        ai.changeTurn();
        printBoard();
        continue;
      }
      // Otherwise, take input from the player and make the appropriate move.
      System.out.println("Please enter the slot number of your move.");
      numInput = in.nextInt();
      if (numInput < 1 || numInput > 9 || isTaken(numInput)) {
        System.out.println("Invalid number! Please enter an available number between 1 and 9.");
        continue;
      }
      board[numInput-1] = "X";
      ai.changeTurn();
    }
  }

  /**
   * Check if a set of three indices form a completed line of a single letter in String[] board.
   * @param firstIndex the index of the first element
   * @param secondIndex the index of the second element
   * @param thirdIndex the index of the third element
   * @return a boolean, whether or not the indices form a uniform line.
   */
  private static boolean isLine(int firstIndex, int secondIndex, int thirdIndex) {
    return board[firstIndex].equals(board[secondIndex]) && board[firstIndex].equals(board[thirdIndex]);

  }

  /**
   * Check all eight possible winning configurations and determine the winner if there is one.
   * @return the winning letter, "draw", or "none"
   */
  private static String detectWinner() {
    if (isLine(0, 1, 2)) {
      System.out.println(board[0] + " " +
          "is the winner!");
      return board[0];
    }
    if (isLine(3, 4, 5)) {
      System.out.println(board[3] + " is the winner!");
      return board[3];
    }
    if (isLine(6, 7, 8)) {
      System.out.println(board[6] + " is the winner!");
      return board[6];
    }
    if (isLine(0, 3, 6)) {
      System.out.println(board[0] + " is the winner!");
      return board[0];
    }
    if (isLine(1, 4, 7)) {
      System.out.println(board[1] + " is the winner!");
      return board[1];
    }
    if (isLine(2,5, 8)) {
      System.out.println(board[2] + " is the winner!");
      return board[2];
    }
    if (isLine(0, 4, 8)) {
      System.out.println(board[0] + " is the winner!");
      return board[0];
    }
    if (isLine(2, 4, 6)) {
      System.out.println(board[2] + " is the winner!");
      return board[2];
    }
    for (int i = 0; i < 9; i++) {
      if (board[i].equals(String.valueOf(i+1))) {
        break;
      } else if (i == 8) {
        System.out.println("The game has ended in a draw.");
        return "draw";
      }
    }
    return "none";
  }

  /**
   * Check whether the user's input is an available space.
   * @param numInput the input provided by the user
   * @return a boolean representing the availability of the user's space, where true is taken.
   */
  private static boolean isTaken(int numInput) {
    return !board[numInput-1].equals(String.valueOf(numInput));
  }


  /**
   * Generates an empty board represented by the one-dimensional String[] board.
   */
  private static void generateEmptyBoard() {
    for (int i = 0; i < 9; i++) {
        board[i] = String.valueOf(i+1);
    }
  }

  /**
   * Prints the String[] board as three lines, in the format of a Tic Tac Toe board.
   */
  private static void printBoard() {
    for(int i = 0; i < 9; i++) {
      System.out.print(board[i]);
      if ((i+1) % 3 == 0) {
        System.out.println();
      } else {
        System.out.print(" | ");
      }
    }
  }
}
