package _project;
import java.util.*;
public class BoggleSinglePlayerBot {
	public static void main(String[] args) {
		Dice dice = new Dice();
		dice.shuffle();
		char[][] board = dice.getDice();

		int difficulty = 5; // 1, 2, 3, 4, or 5
		GetAllWords gaw = new GetAllWords();
		
		int wordsToGuess = difficulty+2;
		ArrayList<String> words = gaw.getWords(board, wordsToGuess);
		for (int i = 0; i < wordsToGuess; i++) {
			int r = (int)(Math.random() * (words.size()-1));
			System.out.println(words.get(r));
		}
	}
}