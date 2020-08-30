/**
 * References for implementation and understanding of the minimax algorithm used can be found here:
 * https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
 * https://www.youtube.com/watch?v=KU9Ch59-4vw
 * https://www.youtube.com/watch?v=trKjYdBASyQ
 */

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {
  private String player, opponent;
  private String[] board;
  private boolean turn;

  public AIPlayer(String player, boolean turn, String[] board) {
    this.player = player;
    this.turn = turn;
    this.board = board;
    if (player.equals("X")) {
      opponent = "O";
    } else {
      opponent = "X";
    }
  }

  /**
   * Updates String[] board based on the result of the Minimax algorithm.
   */
  public void move() {
    int[] result = minimax(0, player);
    board[result[1]] = player;
  }


  /**
   * A recursive implementation of the Minimax algorithm using a heuristic evaluation function to analyze
   * possible move sets for both players in order to determine the best move for the A.I. to make.
   *
   * @param level     the level of depth
   * @param curPlayer
   * @return
   */
  public int[] minimax(int level, String curPlayer) {
    List<Integer> potentialMoves = generateMoves();
    int bestScore = (curPlayer.equals(player)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    int currentScore;
    int bestPosition = -1;

    // If there are no remaining moves or the level is 2
    if (potentialMoves.isEmpty() || level == 2) {
      // evaluate the best score to be returned
      bestScore = evaluate();
    } else {
      // If there are remaining moves or the level is less than 2, loop through all possible moves
      for (int move : potentialMoves) {
        // Set each move as though it happens, and recursively call the minimax algorithm with that move.
        board[move] = curPlayer;
        if (curPlayer.equals(player)) {
          // Human player is the maximizing player in this case.
          currentScore = minimax(level + 1, opponent)[0];
          // If the possible score from that move is greater than the current best score,
          // update bestScore and bestPosition.
          if (currentScore > bestScore) {
            bestScore = currentScore;
            bestPosition = move;
          }
        } else {
          // AI is the minimizing player
          currentScore = minimax(level + 1, player)[0];
          if (currentScore < bestScore) {
            bestScore = currentScore;
            bestPosition = move;
          }
        }
        board[move] = String.valueOf(move + 1);
      }
    }
    return new int[]{bestScore, bestPosition};
  }


  /**
   * Sums the scores of each possible line
   *
   * @return score, int representing the sum of the scores
   */
  private int evaluate() {
    int score = 0;
    score += evaluateLine(0, 1, 2);
    score += evaluateLine(3, 4, 5);
    score += evaluateLine(6, 7, 8);
    score += evaluateLine(0, 3, 6);
    score += evaluateLine(1, 4, 7);
    score += evaluateLine(2, 5, 8);
    score += evaluateLine(0, 4, 8);
    score += evaluateLine(2, 4, 6);
    return score;
  }

  /**
   * Evaluate the heuristic scores of a single line on the board
   *
   * @param pos1 the first index
   * @param pos2 the second index
   * @param pos3 the third index
   * @return
   */
  private int evaluateLine(int pos1, int pos2, int pos3) {
    int score = 0;
    if (board[pos1].equals(player)) {
      score = 1;
    } else if (board[pos1].equals(opponent)) {
      score = -1;
    }

    if (board[pos2].equals(player)) {
      if (score == 1) {
        score = 10;
      } else if (score == -1) {
        return 0;
      } else {
        score = 1;
      }
    } else if (board[pos2].equals(opponent)) {
      if (score == -1) {
        score = -10;
      } else if (score == 1) {
        return 0;
      } else {
        score = -1;
      }
    }

    if (board[pos3].equals(player)) {
      if (score > 0) {
        score *= 10;
      } else if (score < 0) {
        return 0;
      } else {
        score = 1;
      }
    } else if (board[pos3].equals(opponent)) {
      if (score < 0) {
        score *= 10;
      } else if (score > 1) {
        return 0;
      } else {
        score = -1;
      }
    }

    return score;
  }

  /**
   * @return a list of available spaces for moves
   */
  private List<Integer> generateMoves() {
    List<Integer> potentialMoves = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      if (board[i].equals(String.valueOf(i + 1))) {
        potentialMoves.add(i);
      }
    }
    return potentialMoves;
  }

  public boolean isTurn() {
    return turn;
  }

  public void changeTurn() {
    this.turn = !turn;
  }


}
