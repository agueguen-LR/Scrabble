/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrabble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The Player class represents a player in the Scrabble game.
 * Each player has a rack of tiles, a score, and a set of valid words.
 * The player can draw tiles from a bag and place words on the board.
 * 
 * @author agueguen
 */
public class Player {

    private ArrayList<Tile> tileRack = new ArrayList<Tile>();
    private final ValidWords validWords;
    private int score = 0;

    /**
     * Constructs a new Player with the specified language for valid words.
     * 
     * @param language the language for valid words
     */
    public Player(Language language) {
        this.validWords = new ValidWords(language);
    }

    /**
     * Draws a single tile from the bag and adds it to the player's tile rack.
     * 
     * @param bag the bag to draw the tile from
     */
    private void drawTile(Bag bag) {
        Random random = new Random();
        int drawnIndex = random.nextInt(bag.getBagSize());
        Tile temp = bag.get(drawnIndex);
        bag.remove(drawnIndex);
        tileRack.add(temp);
    }

    /**
     * Draws tiles from the bag until the player's tile rack has 7 tiles or the bag is empty.
     * 
     * @param bag the bag to draw tiles from
     */
    public void drawTiles(Bag bag) {
        while (bag.getBagSize() > 0 && this.tileRack.size() < 7) {
            drawTile(bag);
        }
    }

    /**
     * Returns the player's tile rack.
     * 
     * @return the tile rack
     */
    public ArrayList<Tile> getTileRack() {
        return tileRack;
    }

    /**
     * Returns a string representation of the player's tile rack.
     * 
     * @return the string representation of the tile rack
     */
    @Override
    public String toString() {
        return tileRack.toString();
    }

