package BoggleGame;

import javax.swing.*;
import java.awt.event.*; 
import java.awt.*; 

//NOT FINISHED 
//still need to finish: boggle update, 2 player takes turn thing 

public class BoggleGUI extends JFrame{
	static boolean playerAI = true; 
	static int goesFirst = 1; 
	static boolean allowPause = true; 
	static int goal = 15; 
	static String word = ""; 
	static char[][] map=  {{'A', 'A', 'A', 'F', 'R', 'S'}, {'A', 'E', 'E', 'G', 'M', 'U'}, {'C', 'E', 'I', 'I', 'L', 'T'},{ 'D', 'H', 'H', 'N', 'O', 'T'}, {'F', 'I', 'P', 'R', 'S', 'Y'}}; 
	static JLabel[][] showMap = new JLabel [map.length][map[0].length]; 
	public static void main (String args[])
	{
		
		
		//creating GUI components 
		JFrame frame = new JFrame("boggle game"); 
		frame.setSize(650, 550); 
		
		JPanel panInstruction = new JPanel();
		panInstruction.setPreferredSize(new Dimension(650,550)); 
		
		//INSTRUCTION PANEL 
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
		JPanel panPause = new JPanel(); 
		JLabel pauseLabel = new JLabel("Game paused"); 
		JButton resumeBtn = new JButton("Resume"); 
		
		pauseLabel.setFont(new Font("Bradley Hand", Font.BOLD, 40)); 
		panPause.add(pauseLabel); 
		panPause.add(resumeBtn);
		panPause.setVisible(false);
		
		//PLAYING PANEL 
		JPanel panGuess = new JPanel(new FlowLayout()); 
		JPanel panBoggle = new JPanel(new GridLayout(map.length, map[0].length)); 
		panBoggle.setVisible(false);
		
		JLabel guessLabel = new JLabel("What is your answer? "); 
		JTextField guessIn = new JTextField("Please enter your answer here"); 
		JButton guessBtn = new JButton("Enter"); 
		panGuess.add(guessLabel); 
		panGuess.add(guessIn); 
		panGuess.add(guessBtn); 
		
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
		JMenu pause = new JMenu("Pause");  // pause, resume
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
		menuBar.add(pause);
		frame.setJMenuBar(menuBar); 
		//MENU - add menu items
		menu.add(restart);
		menu.add(instruction);  
		menu.add(shakeUp); 
		menu.add(customisation); 
		
		//HOME PAGE PANEL 
		JPanel panStart = new JPanel(); 
		JButton playBtn = new JButton("Play"); 
		JLabel tips = new JLabel ("Remember to check the munu for more features and intruction. "); 
		
		//PAUSE -- add menu items 
		pause.add(pauseOp); 
		pause.add(resume); 
		
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
				
				//everything start doing things (panBoggle)
				//player1 always read user input --> a method that create text field, read user input, invoke checking method
				//player2 if (boolean playerAI is true, invoke AI method that create AI guess)
				//              else (method that player1 used -- >method take the number of player as parameter eg player"1" , player"2")
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
}
