import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CheckWordsInBoard {
    public static int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    public static int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
    public static int intStartR = 0;
    public static int intStartC = 0;
    public static boolean isSafe(int x, int y, boolean[][] processed, char[][] board, String word, String path) {
        boolean isSafe = false;
        if((x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length) && !processed[x][y]) {
            if(word.charAt(path.length()) == board[x][y]){
                isSafe = true;
            }
            else {
                isSafe = false;
            }
        }
        return isSafe;
    }

    // A recursive function to generate and print all possible words in a boggle
    public static void searchBoggle(char[][] board, String word, ArrayList<String> result,
                                    boolean[][] processed, int i, int j, String path) {
        // Mark the current node as processed
        processed[i][j] = true;

        // Update the path with the current character and insert it into the set
        path += board[i][j];

        // Check whether the path is present in the input set
        if (word.equals(path)) {
            result.add(path);
            return;
        }

        // Check for all eight possible movements from the current cell
        for (int k = 0; k < row.length; k++) {
            if (isSafe(i + row[k], j + col[k], processed, board, word, path)) {
                searchBoggle(board, word, result, processed, i + row[k], j + col[k], path);
            }
        }
        // Mark the current node as unprocessed
        processed[i][j] = false;
    }

    public static ArrayList<String> searchBoggle(char[][] board, String word) {
        ArrayList<String> result = new ArrayList<>();

        if (board.length == 0) {
            return result;
        }

        boolean[][] processed = new boolean[board.length][board[0].length];

        searchBoggle(board, word, result, processed, intStartR, intStartC, "");
        //searchBoggle(board, word, arrWords, result, processed, intStartR, intStartC, "");
        if(result.size() > 0) {
            return result;
        }

        return result;
    }

    public static ArrayList<String> findAllWords(char [][] board) {
        Map<String, ArrayList<String>> mapDic = new HashMap<String, ArrayList<String>>();
        ArrayList<String> dicWords = new ArrayList<String>();

        //Put dictionary into Map
        File fileDictionary = new File("dictionary.txt");
        try {
            Scanner inputDic = new Scanner(fileDictionary);
            while(inputDic.hasNext()){
                String strNext = inputDic.next().toUpperCase();
                if(strNext.length() > 2) {
                    String strFirstLetter = strNext.substring(0, 1);
                    if(mapDic.get(strFirstLetter) != null) {
                        mapDic.get(strFirstLetter).add(strNext);
                    }
                    else {
                        ArrayList<String> arrTemp = new ArrayList<String>();
                        arrTemp.add(strNext);
                        mapDic.put(strFirstLetter, arrTemp);
                    }
                }
            }
            inputDic.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                 intStartR = i;
                 intStartC = j;
                ArrayList<String> arrWordList = mapDic.get(Character.toString(board[i][j]));

                for (int k = 0; k < arrWordList.size(); k++) {
                    ArrayList<String> arrWords = new ArrayList<String>();
                    String words = arrWordList.get(k);

                    for(int l = 0; l < words.length(); l++) {
                        arrWords.add(Character.toString(words.charAt(l))) ;
                    }

                    ArrayList<String> validWords = searchBoggle(board, words);

                    if(!validWords.isEmpty()) {
                        if(!(dicWords.contains(validWords.get(0).toString()))){
                            dicWords.add(validWords.get(0).toString());
                            System.out.println(validWords.get(0).toString());
                        }
                    }
                }
            }
        }
        return dicWords;
    }

    public static boolean checkWordOnBoard(String word, char [][] board) {
        ArrayList<String> arrWords = new ArrayList<>();

        for(int i = 0; i < word.length(); i++) {
            arrWords.add(Character.toString(word.charAt(i)));
        }

        for(int i = 0; i < board.length; i++ ) {
            for (int j = 0; j <board[0].length; j++) {
                if(board[i][j] == word.charAt(0)) {
                    intStartR = i;
                    intStartC = j;
                    ArrayList<String> validWords = searchBoggle(board, word);
                    if(validWords.indexOf(word) > -1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        /*
        // Hardcode route
        char [][] board = {
                {'W', 'S', 'J', 'T', 'R'},
                {'U', 'L', 'M', 'O', 'E'},
                {'C', 'F', 'X', 'K', 'Y'},
                {'T', 'A', 'N', 'D', 'L'},
                {'B', 'H', 'B', 'J', 'E'},
        };*/

        // File route
        System.out.println("Please enter the length and width of the board");
        int length = input.nextInt();
        int width = input.nextInt();
        char [][] board = new char[length][width]; // You can replace this with other char arrays
        System.out.println("Please enter the name of the board file.");
        String strBlank = input.nextLine();
        String strFile = input.nextLine();
        File fileBoard = new File(strFile);
        Scanner inputBrd = new Scanner(fileBoard);

        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                board[i][j] = inputBrd.next().charAt(0);
            }
        }
        inputBrd.close();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("Please enter the word you would like to search for.");
        String word = input.nextLine().toUpperCase();
        boolean isFound = checkWordOnBoard(word, board);
        System.out.println(isFound);

        // This method is a bit suspect
        // Not anymore :^)
        ArrayList<String> validWords = searchBoggle(board, word);
        System.out.println(validWords);
        findAllWords(board);
    }
}
