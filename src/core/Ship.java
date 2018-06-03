/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UI.Battlefield;

/**
 *
 * @author Sandeep
 */
public class Ship {
    public ShipPart part[];
    public int size, hp;
    public boolean sunkenShip, isSet;
   
    public Ship(int size) {
        part = new ShipPart[size];
        
        for (int i = 0; i < size; i++) {
            part[i] = new ShipPart();
        }
        
        this.size = size;
        hp = size;
        
        sunkenShip = false;
        isSet = false;
    }
    
    public void setShip(int x, int y, boolean orientation, Grid board) {
        
        // if orientation is vertical
        if (orientation) {
            for (int i = 0; i < size; i++) {
                part[i].x = x;
                part[i].y = y + i;
                board.grid[x][y + i] = true;
            }
        }
        else { // if orientation is horizontal
            for (int i = 0; i < size; i++) {
                part[i].x = x + i;
                part[i].y = y;
                board.grid[x + i][y] = true;
            }
        }
        
        // ship is set
        isSet = true;
    }
    
    public void printLocation() {
        for (int i = 0; i < size; i++) {
            System.out.println("(" + (part[i].x) + ", "
                                   + (part[i].y) + ")");
        }
    }
    
    public boolean hit(int x, int y) {
        
        for (int i = 0; i < size; i++) {
            if (part[i].x == x) {
                if (part[i].y == y) {
                    hp--;
                    System.out.println("ship's hp = " + hp);
                    isSunk();
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void isSunk() {
        if (hp == 0) {
            System.out.println("You sunk a ship!");
            Battlefield.appendAndScroll("*A ship has sunk!*\n");
            sunkenShip = true;
        }
    }
}


