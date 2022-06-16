package _project;
import java.util.*;
public class BoggleSinglePlayerBot {
	public static void main(String[] args) {
		Dice dice = new Dice();
		dice.shuffle();
		char[][] board = dice.getDice();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		
		int difficulty = 5; // 1, 2, 3, 4, or 5
		GetAllWords gaw = new GetAllWords();
		
		int wordsToGuess = difficulty+2;
		ArrayList<String> words = gaw.getWords(board, wordsToGuess);
		for (int i = 0; i < wordsToGuess; i++) {
			System.out.println(words.get(i));
		}
	}
}