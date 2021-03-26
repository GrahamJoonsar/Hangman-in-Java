package Hangman;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class hangman{
	
	// These variables have to be accessible across functions
	static int totalGuesses = 0;
	static int wrongGuesses = 0;
	static boolean isComplete = false;
	static String head = " ";
	static String body = " ";
	static String lArm = " ";
	static String rArm = " ";
	static String lLeg = " ";
	static String rLeg = " ";
	static char[] guessedLetters;
	
	public static void clearScreen() {  // Supposed to clear the CMD screen, but it does not work
		System.out.print("\033[H\033[2J"); 
		System.out.flush();
	} 
	
	public static void print(String str) { // So I don't have to type out the entire command every time
		System.out.println(str);
	}
	
	public static String getWord() { // Gets the word from the text file
		BufferedReader reader;
		Random rand = new Random();
		String line = "";
		int randLine = rand.nextInt(84086); // Gets the line of the word
		int checker = 0; // Necessary for checking when the line is at the word
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\Graham\\Desktop\\Code\\JavaPrograms\\Hangman\\src\\Hangman\\words.txt")); // This is the path that you need to change
			line = reader.readLine();
			while (line != null) { // Loops through the file
				if (checker == randLine) {
					break;
				}
				line = reader.readLine();
				checker++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	public static void checkUInput(char uInput, String word, String[] gWS) {
		int noneMatch = 0;
		totalGuesses++;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == uInput) { // Loops through the word and checks if it is the same as the user guessed character
				gWS[i] = String.valueOf(uInput); // sets the display word to fill in the guessed and correct char
				noneMatch++;
			}
		}
		if (noneMatch == 0) { // The guess is wrong
			wrongGuesses++;
		}
	}
	
	public static void display(String[] gWS, int wordLength, char[] gLetters, boolean lost) {
		String guessedWordSpaces = "";
		String guessedLetters = "";
		if (!lost) {
			for (int i = 0; i < wordLength; i++) { // This is how the word gets filled in as you guess
				guessedWordSpaces += gWS[i] + " ";
			}
		}
		for (int j = 0; j < totalGuesses; j++) { // This shows the characters that the user has already guessed
			guessedLetters += gLetters[j] + " ";
		}
		// Creating the hangman
		if (wrongGuesses == 1) {
			head = "O";
		}
		if (wrongGuesses == 2) {
			body = "|";
		}
		if (wrongGuesses == 3) {
			lArm = "-";
		}
		if (wrongGuesses == 4) {
			rArm = "-";
		}
		if (wrongGuesses == 5) {
			lLeg = "/";
		}
		if (wrongGuesses == 6) {
			rLeg = "\\";
		}
		// Drawing the hangman platform
		print("       __________");
		print("       |        |");
		print("       |        |");
		print("       |        |");
		print("       " + head + "        |");
		print("      " + lArm + body + rArm + "       |");
		print("      " + lLeg + " " + rLeg + "       |");
		print("                |");
		print("    ____________|");
		
		if (!lost) { // Necessary for when the game ends
			print("\nGuessed Letters: " + guessedLetters);
			print("\n    " + "Word: " + guessedWordSpaces);
		}
	}
	
	public static void checkGameStatus(String[] gWS, int wordLength, String word) {
		int notGuessedLetters = 0;
		if (wrongGuesses >= 6) { // Checks if the player loses
			isComplete = true;
			display(new String[1], wordLength, guessedLetters, true);
			print("You Lose!");
			print("The word was " + word);
		}
		for (int i = 0; i < wordLength; i++) { // Checks all the letters that haven't been guessed yet
			if (gWS[i] == "_ ") {
				notGuessedLetters++;
			}
		}
		if (notGuessedLetters == 0) { // Checks if the player wins
			isComplete = true;
			print("You win");
			print("The word was " + word);
		}
	}
	

	
	public static void main(String args[])throws Exception {
		Scanner getInput = new Scanner(System.in); // For getting the players guess
		String word = getWord(); // Gets the random word
		int wordLength = word.length();
		String[] guessedWordSpaces;
		guessedWordSpaces = new String[wordLength];
		char guessedWord = ' '; // For user input
		guessedLetters = new char[26];
		
		for (int i = 0; i < wordLength; i++) { // Initializes the display word
			guessedWordSpaces[i] =  "_ ";
		}
		
		
		while (!isComplete) { // Main game loop
			if (totalGuesses > 0) {
				guessedLetters[totalGuesses-1] = guessedWord; // Gets the users already guessed letters
			}
			clearScreen();
			display(guessedWordSpaces, wordLength, guessedLetters, false); // Displays the hangman
			print("Guess a letter:");
			guessedWord = getInput.nextLine().charAt(0); // Gets input
			checkUInput(guessedWord, word, guessedWordSpaces); // Checks input
			checkGameStatus(guessedWordSpaces, wordLength, word); // Checks if player won or lost
			
		}
	}
}
