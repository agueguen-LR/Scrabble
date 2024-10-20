/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrabble;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author agueguen
 */
public class Game {
    
    private Language language;
    private Bag bag;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Board board;
    private int playerTurn;
    private JFrame frame = new JFrame("Scrabble Board");

    public Game(int numberOfPlayers, Language language) {
        this.language = language;
        this.bag = new Bag(language);
        for (int i = 0; i<numberOfPlayers; i++){
            players.add(new Player(language));
            players.get(i).drawTiles(bag);
        }
        this.board = new Board();
        Random random = new Random();
        this.playerTurn = random.nextInt(numberOfPlayers);
        SwingUtilities.invokeLater(() -> {
            board.setBackground(Color.GREEN.darker().darker());
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(board, BorderLayout.CENTER);           
            frame.setVisible(true);            
        });   
        
    }
    
    public void StartManualGame(){
        ManualGameLoop();
    }
    
    private void ManualGameLoop(){
        
        System.out.println("Player " + playerTurn + " starts");
        Scanner input = new Scanner(System.in);
        
        while (!playerHasFinished()){  
            System.out.println(players.get(playerTurn));
            System.out.println("Please input your word:");
            String word = input.nextLine();
            System.out.println("Please input the x coordinate of the starting letter:");
            int x = input.nextInt();
            System.out.println("Please input the y coordinate of the starting letter:");
            int y = input.nextInt();
            System.out.println("Does the word go downwards? (true/false):");
            boolean placingDownwards = input.nextBoolean();        
            
            while (!players.get(playerTurn).placeWord(word, x, y, placingDownwards, board)){
                input.nextLine();
                System.out.println("Invalid word placement. Please try again.");
                System.out.println(players.get(playerTurn));
                System.out.println("Please input your word:");
                word = input.nextLine();
                System.out.println("Please input the x coordinate of the starting letter:");
                x = input.nextInt();
                System.out.println("Please input the y coordinate of the starting letter:");
                y = input.nextInt();
                System.out.println("Does the word go downwards? (true/false):");
                placingDownwards = input.nextBoolean(); 
            }
            SwingUtilities.invokeLater(() -> {               
                frame.revalidate();
                frame.repaint();
            });  
            System.out.println("Player " + playerTurn + "'s score is now " + players.get(playerTurn).getScore());
            players.get(playerTurn).drawTiles(bag);
            playerTurn = (playerTurn+1)%players.size();
            System.out.println("Player " + playerTurn + "'s turn");
            input.nextLine();
            
        }
        System.out.println("A Player has finished");
    }
    
    private boolean playerHasFinished(){
        for (Player player: players){
            if (player.getTileRack().size() == 0){
                return true;
            }
        }
        return false;
    }
    
}
