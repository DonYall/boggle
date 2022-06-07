package _project;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GetAllWords {
    // Eight possible movements from a cell
    // (top, right, bottom, left, and four diagonal moves)
    int[] row = {-1, -1, -1, 0, 1, 0, 1, 1};
    int[] col = {-1, 1, 0, -1, -1, 1, 0, 1};
    ArrayList<String> validStuff = new ArrayList<String>();
    
    // Function to check if it is possible to go to cell (x, y) from the current cell.
    // The function returns false if (x, y) is not a valid matrix point
    // or if cell (x, y) has already been processed.
    public boolean isSafe(int x, int y, boolean[][] processed) {
        return (x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length) && !processed[x][y];
    }

    // A recursive function to generate all possible words in a boggle set
    public void searchBoggle(char[][] board, Set<String> words, Set<String> result,
                                    boolean[][] processed, int i, int j, String path) {
        // Mark the current node as processed
        processed[i][j] = true;

        // Update the path with the current character and insert it into the set
        path += board[i][j];

        // Check whether the path is present in the input set
        if (words.contains(path)) {
            result.add(path);
        }

        // Check for all eight possible movements from the current cell
        for (int k = 0; k < row.length; k++) {
            // Skip if a cell is invalid, or it is already processed
            if (isSafe(i + row[k], j + col[k], processed)) {
                searchBoggle(board, words, result, processed, i + row[k], j + col[k], path);
            }
        }

        // Backtrack: Mark the current node as unprocessed
        processed[i][j] = false;
    }

    // Function to search for a given set of words in a boggle
    public Set<String> searchBoggle(char[][] board, Set<String> words) {
        // Construct a set to store valid words constructed from the boggle
        Set<String> result = new HashSet<>();

        // Base case
        if (board.length == 0) {
            return result;
        }

        // `MN` board
        int M = board.length;
        int N = board[0].length;

        // Construct a boolean matrix to store whether a cell is processed or not
        boolean[][] processed = new boolean[M][N];

        // Generate all possible words in a boggle
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // Consider each character as a starting point and run DFS
                searchBoggle(board, words, result, processed, i, j, "");
            }
        }
        return result;
    }

    public ArrayList<String> mainn() throws Exception{
        Scanner input = new Scanner(System.in);
        /**
        int length = input.nextInt();
        int width = input.nextInt();
        char [][] arrBoard = new char[length][width];
        File fileBoard = new File("board.txt");
        Scanner inputBrd = new Scanner(fileBoard);

        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                arrBoard[i][j] = inputBrd.next().charAt(0);
            }
        }
        **/

        char[][] board = {{'M', 'S', 'E'}, 
        				  {'R', 'A', 'T'}, 
        				  {'L', 'O', 'N'}};

        Set<String> sWords = new HashSet<String>();

        File fileDictionary = new File("dictionary.txt");
        try {
            // Putting all the words in the dictionary into a set
            Scanner inputDic = new Scanner(fileDictionary);
            while (inputDic.hasNext()) {
                sWords.add(inputDic.next().toUpperCase());
            }
            inputDic.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //System.out.println(sWords.size() + " possible combinations\n");
        // Searching the boggle for words in the set
        Set<String> validWords = searchBoggle(board, sWords);;

        // Printing out all the possible words in the boggle based on something
        for(String s : validWords){
            if(s.length() >= 3){
                validStuff.add(s);
            }
        }
        return(validStuff);
    }
}
