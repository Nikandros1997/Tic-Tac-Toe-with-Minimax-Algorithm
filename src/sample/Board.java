package sample;

public class Board {

   private char[][] board = new char[3][3];

   public char[][] getBoard() {
      return board;
   }

   public Board(char[][] board) {
      for(int i = 0; i < 3; i++) {
         for(int j = 0; j < 3; j++) {
            this.board[j][i] = board[j][i];
         }
      }
   }

   public Board() {
      for(int i = 0; i < 3; i++) {
         for(int j = 0; j < 3; j++) {
            board[j][i] = ' ';
         }
      }
   }

   public void placeSymbol(Player p, Move m) {
      board[m.getRow()][m.getColumn()] = p.getSymbol();
   }

   public boolean isFree(int row, int column) {
      return board[row][column] == ' ';
   }

   public boolean checkForWinner(Player currentPlayer) {

      int horizontalMatch = 0;
      int verticalMatch = 0;
      int crossMatch1 = 0;
      int crossMatch2 = 0;

      for(int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if(currentPlayer.getSymbol() == board[j][i]) {
               horizontalMatch++;
            }

            if(currentPlayer.getSymbol() == board[i][j]) {
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
         if(currentPlayer.getSymbol() == board[i][i]) {
            crossMatch1++;
         }

         if(currentPlayer.getSymbol() == board[i][2 - i]) {
            crossMatch2++;
         }
      }

      return crossMatch1 == 3 || crossMatch2 == 3;
   }

   public boolean freePlaces() {
      for(int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if(board[j][i] == ' ') {
               return true;
            }
         }
      }
      return false;
   }

   public String toString() {
      String output = "";

      for(int i = 0; i < 3; i++) {
         for(int j = 0; j < 3; j++)
            output += board[j][i] + ((j < 2)? "|" : "");

         output += "\n";
         if(i < 2)
            output += "-+-+-\n";
      }
      return output;
   }
}
