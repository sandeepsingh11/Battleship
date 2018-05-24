/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import core.Game;
import core.Grid;
import core.Player;
import core.Ship;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Sandeep
 */
public class Battlefield {
    JPanel battlefield;
    public static JButton[][] cells;
    JPanel ships;
    public static JButton[] shipBtn;
    JButton start;
    Player player;
    Set<Integer> selectedShips = new HashSet<>();
    BattleshipUI ui;
    JPanel textbox;
    public static JTextArea text;
    JScrollPane scroll;
    int shipNum = -1;
    
    public Battlefield(boolean dontInitialize) {}
    
    public Battlefield(Player player, BattleshipUI ui) {
        this.player = player;
        this.ui = ui;
        
        initComp();
    }
    
    class shipButton implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            JButton ship = (JButton) e.getSource();
            // "num" can be [0 - 4]
            shipNum = (int) ship.getClientProperty("num");
            System.out.println("selected ship: " + (shipNum + 1));
        }
    }
    
    class cellButton implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            // user has to pick a ship first
            if (shipNum == -1) {
                System.out.println("Pick a ship first!");
                Battlefield.appendAndScroll("Select a ship first!\n");
            }
            else 
            {
                JButton cell = (JButton) e.getSource();
                int x = (int) cell.getClientProperty("x");
                int y = (int) cell.getClientProperty("y");
                System.out.println("selected cell: (" + x + ", " + y + ")");

                // choose vertical or horizontal orientation for the
                // ship placement. Then set the ship on the board
                class orienListener implements ActionListener 
                {
                    @Override
                    public void actionPerformed (ActionEvent ea) {
                        boolean valid;
                        
                        if (ea.getActionCommand().equals("Vertical")) 
                        {
                            System.out.println("vert chosen");
                            
                            // valid if within bounds and spaces are not occupied (by a diff ship)
                            valid = player.board.isValid(x, y, true, player.ships[shipNum]);
                            
                            // if valid, then place ship on board
                            if (valid)
                            {                                
                                // if it's the same ship, then reset
                                if (selectedShips.contains(shipNum)) 
                                {
                                    System.out.println("Ship #" + (shipNum + 1) + " is already in use");
                                    Battlefield.appendAndScroll("Ship #" + (shipNum + 1) + " is already in use\n");
                                    // reset the old ship's position and bg color
                                    resetShip(player.ships[shipNum], player.board);
                                }
                                
                                // SET ship's x & y values, and set corresponding board locations to true
                                player.ships[shipNum].setShip(x, y, true, player.board);
                                
                                // set bg color for ship placement on the board
                                for (int i = 0; i < player.ships[shipNum].size; i++)
                                    cells[x][y + i].setBackground(Color.black);
                                
                                // make ship button green to confirm ship placement
                                shipBtn[shipNum].setBackground(Color.green);
                                
                                // keeps track of placed ships
                                selectedShips.add(shipNum);
                            }
                            
                        }
                        else 
                        {
                            System.out.println("horiz chosen");
                            
                            // valid if within bounds and spaces are not occupied (by a diff ship)
                            valid = player.board.isValid(x, y, false, player.ships[shipNum]);
                            
                            // if valid, then place ship on board
                            if (valid)
                            {
                                // if it's the same ship, then reset
                                if (selectedShips.contains(shipNum)) 
                                {
                                    System.out.println("Ship #" + (shipNum + 1) + " is already in use");
                                    Battlefield.appendAndScroll("Ship #" + (shipNum + 1) + " is already in use\n");
                                    // reset the old ship's position and bg color
                                    resetShip(player.ships[shipNum], player.board);
                                }
                                
                                // SET ship's x & y values, and set corresponding board locations to true
                                player.ships[shipNum].setShip(x, y, false, player.board);
                                
                                // set bg color for ship placement on the board
                                for (int i = 0; i < player.ships[shipNum].size; i++)
                                    cells[x + i][y].setBackground(Color.black);
                                
                                // make ship button green to confirm ship placement
                                shipBtn[shipNum].setBackground(Color.green);
                                
                                // keeps track of placed ships
                                selectedShips.add(shipNum);
                            }
                            
                        }
                    }
                }

                // popup and its menu items
                JPopupMenu pm = new JPopupMenu("Orientation");
                JMenuItem vert = new JMenuItem("Vertical");
                vert.addActionListener(new orienListener());
                JMenuItem horiz = new JMenuItem("Horizontal");
                horiz.addActionListener(new orienListener());

                pm.add(vert);
                pm.add(horiz);

                // display popup within selected cell
                pm.show(cell, 0, 0);
            }
        }
    }
    
    public void initComp() {
        battlefield = new JPanel();
        // grid layout is 10 x 10, with horiz/vert gap of 3px
        battlefield.setLayout(new GridLayout(10, 10, 3, 3));
        
        // set board where ships will go
        cells = new JButton[10][10];
        Dimension cellSize = new Dimension(30, 30);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cells[x][y] = new JButton();
                cells[x][y].setBackground(Color.GRAY);
                cells[x][y].setPreferredSize(cellSize);
                cells[x][y].putClientProperty("x", x);
                cells[x][y].putClientProperty("y", y);
                cells[x][y].addActionListener(new cellButton());
                
                battlefield.add(cells[x][y]);
            }
        }
        
        Dimension minDimAttempt = new Dimension(375, 325);
        Dimension perDimAttempt = new Dimension(300, 300);
        battlefield.setMinimumSize(minDimAttempt);
        battlefield.setPreferredSize(perDimAttempt);
        battlefield.setBorder(BorderFactory.createTitledBorder("Battlefield"));
    
        
        // set panel to hold ship selections
        ships = new JPanel();
        shipBtn = new JButton[5];
        for (int i = 0; i < 5; i++) {
            shipBtn[i] = new JButton("Ship: " + (i + 1));
            shipBtn[i].putClientProperty("num", i);
            shipBtn[i].addActionListener(new shipButton());
            
            ships.add(shipBtn[i]);
        }
        
        // start button
        start = new JButton("Start");
        start.addActionListener(ui. new Start());
        ships.add(start);
        
        minDimAttempt = new Dimension(80, 325);
        perDimAttempt = new Dimension(80, 300);
        ships.setMinimumSize(minDimAttempt);
        ships.setPreferredSize(perDimAttempt);
        ships.setBorder(BorderFactory.createTitledBorder("Ships"));
        
        // text dialog box
        textbox = new JPanel();
        
        text = new JTextArea("- GAME UPDATES -\n\n");
        text.setEditable(false);
        textbox.add(text);
        
        textbox.setPreferredSize(new Dimension(200, 300));
        textbox.setMinimumSize(new Dimension(200, 300));
        textbox.setBorder(BorderFactory.createTitledBorder("Updates"));
        
        // make textbox scrollable
        scroll = new JScrollPane(text);
        scroll.setPreferredSize(new Dimension(190, 270));
        textbox.add(scroll);
    }
    
    private void resetShip(Ship ship, Grid grid) {
        for (int i = 0; i < ship.size; i++) {
            
            // set ship's previous spaces to false
            int x = ship.part[i].x;
            int y = ship.part[i].y;
            Battlefield.appendAndScroll("*(" + x + ", " + y + ")\n");
            grid.grid[x][y] = false;
            
            // change color on UI board
            cells[ship.part[i].x][ship.part[i].y].setBackground(Color.GRAY);
            
            System.out.println("%%CLEARED: " + x + y);
        }
    }
    
    public void record(int x, int y, boolean hit) {
        if (hit)
            cells[x][y].setBackground(Color.RED);
        else
            cells[x][y].setBackground(Color.WHITE);
    }
    
    public static void appendAndScroll(String str) {
       // add text to textarea box
       text.append(str);
       
       // autoscroll the textarea
       text.setCaretPosition(text.getText().length());
    }
}
