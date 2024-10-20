/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package scrabble;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author agueguen
 */
public class Scrabble {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
//        Language english = new Language("english");
//        Game TwoPlayersEnglish = new Game(2, english);
        
        Language français = new Language("français");
        Game TwoPlayersFrench = new Game(2, français);
        TwoPlayersFrench.StartManualGame();
//        
//        ValidWords validWords = new ValidWords(english);
//        System.out.println(validWords.getValidWords().size());
//        System.out.println(english.getValues());
//        System.out.println(english.getDistribution());
//        Bag bag = new Bag(english);
//        Player player1 = new Player(english);
//        player1.drawTiles(bag);
//        System.out.println(bag);
//        System.out.println(player1);
//        Square testSquare = new Square(2, true);
//        System.out.println(testSquare.getMultiplier());
//        Board board = new Board();
//        System.out.println(board);
//        Tile E = new Tile('E', english);
//        Tile A = new Tile('A', english);
//        Tile I = new Tile('I', english);
//        Tile O = new Tile('O', english);
//        player1.placeTile(0, 7, board, E);
//        player1.placeTile(1, 7, board, E);
//        player1.placeTile(6, 7, board, E);
//        player1.placeTile(8, 7, board, I);
//        player1.placeTile(9, 7, board, I);
//        player1.placeTile(7, 5, board, O);
//        player1.placeTile(7, 6, board, O);
//        player1.placeTile(7, 8, board, A);
//        player1.placeTile(7, 9, board, A);
//        System.out.println(board.getLeftWord(2,7));
//        System.out.println(board.getRightWord(7,7));
//        System.out.println(board.getUpWord(7,7));
//        System.out.println(board.getDownWord(7,7));        
//        System.out.println(player1);
//        System.out.println(board.getBoard().get(7).get(7).getTile().getLetter());
        
//        System.out.println(player1.placeWord("FAKER", 4, 5, false, board));
//        System.out.println(player1.placeWord("CRUST", 7, 7, false, board));
////        System.out.println(player1.placeWord("CRUSTY", 7, 7, false, board));
//        System.out.println(player1.placeWord("ALE", 11, 6, false, board));
////        System.out.println(player1.placeWord("BAR", 6, 7, false, board));
//        System.out.println(player1.placeWord("RARE", 8, 5, true, board));
//        System.out.println(player1.placeWord("FAKER", 4, 5, false, board));
//        System.out.println(player1.placeWord("FALLS", 12, 3, true, board));
////        System.out.println(player1.placeWord("ARE", 11, 6, false, board));


        
        
        /*
        * Application to show Scrabble Board
        */
//        SwingUtilities.invokeLater(() -> {
//            board.setBackground(Color.GREEN.darker().darker());
//            var frame = new JFrame("Scrabble Board");
//            frame.setSize(800, 600);
//            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            frame.getContentPane().add(board, BorderLayout.CENTER);
//            frame.setVisible(true);
//        });

        
        
    }
    
}
