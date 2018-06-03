/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import core.Game;
import core.Player;
import core.ShipPart;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import javax.swing.SwingWorker;

/**
 *
 * @author Sandeep
 */
public class Radar  {
    JPanel grid, radar, enemyShips;
    static JButton[][] cells;
    JLabel blank;
    JLabel[] letters, numbers;
    public static JButton[] enemyShipLabel;
    Set<Integer> firedCells = new HashSet<>();
    ShipPart orderedPair;
    boolean fired;
    
    private class radarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            boolean human = Game.getTurn();
            if (human) {
                // activate only on Player 1's turn
                
                JButton cellFired = (JButton) e.getSource();

                orderedPair = new ShipPart();
                orderedPair.x = (int) cellFired.getClientProperty("x");
                orderedPair.y = (int) cellFired.getClientProperty("y");
                int id = (int) cellFired.getClientProperty("id");

                if ( !(firedCells.contains(id)) ) {
                    System.out.println(orderedPair.x + ", " + orderedPair.y + " selected\n"
                            + "----->>>Set = " + firedCells);

                    fired = true;
                    firedCells.add(id);

                }
                else {
                    Battlefield.appendAndScroll("*Pick another cell\n");
                }
            }
            
        }
    }
    
    public Radar(boolean dontInitialize) { }
    
    public Radar() {
        initComponents();
    }
    
    public void initComponents() {
        
        // set grid panel
        grid = new JPanel();
        // grid layout is 10 x 10, with horiz/vert gap of 3px
        grid.setLayout(new GridLayout(11, 11, 3, 3));
        
        // set letter and number labels
        letters = new JLabel[10];
        numbers = new JLabel[10];
        
        cells = new JButton[10][10];
        Dimension cellSize = new Dimension(30, 30);
        
        
        // add elements to the grid
        int cellID = 0;
        for (int col = 0; col < 11; col++) {
            
            for (int row = 0; row < 11; row++) {

                if (col == 0 && row == 0) {
                    // set blank space for the corner of grid
                    blank = new JLabel("");
                    grid.add(blank);
                }
                else
                {
                    if (row == 0)
                    {
                        // add letter label
                        char alpha = (char) (col - 1 + 'A');
                        letters[col - 1] = new JLabel(Character.toString(alpha));
                        grid.add(letters[col - 1]);
                    }
                    else if (col == 0)
                    {
                        // set number label
                        numbers[row - 1] = new JLabel("" + row);
                        grid.add(numbers[row - 1]);
                    }
                    else
                    {
                        // add cell button
                        cells[row - 1][col - 1] = new JButton();
                        cells[row - 1][col - 1].setPreferredSize(cellSize);
                        cells[row - 1][col - 1].setBackground(Color.GRAY);
                        cells[row - 1][col - 1].setEnabled(false);
                        cells[row - 1][col - 1].putClientProperty("x", row - 1);
                        cells[row - 1][col - 1].putClientProperty("y", col - 1);
                        cells[row - 1][col - 1].putClientProperty("id", cellID);
                        cells[row - 1][col - 1].addActionListener(new radarListener());

                        grid.add(cells[row - 1][col - 1]);

                        cellID++;
                    }
                }
                
            }
            
        }
 
        
        // set display for P2's ships
        enemyShips = new JPanel();
        enemyShips.setBorder(BorderFactory.createTitledBorder("Enemy"));
        
        enemyShipLabel = new JButton[5];
        for (int i = 0; i < 5; i++)
        {
            enemyShipLabel[i] = new JButton("Ship: " + (i + 1));
            enemyShipLabel[i].setBackground(Color.WHITE);
            enemyShipLabel[i].setEnabled(false);
            
            enemyShips.add(enemyShipLabel[i]);
        }
        
        Dimension perDim = new Dimension(100, 300);
        enemyShips.setPreferredSize(perDim);
        
        
        // add all the components together
        radar = new JPanel();
        // border layout with horiz/vert gap of 2px
        radar.setLayout(new BorderLayout(2, 2));
        
        radar.add(grid, BorderLayout.CENTER);
        radar.add(enemyShips, BorderLayout.EAST);
        
        Dimension minDimRadar = new Dimension(375, 375);
        Dimension perDimRadar = new Dimension(375, 375);
        radar.setMinimumSize(minDimRadar);
        radar.setPreferredSize(perDimRadar);
        radar.setBorder(BorderFactory.createTitledBorder("Radar"));
    }
    
    public ShipPart getCellTofire() {
        fired = false;
        
        Thread t1 = new Thread();
        
        while (!fired) {
            try {
                t1.sleep(500);
                //System.out.println("waiting...");
            } catch (InterruptedException ex) {
                Logger.getLogger(Radar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return orderedPair;
    }
    
    public void record(int x, int y, boolean hit) {
        if (hit) {
            cells[x][y].setBackground(Color.RED);
        }
        else
            cells[x][y].setBackground(Color.WHITE);
    }
}
