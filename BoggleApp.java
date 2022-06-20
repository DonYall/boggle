import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BoggleApp {
    public static void main(String[] args){
        ArrayList<String> arrDic = new ArrayList<String>();
        // Set<String> setDic = new HashSet<String>();

        File fileDictionary = new File("dictionary.txt");
        try {
            Scanner inputDic = new Scanner(fileDictionary);
            while(inputDic.hasNext()){
                String strNext = inputDic.next().toUpperCase();
                if(strNext.length() > 2) {
                    arrDic.add(strNext);
                    // setDic.add(strNext);
                }
            }
            //System.out.println(arrDic);

            inputDic.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Boggle myFrame = new Boggle();
        BoggleFrame myFrame = new BoggleFrame(arrDic);
    }
}