/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UI.Radar;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Sandeep
 */
public class Player {
    public Grid board;
    public Ship ships[];
    public int army = 5;
	
    public Player() {
        board = new Grid();
        ships = new Ship[5];
        
        ships[0] = new Ship(2);
        ships[1] = new Ship(3);
        ships[2] = new Ship(3);
        ships[3] = new Ship(4);
        ships[4] = new Ship(5);  
    }
    
    public void manualSetup(boolean human) {
        if (human) {
            // get user input
            // ***CHECK FOR OUT-OF-BOUNDS***
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter x origin[a-j]: ");
            char xChar = scanner.next().charAt(0);
            // 'A' and 'a' = 10
            int x = Character.getNumericValue(xChar) - 10;

            System.out.println("Enter y origin[1-10]: ");
            int y = scanner.nextInt() - 1;

            System.out.println("Ship number[1-5]: ");
            int num = scanner.nextInt() - 1;

            System.out.println("Vertical? y or n: ");
            String orien = scanner.next();

            // get orientation of ship
            boolean vertical;
            if (orien.matches("y"))
                vertical = true;
            else
                vertical = false;

            // initialize the ship parts
            ships[num].setShip(x, y, vertical, board);

            // print those values
            ships[num].printLocation();
        }
        else {
            Random rand = new Random();
            
            for (int i = 0; i < 5; i++) {
                boolean valid;
                do {
                    int x = rand.nextInt(10);
                    int y = rand.nextInt(10);
                    boolean vertical = rand.nextBoolean();

                    System.out.println("p2: " + x + " " + y + " " + vertical);

                    valid = board.isValid(x, y, vertical, ships[i]);
                    if (valid)
                        ships[i].setShip(x, y, vertical, board);
                    
                } while (!valid);
                
                ships[i].printLocation();
            }
        }
    }
    
    public void fire(int x, int y, boolean human) {      
        if (board.grid[x][y]){
            System.out.println("->It's a hit! " + x + " " + y);
                    
            boolean foundDamage = false;
            // go through Player's ships[] to record
            // the hit and check if the ship sunk
            for (int i = 0; i < 5; i++) {
                // if this ship has already sunk, skip it
                if (ships[i].sunkenShip)
                    continue;
                
                System.out.println("ship # " + (i + 0));
                
                // record hit and check if it sank
                foundDamage = ships[i].hit(x, y);
                if (foundDamage) {
                    // if ship sank, health - 1
                    if (ships[i].sunkenShip) {
                        army--;
                        
                        System.out.println("Ships left: " + army);
                    }
                    
                    // break once we found the hit ship
                    break;
                }
            }
            // record the correct peg color on the upper board panel
            this.board.record(x, y, true, human);
            
            return;
        }
        
        System.out.println("->It's a miss...");
        this.board.record(x, y, false, human);
    }    
}
