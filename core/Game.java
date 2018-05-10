/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UI.Battlefield;
import UI.BattleshipUI;
import UI.Radar;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 *
 * @author Sandeep
 */
public class Game{
    private Player p1;
    private Player p2;
    BattleshipUI battleship;

    public Game() {
        p1 = new Player();
        p2 = new Player();
        
        battleship = new BattleshipUI(getP1(), this);

        //playManual();
    }
    
    public void playManual() {
        // to test the back end, I will use the manual
        // implementation of ordered pairs.

        getP1().manualSetup(true);
        getP2().manualSetup(false);

        boolean human = true;
        
        // play until one if the player's health reaches zero
        while (getP1().army > 0 && getP2().army > 0) {
            // p1's turn
            if (human) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Select x-coordinate to fire: ");
                int x = scanner.nextInt();
                System.out.println("Select y-coordinate to fire: ");
                int y = scanner.nextInt();

                getP2().fire(x, y, true);
                
                human = false;
            }
            else { // p2's turn
                Random rand = new Random();
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);

                System.out.println("P2: (" + x + ", " + y + ")");
                
                getP1().fire(x, y, false);
                
                human = true;
            }
        }
        
        if (getP1().army == 0)
            System.out.println("Player 2 wins!");
        else
            System.out.println("Player 1 wins!");
    }
    
    public void play(Battlefield battlefield, Radar radar) {
        System.out.println("MADE IT!!!");
        // set up CPU's ships
        getP2().manualSetup(false);
        
        boolean human = true;
        
        // play until one of the players' health reaches zero
        while (getP1().army > 0 && getP2().army > 0) {

            // p1's turn
            if (human) {
                boolean setShip = false;
                System.out.println("--> Select area to fire");
                Battlefield.appendAndScroll("--> Select cell to fire:\n");
                
                // get cell to fire at
                ShipPart orderedPair = new ShipPart();
                orderedPair = radar.getCellTofire();
                System.out.println("Firing at: " + orderedPair.x + ", " + orderedPair.y);
                Battlefield.appendAndScroll("Firing at:" + orderedPair.x + ", " + orderedPair.y + "\n");
                
                getP2().fire(orderedPair.x, orderedPair.y, true);

                Thread t1 = new Thread();
                try {
                    System.out.println("WAIT");
                t1.sleep(2000);
                //System.out.println("waiting...");
                } catch (InterruptedException ex) {
                Logger.getLogger(Radar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                human = false;
                
                //p1.army = 0;
            }
            else
            { // p2's turn
                Random rand = new Random();
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);

                System.out.println("P2: (" + x + ", " + y + ")");
                Battlefield.appendAndScroll("P2: (" + x + ", " + y + ")\n");
                
                getP1().fire(x, y, false);

                human = true;
            }

        }
        
        // some player reached zero
        if (getP1().army == 0)
            System.out.println("Player 2 wins!");
        else
            System.out.println("Player 1 wins!");
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
}
