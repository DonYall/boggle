import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CheckWordsInBoard {
    public static int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    public static int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
    public static int intStartR = 0;
    public static int intStartC = 0;
    public static boolean isSafe(int x, int y, boolean[][] processed, char[][] board, ArrayList<String> words) {
        // return (x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length) && !processed[x][y] ;
        boolean isSafe = false;
        if((x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length) && !processed[x][y]) {
            if(words.indexOf(Character.toString(board[x][y])) < 0){
                isSafe = false;
            }
            else {
                isSafe = true;
            }
        }
        return isSafe;
    }

    // A recursive function to generate and print all possible words in a boggle
    public static void searchBoggle(char[][] board, String word, ArrayList<String> arrWords,
                                    ArrayList<String> result, boolean[][] processed,
                                    int i, int j, String path) {
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
            if (isSafe(i + row[k], j + col[k], processed, board, arrWords)) {
                searchBoggle(board, word, arrWords, result, processed, i + row[k], j + col[k], path);
            }
        }
        // Mark the current node as unprocessed
        processed[i][j] = false;
    }

    // Find one word in the Boggle Matrix
    /*public static ArrayList<String> searchBoggle(char[][] board, String word, ArrayList<String> arrWords) {
        ArrayList<String> result = new ArrayList<>();

        if (board.length == 0) {
            return result;
        }

        boolean[][] processed = new boolean[board.length][board[0].length];

        searchBoggle(board, word, arrWords, result, processed, intStartR, intStartC, "");
        return result;
    }*/

    public static ArrayList<String> searchBoggle(char[][] board, String word, ArrayList<String> arrWords) {
        ArrayList<String> result = new ArrayList<>();

        if (board.length == 0) {
            return result;
        }

        boolean[][] processed = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++ ) {
            for (int j = 0; j <board[0].length; j++) {
                if(board[i][j] == word.charAt(0)) {
                    intStartR = i;
                    intStartC = j;
                    searchBoggle(board, word, arrWords, result, processed, intStartR, intStartC, "");
                    if(result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public static void findAllWords(char [][] board) {
        Map<String, ArrayList<String>> mapDic = new HashMap<String, ArrayList<String>>();
        ArrayList<String> dicWords = new ArrayList<String>();

        //Put dictionary into Map
        File fileDictionary = new File("dictionary.txt");
        try {
            Scanner inputDic = new Scanner(fileDictionary);
            while(inputDic.hasNext()){
                String strNext = inputDic.next().toUpperCase();
                if(strNext.length() > 2) {
                    // arrDic.add(strNext);
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
                    String words = arrWordList.get(k);
                    ArrayList<String> arrWords = new ArrayList<String>();

                    for(int l = 0; l < words.length(); l++) {
                        arrWords.add(Character.toString(words.charAt(l))) ;
                    }
                    ArrayList<String> validWords = searchBoggle(board, words, arrWords);

                    if(!validWords.isEmpty()) {
                        if(!(dicWords.contains(validWords.get(0).toString()))){
                            dicWords.add(validWords.get(0).toString());
                        }
                    }
                }
            }
        }
        // Not mandatory you could just return the arraylist instead
        for(String s : dicWords){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        // Still can't find a way to read from file without the future methods taking
        // 10 years to complete :/
        // Fixed, kind of
        char [][] board = {
                {'W', 'S', 'J', 'T', 'R'},
                {'U', 'L', 'M', 'O', 'E'},
                {'C', 'F', 'X', 'K', 'Y'},
                {'T', 'A', 'N', 'D', 'L'},
                {'B', 'H', 'B', 'J', 'E'},
        };

        /*
        int length = input.nextInt();
        int width = input.nextInt();
        char [][] board = new char[length][width]; // You can replace this with other char arrays
        File fileBoard = new File("board.txt");
        Scanner inputBrd = new Scanner(fileBoard);

        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                board[i][j] = inputBrd.next().charAt(0);
            }
        }
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }

        String blank = input.nextLine();
        */
        String word = input.nextLine().toUpperCase();
        ArrayList<String> arrWords = new ArrayList<>();

        for(int i = 0; i < word.length(); i++) {
            arrWords.add(Character.toString(word.charAt(i))) ;
        }

        // This method is a bit suspect
        ArrayList<String> validWords = searchBoggle(board, word, arrWords);
        System.out.println(validWords);
        findAllWords(board);
    }
}
