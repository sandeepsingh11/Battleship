/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author Sandeep
 */
public class ShipPart {
    // set to -1 because the computer automatically sets
    // x and y to 0, so even though, at the testing stage,
    // I initialized one ship, but the hit() always recognized
    // the first ship at (0, 0), even though I never initialized
    // it, and it's because of the above problem (0, 0). So,
    // to solve this, set the default values to (-1, -1),
    // so that when I check (0, 0), it will not recognize
    // ships that I have not initialized.
    public int x = - 1, y = -1;

    public ShipPart() { }
    // I'm not sure yet how to implement this. Either the individual ship
    // parts are *manually* picked (OK for beta run) in which case I would
    // utilize the below constructor and add() each to the overall Ship,
    // or the individual parts are *simultaneously* chosen (equivalent to 
    // placing a ship on the board with set pairs) and I would add those
    // as it is placed (do some increment of ASCII and numbers) and
    // completely do away with this subclass...

    // and to do the above ASCII stuff, I will have to take into account
    // of the ship's orientation for proper calculations.

    // to start off with, I will implement the *first* option of manually
    // typing the values for the ordered pairs to get the program functioning.
    // Later, and ultimately, when I add the visual components, I will changed
    // this implementation to the *second* option of simultaneous calculation
    // of ordered pairs.

}
