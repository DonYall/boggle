package ok;
import ok.GetAllWords;

import java.util.*;
public class BoggleSinglePlayerBot {
	public static void main(String[] args) {
		int difficulty = 2; // 1, 2, 3, 4, or 5
        GetAllWords gaw = new GetAllWords();
		ArrayList<String> words = gaw.getWords();
		
		int wordsToGuess = (int)(Math.sqrt(difficulty)*5);
		for (int i = 0; i < wordsToGuess; i++) {
			int r = (int)(Math.random() * (words.size()-1));
			System.out.println(words.get(r));
        }
	}
}