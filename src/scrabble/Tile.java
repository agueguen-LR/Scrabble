package scrabble;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * The {@code Tile} class represents a single tile in the Scrabble game.
 * A tile has a letter and a corresponding value based on the specified language configuration.
 * <p>
 * The value of the tile is determined by looking up the letter in the {@code Language} object provided during construction.
 * </p>
 * 
 * @see Language
 * @see Bag
 * @see Board
 * 
 * @author agueguen
 */
public class Tile {
    
    private Character letter;
    private Integer value;

    /**
     * Constructs a {@code Tile} object with the specified letter and language configuration.
     * The value of the tile is retrieved from the provided {@code Language} object.
     * 
     * @param letter the letter represented by the tile
     * @param language the {@code Language} object containing the values for letters
     */
    public Tile(char letter, Language language) {
        this.letter = letter;
        this.value = language.getValues().get(letter);
    }

    public Tile() {
    }
    

    /**
     * Returns a string representation of the tile.
     * The string representation consists of the letter enclosed in square brackets.
     * 
     * @return a string representation of the tile
     */
    @Override
    public String toString() {
        return "[" + letter + "]";
    }

    public Character getLetter() {
        return letter;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Setter for this Tile's letter, to be used for viewing and resetting blanks
     * @param letter letter on the tile
     */
    public void setLetter(Character letter) {
        this.letter = letter;
    }
    
    
    
    

}
