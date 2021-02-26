import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game in a very
 * simple GUI window.
 *
 * @author Lynn Marshall
 * @version November 8, 2012
 *
 *@author Mohamed Ghazal
 *@version March 24, 2019
 */

public class TicTacToeFrame extends TicTacToe
{
   private JTextArea status; // text area to print game status

   public TicTacToeFrame()
   {
      JFrame frame = new JFrame("tictactoe");
      Container contentPane = frame.getContentPane();
      contentPane.setLayout(new BorderLayout());
      status = new JTextArea(20,10);
      JScrollPane pane = new JScrollPane(status);
      contentPane.add(pane,BorderLayout.CENTER);
       // move cursor to end

      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit when we hit the "X"
      frame.pack(); // pack everthing into our frame
      frame.setResizable(true); // we can resize it
      frame.setVisible(true); // it's visible
   }

   /**
    * Prints the board to the GUI using toString().
    */
    public void print()
    {
        status.append("\n" + toString());
        status.setCaretPosition(status.getDocument().getLength());
    }

}
