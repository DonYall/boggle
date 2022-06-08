package _project;

public class Dice {
	char[][][] possibleDice = { { {'A', 'A', 'A', 'F', 'R', 'S'},
								  {'A', 'E', 'E', 'G', 'M', 'U'},
								  {'C', 'E', 'I', 'I', 'L', 'T'},
								  {'D', 'H', 'H', 'N', 'O', 'T'},
								  {'F', 'I', 'P', 'R', 'S', 'Y'}
		
								},
								{ {'A', 'A', 'E', 'E', 'E', 'E'},
								  {'A', 'E', 'G', 'M', 'N', 'N'},
								  {'C', 'E', 'I', 'L', 'P', 'T'},
								  {'D', 'H', 'L', 'N', 'O', 'R'},
								  {'G', 'O', 'R', 'R', 'V', 'W'}
		
								},
								
								{ {'A', 'A', 'F', 'I', 'R', 'S'},
								  {'A', 'F', 'I', 'R', 'S', 'Y'},
								  {'C', 'E', 'I', 'P', 'S', 'T'},
								  {'E', 'I', 'I', 'I', 'T', 'T'},
								  {'H', 'I', 'P', 'R', 'R', 'Y'}
		
								},
		
								{ {'A', 'D', 'E', 'N', 'N', 'N'},
								  {'B', 'J', 'K', 'Q', 'X', 'Z'},
								  {'D', 'D', 'L', 'N', 'O', 'R'},
								  {'E', 'M', 'M', 'O', 'T', 'T'},
								  {'N', 'O', 'O', 'T', 'U', 'W'}
		
								},
		
								{ {'A', 'E', 'E', 'E', 'E', 'M'},
								  {'C', 'C', 'N', 'S', 'T', 'W'},
								  {'D', 'H', 'H', 'L', 'O', 'R'},
								  {'E', 'N', 'S', 'S', 'S', 'U'},
								  {'O', 'O', 'O', 'T', 'T', 'U'} } };

	
	public void shuffle() {
		char[][][] tempDice = new char[possibleDice.length][possibleDice[0].length][possibleDice[0][0].length];
		for (int i = 0; i < possibleDice.length; i++) {
			tempDice[i] = possibleDice[(int)(Math.random() * (possibleDice.length-1))];
		}
	}
	
	public char[][] getDice() {
		char[][] dice = new char[possibleDice.length][possibleDice[0].length];
		for (int i = 0; i < dice.length; i++) {
			for (int j = 0; j < dice[i].length; j++) {
				dice[i][j] = possibleDice[i][j][0];
			}
		}
		return(dice);
	}
}