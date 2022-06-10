package ok;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GetAllWords {
    public int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    public int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
    public int intStartR = 0;
    public int intStartC = 0;
    public boolean isSafe(int x, int y, boolean[][] processed, char[][] board, ArrayList<String> words) {
        //return (x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length) && !processed[x][y] ;
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

    // A recursive function to generate all possible words in a boggle
    public void searchBoggle(char[][] board, String words, ArrayList<String> arrWords,
                                    ArrayList<String> result, boolean[][] processed,
                                    int i, int j, String path) {
        // Mark the current node as processed
        processed[i][j] = true;
        // Update the path with the current character and insert it into the set
        path += board[i][j];

        // Check whether the path is present in the input set
        if (words.equals(path)) {
            result.add(path);
            return;
        }
        // Check for all eight possible movements from the current cell
        for (int k = 0; k < row.length; k++) {
            if (isSafe(i + row[k], j + col[k], processed, board, arrWords)) {
                searchBoggle(board, words,arrWords, result, processed, i + row[k], j + col[k], path);
            }
        }
        // Mark the current node as unprocessed
        processed[i][j] = false;
    }

    // Find one word in the Boggle Matrix
    public ArrayList<String> searchBoggle(char[][] board, String words, ArrayList<String> arrWords ) {
        ArrayList<String> result = new ArrayList<>();

        if (board.length == 0) {
            return result;
        }
        int M = board.length;
        int N = board[0].length;
        boolean[][] processed = new boolean[M][N];

        searchBoggle(board, words, arrWords, result, processed, intStartR, intStartC, "");
        return result;
    }

    public ArrayList<String> findAllWords(char [][] board) {
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
                        dicWords.add(validWords.toString());
                    }
                }
            }
        }
        
        return(dicWords);
    }

    public ArrayList<String> getWords() {
        Scanner input = new Scanner(System.in);
        // Still can't find a way to read from file without the future methods taking
        // 10 years to complete :/
        char [][] board = {
                {'W', 'S', 'J', 'T', 'R'},
                {'U', 'L', 'M', 'O', 'E'},
                {'C', 'F', 'X', 'K', 'Y'},
                {'T', 'A', 'N', 'D', 'L'},
                {'B', 'H', 'B', 'J', 'E'},
        };

        String word = input.nextLine();
        ArrayList<String> arrWords = new ArrayList<>();

        for(int i = 0; i < word.length(); i++) {
            arrWords.add(Character.toString(word.charAt(i))) ;
        }

        return findAllWords(board);
    }
}