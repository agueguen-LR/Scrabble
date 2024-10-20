/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scrabble;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * The {@code Language} class represents a language configuration for a Scrabble game.
 * It manages the values and distribution of letters in the specified language.
 * The class can load existing language configurations from a file or generate a new language configuration.
 * <p>
 * The class reads and writes the language configurations from/to a file named "languages.txt".
 * If the specified language is not found, it prompts the user to input values and distribution for each letter
 * and then saves this new configuration to the file.
 * </p>
 * 
 * @author agueguen
 */
public class Language {

    private String name;
    private HashMap<Character, Integer> values = new HashMap<Character, Integer>();
    private HashMap<Character, Integer> distribution = new HashMap<Character, Integer>();
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Constructs a new {@code Language} object with the specified name.
     * It loads the language configuration from the "languages.txt" file or generates a new configuration if not found.
     * 
     * @param name the name of the language
     */
    public Language(String name) {
        this.name = name;
        File languagesFile = new File("languages.txt");
        LoadFile(languagesFile);
        try {
            Scanner reader = new Scanner(languagesFile);
            ArrayList<String> langs = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            ArrayList<String> distrs = new ArrayList<String>();
            while (reader.hasNextLine()) {
                String[] temp = reader.nextLine().split(";");
                langs.add(temp[0]);
                vals.add(temp[1]);
                distrs.add(temp[2]);
            }
            if (langs.contains(name)){
                String[] val = vals.get(langs.indexOf(name)).substring(1,vals.get(langs.indexOf(name)).length()-1).split(", ");
                String[] distr = distrs.get(langs.indexOf(name)).substring(1,distrs.get(langs.indexOf(name)).length()-1).split(", ");
                for (int i = 0; i<val.length; i++){
                    this.values.put(val[i].split("=")[0].charAt(0), Integer.parseInt(val[i].split("=")[1]));
                    this.distribution.put(distr[i].split("=")[0].charAt(0), Integer.parseInt(distr[i].split("=")[1]));
                }
                
                //blanks
                this.values.put(' ', 0);
                this.distribution.put(' ', 2);
                
            } else{
                System.out.println("Language not found, generating new language...");
                generateNewLanguage();
                try {
                    FileWriter writer = new FileWriter("languages.txt", true);
                    writer.write(name + ";" + this.values.toString() + ";" + this.distribution.toString() + "\n");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Ensures the existence of the specified file. If the file does not exist, it is created.
     * 
     * @param languagesFile the file to be checked or created
     */
    private void LoadFile(File languagesFile){
        try {
            if (languagesFile.createNewFile()) {
                System.out.println("File created: " + languagesFile.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the letter values for the language.
     * 
     * @return a {@code HashMap} containing the letter values
     */
    public HashMap<Character, Integer> getValues() {
        return values;
    }

    /**
     * Returns the letter distribution for the language.
     * 
     * @return a {@code HashMap} containing the letter distribution
     */
    public HashMap<Character, Integer> getDistribution() {
        return distribution;
    }

    /**
     * Generates a new language configuration by prompting the user to input values and distribution for each letter.
     * The input is collected through the standard input (console).
     */
    private void generateNewLanguage(){
        Scanner scanner = new Scanner(System.in);
        for (char c : alphabet.toCharArray()){
            System.out.println("Value of the letter " + c + ": ");
            values.put(c, scanner.nextInt());
            System.out.println("Amount of the letter " + c + ": ");
            distribution.put(c, scanner.nextInt());
        }
    }

    public String getName() {
        return name;
    }

}
