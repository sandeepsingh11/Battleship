/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import core.Player;
import core.ShipPart;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JPanel grid, radar, alphaPanel, numPanel;
    static JButton[][] cells;
    JLabel[] letters, numbers;
    ShipPart orderedPair;
    boolean fired;
    
    private class radarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton cellFired = (JButton) e.getSource();
            int x = (int) cellFired.getClientProperty("x");
            int y = (int) cellFired.getClientProperty("y");
            orderedPair = new ShipPart();
            
            System.out.println("radarListener!");
            orderedPair.x = x;
            orderedPair.y = y;
            
            if ( (orderedPair.x != -1) && (orderedPair.y != -1) ) {
                System.out.println(orderedPair.x + ", " + orderedPair.y + " selected");

                fired = true;    
            }
        }
    }
    
    public Radar(boolean dontInitialize) { }
    
    public Radar() {
        initComponents();
    }
    
    public void initComponents() {
        // set letter and number labels
        alphaPanel = new JPanel(new GridLayout(0, 10));
        letters = new JLabel[10];
        numPanel = new JPanel(new GridLayout(10, 0));
        numbers = new JLabel[10];
        
        for (int i = 0; i < 10; i++) {
            
            // add letter
            char alpha = (char) (i + 'A');
            letters[i] = new JLabel(Character.toString(alpha));
            alphaPanel.add(letters[i]);
            
            // add number
            numbers[i] = new JLabel("" + (i + 1));
            numPanel.add(numbers[i]);
        }
        
        
        // set grid panel
        grid = new JPanel();
        // grid layout is 10 x 10, with horiz/vert gap of 3px
        grid.setLayout(new GridLayout(10, 10, 3, 3));
        
        cells = new JButton[10][10];
        Dimension cellSize = new Dimension(30, 30);
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 10; row++) {
                cells[row][col] = new JButton();
                cells[row][col].setPreferredSize(cellSize);
                cells[row][col].setBackground(Color.GRAY);
                cells[row][col].setEnabled(false);
                cells[row][col].putClientProperty("x", row);
                cells[row][col].putClientProperty("y", col);
                cells[row][col].addActionListener(new radarListener());
                
                grid.add(cells[row][col]);
            }
        }
        
        
        // add all components together
        radar = new JPanel();
        // border layout with horiz/vert gap of 2px
        radar.setLayout(new BorderLayout(2, 2));
        
        radar.add(alphaPanel, BorderLayout.NORTH);
        radar.add(numPanel, BorderLayout.WEST);
        radar.add(grid, BorderLayout.CENTER);
        
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
