/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UI.Battlefield;
import UI.Radar;

/**
 *
 * @author Sandeep
 */
public class Grid {
    public boolean grid[][], upperBoard[][];
    Radar radar;
    Battlefield battlefield;
    
    public Grid() {
        // A - J x 1 - 10
        grid = new boolean[10][10];
        
        upperBoard = new boolean[10][10];
    }
    
    public boolean isValid(int x, int y, boolean orientation, Ship ship, boolean human) {
        // vert orientation
        if (orientation) {
            for (int i = 0; i < ship.size; i++) {
                
                // check if ship is going out-of-bounds
                if ((y + i) > 9) {
                    if (human) {
                        System.out.println("ship reached out of bounds: y = " + (y + i));
                        Battlefield.text.append("ship is out-of bounds; try again\n");
                    }
                    
                    return false;
                }
                
                // check if space is occupied
                if (grid[x][y + i]){
                    boolean sameShip = false;
                    
                    for (int k = 0; k < ship.size; k++) {
                        
                        // if space occupied is from the currently selected ship
                        if ( (x == (ship.part[k].x)) && ((y + i) == (ship.part[k].y)) ) {
                            // cell occupied, BUT we are replacing this part anyways; ignore
                            sameShip = true;
                        }
                    }
                    
                    // if occupied space is not this ship, then invalid placement
                    if (!sameShip) {
                        // cell is already occupied; not valid
                        if (human) {
                            System.out.println("space is already occupied: " + x + " " + (y + i));
                            Battlefield.text.append("space" + x + " " + (y + i) + " is already occupied!\n");
                        }
                        
                        return false;
                    }
                }
                
            }
        } else { // horiz orientation
            for (int i = 0; i < ship.size; i++) {
                
                // check if ship is going out-of-bounds
                if ((x + i) > 9) {
                    // ship is going out-of-bounds
                    if (human) {
                        System.out.println("ship reached out of bounds: x = " + (x + i));
                        Battlefield.text.append("ship is out-of bounds; try again\n");
                    }
                    
                    return false;
                }
                
                // check if space is occupied
                if (grid[x + i][y]){
                    boolean sameShip = false;
                    
                    for (int k = 0; k < ship.size; k++) {
                        
                        // if space occupied is from the currently selected ship
                        if ( ((x + i) == (ship.part[k].x)) && (y == (ship.part[k].y)) ) {
                            // cell occupied, BUT we are replacing this part anyways; ignore
                            sameShip = true;
                        }
                    }
                    
                    // if occupied space is not this ship, then invalid placement
                    if (!sameShip) {
                        // cell is already occupied; not valid
                        if (human) {
                            System.out.println("space is already occupied: " + (x + i) + " " + y);
                            Battlefield.text.append("space" + (x + i) + " " + y + " is already occupied!\n");
                        }
                        
                        return false;
                    }
                }
                
            }
            
        }

        // no errors; valid
        return true;
    }
    
    public void record(int x, int y, boolean hit, boolean human) {
        if (hit) {
            // record hit
            upperBoard[x][y] = true;
        }
        
        if (human) {
            // color cell on the radar (upper board)
            radar = new Radar(false);
            radar.record(x, y, hit);
        }
        else {
            // color cell on the battlefield (lower board)
            battlefield = new Battlefield(false);
            battlefield.record(x, y, hit);
        }
    }
}
