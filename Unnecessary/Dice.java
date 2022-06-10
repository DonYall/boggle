package _project;

public class Dice {
    // Create a 3D array of dice faces
	char[][][] possibleDice = {{{'a', 'a', 'a', 'f', 'r', 's'},
                                {'a', 'e', 'e', 'g', 'm', 'u'},
                                {'c', 'e', 'i', 'i', 'l', 't'},
                                {'d', 'h', 'h', 'n', 'o', 't'},
                                {'f', 'i', 'p', 'r', 's', 'y'}
                            },
                            {   {'a', 'a', 'e', 'e', 'e', 'e'},
                                {'a', 'e', 'g', 'm', 'n', 'n'},
                                {'c', 'e', 'i', 'l', 'p', 't'},
                                {'d', 'h', 'l', 'n', 'o', 'r'},
                                {'g', 'o', 'r', 'r', 'v', 'w'}
                            },
                            {   {'a', 'a', 'f', 'i', 'r', 's'},
                                {'a', 'f', 'i', 'r', 's', 'y'},
                                {'c', 'e', 'i', 'p', 's', 't'},
                                {'e', 'i', 'i', 'i', 't', 't'},
                                {'h', 'i', 'p', 'r', 'r', 'y'}
                            },
                            {   {'a', 'd', 'e', 'n', 'n', 'n'},
                                {'b', 'j', 'k', 'q', 'x', 'z'},
                                {'d', 'd', 'l', 'n', 'o', 'r'},
                                {'e', 'm', 'm', 'o', 't', 't'},
                                {'n', 'o', 'o', 't', 'u', 'w'}
                            },
                            {   {'a', 'e', 'e', 'e', 'e', 'm'},
                                {'c', 'c', 'n', 's', 't', 'w'},
                                {'d', 'h', 'h', 'l', 'o', 'r'},
                                {'e', 'n', 's', 's', 's', 'u'},
                                {'o', 'o', 'o', 't', 't', 'u'} } };

    // Shuffles the location of the dice, and shuffles the top face of the die
	public void shuffle() {
        // Create a temporary array to hold the dice layout
		char[][][] tempDice = new char[possibleDice.length][possibleDice[0].length][possibleDice[0][0].length];
        for(int i = 0; i < tempDice.length; i++) {
            for(int j = 0; j < tempDice[i].length; j++) {
                for(int k = 0; k < tempDice[i][j].length; k++) {
                    tempDice[i][j][k] = possibleDice[i][j][k];
                }
            }
        }
        // Move the dice around
		for (int i = 0; i < tempDice.length; i++) {
            for (int j = 0; j < tempDice[0].length; j++) {
                // Swapping rather than copying a random element avoids duplicates
                tempDice = swap(tempDice, i, j, (int)(Math.random() * tempDice.length), (int)(Math.random() * tempDice[0].length));
            }
		}
        // Shuffle the top face of every die (the element at index 0 represents the top face)
        for (int i = 0; i < tempDice.length; i++) {
            for (int j = 0; j < tempDice[0].length; j++) {
                // Swap the top face of the die with a random face of the die
                int rand = (int)Math.random() * (tempDice[0][0].length-1);
                char temp = tempDice[i][j][0];
                tempDice[i][j][0] = tempDice[i][j][rand];
                tempDice[i][j][rand] = temp;
                // We swap rather than copying a value to the top face so that the same die can be used in future shuffles
            }
        }
        possibleDice = tempDice.clone();
	}
	
    // Returns the current top face of every die as a 2D array
	public char[][] getDice() {
		char[][] dice = new char[possibleDice.length][possibleDice[0].length];
		for (int i = 0; i < dice.length; i++) {
			for (int j = 0; j < dice[i].length; j++) {
				dice[i][j] = possibleDice[i][j][0];
			}
		}
		return(dice);
	}

    // Swaps two 1D elements in a 3D array
    public char[][][] swap(char[][][] arr, int r1, int r2, int c1, int c2) {
        char[] temp = arr[r1][c1].clone();
        arr[r1][c1] = arr[r2][c2].clone();
        arr[r2][c2] = temp;
        return(arr);
    }
}