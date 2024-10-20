package scrabble;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * The Board class represents the game board for Scrabble.
 * It is a 15x15 grid of squares, each of which may contain a tile.
 * The board can display itself graphically.
 * 
 * @author agueguen
 */
public class Board extends JPanel {
    private static final long serialVersionUID = 7148504528535036003L;
    
    private ArrayList<ArrayList<Square>> board = new ArrayList<ArrayList<Square>>();
    private int[][] classicBoard = {
        {13, 1, 1, 2, 1, 1, 1, 13, 1, 1, 1, 2, 1, 1, 13},
        {1, 12, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 12, 1},
        {1, 1, 12, 1, 1, 1, 2, 1, 2, 1, 1, 1, 12, 1, 1},
        {2, 1, 1, 12, 1, 1, 1, 2, 1, 1, 1, 12, 1, 1, 2},
        {1, 1, 1, 1, 12, 1, 1, 1, 1, 1, 12, 1, 1, 1, 1},
        {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
        {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
        {13, 1, 1, 2, 1, 1, 1, 12, 1, 1, 1, 2, 1, 1, 13},
        {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
        {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
        {1, 1, 1, 1, 12, 1, 1, 1, 1, 1, 12, 1, 1, 1, 1},
        {2, 1, 1, 12, 1, 1, 1, 2, 1, 1, 1, 12, 1, 1, 2},
        {1, 1, 12, 1, 1, 1, 2, 1, 2, 1, 1, 1, 12, 1, 1},
        {1, 12, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 12, 1},
        {13, 1, 1, 2, 1, 1, 1, 13, 1, 1, 1, 2, 1, 1, 13},
    };

    /**
     * Constructs a new Board and initializes it with the classic Scrabble layout.
     */
    public Board() {   
        for (int[] row : classicBoard) {
            ArrayList<Square> rowSquare = new ArrayList<Square>();
            for (int sq : row) {
                rowSquare.add(new Square(sq % 10, sq / 10 == 1));
            }
            this.board.add(rowSquare);
        }
    }

    /**
     * Returns a string representation of the board.
     * 
     * @return the string representation of the board
     */
    @Override
    public String toString() {
        String str = "Board{\n";
        for (ArrayList<Square> row : this.board) {
            str += row.toString() + "\n";
        }
        return str + "}";
    }

    /**
     * Returns the 2D list representing the board.
     * 
     * @return the 2D list of squares
     */
    public ArrayList<ArrayList<Square>> getBoard() {
        return board;
    }

    /**
     * Returns the word formed to the left of the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the word to the left
     */
    public String getLeftWord(int x, int y) {
        String word = "";
        int i = x - 1;
        Square left;
        while (i >= 0 && (left = board.get(y).get(i)).getTile() != null) {
            word = left.getTile().getLetter() + word;
            i--;
        }
        return word;
    }
    
    /**
     * Returns the word formed to the right of the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the word to the right
     */
    public String getRightWord(int x, int y) {
        String word = "";
        int i = x + 1;
        Square right;
        while (i <= 14 && (right = board.get(y).get(i)).getTile() != null) {
            word += right.getTile().getLetter();
            i++;
        }
        return word;
    }
        
    /**
     * Returns the word formed above the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the word above
     */
    public String getUpWord(int x, int y) {
        String word = "";
        int i = y - 1;
        Square up;
        while (i >= 0 && (up = board.get(i).get(x)).getTile() != null) {
            word = up.getTile().getLetter() + word;
            i--;
        }
        return word;
    }
            
    /**
     * Returns the word formed below the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the word below
     */
    public String getDownWord(int x, int y) {
        String word = "";
        int i = y + 1;
        Square down;
        while (i <= 14 && (down = board.get(i).get(x)).getTile() != null) {
            word += down.getTile().getLetter();
            i++;
        }
        return word;
    }
    
    /**
     * Returns the tiles to the left of the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the tiles to the left
     */
    public ArrayList<Tile> getLeftTiles(int x, int y) {
        ArrayList<Tile> word = new ArrayList<Tile>();
        int i = x - 1;
        Square left;
        while (i >= 0 && (left = board.get(y).get(i)).getTile() != null) {
            word.addFirst(left.getTile());
            i--;
        }
        return word;
    }
    
    /**
     * Returns the tiles to the right of the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the tiles to the right
     */
    public ArrayList<Tile> getRightTiles(int x, int y) {
        ArrayList<Tile> word = new ArrayList<Tile>();
        int i = x + 1;
        Square right;
        while (i <= 14 && (right = board.get(y).get(i)).getTile() != null) {
            word.addLast(right.getTile());
            i++;
        }
        return word;
    }
        
    /**
     * Returns the tiles above the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the tiles above
     */
    public ArrayList<Tile> getUpTiles(int x, int y) {
        ArrayList<Tile> word = new ArrayList<Tile>();
        int i = y - 1;
        Square up;
        while (i >= 0 && (up = board.get(i).get(x)).getTile() != null) {
            word.addFirst(up.getTile());
            i--;
        }
        return word;
    }
            
    /**
     * Returns the tiles below the given coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the tiles below
     */
    public ArrayList<Tile> getDownTiles(int x, int y) {
        ArrayList<Tile> word = new ArrayList<Tile>();
        int i = y + 1;
        Square down;
        while (i <= 14 && (down = board.get(i).get(x)).getTile() != null) {
            word.addLast(down.getTile());
            i++;
        }
        return word;
    }    
    
    /**
     * Visual App to see the board
     * Called by the runtime system whenever the panel needs painting.
     * 
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var topRight = new Point(1, 1);
        var squareLength = getHeight() / 15;
        var offset = (getWidth() - getHeight()) / 2;

        // Calculate the font size based on the square length
        int fontSize = (int) (squareLength * 0.75);
        g.setFont(new Font("Arial", Font.BOLD, fontSize));

        // Get FontMetrics to measure the size of the string
        FontMetrics metrics = g.getFontMetrics();

        for (int i = 0; i < 15; i++) {
            ArrayList<Square> row = board.get(i);
            for (int j = 0; j < 15; j++) {
                // Draw the squares
                g.setColor(Color.WHITE);
                g.fillRect(topRight.x + offset + squareLength * j, topRight.y + squareLength * i, squareLength, squareLength);
                g.setColor(row.get(j).getSquareColor());
                g.fillRect(topRight.x + 1 + offset + squareLength * j, topRight.y + 1 + squareLength * i, squareLength - 2, squareLength - 2);
                
                // Draw the letter centered in the square
                try {
                    Tile tile = board.get(i).get(j).getTile();
                    String letter = tile.getLetter().toString();
                    
                    g.setColor(Color.getHSBColor(47, 48, 98));
                    g.fillRect(topRight.x + 3 + offset + squareLength * j, topRight.y + 3 + squareLength * i, squareLength - 6, squareLength - 6);
                    
                    int stringWidth = metrics.stringWidth(letter);
                    int stringHeight = metrics.getHeight();

                    // Calculate x and y coordinates to center the text
                    int x = topRight.x + offset + squareLength * j + (squareLength - stringWidth) / 2;
                    int y = topRight.y + squareLength * i + (squareLength + stringHeight) / 2 - metrics.getDescent();

                    g.setColor(Color.BLACK);
                    g.drawString(letter, x, y);
                    
                } catch (NullPointerException e) {
                    // Handle the case where there is no tile gracefully
                }
            }
        }
    }
}
