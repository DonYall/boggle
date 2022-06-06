import javax.swing.*;
import java.io.*;
import java.util.*; 
public class Boggle1 extends JFrame{
	JLabel enter = new JLabel ("Enter"); 
	static File dictionary = new File("dictionary.txt"); 
	static boolean valid = false; 
	static ArrayList<String> dictionaryList = new ArrayList<String>(); 
	
	static {
		try
		{
			Scanner sc = new Scanner (dictionary); 
			while (sc.hasNext())
			{
				dictionaryList.add(sc.nextLine()); 
			}
		}
		catch(IOException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public static void main (String args[])
	{
		 
		System.out.println(checkDictonary("dajhbcksvfueyjvgdhc")); 
	}
	
	public Boggle1()//constructor 
	{
		
	}
	
	public static boolean checkDictonary(String word)
	{
		String checking; 
		valid = false; 
		for (int i =0; i<dictionaryList.size() ; i++)
		{
			checking = dictionaryList.get(i); 
			if (checking.length() == word.length() || !valid)
			{
				if (checking.equals(word))
				{
					valid = true; 
					return true; 
				}
			}
		}
		return false; 
	}
}