    /**
     * Returns the player's score.
     * 
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Checks if the player has a tile with the specified letter in the tile rack.
     * 
     * @param letter the letter to check for
     * @return the tile with the specified letter, or null if not found
     */
    private Tile hasTile(Character letter) {
        for (Tile tile : tileRack) {
            if (tile.getLetter().equals(letter)) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Checks if the player has a tile with the specified letter in the tile rack,
     * excluding already placed tiles.
     * If the player owns a blank, can output the blank if the player doesn't have the letter otherwise
     * 
     * @param letter the letter to check for
     * @param alreadyPlacedTiles the tiles already placed on the board
     * @return the tile with the specified letter, or null if not found
     */
    private Tile hasTile(Character letter, HashMap<Tile, Integer> alreadyPlacedTiles) {
        //ArrayList in case player has both blanks
        ArrayList<Tile> blank = new ArrayList<Tile>();
        for (Tile tile : tileRack) {
                       
            if (tile.getLetter().equals(' ') && !alreadyPlacedTiles.keySet().contains(tile)){
                blank.add(tile);
            }
            
            if (tile.getLetter().equals(letter) && !alreadyPlacedTiles.keySet().contains(tile)) {
                return tile;
            }
        }
        //player doesn't have the tile but has a blank, outputs the blank instead of null
        if (!blank.isEmpty()){
            return blank.get(0);
        }
        return null;
    }

    /**
     * Places a tile on the board at the specified coordinates and removes it from the tile rack.
     * 
     * @param x the x-coordinate to place the tile
     * @param y the y-coordinate to place the tile
     * @param board the board to place the tile on
     * @param tile the tile to place
     */
    private void placeTile(int x, int y, Board board, Tile tile) {
        board.getBoard().get(y).get(x).setTile(tile);
//        System.out.println("Placed Tile " + tile);
        tileRack.remove(tile);
    }

    /**
     * Places a word on the board starting at the specified coordinates in the specified direction.
     * Only places the word if the word and placement is valid by Scrabble rules
     * Updates score
     * Logic followed by this method is isolated from any AI player, this method is simply an interaction between the player and the board
     * 
     * @param word the word to place
     * @param x the starting x-coordinate
     * @param y the starting y-coordinate
     * @param directionIsDown true if the word is placed vertically, false if placed horizontally
     * @param board the board to place the word on
     * @return true if the word was successfully placed, false otherwise
     */
    public boolean placeWord(String word, int x, int y, boolean directionIsDown, Board board) {
        // Verifies given coordinates are within the bounds of the Scrabble board
        if (x<0 || x>14 || y<0 || y>14) {
            System.out.println("Coordinates are invalid");
            return false;
        }
        
        // Verifies given word is among valid Scrabble words for the language
        if (!isValid(word)) {
            System.out.println("Word is invalid");
            return false;
        }

        // stores blanks used so they can be reset from whatever letter they were used as to ' ' in case the word given was invalid
        ArrayList<Tile> blanksUsed = new ArrayList<Tile>();
        
        // stores the score to be added if the word given is valid and placeable
        int tempScore = 0;
        // stores the multiplier to be applied to the word
        int tempMultiplier = 1;
        
        // stores the score to be added from adjacent words
        int adjacentScore = 0;
        
        // Flag to keep track of whether the word is connected to another word on the board (or is the first word and therefore passes through Square x=7 y=7)
        boolean connectedFlag = false;

        // HashMap of all tiles to be placed (key), and the coordinates to place them at (x*100+y)
        HashMap<Tile, Integer> tilePlaceCoords = new HashMap<>();

        if (directionIsDown) {
            // Verifies the entire word was given (there are no prefixes or suffixes to the main word)
            if (!(board.getUpWord(x, y).equals("") && board.getDownWord(x, y + word.length() - 1).equals(""))) {
                System.out.println("Please enter the entire word, including any letters already present on the board");
                return false;
            }

            int i = y;
            Square currentSquare;
            Tile tile;

            // Stay within bounds of the board and only operate for the length of the word
            while (i <= 14 && i < y + word.length()) {
                // If the word passes through the center square, it is always connected (makes first word of the game placed 'connected')
                if (i == 7 && x == 7) {
                    connectedFlag = true;
                }

                // Letter of the word we are on in the loop
                char currentLetter = word.charAt(i - y);
                // Tile from player rack we wish to place
                tile = this.hasTile(currentLetter, tilePlaceCoords);
                // Square which may contain letter from the word
                currentSquare = board.getBoard().get(i).get(x);

                // If Square does contain the letter from the word, set connectedFlag to true, add value to tempScore and continue
                if ((currentSquare.getTile() != null && currentSquare.getTile().getLetter().equals(currentLetter))) {
                    connectedFlag = true;
                    tempScore += currentSquare.getTile().getValue();
                }
                // If Square is empty and we have the tile needed for the word in our tile rack
                else if (currentSquare.getTile() == null && tile != null) {
                    
                    //if the tile used is a blank, set the blank's letter to the currentLetter for word verification and viewing purposes, then add to blanksUsed
                    if (tile.getLetter() == ' '){
                        tile.setLetter(currentLetter);
                        blanksUsed.add(tile);
                    }
                    
                    // Check if there are tiles adjacent to this one, if no, add tile to tilePlaceCoords HashMap, if yes, do they form a valid word? if no, return false and don't place anything, if yes, add tile to tilePlaceCoords HashMap and set connectedFlag to true
                    String prefix = board.getLeftWord(x, i);
                    String suffix = board.getRightWord(x, i);

                    boolean connectedToWord = false;
                    if ((prefix.equals("") && suffix.equals("")) || (connectedToWord = isValid(prefix + currentLetter + suffix))) {
                        if (connectedToWord) {
                            connectedFlag = true;
                            //adds the correctly multiplied score from the adjacent word to adjacentScore
                            int tempAdjacentScore = 0;
                            for (Tile t : board.getLeftTiles(x, i)){
                                tempAdjacentScore += t.getValue();
                            }
                            for (Tile t : board.getRightTiles(x, i)){
                                tempAdjacentScore += t.getValue();
                            }
                            if (currentSquare.getIsWordMultiplier()){
                                adjacentScore += (tempAdjacentScore + tile.getValue())*currentSquare.getMultiplier();
                            } else{
                                adjacentScore += tempAdjacentScore + tile.getValue()*currentSquare.getMultiplier();
                            }
                        }
                        //adds score/multiplier from the tile to tempScore and tempMultiplier
                        if (currentSquare.getIsWordMultiplier()){
                            tempMultiplier *= currentSquare.getMultiplier();
                            tempScore += tile.getValue();
                        } else{
                            tempScore += tile.getValue()*currentSquare.getMultiplier();
                        }
                        //adds tile to HashMap of tiles to be placed
                        tilePlaceCoords.put(tile, x * 100 + i);
                    } else {
                        System.out.println("Connected word " + prefix + currentLetter + suffix + " is/becomes invalid");
                        resetBlanks(blanksUsed);
                        return false;
                    }
                }
                // Else: we don't have the letter we need or the square is occupied by an incorrect letter, return false and don't place anything
                else {
                    System.out.println("Player doesn't have letter, or met incorrect letter");
                    resetBlanks(blanksUsed);
                    return false;
                }
                i++;
            }

            if (!connectedFlag) {
                System.out.println("Not connected to other tiles");
                resetBlanks(blanksUsed);
                return false;
            }
            // If the entire word can be placed (condition i<y+word.length() was false and stopped the while loop) and is connected to another word, place the tiles on the board
            if (i == y + word.length()) {
                int wordScore = tempScore*tempMultiplier + adjacentScore;
                
                //if all 7 tiles of the player are placed, it is worth 50 extra points
                if (tilePlaceCoords.size() == 7){
                    wordScore+=50;
                }
                                
                System.out.println("Word is worth " + wordScore + " points.");
                score += wordScore;
                for (Tile t : tilePlaceCoords.keySet()) {
                    placeTile(tilePlaceCoords.get(t) / 100, tilePlaceCoords.get(t) % 100, board, t);
                }
                return true;
            }
            // If we have reached the edge of the board, return false and don't place anything
            else {
                resetBlanks(blanksUsed);
                return false;
            }
        } else {
            // Tries to place word horizontally, if conditions are valid for such to be done
            if (!(board.getLeftWord(x, y).equals("") && board.getRightWord(x + word.length() - 1, y).equals(""))) {
                System.out.println("Please enter the entire word, including any letters already present on the board");
                return false;
            }

            int i = x;
            Square currentSquare;
            Tile tile;

            // Stay within bounds of the board and only operate for the length of the word
            while (i <= 14 && i < x + word.length()) {
                // If the word passes through the center square, it is always connected (makes first word of the game placed 'connected')
                if (i == 7 && x == 7) {
                    connectedFlag = true;
                }

                // Letter of the word we are on in the loop
                char currentLetter = word.charAt(i - x);
                // Tile from player rack we wish to place
                tile = this.hasTile(currentLetter, tilePlaceCoords);
                // Square which may contain letter from the word
                currentSquare = board.getBoard().get(y).get(i);

                // If Square does contain the letter from the word, set connectedFlag to true, add value to tempScore and continue
                if ((currentSquare.getTile() != null && currentSquare.getTile().getLetter().equals(currentLetter))) {
                    connectedFlag = true;
                    tempScore += currentSquare.getTile().getValue();
                }
                // If Square is empty and we have the tile needed for the word in our tile rack
                else if (currentSquare.getTile() == null && tile != null) {
                    
                    //if the tile used is a blank, set the blank's letter to the currentLetter for word verification and viewing purposes, then add to blanksUsed
                    if (tile.getLetter() == ' '){
                        tile.setLetter(currentLetter);
                        blanksUsed.add(tile);
                    }

                    // Check if there are tiles adjacent to this one, if no, add tile to tilePlaceCoords HashMap, if yes, do they form a valid word? if no, return false and don't place anything, if yes, add tile to tilePlaceCoords HashMap and set connectedFlag to true
                    String prefix = board.getUpWord(i, y);
                    String suffix = board.getDownWord(i, y);

                    boolean connectedToWord = false;
                    if ((prefix.equals("") && suffix.equals("")) || (connectedToWord = isValid(prefix + currentLetter + suffix))) {
                        if (connectedToWord) {
                            connectedFlag = true;
                            //adds the correctly multiplied score from the adjacent word to adjacentScore
                            int tempAdjacentScore = 0;
                            for (Tile t : board.getUpTiles(i, y)){
                                tempAdjacentScore += t.getValue();
                            }
                            for (Tile t : board.getDownTiles(i, y)){
                                tempAdjacentScore += t.getValue();
                            }
                            if (currentSquare.getIsWordMultiplier()){
                                adjacentScore += (tempAdjacentScore + tile.getValue())*currentSquare.getMultiplier();
                            } else{
                                adjacentScore += tempAdjacentScore + tile.getValue()*currentSquare.getMultiplier();
                            }
                        }
                        //adds score/multiplier from the tile to tempScore and tempMultiplier
                        if (currentSquare.getIsWordMultiplier()){
                            tempMultiplier *= currentSquare.getMultiplier();
                            tempScore += tile.getValue();
                        } else{
                            tempScore += tile.getValue()*currentSquare.getMultiplier();
                        }
                        //adds tile to HashMap of tiles to be placed                        
                        tilePlaceCoords.put(tile, i * 100 + y);
                    } else {
                        System.out.println("Connected word " + prefix + currentLetter + suffix + " is/becomes invalid");
                        resetBlanks(blanksUsed);
                        return false;
                    }
                }
                // Else: we don't have the letter we need or the square is occupied by an incorrect letter, return false and don't place anything
                else {
                    System.out.println("Player doesn't have letter, or met incorrect letter");
                    resetBlanks(blanksUsed);
                    return false;
                }
                i++;
            }

            if (!connectedFlag) {
                System.out.println("Not connected to other tiles");
                resetBlanks(blanksUsed);
                return false;
            }
            // If the entire word can be placed (condition i<x+word.length() was false and stopped the while loop) and is connected to another word, place the tiles on the board
            if (i == x + word.length()) {
                int wordScore = tempScore*tempMultiplier + adjacentScore;
                
                //if all 7 tiles of the player are placed, it is worth 50 extra points
                if (tilePlaceCoords.size() == 7){
                    wordScore+=50;
                }
                
                System.out.println("Word is worth " + wordScore + " points.");
                score += wordScore;
                for (Tile t : tilePlaceCoords.keySet()) {
                    placeTile(tilePlaceCoords.get(t) / 100, tilePlaceCoords.get(t) % 100, board, t);
                }
                return true;
            }
            // If we have reached the edge of the board, return false and don't place anything
            else {
                resetBlanks(blanksUsed);
                return false;
            }
        }
    }

    /**
     * Checks if the given word is valid according to the valid words set.
     * 
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    private boolean isValid(String word) {
        return validWords.getValidWords().contains(word);
    }
    
    /**
     * Resets all blanks modified back to ' '
     * @param blanks ArrayList<Tile> containing any used blanks
     */
    private void resetBlanks(ArrayList<Tile> blanks){
        for (Tile blank: blanks){
            blank.setLetter(' ');
        }
    }
    
}
