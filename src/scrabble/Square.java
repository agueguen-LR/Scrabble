/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrabble;

import java.awt.Color;

/**
 * The Square class represents a single square on a Scrabble board.
 * Each square may have a multiplier effect and can contain a tile.
 * 
 * @author agueguen
 */
public class Square {
    
    private final Integer multiplier;
    private final Boolean isWordMultiplier;
    private Tile tile;
    private Color squareColor;

    /**
     * Constructs a Square with the specified multiplier and type.
     * 
     * @param multiplier the multiplier value (e.g., 2 for double, 3 for triple)
     * @param isWordMultiplier true if this is a word multiplier, false if it is a letter multiplier
     */
    public Square(Integer multiplier, Boolean isWordMultiplier) {
        this.multiplier = multiplier;
        this.isWordMultiplier = isWordMultiplier;
        if (isWordMultiplier) {
            if (multiplier.equals(2)) {
                this.squareColor = Color.ORANGE;
            } else {
                this.squareColor = Color.RED;
            }        
        } else {
            if (multiplier.equals(1)) {
                this.squareColor = Color.GREEN.darker();
            } else if (multiplier.equals(2)) {
                this.squareColor = Color.CYAN;
            } else {
                this.squareColor = Color.BLUE;
            }
        }
    }

    /**
     * Returns the multiplier value of this square.
     * 
     * @return the multiplier value
     */
    public Integer getMultiplier() {
        return multiplier;
    }

    /**
     * Returns whether this square is a word multiplier.
     * 
     * @return true if this square is a word multiplier, false otherwise
     */
    public Boolean getIsWordMultiplier() {
        return isWordMultiplier;
    }

    /**
     * Sets the tile placed on this square.
     * 
     * @param tile the tile to place on this square
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Returns the tile placed on this square.
     * 
     * @return the tile on this square, or null if there is no tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Returns the color of this square.
     * 
     * @return the color of this square
     */
    public Color getSquareColor() {
        return squareColor;
    }

    /**
     * Returns a string representation of this square.
     * The string representation indicates whether the square is a word or letter multiplier and its value.
     * 
     * @return the string representation of this square
     */
    @Override
    public String toString() {
        String word = "l";
        if (this.isWordMultiplier == true) {
            word = "w";
        }
        return word + multiplier;
    }
}
