package _project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GetAllWords {
	public int wordsToGuess;
    public int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    public int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
    public int intStartR = 0;
    public int intStartC = 0;
    public boolean isSafe(int x, int y, boolean[][] processed, char[][] board, ArrayList<String> words) {
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
    public void searchBoggle(char[][] board, String word, ArrayList<String> arrWords,
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

    public ArrayList<String> searchBoggle(char[][] board, String word, ArrayList<String> arrWords) {
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

    public ArrayList<String> findAllWords(char [][] board) {
    	ArrayList<String> finalWords = new ArrayList<String>();
        Map<String, ArrayList<String>> mapDic = new HashMap<String, ArrayList<String>>();
        ArrayList<String> dicWords = new ArrayList<String>();

        //Put dictionary into Map
        File fileDictionary = new File("dictionary.txt");
        try {
            Scanner inputDic = new Scanner(fileDictionary);
            while(inputDic.hasNext()){
                String strNext = inputDic.next();
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
                        arrWords.add(Character.toString(words.charAt(l)));
                    }
                    ArrayList<String> validWords = searchBoggle(board, words, arrWords);

                    if(!validWords.isEmpty()) {
                        if(!(dicWords.contains(validWords.get(0).toString()))){
                            finalWords.add(validWords.get(0).toString());
                            System.out.println(dicWords.get(0));
                            if (dicWords.size() >= wordsToGuess) return(dicWords);
                        }
                    }
                }
            }
		}
        return(dicWords);
    }

    public ArrayList<String> getWords(char[][] board, int totalWords) {
        // Still can't find a way to read from file without the future methods taking 10 years to complete :/
        // Fixed, kind of
    	wordsToGuess = totalWords;
        return(findAllWords(board));
    }
    
    public int binarySearch(String[] arr, int l, int r, String x)
    {
    	// Restrict the boundary of right index
        // and the left index to prevent
        // overflow of indices
        if (r >= l && l <= arr.length - 1) {
 
            int mid = l + (r - l) / 2;
            int res = x.compareTo(arr[mid]);

            // If the element is present
            // at the middle itself
            if (res == 0) {
                return mid;
            }
            // If element is smaller than mid, then it can
            // only be present in left subarray
            if (res < 0 ) {
                return binarySearch(arr, l, mid - 1, x);
            }
            // Else the element can only be present
            // in right subarray
            else if (res > 0) {
            	return binarySearch(arr, mid + 1, r, x);
            }
        }
 
        // We reach here when element is not present in
        // array
        return -1;
    }
 
    // Method 2
    // Main driver method
    public void searc()
    {
 
        // Custom input array
        String arr[] = {"abs","bear","cringe","dare","enough","feeling" };
 
        // Length of array
        int n = arr.length;
 
        // Custom element to be checked
        // whether present or not
        String x = "feeling";
 
        // Calling above method
        int result = binarySearch(arr, 0, n - 1, x);
 
        // Element present
        if (result == -1)
 
            // Print statement
            System.out.println("Element not present");
 
        // Element not present
        else
 
            // Print statement
            System.out.println("Element found at index "
                               + result);
    }
}