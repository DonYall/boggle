package BoggleGame;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*; 
//NOT FINISHED 
//still need to finish: connect with other part  
public class BoggleGUI2 extends JFrame{
	//declare static variables 
	static boolean playerAI = false; 
	static int goesFirst = 1; 
	static boolean allowPause = true; 
	static boolean pause = true; 
	static int score1 = 0; 
	static int score2 = 0; 
	static int goal = 15; 
	static String word = ""; 
	static char[][] map=  {{'A', 'A', 'A', 'F', 'R', 'S'}, {'A', 'E', 'E', 'G', 'M', 'U'}, {'C', 'E', 'I', 'I', 'L', 'T'},{ 'D', 'H', 'H', 'N', 'O', 'T'}, {'F', 'I', 'P', 'R', 'S', 'Y'}};
	//static char[][]= Dice.getDice(); 
	static JLabel[][] showMap = new JLabel [map.length][map[0].length]; 
	static int counter = 0; 
	static ArrayList<String> path = new ArrayList<String>(); 
	static boolean player1; 
	static int player = 1; 
	static boolean played = false; 
	
	public static void main (String args[]) // create main method 
	{
		//TESTING 
		path.add("12");
		path.add("13"); 
		
		//creating GUI components 
		JFrame frame = new JFrame("boggle game"); 
		frame.setSize(750, 680); 
		
		JPanel panInstruction = new JPanel();
		panInstruction.setPreferredSize(new Dimension(650,550)); 
		
		//INSTRUCTION PANEL 
		//declare components for instruction label 
		JLabel instructionLabel0 = new JLabel("INSTRUCTION: ");
		JLabel instructionLabel1 = new JLabel("Boggle overview");
		JLabel instructionLabel2 = new JLabel("Boggle is a Hasbro word game that requires 16 letter cubes, "); 
		JLabel instructionLabel3 = new JLabel("the cube grid with dome, and a 3-minute sand timer, "); 
		JLabel instructionLabel4 = new JLabel("all of which should be included with your game. ");
		JLabel instructionLabel5 = new JLabel("The game requires a minimum of two players with no maximum. "); 
		JLabel instructionLabel6 = new JLabel("It is recommended for ages 8 and up.");
		JLabel instructionLabel7 = new JLabel("The goal of the game is have the highest point total. "); 
		JLabel instructionLabel8 = new JLabel("To gain points, players must create words from the randomly assorted letters in the cube grid. "); 
		JLabel instructionLabel9 = new JLabel("The longer the word, the higher the point value of the word, according to Boggle rules.");
		//add components to panInstruction
		panInstruction.add(instructionLabel0); 
		panInstruction.add(instructionLabel1); 
		panInstruction.add(instructionLabel2); 
		panInstruction.add(instructionLabel3); 
		panInstruction.add(instructionLabel4); 		
		panInstruction.add(instructionLabel5); 
		panInstruction.add(instructionLabel6); 
 		panInstruction.add(instructionLabel7); 
 		panInstruction.add(instructionLabel8); 
 		panInstruction.add(instructionLabel9); 
 		//set visible of panInstruction to true 
 		panInstruction.setVisible(false);

 		//PAUSE PANEL 
 		//declare GUI components for panPause 
		JPanel panPause = new JPanel(); 
		JLabel pauseLabel = new JLabel("Game paused"); 
		JButton resumeBtn = new JButton("Resume"); 
		//add components to panPause
		pauseLabel.setFont(new Font("Bradley Hand", Font.BOLD, 40)); //set font of the JLabel 
		panPause.add(pauseLabel); 
		panPause.add(resumeBtn);
		//set visible to false 
		panPause.setVisible(false);
		
		//PLAYING PANELS
		//declare components for playing panels 
		JPanel panGuess = new JPanel(new FlowLayout()); 
		JPanel panBoggle = new JPanel(new GridLayout(map.length, map[0].length)); 
		JPanel panAIGuess = new JPanel(new FlowLayout()); 
		JPanel panScore= new JPanel (new FlowLayout()); 
		JLabel guessLabel = new JLabel("What is your answer? "); 
		JTextField guessIn = new JTextField("Please enter your answer here"); 
		JButton guessBtn = new JButton("Enter"); 
		JLabel computerLabel0 = new JLabel("The answer from computer is... "); 
		JLabel computerLabel1 = new JLabel("still thinking... "); 
		JLabel scoreLabel0 = new JLabel("Current score of player 1:"); 
		JLabel scoreLabel1 = new JLabel("0"); 
		JLabel scoreLabel2 = new JLabel("          Current score of player 2:"); 
		JLabel scoreLabel3 = new JLabel("0"); 
		
		panScore.setPreferredSize(new Dimension(3, 550));
		panBoggle.setPreferredSize(new Dimension(600, 550)); 
		
		panGuess.add(guessLabel);  
		panGuess.add(guessIn); 
		panGuess.add(guessBtn); 
		panAIGuess.add(computerLabel0); 
		panAIGuess.add(computerLabel1); 
		panScore.add(scoreLabel0);
		panScore.add(scoreLabel1); 
		panScore.add(scoreLabel2); 
		panScore.add(scoreLabel3); 
		
		panScore.setVisible(false); 
		panBoggle.setVisible(false);
		panGuess.setVisible(false);
		panAIGuess.setVisible(false);
		
		//CUSTOMISATION PANEL 
		JPanel panCustomisation = new JPanel(new GridLayout(9, 2)); 
		panCustomisation.setPreferredSize(new Dimension(650, 550));
		
		JLabel customisationLabel0 = new JLabel("CUSTOMISATION"); 
		JLabel playersMode = new JLabel("Which mdoe would you like to choose? "); 
		JButton singlePlayerBtn = new JButton("Single Player"); 
		JButton twoPlayerBtn = new JButton("Two Player"); 
		JLabel whoStartLabel = new JLabel("Who wants to start first? If you chose single player"); 
		JButton player1Btn = new JButton("Player1"); 
		JButton player2Btn = new JButton("Player2"); 
		JLabel allowPauseLabel = new JLabel("Will this game allow pausing? "); 
		JButton yesBtn = new JButton("Yes"); 
		JButton noBtn = new JButton("No"); 
		JTextField ptIn = new JTextField("Enter points to play"); 
		JLabel targetScore = new JLabel("Tournament scroe? Please enter an integer. "); 
		JButton ptBtn = new JButton("Enter"); 
		
		panCustomisation.add(customisationLabel0); 
		panCustomisation.add(new JLabel(""));
		panCustomisation.add(playersMode); 
		panCustomisation.add(new JLabel(""));
		panCustomisation.add(singlePlayerBtn); 
		panCustomisation.add(twoPlayerBtn); 
		panCustomisation.add(whoStartLabel);
		panCustomisation.add(new JLabel(" mode, you are player1 while the AI is player2"));
		panCustomisation.add(player1Btn);
		panCustomisation.add(player2Btn); 
		panCustomisation.add(allowPauseLabel); 
		panCustomisation.add(new JLabel(""));
		panCustomisation.add(yesBtn);
		panCustomisation.add(noBtn);
		panCustomisation.add(targetScore); 
		panCustomisation.add(new JLabel(""));
		panCustomisation.add(ptIn); 
		panCustomisation.add(ptBtn); 
		panCustomisation.setVisible(false); 
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar(); 
		//menu 
		JMenu menu = new JMenu("Menu");  // restart, shake up button, instruction, customisation
		JMenu pauseMenu = new JMenu("Pause");  // pause, resume
		//menu menu items 
		JMenuItem instruction = new JMenuItem("Instruction"); 
		JMenuItem restart = new JMenuItem("Home/ Restart"); 
		JMenuItem shakeUp = new JMenuItem("Shake-up"); 
		JMenuItem customisation = new JMenuItem("Customisation"); 
		//pause menu item 
		JMenuItem pauseOp = new JMenuItem( "Pause"); 
		JMenuItem resume = new JMenuItem("Resume"); 
		
		//MENU BAR 
		menuBar.add(menu); 
		menuBar.add(pauseMenu);
		frame.setJMenuBar(menuBar); 
		//MENU - add menu items
		menu.add(restart);
		menu.add(instruction);  
		menu.add(customisation); 
		
		//HOME PAGE PANEL 
		JPanel panStart = new JPanel(); 
		JButton playBtn = new JButton("Play"); 
		JLabel tips = new JLabel ("Remember to check the munu for more features and intruction. "); 
		
		//PAUSE -- add menu items 
		pauseMenu.add(pauseOp); 
		pauseMenu.add(resume); 
		
		//START PANEL 
		GridLayout gl1 = new GridLayout (2,1);
		panStart.setLayout(gl1);
		panStart.setPreferredSize(new Dimension(650, 50));
		panStart.add(playBtn); 
		panStart.add(tips); 
		frame.add(panStart, BorderLayout.NORTH); 
		frame.setVisible(true); 
		
		for (int i =0; i<map.length; i++) {
			for (int j=0; j<map[i].length; j++)
			{
				showMap[i][j] = new JLabel(Character.toString(map[i][j])); 
				panBoggle.add(showMap[i][j]); 
				showMap[i][j].setBackground(Color.WHITE); 
				showMap[i][j].setOpaque(true);
			}
		}
		
		class ptBtnPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				goal = Integer.parseInt(ptIn.getText()); 
				System.out.println("ptBtnPressed pressed, goal stored"); 
			}
		}
		ptBtn.addActionListener(new ptBtnPressed());
		
		class singlePlayerPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				playerAI = true;
				System.out.println("singlePlayerBtn presse");
				System.out.print(playerAI);
			}
		}
		singlePlayerBtn.addActionListener(new singlePlayerPressed()); 
		
		class twoPlayerPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				playerAI = false; 
				System.out.println("twoPlayerBtn pressed, playerAI to false"); 
			}
		}
		twoPlayerBtn.addActionListener(new twoPlayerPressed()); 
		
		class player1Pressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				goesFirst = 1; 
			}
		}
		player1Btn.addActionListener(new player1Pressed()); 
		
		class player2Pressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				goesFirst = 2; 
				
			}
		}
		player2Btn.addActionListener(new player2Pressed()); 
		
		class yesPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				allowPause = true; 
			}
		}
		yesBtn.addActionListener(new yesPressed()); 
		
		class noPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				allowPause = false; 
			}
		}
		noBtn.addActionListener(new noPressed()); 
		
		class instructionPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame.getContentPane().removeAll();
				frame.repaint(); 
				frame.revalidate(); 
				
				frame.add(panInstruction); 
				panInstruction.setVisible(true);
				validate(); 
			}
		}
		instruction.addActionListener(new instructionPressed());
		
		class restartPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint();
				frame.revalidate(); 
				
				if (played) 
				{
					changeColor(false); 
					played = false; 
				}
				
				frame.add(panStart); 
				panStart.setVisible(true);
				validate(); 
				
				//randomise the dice 
				//set score to zero 
				//stop timer 
			}
			
		}
		restart.addActionListener(new restartPressed());
		
		class shakeUpPressed extends JFrame implements ActionListener
		{
			public void actionPerformed (ActionEvent e)
			{
				for (int i =0; i<map.length; i++) {
					for (int j=0; j<map[i].length; j++)
					{
						showMap[i][j].setBackground(Color.WHITE); 
					}
				}
				//randomise the dice 
			}
		}
		shakeUp.addActionListener(new shakeUpPressed());
		
		class customisationPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint(); 
				frame.revalidate(); 
				
				frame.add(panCustomisation); 
				panCustomisation.setVisible(true);
				validate(); 
			}
			
		}
		customisation.addActionListener(new customisationPressed());
		
		class pausePressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint(); 
				frame.revalidate(); 
				
				//stop the timer 
				frame.add(panPause); 
				panPause.setVisible(true); 
			}
			
		}
		pauseOp.addActionListener(new pausePressed());
		
		class resumePressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint(); 
				frame.revalidate(); 
				
				frame.add(panGuess, BorderLayout.NORTH); 
				panGuess.setVisible(true); 
				
				frame.add(panBoggle, BorderLayout.CENTER); 
				panBoggle.setVisible(true); 
				
			}
		}
		resume.addActionListener(new resumePressed());
		resumeBtn.addActionListener(new resumePressed()); 
		
		class playPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				System.out.println("playPressed"); 
				
				played = true; 
				
				frame.getContentPane().removeAll();
				frame.repaint(); 
				frame.revalidate();
				
				//Player 1 
				frame.add(panGuess, BorderLayout.NORTH); 
				panGuess.setVisible(true); 
				
				//panBoggle.removeAll();
				//panBoggle.repaint(); 
				//panBoggle.revalidate();
				
				changeColor(false); 
				frame.add(panBoggle, BorderLayout.SOUTH); 
				frame.add(panScore, BorderLayout.CENTER); 
				panScore.setVisible(true);
				panBoggle.setVisible(true); 
			}
		}
		playBtn.addActionListener(new playPressed()); 
		
		class guessBtnPressed extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) { 
				String scoreString; 
				String guess = guessIn.getText(); 
				System.out.println(player); 
				changeColor(false); //turn the previous cell back to white 
				
				switch(player%2)
				{
				case 1: 
					//player 1 
					// check word is valid or not 
					// if it is valid, update the gui, return path 
					//path stored when checking valid or not
					score1 = score1 + 5; // 5 should be a value returned by a method 
					scoreString = Integer.toString(score1); 
					scoreLabel1.setText(scoreString); 
					
					changeColor(true); 
					
					if (playerAI)
					{
						frame.remove(panGuess); 
						frame.add(panAIGuess); 
						panAIGuess.setVisible(true); 
						
						//word = the thing that AI generated 
						//check if it is valid 
						//if it is valid, update the gui 
						//path stored when checking valid or not 
						
						score2 = score2 + 5; // 5 should be a value returned by a method 
						scoreString = Integer.toString(score2); 
						scoreLabel1.setText(scoreString); 
					}
					break; 
					
				case 2: 
					//player 2
					if (!playerAI) 
					{
						// check word is valid or not
						// if it is valid, update the gui, return path 
						//path stored when checking valid or not 
						
						score2 = score2 + 5; // 5 should be a value returned by score related method 
						scoreString = Integer.toString(score2); 
						scoreLabel3.setText(scoreString); 
					}
					changeColor(true); 
					break; 
				}
				
				if (playerAI)
				{
					player++; 
				}
				
				for (int i =0; i<guess.length(); i++)
				{
					word = word + guess.substring(i).toUpperCase(); 
				}
				
				if (counter == 2)
				{
					menu.add(shakeUp); 
				}
			}
		}
		guessBtn.addActionListener(new guessBtnPressed()); 
			
	}
	
	
	
	public static void changeColor (boolean showFinal)
	{
		System.out.print("Change color invoked");
		
		if (path.size() !=0)
		{
			for (int i =0; i<path.size(); i++) //for loop 
			{
				String rowCol = path.get(i); //get x y coordinate 
				int row = Integer.parseInt(""+rowCol.charAt(0)); //get y coordinate 
				int col = Integer.parseInt(""+rowCol.charAt(1)); //get x coordinate 
				
				if (showFinal)
				{
					System.out.println(" colorToOrange invoked"); 
					showMap[row][col].setBackground(Color.ORANGE); 
				}
				else 
				{
					System.out.println(" colorToWhite invoked"); 
					showMap[row][col].setBackground(Color.WHITE); 
					showMap[row][col].setOpaque(true);
				}
			}
		}
	}
}
