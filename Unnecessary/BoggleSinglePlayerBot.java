package _project;
import java.util.*;
public class BoggleSinglePlayerBot {
	public static void main(String[] args) {
		Dice dice = new Dice();
		dice.shuffle();
		//char[][] board = dice.getDice();
		char[][] board = {  {'a', 'b', 'c', 'd', 'e'},
							{'f', 'g', 'h', 'i', 'j'},
							{'k', 'l', 'm', 'n', 'o'},
							{'p', 'q', 'r', 's', 't'},
							{'u', 'v', 'w', 'x', 'y'}};
		int difficulty = 5; // 1, 2, 3, 4, or 5
		GetAllWords gaw = new GetAllWords();
		ArrayList<String> words = gaw.getWords(board);
		
		int wordsToGuess = difficulty+2;
		for (int i = 0; i < wordsToGuess; i++) {
			int r = (int)(Math.random() * (words.size()-1));
			System.out.println(words.get(r));
		}
	}
}