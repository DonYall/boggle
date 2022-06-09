package BoggleGame;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*; 

//ALMOST FINISHED 
//still need to finish: boggle update (idk what should I update it to)

public class BoggleGUI extends JFrame{
	
	static int counter = 0; //count how rounds happened 
	static boolean playerAI = true; 
	static int goesFirst = 1; 
	static boolean allowPause = true; 
	static int goal = 15; 
	static int score = 0; 
	static String word = ""; 
	static char[][] map=  {{'A', 'A', 'A', 'F', 'R', 'S'}, {'A', 'E', 'E', 'G', 'M', 'U'}, {'C', 'E', 'I', 'I', 'L', 'T'},{ 'D', 'H', 'H', 'N', 'O', 'T'}, {'F', 'I', 'P', 'R', 'S', 'Y'}}; 
	static JLabel[][] showMap = new JLabel [map.length][map[0].length]; 
	static boolean pause; 
	static JFrame frame = new JFrame("boggle game"); 
	
	static //MENU BAR
	JMenuBar menuBar = new JMenuBar(); 
	//menu 
	JMenu menu = new JMenu("Menu");  // restart, shake up button, instruction, customisation
	JMenu pauseMenu = new JMenu("Pause");  // pause, resume
	//menu menu items 
	JMenuItem instruction = new JMenuItem("Instruction"); 
	JMenuItem restart = new JMenuItem("Home/ Restart"); 
	static JMenuItem shakeUp = new JMenuItem("Shake-up"); 
	JMenuItem customisation = new JMenuItem("Customisation"); 
	//pause menu item 
	JMenuItem pauseOp = new JMenuItem( "Pause"); 
	JMenuItem resume = new JMenuItem("Resume"); 
	
	
	//Component for INSTRUCTION PANEL 
	JPanel panInstruction = new JPanel();
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
	
	//Component for HOME PAGE PANEL 
	JPanel panStart = new JPanel(); 
	JButton playBtn = new JButton("Play"); 
	JLabel tips = new JLabel ("Remember to check the munu for more features and intruction. "); 
	
	//component for CUTOMISATION PANEL 
	JPanel panCustomisation = new JPanel(new GridLayout(9, 2)); 
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
	
	//components for PAUSE PANEL
	JPanel panPause = new JPanel(); 
	JLabel pauseLabel = new JLabel("Game paused"); 
	JButton resumeBtn = new JButton("Resume"); 
	
	//components for PLAYING PANELS
	static JPanel panGuess = new JPanel(new FlowLayout()); 
	JPanel panBoggle = new JPanel(new GridLayout(map.length, map[0].length)); 
	static JPanel panAIGuess = new JPanel(new FlowLayout()); 
	JLabel guessLabel = new JLabel("What is your answer? "); 
	JTextField guessIn = new JTextField("Please enter your answer here"); 
	JButton guessBtn = new JButton("Enter"); 
	JLabel computerLabel0 = new JLabel("The answer from computer is... "); 
	JLabel computerLabel1 = new JLabel("still thinking... "); 
	
	public BoggleGUI()
	{
		//creating GUI components 
		frame.setSize(650, 550); 
		
		//INSTRUCION PANEL 
		panInstruction.setPreferredSize(new Dimension(650,550)); 
		
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
		panInstruction.setVisible(false);
		
		//PAUSE PANEL 
		pauseLabel.setFont(new Font("Bradley Hand", Font.BOLD, 40)); 
		panPause.add(pauseLabel); 
		panPause.add(resumeBtn);
		panPause.setVisible(false);
		
		//PLAYING PANEL 
		panGuess.add(guessLabel); 
		panGuess.add(guessIn); 
		panGuess.add(guessBtn); 
		panAIGuess.add(computerLabel0); 
		panAIGuess.add(computerLabel1); 
		
		panBoggle.setVisible(false);
		panGuess.setVisible(false);
		panAIGuess.setVisible(false);
		
		//CUSTOMISATION PANEL 
		panCustomisation.setPreferredSize(new Dimension(650, 550));
		
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
		menuBar.add(menu); 
		menuBar.add(pauseMenu);
		frame.setJMenuBar(menuBar); 
		//MENU - add menu items
		menu.add(restart);
		menu.add(instruction);  
		//menu.add(shakeUp); 
		menu.add(customisation); 
		
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
				
				//choose player mode (default player to ai)
				//point to play (default 15 points )
				//allow pause or not (default "allowed")
				//who goes first (default player 1)
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
				
				frame.getContentPane().removeAll();
				frame.repaint(); 
				frame.revalidate();
				
				//Player 1 
				frame.add(panGuess, BorderLayout.NORTH); 
				panGuess.setVisible(true); 
				
				//this for loop suppose to be something 
				for (int i =0; i<map.length; i++) {
					for (int j=0; j<map[i].length; j++)
					{
						showMap[i][j] = new JLabel(Character.toString(map[i][j])); 
						//System.out.println(Character.toString(map[i][j])); 
						panBoggle.add(showMap[i][j]); 
						//System.out.println(showMap[i][j].getText()); 
						showMap[i][j].setOpaque(true);
					}
				}
				
				frame.add(panBoggle); 
				panBoggle.setVisible(true); 
				
				play(); 
			}
		}
		playBtn.addActionListener(new playPressed()); 
		
		class guessBtn extends JFrame implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				String guess = guessIn.getText(); 
				for (int i =0; i<guess.length(); i++)
				{
					word = word + guess.substring(i).toUpperCase(); 
				}
			}
			
		}
	}
	
	public static void play ()
	{
		ArrayList<String> path = new ArrayList<String>(); 
		do 
		{
			//player 1
			// check word is valid or not
			// if it is valid, update the gui, return path 
			//path stored when checking valid or not 
			
			//player 2
			if (playerAI)
			{
				frame.remove(panGuess); 
				frame.add(panAIGuess); 
				
				
				//word = the thing that AI generated 
				//check if it is valid 
				//if it is valid, update the gui 
				//path stored when checking valid or not 
			}
			else 
			{
				// check word is valid or not
				// if it is valid, update the gui, return path 
				//path stored when checking valid or not 
							
			}
			counter ++; 
			if (counter >2)
			{
				menuBar.add(shakeUp); 
			}
		}while (!pause || score != goal); 
	}
	
	public static void updateGUI (ArrayList<String> path)
	{
		for (int i =0; i<path.size(); i++) //for loop 
		{
			String rowCol = path.get(i); //get x y coordinate 
			int row = Integer.parseInt(""+rowCol.charAt(0)); //get y coordinate 
			int col = Integer.parseInt(""+rowCol.charAt(1)); //get x coordinate 
			
			//A method that can change the GUI, but idk what do we want (eg I am going to invoke a method tho)
			//changeColor(maze[row][col].getText(), row, col); //invoke sub method 
		}
	}
}
