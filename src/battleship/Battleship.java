/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import UI.Battlefield;
import UI.BattleshipUI;
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
        
        // set up new game
        Game game = new Game();
    }
}

/*
- RECOLOR THE REPLACING OF USER SHIPS [DONE]-

- DIALOG BOX FOR GAME UPDATES [DONE]-
- FIX AUTO SCROLL FOR THE ABOVE [DONE]-
- PRINT LETTER TOO. CHANGE TEXT FOR ERR SHIP PLACEMENT
- CLEAN DIALOG

- WINDOW SIZE [EHH]-
- RESET MENU OPTION [DONE]-
- FIX UI GRID [DONE]-
- ADD PLAYERS' HP UNDER ENEMY PANEL?

- ADD AI [PRETTY GOOD ACTUALLY TO FINISH SKINKING A SHIP]-
- P2 IS NOT SELECTING MY SHIPS FOR SOME REASON [DONE]- (weird distribution though...)
- FUNC OF SELECTING ANOTHER CELL HAPPENS IF YOU MISCLICK DURING P2'S TURN (fix in radar...)
- FIX AI FOR WHEN SHIPS ARE TOGETHER [EHH, GETTING THERE]-

- P2 SHIPS ARE PLACED CLOSE PRETTY TOGETHER (part of the psuedo random?)

- WHEN RESET, MAKE SURE TO RESET *ALL* VALS; BUTTONS DONT TURN RED WHEN SUNK [DONE]-
- REST IS GOOD, BUT NOW P2 GOES TWICE. RATHER, IT SKIPS P1'S TURN. [DONE; IT JUST STOPPED BY ITSELF LOL]-

- HELP MENU?

- PLZ CLEAN UP CODE, DERP
*/