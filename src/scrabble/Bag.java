/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrabble;

import java.util.ArrayList;

/**
 * The {@code Bag} class represents a bag of tiles in the Scrabble game.
 * It is responsible for holding the tiles and managing their distribution based on the specified language configuration.
 * <p>
 * The bag is initialized with tiles according to the letter distribution defined in the {@code Language} object.
 * </p>
 * 
 * @see Language
 * @see Tile
 * @see Board
 * 
 * @author agueguen
 */
public class Bag {

    private ArrayList<Tile> bag = new ArrayList<Tile>();

    /**
     * Constructs a {@code Bag} object and fills it with tiles based on the specified language configuration.
     * The number of each letter tile added to the bag is determined by the letter distribution in the {@code Language} object.
     * 
     * @param language the {@code Language} object containing the distribution of letters
     */
    public Bag(Language language) {
        language.getDistribution().forEach((letter, distr) -> {
            for (int i = 0; i<distr; i++){
                bag.add(new Tile(letter, language));
            }
        });
    }

    /**
     * Returns the number of tiles currently in the bag.
     * 
     * @return the size of the bag
     */
    public Integer getBagSize(){
        return bag.size();
    }

    /**
     * Removes the tile at the specified index from the bag.
     * 
     * @param index the index of the tile to be removed
     */
    public void remove(int index){
        bag.remove(index);
    }

    /**
     * Retrieves the tile at the specified index from the bag.
     * 
     * @param index the index of the tile to be retrieved
     * @return the {@code Tile} object at the specified index
     */
    public Tile get(Integer index){
        return bag.get(index);
    }

    /**
     * Returns a string representation of the bag.
     * The string representation consists of the word "Bag:" followed by the list of tiles in the bag.
     * 
     * @return a string representation of the bag
     */
    @Override
    public String toString() {
        return "Bag:" + bag;
    }

}
