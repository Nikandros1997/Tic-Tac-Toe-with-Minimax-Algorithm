package sample;

import java.util.Scanner;

public class Game {

   private boolean turn = true;

   private Player player1 = new Player(1, 'X');
   private Player player2 = new Player(2, 'O');

   private Board b = new Board();

   public Game() {
      player1 = new Player(1, 'X', 2);
      player1.activateAI();
   }

   public void start() {
      while(true) {
         Player currentPlayer = playingPlayer();

         System.out.print(playerTurn() + ": ");
         System.out.println("Where do you want to put your symbol?");
         String inputString = "";

         if(!currentPlayer.isAI()) {
            Scanner scanner = new Scanner(System.in);
            inputString = scanner.nextLine();
         } else {
            inputString = currentPlayer.choosePlace(b.getBoard());
         }

         if(inputString.equals("q")) {
            System.exit(0);
         } else {
            int row = Integer.parseInt(String.valueOf(inputString.charAt(0)));
            int column = Integer.parseInt(String.valueOf(inputString.charAt(1)));

            Move m = new Move(row, column);

            if(row > 2 || column > 2) {
               System.out.println("Cell does not exist!");
            } else {

               if(b.isFree(row, column)) {
                  b.placeSymbol(currentPlayer, m);

                  System.out.println(b);

                  if(b.checkForWinner(currentPlayer)) {
                     System.out.println(currentPlayer + " won!");
                     break;
                  }

                  if(!b.freePlaces()) {
                     System.out.println("It's a tie!");
                     break;
                  }

                  turn = !turn;
               } else {
                  System.out.println("Cell [" + row + ", " + column +"] not free!");
               }
            }
         }
      }
   }

   private Player playingPlayer() {
      return turn ? player1 : player2;
   }

   private String playerTurn() {
      return turn? player1.toString() : player2.toString();
   }

}
