package sample;

import java.util.ArrayList;
import java.util.Random;

public class Player {

   private int player;
   private char symbol;
   private boolean ai;
   private int difficulty;

   public Player(int player, char symbol) {
      this.player = player;
      this.symbol = symbol;
   }

   public Player(int player, char symbol, int difficulty) {
      this.player = player;
      this.symbol = symbol;
      this.difficulty = difficulty;
   }

   public void activateAI() {
      this.ai = true;
   }

   public boolean isAI() {
      return ai;
   }

   public ArrayList<Move> availableMoves(char[][] board) {
      ArrayList<Move> freeSpots = new ArrayList<>();

      for(int i = 0; i < 3; i++) {
         for(int j = 0; j < 3; j++) {
            if(board[j][i] == ' ') {
               freeSpots.add(new Move(j, i));
            }
         }
      }

      return freeSpots;
   }

   public String choosePlace(char[][] board) {
      Move bestMove = bestMove(board);

      return bestMove.toString();
   }

   public Move bestMove(char[][] board) {
      ArrayList<Move> availableSpots = availableMoves(board);

      Move bestMove = null;

      switch (difficulty) {
         case 1:
            Random rand = new Random();
            int randomMove = rand.nextInt(availableSpots.size());
            bestMove = availableSpots.get(randomMove);
            break;
         case 2:
            bestMove = fitness(board, availableSpots);
            break;
         case 3:
            break;
      }

      return bestMove;
   }

   public Move fitness(char[][] board, ArrayList<Move> availableSpots) {
      int bestScore = -1000;
      Move bestMove = null;

      for(Move m : availableSpots) {
         Board deepCopy = new Board(board);
         deepCopy.placeSymbol(this, m);

         int score = minimax(deepCopy.getBoard(), 0, false);

         if(score > bestScore) {
            bestScore = score;
            bestMove = m;
         }
      }

      return bestMove;
   }

   public int minimax(char[][] board, int depth, boolean isMaximizing) {
      ArrayList<Move> availableSpots = availableMoves(board);

      if(checkIfWinner(board)) {
         return 1;
      } else if(checkIfLoser(board)) {
         return -1;
      } else if(checkForTie(board)) {
         return 0;
      }

      if(isMaximizing) {
         int bestScore = -1000;
         for(Move m : availableSpots) {
            Board deepCopy = new Board(board);
            deepCopy.placeSymbol(this, m);

            int score = minimax(deepCopy.getBoard(), depth + 1, false);

            bestScore = Math.max(bestScore, score);
         }
         return bestScore;
      } else {
         int bestScore = 1000;

         for(Move m : availableSpots) {
            Board deepCopy = new Board(board);
            deepCopy.placeSymbol(new Player(this.player == 1 ? 2 : 1, this.player == 1 ? 'O' : 'X'), m);

            int score = minimax(deepCopy.getBoard(), depth + 1, true);

            bestScore = Math.min(bestScore, score);
         }
         return bestScore;
      }
   }

   private boolean checkForTie(char[][] board) {
      return availableMoves(board).size() == 1;
   }

   private boolean checkIfWinner(char[][] board) {
      int horizontalMatch = 0;
      int verticalMatch = 0;
      int crossMatch1 = 0;
      int crossMatch2 = 0;

      for(int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if(getSymbol() == board[j][i]) {
               horizontalMatch++;
            }

            if(getSymbol() == board[i][j]) {
               verticalMatch++;
            }
         }

         if(horizontalMatch == 3 || verticalMatch == 3) {
            return true;
         } else {
            horizontalMatch = verticalMatch = 0;
         }
      }

      for(int i = 0; i < 3; i++) {
         if(getSymbol() == board[i][i]) {
            crossMatch1++;
         }

         if(getSymbol() == board[i][2 - i]) {
            crossMatch2++;
         }
      }

      return crossMatch1 == 3 || crossMatch2 == 3;
   }

   private boolean checkIfLoser(char[][] board) {
      int horizontalMatch = 0;
      int verticalMatch = 0;
      int crossMatch1 = 0;
      int crossMatch2 = 0;

      for(int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if(board[j][i] != getSymbol() && board[j][i] != ' ') {
               horizontalMatch++;
            }

            if(board[i][j] != getSymbol() && board[i][j] != ' ') {
               verticalMatch++;
            }
         }

         if(horizontalMatch == 3 || verticalMatch == 3) {
            return true;
         } else {
            horizontalMatch = verticalMatch = 0;
         }
      }

      for(int i = 0; i < 3; i++) {
         if(board[i][i] != getSymbol() && board[i][i] != ' ') {
            crossMatch1++;
         }

         if(board[i][2 - i] != getSymbol() && board[i][2 - i] != ' ') {
            crossMatch2++;
         }
      }

      return crossMatch1 == 3 || crossMatch2 == 3;
   }

   public char getSymbol() {
      return symbol;
   }

   public String toString() {
      return "Player " + player + " [" + symbol + "]";
   }
}
