/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import core.Game;
import core.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.JPanel;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import javax.swing.SwingWorker;

/**
 *
 * @author Sandeep
 */
public class BattleshipUI implements ActionListener{
    JFrame frame;
    JPanel panel;
    JMenuBar menuBar;
    JMenu gameMenu;
    JMenuItem newGame;
    JMenuItem exitGame;
    Player player;
    Battlefield lowerBoard;
    Radar upperBoard;
    Game game;
    
    public BattleshipUI (Player player, Game game) {
        this.player = player;
        this.game = game;
        
        initCompononts();
    }
    
    public class Start implements ActionListener, Runnable {
        JButton start;
        
        @Override
        public void actionPerformed (ActionEvent e) {
            
            start = (JButton) e.getSource();
            
            // check if all ships have been set. If so, continue to actual game
//            if (lowerBoard.selectedShips.size() != 5) {
//                JOptionPane.showMessageDialog(null, "Whoa there, set all your ships first!");
//            }
//            else
//            {
                // disable/enable corresponding buttons
//            }
            
                System.out.println("EDT? (clicked start) " + isEventDispatchThread());
                
                // prepare board for play
                readyToPlay(start);
            
                // start the game!
                System.out.println("START GAME!");
                Runnable r = new Start();
                Thread t1 = new Thread(r);
                t1.start();
                // goes to run() in a new thread
        }

        @Override
        public void run() {
            System.out.println("in run()!");
            game.play(lowerBoard, upperBoard);
        }
        
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals("Exit Game")) {
            int close = JOptionPane.showConfirmDialog(null, "Confirm to exit Battleship?", 
                    "Exit game?", JOptionPane.YES_NO_OPTION);
            
            if (close == YES_OPTION)
                System.exit(0);
        }
    }
    
    
    public void initCompononts() {
        // set menu bar and its components
        gameMenu = new JMenu("Game");
        newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);
        exitGame = new JMenuItem("Exit Game");
        exitGame.addActionListener(this);
        gameMenu.add(exitGame);
        
        menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        
        frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        
        
        // set upper panel (radar)
        upperBoard = new Radar();
       
        panel = new JPanel();
        // border layout with horiz/vert gap of 3px
        panel.setLayout(new BorderLayout(3, 3));
        
        panel.add(upperBoard.radar, BorderLayout.NORTH);
        
        // set lower panel (battlefield)
        lowerBoard = new Battlefield(player, this);
        
        panel.add(lowerBoard.battlefield, BorderLayout.CENTER);
        panel.add(lowerBoard.ships, BorderLayout.EAST);
        
        frame.add(panel);
        
        // set display
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void readyToPlay(JButton start) {
        
        // disable ship buttons
        for (int i = 0; i < 5; i++) {
            lowerBoard.shipBtn[i].setBackground(Color.white);
            lowerBoard.shipBtn[i].setEnabled(false);
        }
        
        // disable start button
        start.setEnabled(false);
        
        // disable lowerboard (where ships are) and enable upperboard
        // (where the player will fire)
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                lowerBoard.cells[row][col].setEnabled(false);
                upperBoard.cells[row][col].setEnabled(true);
                //upperBoard.cells[row][col].setBackground(Color.white);
            }
        }
        
        System.out.println("->done with readyToPlay()");
    }
    
}
