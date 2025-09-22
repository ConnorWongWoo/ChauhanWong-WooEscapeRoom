/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;
/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
{
  /* TO-DO: Process game commands from user input:
      right, left, up, down: move player size of move, m, if player try to go off grid or bump into wall, score decreases
      jump over 1 space: player cannot jump over walls
      pick up prize: score increases, if there is no prize, penalty
      help: display all possible commands
      end: reach the far right wall, score increase, game ends, if game ends without reaching far right wall, penalty
      replay: shows number of player steps and resets the board, player or another player can play the same board
        
      if player land on a trap, spring a trap to increase score: the program must first check if there is a trap, if none exists, penalty
      Note that you must adjust the score with any method that returns a score
      Optional: create a custom image for player - use the file player.png on disk
    */
  public static void main(String[] args) 
  {      
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    GameGUI game = new GameGUI();
    game.createBoard();

    // size of move
    int m = 30; 
    // individual player moves
    int px = 0;
    int py = 0; 
    
    int score = 0;

    Scanner in = new Scanner(System.in);
    String[] validCommands = {
      // Right
      "right", "r", "jr",
      // Left
      "left", "l", "jl",
      // Up
      "up", "u", "ju",
      // Down
      "down", "d", "jd",
      // jump
      "jump", "jumpleft", "jumpright", "jumpup", "jumpdown",
      // Pickup 
      "pickup", "p", 
      // Spring Traps
      "springtrap","st",
      "quit", "q", "replay", "help", "?"};
  
    // set up game
    boolean play = true;
    int moveCount = 21;
    while (play)
    {
      game.setMoveCount(moveCount); 
      game.repaint(); 
      System.out.print("Enter command:");
      String input = UserInput.getValidInput(validCommands);

	    /* process user commands*/
      if (input.equals("right") || input.equals("r")) {
        score += game.movePlayer(m, 0);
      }
      else if (input.equals("left") || input.equals("l")) {
        score += game.movePlayer(-m, 0);
      }
      else if (input.equals("jr") || input.equals("jumpright")) {
        score += game.movePlayer(2*m, 0);
      }
      else if (input.equals("jl") || input.equals("jumpleft")) {
        score += game.movePlayer(-2*m, 0);
      }
      else if (input.equals("up") || input.equals("u")) {
        score += game.movePlayer(0, -m);
      }
      else if (input.equals("down") || input.equals("d")) {
        score += game.movePlayer(0, m);
      }
      else if (input.equals("jd") || input.equals("jumpdown")) {
        score += game.movePlayer(0, 2*m);
      }
      else if (input.equals("ju") || input.equals("jumpup")) {
        score += game.movePlayer(0, -2*m);
      }
      else if (input.equals("p") || input.equals("pickup")) {
        score += game.pickupPrize();
      }
      else if (input.equals("springtrap") || input.equals("st")) {
        score += game.springTrap(px,py);
      }
      else if (input.equals("quit") || input.equals("q")) {
        game.endGame();
      }
      else if (input.equals("replay") || input.equals("r")) {
        game.replay();
      }
      if (game.isTrap(px, py)) {
        score -= game.springTrap(px, py);
      }
      if (moveCount - 1 == 0) {
        System.out.println("You ran out of moves.");
        break;
      }
      else {
        moveCount -= 1;
      }
      System.out.println("score:"+ score);

    }
    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }

}

