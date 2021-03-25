package Hangman;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class hangman{
	
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
	
	public static void clearScreen() {  
		System.out.print("\033[H\033[2J"); 
		System.out.flush();
	} 
	
	public static void print(String str) {
		System.out.println(str);
	}
	
	public static String getWord() {
		BufferedReader reader;
		Random rand = new Random();
		String line = "";
		int randLine = rand.nextInt(84086);
		int checker = 0;
		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\Graham\\Desktop\\Code\\JavaPrograms\\Hangman\\src\\Hangman\\words.txt"));
			line = reader.readLine();
			while (line != null) {
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
			if (word.charAt(i) == uInput) {
				gWS[i] = String.valueOf(uInput);
				noneMatch++;
			}
		}
		if (noneMatch == 0) {
			wrongGuesses++;
		}
	}
	
	public static void display(String[] gWS, int wordLength, char[] gLetters, boolean lost) {
		String guessedWordSpaces = "";
		String guessedLetters = "";
		if (!lost) {
			for (int i = 0; i < wordLength; i++) {
				guessedWordSpaces += gWS[i] + " ";
			}
		}
		for (int j = 0; j < totalGuesses; j++) {
			guessedLetters += gLetters[j] + " ";
		}

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

		print("       __________");
		print("       |        |");
		print("       |        |");
		print("       |        |");
		print("       " + head + "        |");
		print("      " + lArm + body + rArm + "       |");
		print("      " + lLeg + " " + rLeg + "       |");
		print("                |");
		print("    ____________|");
		
		if (!lost) {
			print("\nGuessed Letters: " + guessedLetters);
			print("\n    " + "Word: " + guessedWordSpaces);
		}
	}
	
	public static void checkGameStatus(String[] gWS, int wordLength, String word) {
		int notGuessedLetters = 0;
		if (wrongGuesses >= 6) {
			isComplete = true;
			display(new String[1], wordLength, guessedLetters, true);
			print("You Lose!");
			print("The word was " + word);
		}
		for (int i = 0; i < wordLength; i++) {
			if (gWS[i] == "_ ") {
				notGuessedLetters++;
			}
		}
		if (notGuessedLetters == 0) {
			isComplete = true;
			print("You win");
			print("The word was " + word);
		}
	}
	

	
	public static void main(String args[])throws Exception {
		Scanner getInput = new Scanner(System.in);
		String word = getWord();
		int wordLength = word.length();
		String[] guessedWordSpaces;
		guessedWordSpaces = new String[wordLength];
		char guessedWord = ' ';
		guessedLetters = new char[26];
		
		for (int i = 0; i < wordLength; i++) {
			guessedWordSpaces[i] =  "_ ";
		}
		
		
		while (!isComplete) {
			if (totalGuesses > 0) {
				guessedLetters[totalGuesses-1] = guessedWord;
			}
			clearScreen();
			display(guessedWordSpaces, wordLength, guessedLetters, false);
			print("Guess a letter:");
			guessedWord = getInput.nextLine().charAt(0);
			checkUInput(guessedWord, word, guessedWordSpaces);
			checkGameStatus(guessedWordSpaces, wordLength, word);
			
		}
	}
}