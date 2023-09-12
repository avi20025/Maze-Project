/*
 * Avinash Vadivelu
 * axv200086
 * .004
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/*
 * This class generates a maze using an object from the MazeGenerator class with a press of a button and user input
 * for row size and column size.
 */

public class MazeSimulation {
   public static void main(final String args[]) {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter row size:");
    int rowSize = input.nextInt();
    System.out.println("Enter column size:");
    int colSize = input.nextInt();
    JButton btn = new JButton("Click to draw maze");
      MazeGenerator mazeGen = new MazeGenerator(colSize, rowSize);
      ActionListener actionListener = new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            try {
                mazeGen.output();
            } catch (IOException e) {
                e.printStackTrace();
            }
         }
      };
      btn.setActionCommand("FirstButton");
      btn.addActionListener(actionListener);
      JOptionPane.showMessageDialog(null, btn);
   }
}