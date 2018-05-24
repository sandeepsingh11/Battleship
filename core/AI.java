/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UI.Battlefield;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Sandeep
 */
public class AI {
    // RESET/REPLACE META INFO FUNC [DONE]-
    // CHECK OUT OF BOUNDS [DONE]-
    // SWITCH DIRECTIONS [DONE]-
    // RECORD CURRENTLYAT DATA [DONE]-
    // CALC CHANGECOMPASS [DONE]-
    // INITIAL DIRECTION [DONE]-
    // WHAT IF SHIPS ARE PLACED TOGETHER???????????????? (STOP AFTER 5 MOVES?)

    ShipPart origin, currentlyAt;
    char direction;
    public boolean keepGoing = true;
    boolean active = false;
    int changeCompass = 0; // alert to change compass pairs

    public AI() {
        this.origin = new ShipPart();
        this.currentlyAt = new ShipPart();
        this.active = false;
        this.changeCompass = 0;
        this.keepGoing = true;
    }

    public void setAI(int x, int y) {
        this.origin.x = this.currentlyAt.x = x;
        this.origin.y = this.currentlyAt.y = y;

        // randomly pick direction to start with
        Random rand = new Random();
        int compass = rand.nextInt(4);
        switch(compass) {
            case 0:
                this.direction = 'n';
                break;

            case 1:
                this.direction = 's';
                break;

            case 2:
                this.direction = 'e';
                break;

            case 3:
                this.direction = 'w';
                break;   
        }
    }

    public ShipPart chooseCell(AI meta) {
        boolean valChanged = false;

        // choose cell here
        do {
            switch(this.direction) {
                case 'n':
                    if (meta.keepGoing) {
                        meta.currentlyAt.y = meta.currentlyAt.y - 1;

                        // check if this location is valid
                        if (validFire(meta.currentlyAt))
                            valChanged = false;
                        else
                            valChanged = true;
                    }
                    else {
                        // switch directions
                        meta.keepGoing = true;
                        meta.direction = 's';
                        meta.changeCompass++;
                        meta.currentlyAt.x = meta.origin.x;
                        meta.currentlyAt.y = meta.origin.y;
                        valChanged = true;
                    }
                break;

                case 's':
                    if (meta.keepGoing) {
                        meta.currentlyAt.y = meta.currentlyAt.y + 1;

                        // check if this location is valid
                        if (validFire(meta.currentlyAt))
                            valChanged = false;
                        else
                            valChanged = true;
                    }
                    else {
                        // switch directions
                        meta.keepGoing = true;
                        meta.direction = 'n';
                        meta.changeCompass++;
                        meta.currentlyAt.x = meta.origin.x;
                        meta.currentlyAt.y = meta.origin.y;
                        valChanged = true;
                    }
                break;

                case 'e':
                    if (meta.keepGoing) {
                        meta.currentlyAt.x = meta.currentlyAt.x + 1;

                        // check if this location is valid
                        if (validFire(meta.currentlyAt))
                            valChanged = false;
                        else
                            valChanged = true;
                    }
                    else {
                        // switch directions
                        meta.keepGoing = true;
                        meta.direction = 'w';
                        meta.changeCompass++;
                        meta.currentlyAt.x = meta.origin.x;
                        meta.currentlyAt.y = meta.origin.y;
                        valChanged = true;
                    }
                break;

                case 'w':
                    if (meta.keepGoing) {
                        meta.currentlyAt.x = meta.currentlyAt.x - 1;

                        // check if this location is valid
                        if (validFire(meta.currentlyAt))
                            valChanged = false;
                        else
                            valChanged = true;
                    }
                    else {
                        // switch directions
                        meta.keepGoing = true;
                        meta.direction = 'e';
                        meta.changeCompass++;
                        meta.currentlyAt.x = meta.origin.x;
                        meta.currentlyAt.y = meta.origin.y;
                        valChanged = true;
                    }
                break;
            }
            
            Battlefield.appendAndScroll("***" + meta.changeCompass + "\n");

        } while (valChanged);

        return meta.currentlyAt;
    }

    private boolean validFire(ShipPart location) {

        // true if within board bounds and valid space
        if ((location.x >= 0) && (location.x <= 9)) {
            if ((location.y >= 0) && (location.y <= 9)) {
                
                if ( (Battlefield.cells[this.currentlyAt.x][this.currentlyAt.y].getBackground()) == Color.GRAY ||
                     (Battlefield.cells[this.currentlyAt.x][this.currentlyAt.y].getBackground()) == Color.BLACK ) {
                    
                    return true;
                }
                
            }
        }

        // out-of-bounds, change direction
        if (this.changeCompass == 2) {

            // in special cases where either the vertical or horizontal
            // direction (N & S or E % W) is used but the ship isn't
            // there, then change the directional pair

            if ((this.direction == 'n') || (this.direction == 's')) {
                this.direction = 'w';
                this.changeCompass++;
            }
            else {
                this.direction = 'n';
                this.changeCompass++;
            }
        }
        else {
            // else just flip the directional pair

            switch (this.direction) {
                case 'n':
                    this.direction = 's';
                    this.changeCompass++;
                    break;

                case 's':
                    this.direction = 'n';
                    this.changeCompass++;
                    break;

                case 'e':
                    this.direction = 'w';
                    this.changeCompass++;
                    break;

                default:
                    this.direction = 'e';
                    this.changeCompass++;
                    break;
            }
        }

        this.currentlyAt.x = this.origin.x;
        this.currentlyAt.y = this.origin.y;
        
        return false;
    }

    protected void resetAI(AI meta) {
        meta.active = false;
        meta.changeCompass = 0;
        meta.keepGoing = true;
    }
}
