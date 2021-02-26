import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 *
 * @author Lynn Marshall
 * @version November 8, 2012
 *
 *@author Mohamed Ghazal
 *@version March 29, 2019
 */

public class TicTacToe implements ActionListener
{
   public static final String PLAYER_X = "X"; // player using "X"
   public static final String PLAYER_O = "O"; // player using "O"
   public static final String EMPTY = " ";  // empty cell
   public static final String TIE = "T"; // game ended in a tie

   private String player;   // current player (PLAYER_X or PLAYER_O)
   private String winner;   // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
   private int numFreeSquares; // number of squares still free

   private JButton board[][]; // 3x3 array representing the board
   private JButton playAgain;
   private JMenuItem resetItem, quitItem;
   private JLabel text;
   private JLabel oScore;
   private JLabel xScore;
   
   private int row;
   private int col;
   private int xWins;
   private int oWins;
   
   AudioClip win, tie, click;
   

   
   /**
    * Constructs a new Tic-Tac-Toe board.
    */
   public TicTacToe()
   {
     xWins = 0;
     oWins = 0;
     buildBoard();
     //playGame();
   }
   
   /**
    * builds the board with the required layouts and settings to be displayed and sets the
    * sizes and visivility
    */
   private void buildBoard()
   {
     JFrame frame = new JFrame("TicTacToe");
     Container contentPane = frame.getContentPane();
     contentPane.setLayout(new BorderLayout());
     
     
     JPanel grid = new JPanel();
     grid.setLayout(new GridLayout(3,3));
     contentPane.add(BorderLayout.CENTER,grid);
     
     JPanel score = new JPanel();
     score.setLayout(new BoxLayout(score,BoxLayout.Y_AXIS));
     xScore = new JLabel("X : " + xWins);
     score.add(xScore);
     text = new JLabel("Player X's turn");
     score.add(text);
     oScore = new JLabel("O : " + oWins);
     score.add(oScore);
     playAgain = new JButton("Play Again?");
     playAgain.addActionListener(this);
     score.add(playAgain);
     playAgain.setEnabled(false);
     contentPane.add(BorderLayout.WEST,score);
     
     JMenuBar menubar = new JMenuBar();
     frame.setJMenuBar(menubar);
     
     JMenu fileMenu = new JMenu("Game");
     menubar.add(fileMenu);
     
     resetItem = new JMenuItem("New");
     quitItem = new JMenuItem("Quit");
     
     resetItem.addActionListener(this);
     quitItem.addActionListener(this);
     
     fileMenu.add(resetItem);
     fileMenu.add(quitItem);
     
     final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
     resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
     quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
     
     
     board = new JButton[3][3];
      
     for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            board[i][j] = new JButton();  
            board[i][j].setVisible(true);
            grid.add(board[i][j]);
            board[i][j].addActionListener(this);
        }
      }
      
      
     frame.pack(); // pack everthing into our frame
     frame.setResizable(true); // we can resize it
     frame.setSize(500,500);
     frame.setVisible(true); // it's visible
     clearBoard();
   }
   
   /**
    * checks for button or menu presses and performs the action required for the specified
    * object type
    * 
    * 
    */
   public void actionPerformed(ActionEvent e)
   {
        Object o = e.getSource(); // get the action 
        
        // see if it's a JButton
        if (o instanceof JButton) {
            
            JButton button = (JButton)o;
            
            if(button == playAgain){
                          clearBoard();
                        }
                        
            for( int i=0 ; i<3 ; i++){
                for (int j = 0; j<3; j++){
                    
                      if (button == board[i][j]){
                       col = j;
                       row = i;
                       board[i][j].setText(player.equals(PLAYER_X) ? "X":"O");        // fill in the square with player
                       numFreeSquares--; 
                       board[i][j].setEnabled(false);
                       Applet.newAudioClip((URL)TicTacToe.class.getResource("click.wav"));
                   
                                    // see if the game is over
                        if (haveWinner(row,col)){
                          winner = player;
                          printMessage(winner);// must be the player who just went
                          if(winner.equals(PLAYER_X)){
                            }else{
                            }
                          break;
                       }
                       
                       if (numFreeSquares==0){
                          winner = TIE; // board is full so it's a tie
                          printMessage(winner);
                          break;
                       }     
                       
                       if (player.equals(PLAYER_X)){
                            player = PLAYER_O;
                       } else {
                            player = PLAYER_X;
                       }
                       
                       text.setText("Player " + player + "'s turn");
                   }
                }
            } 
        } else { // it's a JMenuItem
            JMenuItem item = (JMenuItem)o;
            
            if (item == resetItem) { // reset
                clearBoard();
                xWins = 0;
                oWins = 0;
            } else if (item == quitItem) { // quit
                System.exit(0);
            }
               
      }
   }
    

   /**
    * Sets everything up for a new game.  Marks all squares in the Tic Tac Toe board as empty,
    * and indicates no winner yet, 9 free squares and the current player is player X.
    */
   private void clearBoard()
    {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            JButton curr = (JButton)board[i][j];
            curr.setEnabled(true);
            curr.setText(EMPTY);
         }
      }
      winner = EMPTY;
      numFreeSquares = 9;
      oScore.setText(" O : " + oWins);
      xScore.setText(" X : " + xWins);
      player = PLAYER_X;     // Player X always hap[oers the first turn.
   }


   // /**
    // * Plays one game of Tic Tac Toe.
    // */

   // private void playGame()
   // {
     // clearBoard(); // clear the board

      // // loop until the game ends
     // while (true) { // game still in progress
                     
     // }

   // }


   /**
    * Returns true if filling the given square gives us a winner, and false
    * otherwise.
    *
    * @param int row of square just set
    * @param int col of square just set
    *
    * @return true if we have a winner, false otherwise
    */
   private boolean haveWinner(int row, int col)
   {
       // unless at least 5 squares have been filled, we don't need to go any further
       // (the earliest we can have a winner is after player X's 3rd move).

       if (numFreeSquares>4) return false;

       // Note: We don't need to check all rows, columns, and diagonals, only those
       // that contain the latest filled square.  We know that we have a winner
       // if all 3 squares are the same, as they can't all be blank (as the latest
       // filled square is one of them).

       // check row "row"
       if ( board[row][0].getText().equals(board[row][1].getText()) &&
            board[row][0].getText().equals(board[row][2].getText()) ) return true;

       // check column "col"
       if ( board[0][col].getText().equals(board[1][col].getText()) &&
            board[0][col].getText().equals(board[2][col].getText()) ) return true;

       // if row=col check one diagonal
       if (row==col)
          if ( board[0][0].getText().equals(board[1][1].getText()) &&
               board[0][0].getText().equals(board[2][2].getText()) ) return true;

       // if row=2-col check other diagonal
       if (row==2-col)
          if ( board[0][2].getText().equals(board[1][1].getText()) &&
               board[0][2].getText().equals(board[2][0].getText()) ) return true;

       // no winner yet
       return false;
   }


   /**
    * Prints the board to standard out using toString().
    */
    public void print()
    {
       
    }
    
    /**
     * updates the dialog box if the game is over and increments the score for the winner
     * and asks to play again. also disables more buttons from the board from being pressed
     */
   public void printMessage(String winner)
   {
        String message;
        for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) { 
            board[i][j].setEnabled(false);
         }
        }
        
        if (winner.equals(TIE)){
           message = "Tie Game!";
           Applet.newAudioClip(TicTacToe.class.getResource("boo.wav"));
        } else if (winner.equals(PLAYER_X)) {
            message = "Player X wins!";
            Applet.newAudioClip(TicTacToe.class.getResource("cheer.wav"));
            xWins++;
        } else {
            message = "Player O wins!";
            Applet.newAudioClip(TicTacToe.class.getResource("cheer.wav"));
            oWins ++;
        }
       
       text.setText(message);
       playAgain.setEnabled(true);
       
   }
   

    /**
    * Returns a string representing the current state of the game.  This should look like
    * a regular tic tac toe board, and be followed by a message if the game is over that says
    * who won (or indicates a tie).
    *
    * @return String representing the tic tac toe game state
    */
    public String toString()
    {
        
        String output = " " + board[0][0]+" | "+board[0][1] + " | " + board[0][2] + " \n" +
                        "-----------------\n" +
                        " " + board[1][0]+" | "+board[1][1] + " | " + board[1][2] + " \n" +
                        "-----------------\n" +
                        " " + board[2][0]+" | "+board[2][1] + " | " + board[2][2] + " \n" ;
              
        if (winner.equals(TIE)){
            output += "Tie! \n";
        }else if (!(winner == EMPTY)){
            output += "The winner is " + winner + "\n";
        }
        return output;
    }
    
}


