/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrabble;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The ValidWords class is responsible for reading and storing a set of valid Scrabble words
 * from a file specific to a given language.
 * 
 * @author agueguen
 */
public class ValidWords {
    
    private File validWordsFile;
    private HashSet<String> validWords = new HashSet<String>();

    /**
     * Constructs a ValidWords object for the specified language.
     * Reads the valid words from a file corresponding to the language.
     * 
     * @param language the language for which to load valid words
     */
    public ValidWords(Language language) {
        validWordsFile = new File(language.getName() + ".txt");
        try {
            Scanner reader = new Scanner(validWordsFile);
            while (reader.hasNextLine()) {
                this.validWords.add(reader.nextLine().split(" ")[0]);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the set of valid words.
     * 
     * @return a HashSet containing the valid words
     */
    public HashSet<String> getValidWords() {
        return validWords;
    }
}
