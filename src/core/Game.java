/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UI.Battlefield;
import UI.BattleshipUI;
import UI.Radar;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 *
 * @author Sandeep
 */
public class Game{
    private Player p1;
    private Player p2;
    BattleshipUI UI;
    ShipPart orderedPair;
    public static AI ai;
    private static boolean human;
    

    public Game() {
        p1 = new Player();
        p2 = new Player();
        
        UI = new BattleshipUI(getP1(), this);

        //playManual();
    }
    
    public void playManual() {
        // to test the back end, I will use the manual
        // implementation of ordered pairs.

        getP1().manualSetup(true);
        getP2().manualSetup(false);

        human = true;
        
        // play until one if the player's health reaches zero
        while (getP1().getArmy() > 0 && getP2().getArmy() > 0) {
            // p1's turn
            if (human) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Select x-coordinate to fire: ");
                int x = scanner.nextInt();
                System.out.println("Select y-coordinate to fire: ");
                int y = scanner.nextInt();

                getP2().fire(x, y, true, ai);
                human = false;
            }
            else { // p2's turn
                Random rand = new Random();
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);

                System.out.println("P2: (" + x + ", " + y + ")");
                
                getP1().fire(x, y, false, ai);
                
                human = true;
            }
        }
        
        if (getP1().getArmy() == 0)
            System.out.println("Player 2 wins!");
        else
            System.out.println("Player 1 wins!");
    }
    
    public void play(Battlefield battlefield, Radar radar) {
        // set up CPU's ships
        getP2().manualSetup(false);
        ai = new AI();
        
        human = true;
        
        // play until one of the players' health reaches zero
        while (getP1().getArmy() > 0 && getP2().getArmy() > 0) {

            // p1's turn
            if (human) {
                Battlefield.appendAndScroll("\n- P1 -\n");
                System.out.println("--> Select area to fire");
                Battlefield.appendAndScroll("--> Select cell to fire:\n");
                
                // get cell to fire at
                orderedPair = new ShipPart();
                orderedPair = radar.getCellTofire();
                System.out.println("Firing at: " + orderedPair.x + ", " + orderedPair.y);
                Battlefield.appendAndScroll("Firing at:" + orderedPair.x + ", " + orderedPair.y + "\n");
                
                getP2().fire(orderedPair.x, orderedPair.y, true, ai);

                // add 2 second pause before revealing the hit
                Thread t1 = new Thread();
                try {
                    System.out.println("WAIT");
                t1.sleep(2000);
                } catch (InterruptedException ex) {
                Logger.getLogger(Radar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                human = false;
                
                //p2.army = 0;
            }
            else
            { // p2's turn
                Battlefield.appendAndScroll("\n- P2 -\n");
                
                if (ai.active) {
                    // do ai stuff here
                    orderedPair = ai.chooseCell(ai);
                }
                else {
                    boolean duplicate;
                    
                    do {
                        Random rand = new Random();
                        orderedPair.x = rand.nextInt(10);
                        orderedPair.y = rand.nextInt(10);
                        
                        if ( (Battlefield.cells[orderedPair.x][orderedPair.y].getBackground()) == Color.WHITE ||
                             (Battlefield.cells[orderedPair.x][orderedPair.y].getBackground()) == Color.RED )
                            duplicate = true;
                        else
                            duplicate = false;
                        
                        // generate new ordered pair if this cell has already been selected
                    } while (duplicate);
                }
                
                System.out.println("P2: (" + orderedPair.x + ", " + orderedPair.y + ")");
                Battlefield.appendAndScroll("P2: (" + orderedPair.x + ", " + orderedPair.y + ")\n");
                
                getP1().fire(orderedPair.x, orderedPair.y, false, ai);

                human = true;
            }

        }
        
        
        // some player reached zero, end of game
        int playAgain;
        if (getP1().getArmy() == 0)
        {
            Battlefield.appendAndScroll("Player 2 wins!");
            playAgain = JOptionPane.showConfirmDialog(null, "Shucks, P2 won...\nPlay again?", 
                    "Play again?", JOptionPane.YES_NO_OPTION);
        }
        else
        {
            Battlefield.appendAndScroll("Player 1 wins!");
            playAgain = JOptionPane.showConfirmDialog(null, "YAY, P1 WON!\nPlay again?", 
                    "Play again?", JOptionPane.YES_NO_OPTION);
        }
        
        // reset, exit, or do nothing
        if (playAgain == YES_OPTION)
        {
            // reset option
            int reset = JOptionPane.showConfirmDialog(null, "Reset the game?",
                    "Reset game?", JOptionPane.YES_NO_OPTION);
            
            if (reset == YES_OPTION)
                UI.resetGame();
        }
        else
        {
            // exit option
            int close = JOptionPane.showConfirmDialog(null, "Exit Battleship?", 
                    "Exit game?", JOptionPane.YES_NO_OPTION);
            
            if (close == YES_OPTION)
                System.exit(0);
        }
    }
    
    public void resetPlayers() {
        p1 = null;
        p1 = new Player();
        
        p2 = null;
        p2 = new Player();
    }

    /**
     * @return the p1
     */
    public Player getP1() {
        return p1;
    }

    /**
     * @return the p2
     */
    public Player getP2() {
        return p2;
    }
    
    public static boolean getTurn() {
        return human;
    }
}
