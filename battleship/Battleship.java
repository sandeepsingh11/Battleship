/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import UI.Battlefield;
import core.Game;

/**
 *
 * @author Sandeep
 */
public class Battleship {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");
        
        // start new game
        Game game = new Game();
    }
}

/*
- RECOLOR THE REPLACING OF USER SHIPS [DONE]-

- DIALOG BOX FOR GAME UPDATES [DONE]- (pick actual text to print)
- FIX AUTO SCROLL FOR THE ABOVE [DONE]-

- WINDOW SIZE [EHH]-
- RESET MENU OPTION [DONE]-
- FIX UI GRID [DONE]-
- ADD PLAYERS' HP UNDER ENEMY PANEL?

- ADD AI [PRETTY GOOD ACTUALLY TO FINISH SKINKING A SHIP]-
- P2 IS NOT SELECTING MY SHIPS FOR SOME REASON [DONE]- (weird distribution though...)
- FUNC OF SELECTING ANOTHER CELL HAPPENS IF YOU MISCLICK DURING P2'S TURN

- P2 PUTS DUPLICATE SHIPS (5 TOTAL, BUT NOT RIGHT SIZE) LIKE 2 #1'S, 2 #4'S, 1 #5

- HELP MENU?

- PLZ CLEAN UP CODE, DERP
*/