package BoggleGame;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
//import java.util.Timer;
import javax.swing.Timer.*;
import java.awt.*;

//NOT FINISHED 
//still need to finish: user friendliness  
//There is a bug which is, repeated answer should not get mark. But it does

public class BoggleGUI3 extends JFrame {
	// declare static variables
	static boolean playerAI = false;
	static boolean allowPause = true;
	static boolean pause = true;
	static int score1 = 0;
	static int score2 = 0;
	static int goal = 15;
	static String word = ""; 
	// static char[][]= Dice.getDice();
	static Dice dice = new Dice();
	static char[][] map = dice.getDice();
	static JLabel[][] showMap = new JLabel[map.length][map[0].length];
	static int counter = 0;
//	static ArrayList<String> path = new ArrayList<String>();
	static ArrayList<String> wordsAI = new ArrayList<String>();
	static boolean player1;
	static int player = 1;
	static boolean played = false;
	static int wordGuessedValid = 0;

	public static int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
	public static int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
	public static int intStartR = 0;
	public static int intStartC = 0;
	public static boolean pathFound;

	public static ArrayList<int[]> coordinates = new ArrayList<>();

	public static void main(String args[]) // create main method
	{
		// creating GUI components
		JFrame frame = new JFrame("boggle game");
		frame.setSize(750, 680);

		JPanel panInstruction = new JPanel();
		panInstruction.setPreferredSize(new Dimension(650, 550));

		// INSTRUCTION PANEL
		// declare components for instruction label
		JLabel instructionLabel0 = new JLabel("INSTRUCTION: ");
		JLabel instructionLabel1 = new JLabel("Boggle overview");
		JLabel instructionLabel2 = new JLabel("Boggle is a Hasbro word game that requires 16 letter cubes, ");
		JLabel instructionLabel3 = new JLabel("the cube grid with dome, and a 3-minute sand timer, ");
		JLabel instructionLabel4 = new JLabel("all of which should be included with your game. ");
		JLabel instructionLabel5 = new JLabel("The game requires a minimum of two players with no maximum. ");
		JLabel instructionLabel6 = new JLabel("It is recommended for ages 8 and up.");
		JLabel instructionLabel7 = new JLabel("The goal of the game is have the highest point total. ");
		JLabel instructionLabel8 = new JLabel(
				"To gain points, players must create words from the randomly assorted letters in the cube grid. ");
		JLabel instructionLabel9 = new JLabel(
				"The longer the word, the higher the point value of the word, according to Boggle rules.");
		// add components to panInstruction
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
		// set visible of panInstruction to true
		panInstruction.setVisible(false);

		// PAUSE PANEL
		// declare GUI components for panPause
		JPanel panPause = new JPanel();
		JLabel pauseLabel = new JLabel("Game paused");
		JButton resumeBtn = new JButton("Resume");
		// add components to panPause
		pauseLabel.setFont(new Font("Bradley Hand", Font.BOLD, 40)); // set font of the JLabel
		panPause.add(pauseLabel);
		panPause.add(resumeBtn);
		// set visible to false
		panPause.setVisible(false);

		// PLAYING PANELS
		// declare components for playing panels
		JPanel panGuess = new JPanel(new FlowLayout());
		JPanel panBoggle = new JPanel(new GridLayout(map.length, map[0].length));
		JPanel panAIGuess = new JPanel(new FlowLayout());
		JPanel panScore = new JPanel(new FlowLayout());
		JPanel panTimer = new JPanel(new FlowLayout());
		JLabel guessLabel = new JLabel("What is your answer? ");
		JTextField guessIn = new JTextField("Please enter your answer here");
		JButton guessBtn = new JButton("Enter");
		JLabel computerLabel0 = new JLabel("The answer from computer is... ");
		JLabel computerLabel1 = new JLabel("still thinking... ");
		JLabel scoreLabel0 = new JLabel("Current score of player 1:");
		JLabel scoreLabel1 = new JLabel("0");
		JLabel scoreLabel2 = new JLabel("          Current score of player 2:");
		JLabel scoreLabel3 = new JLabel("0");
		JLabel winnerLabel = new JLabel();
		JButton homeBtn = new JButton("Back to homepage");

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

		// CUSTOMISATION PANEL
		JPanel panCustomisation = new JPanel(new GridLayout(9, 2));
		panCustomisation.setPreferredSize(new Dimension(650, 550));

		JLabel customisationLabel0 = new JLabel("CUSTOMISATION");
		JLabel playersMode = new JLabel("Which mdoe would you like to choose? ");
		JButton singlePlayerBtn = new JButton("Single Player");
		JButton twoPlayerBtn = new JButton("Two Player");
		JLabel whoStartLabel = new JLabel("Who wants to start first? If you chose single player mode, ");
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
		panCustomisation.add(new JLabel(" you are player1 while the AI is player2"));
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

		// MENU BAR
		JMenuBar menuBar = new JMenuBar();
		// menu
		JMenu menu = new JMenu("Menu"); // restart, shake up button, instruction, customisation
		JMenu pauseMenu = new JMenu("Pause"); // pause, resume
		// menu menu items
		JMenuItem instruction = new JMenuItem("Instruction");
		JMenuItem restart = new JMenuItem("Home/ Restart");
		JMenuItem shakeUp = new JMenuItem("Shake-up");
		JMenuItem customisation = new JMenuItem("Customisation");
		// pause menu item
		JMenuItem pauseOp = new JMenuItem("Pause");
		JMenuItem resume = new JMenuItem("Resume");

		// MENU BAR
		menuBar.add(menu);
		menuBar.add(pauseMenu);
		frame.setJMenuBar(menuBar);
		// MENU - add menu items
		menu.add(restart);
		menu.add(instruction);
		menu.add(customisation);

		// HOME PAGE PANEL
		JPanel panStart = new JPanel();
		JButton playBtn = new JButton("Play");
		JLabel tips = new JLabel("Remember to check the munu for more features and intruction. ");

		// PAUSE -- add menu items
		pauseMenu.add(pauseOp);
		pauseMenu.add(resume);

		// START PANEL
		GridLayout gl1 = new GridLayout(2, 1);
		panStart.setLayout(gl1);
		panStart.setPreferredSize(new Dimension(650, 50));
		panStart.add(playBtn);
		panStart.add(tips);
		frame.add(panStart, BorderLayout.NORTH);
		frame.setVisible(true);

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				showMap[i][j] = new JLabel(Character.toString(map[i][j]));
				panBoggle.add(showMap[i][j]);
				showMap[i][j].setBackground(Color.WHITE);
				showMap[i][j].setOpaque(true);
			}
		}

		class timerChangePlayer {
			Timer timer;

			public void change() {
				timer = new Timer(500, new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						int score;
						if (player == 1) {
							player = 2;
						} else {
							player = 1;
							if (playerAI) {
								// Let ai guess work first
								wordsAI = aiGuess();
								changeColor(true);

								for (int i = 0; i < wordsAI.size(); i++) {
									score2 = score2 + score(wordsAI.get(i));
									if (i == 0) {
										computerLabel1.setText(wordsAI.get(i));
									} else {
										computerLabel1.setText(computerLabel1.getText() + ", " + wordsAI.get(i));
									}
								}
								scoreLabel3.setText(Integer.toString(score2));
							}
						}
					}
				});
			}

			public void stopTimer() {
				if (timer.isRunning()) {
					timer.stop();
				}
			}

			// public void restartTimer()
			// {
			// timer.restart();
			// }
		}
		timerChangePlayer timerChangePlayer = new timerChangePlayer();

		class ptBtnPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				goal = Integer.parseInt(ptIn.getText());
				System.out.println("ptBtnPressed pressed, goal stored");
			}
		}
		ptBtn.addActionListener(new ptBtnPressed());

		class singlePlayerPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				playerAI = true;
				System.out.println("singlePlayerBtn presse");
				System.out.print(playerAI);
			}
		}
		singlePlayerBtn.addActionListener(new singlePlayerPressed());

		class twoPlayerPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				playerAI = false;
				System.out.println("twoPlayerBtn pressed, playerAI to false");
			}
		}
		twoPlayerBtn.addActionListener(new twoPlayerPressed());

		class player1Pressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				player = 1;
			}
		}
		player1Btn.addActionListener(new player1Pressed());

		class player2Pressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				player = 2;
			}
		}
		player2Btn.addActionListener(new player2Pressed());

		class yesPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (!allowPause) {
					pauseMenu.add(pauseOp);
				}
				allowPause = true;
			}
		}
		yesBtn.addActionListener(new yesPressed());

		class noPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				allowPause = false;
				pauseMenu.remove(pauseOp);
			}
		}
		noBtn.addActionListener(new noPressed());

		class instructionPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint();
				frame.revalidate();

				frame.add(panInstruction);
				panInstruction.setVisible(true);
				validate();
			}
		}
		instruction.addActionListener(new instructionPressed());

		class restartPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint();
				frame.revalidate();

				score1 = 0;
				score2 = 0;
				scoreLabel1.setText("0");
				scoreLabel3.setText("0");

				if (played) {
					changeColor(false);
					played = false;
				}

				frame.add(panStart);
				panStart.setVisible(true);
				validate();

				timerChangePlayer.stopTimer();
			}

		}
		restart.addActionListener(new restartPressed());

		class shakeUpPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				changeColor(false);
				randomizeBoggle();
			}
		}
		shakeUp.addActionListener(new shakeUpPressed());

		class customisationPressed extends JFrame implements ActionListener {
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

		class pausePressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint();
				frame.revalidate();

				// stop the timer

				frame.add(panPause);
				panPause.setVisible(true);
			}

		}
		pauseOp.addActionListener(new pausePressed());

		class resumePressed extends JFrame implements ActionListener {
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

		class playPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.out.println("playPressed");

				played = true;

				frame.getContentPane().removeAll();
				frame.repaint();
				frame.revalidate();

				// Player 1
				frame.add(panGuess, BorderLayout.NORTH);
				panGuess.setVisible(true);

				randomizeBoggle();

				changeColor(false);
				frame.add(panBoggle, BorderLayout.SOUTH);
				frame.add(panScore, BorderLayout.CENTER);
//				frame.add(new countdownTimerShow()); 
				panScore.setVisible(true);
				panBoggle.setVisible(true);

				timerChangePlayer.change();

				if (counter == 2) {
					menu.add(shakeUp);
				}

			}
		}
		playBtn.addActionListener(new playPressed());
		homeBtn.addActionListener(new restartPressed());

		//
		class guessBtnPressed extends JFrame implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				// String scoreString;
				String guess = guessIn.getText();
				System.out.println(player);
				changeColor(false); // turn the previous cell back to white

				if (findWord(guess, map)) {
					System.out.println("Valid answer");
					for (int i =0; i<coordinates.size(); i++)
					{
						System.out.println(coordinates.get(i)[0] + coordinates.get(i)[1]); 
					}
					//updatePath();
					
					for (int i =0; i<coordinates.size(); i++)
					{
						System.out.println(coordinates.get(i)[0]); 
						System.out.println(coordinates.get(i)[1]); 
					}
					
					if (player == 1) {
						score1 = score1 + score(guess);
						System.out.println("The score1 is " + score1);
						scoreLabel1.setText(Integer.toString(score1));
					} else {
						score2 = score2 + score(guess);
						System.out.println("The score2 is " + score2);
						scoreLabel3.setText(Integer.toString(score2));
						// scoreLabel1.setText("I am dead");

					}
					changeColor(true); // turn the answer cell to yellow
				} else {
					System.out.println("Invalid answer");
				}

				if (score1 == goal || score2 == goal) {
					frame.getContentPane().removeAll();
					frame.repaint();
					frame.revalidate();

					if (score1 == goal) {
						winnerLabel.setText("Player 1 won! ");
						frame.add(winnerLabel, BorderLayout.CENTER);
					} else {
						if (playerAI) {
							winnerLabel.setText("The AI won! ");
							frame.add(winnerLabel, BorderLayout.CENTER);
						} else {
							winnerLabel.setText("Player 2 won! ");
							frame.add(winnerLabel, BorderLayout.CENTER);
						}
					}

					frame.add(homeBtn, BorderLayout.SOUTH);
				}
			}
		}
		guessBtn.addActionListener(new guessBtnPressed());
	}

	public static void changeColor(boolean showFinal) {
		System.out.println("Change color invoked");

		for (int i = 0; i < coordinates.size(); i++) {
			// System.out.println("export path");
			System.out.println(coordinates.get(i)[0]);
			System.out.println(coordinates.get(i)[1]);
		}

		if (coordinates.size() != 0) {
			System.out.print("path size does not equal to zero");
			for (int i = 0; i < coordinates.size(); i++) // for loop
			{
				//String rowCol = coordinates.get(i); // get x y coordinate
				int row = coordinates.get(i)[0]; // get y coordinate
				int col =  coordinates.get(i)[1]; // get x coordinate

				if (showFinal) {
					System.out.println(" colorToOrange invoked");
					showMap[row][col].setBackground(Color.ORANGE);
				} else {
					System.out.println(" colorToWhite invoked");
					showMap[row][col].setBackground(Color.WHITE);
					showMap[row][col].setOpaque(true);
				}
			}
		}
	}

	public static void randomizeBoggle() {
		dice.shuffle();
		map = dice.getDice();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				showMap[i][j].setText(Character.toString(map[i][j]));
			}
		}
	}

	public static ArrayList<String> aiGuess() {
		Dice dice = new Dice();
		dice.shuffle();
		char[][] board = dice.getDice();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}

		int difficulty = 5; // 1, 2, 3, 4, or 5

		int wordsToGuess = difficulty + 2;
		ArrayList<String> words = getWords(board, wordsToGuess);

		return words;
	}

	public static int score(String x) {
		int score = 0;
		if (x.length() == 3 || x.length() == 4) {
			score += 1;
		} else if (x.length() == 5) {
			score += 2;
		} else if (x.length() == 6) {
			score += 3;
		} else if (x.length() == 7) {
			score += 4;
		} else if (x.length() >= 8) {
			score += 11;
		}
		return score;
	}
	public static boolean isSafe(int x, int y, boolean[][] processed, char[][] board, String word, String path) {
		boolean isSafe = false;
		if ((x >= 0 && x < processed.length) && (y >= 0 && y < processed[0].length) && !processed[x][y]) {
			if (word.charAt(path.length()) == board[x][y]) {
				isSafe = true;
			} else {
				isSafe = false;
			}
		}
		return isSafe;
	}

	// A recursive function to generate and print all possible words in a boggle
	public static void searchBoggle(char[][] board, String word, ArrayList<String> result, boolean[][] processed, int i,
			int j, String path) {
		// Mark the current node as processed
		processed[i][j] = true;
		int[] point = new int[2];
		int count = -1;

		// Update the path with the current character and insert it into the set
		path += board[i][j];
		count++;
		point[0] = i;
		point[1] = j;

		if (!pathFound) {
			coordinates.add(point);
		}

		// Check whether the path is present in the input set
		if (word.equals(path)) {
			pathFound = true;
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
		int index = coordinates.indexOf(point);

		for (int k = coordinates.size() - 1; k >= index; k--) {
			if (!pathFound) {
				coordinates.remove(k);
			}
		}
	}

	public static ArrayList<String> searchBoggle(char[][] board, String word) {
		ArrayList<String> result = new ArrayList<>();

		if (board.length == 0) {
			return result;
		}

		boolean[][] processed = new boolean[board.length][board[0].length];

		searchBoggle(board, word, result, processed, intStartR, intStartC, "");
		if (result.size() > 0) {
			return result;
		}

		return result;
	}

	public static ArrayList<String> getWords(char[][] board, int limit) {
		Map<String, ArrayList<String>> mapDic = new HashMap<String, ArrayList<String>>();
		ArrayList<String> dicWords = new ArrayList<String>();

		// Put dictionary into Map
		File fileDictionary = new File("dictionary.txt");
		try {
			Scanner inputDic = new Scanner(fileDictionary);
			while (inputDic.hasNext()) {
				String strNext = inputDic.next().toUpperCase();
				if (strNext.length() > 2) {
					String strFirstLetter = strNext.substring(0, 1);
					if (mapDic.get(strFirstLetter) != null) {
						mapDic.get(strFirstLetter).add(strNext);
					} else {
						ArrayList<String> arrTemp = new ArrayList<String>();
						arrTemp.add(strNext);
						mapDic.put(strFirstLetter, arrTemp);
					}
				}
			}
			inputDic.close();
		} catch (FileNotFoundException e) {
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

					for (int l = 0; l < words.length(); l++) {
						arrWords.add(Character.toString(words.charAt(l)));
					}

					ArrayList<String> validWords = searchBoggle(board, words);

					if (!validWords.isEmpty()) {
						if (!(dicWords.contains(validWords.get(0).toString()))) {
							dicWords.add(validWords.get(0).toString());
							if (dicWords.size()>=limit)
							{
								return dicWords;
							}
							
						}
					}
				}
			}
		}
		return dicWords;
	}

	public static boolean findWord(String word, char[][] board) {
		ArrayList<String> arrWords = new ArrayList<>();

		for (int i = 0; i < word.length(); i++) {
			arrWords.add(Character.toString(word.charAt(i)));
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == word.charAt(0)) {
					intStartR = i;
					intStartC = j;
					ArrayList<String> validWords = searchBoggle(board, word);
					if (validWords.indexOf(word) > -1) {
						return true;
					}
				}
			}
		}
		return false;
	}

}

class countdownTimerShow extends JPanel {
	private LocalDateTime startTime;
	private JLabel label;
	protected javax.swing.Timer countdown;

	private Duration duration = Duration.ofSeconds(15);

	public countdownTimerShow() {
		JPanel pan = new JPanel();
		pan.add(label);
		countdown = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// LocalDateTime startTime;
				LocalDateTime now = LocalDateTime.now();
				Duration runningTime = Duration.between(startTime, now);
				Duration timeLeft = duration.minus(runningTime);
				if (timeLeft.isZero() || timeLeft.isNegative()) {
					timeLeft = Duration.ZERO;
					countdown.start();
				}

				label.setText(format(timeLeft));
			}

		});
	}

	protected String format(Duration duration) {
		long seconds = duration.toMillis() / 10000;
		return String.format("%02ds", seconds);

	}
}
