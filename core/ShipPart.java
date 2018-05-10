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

}
