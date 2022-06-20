package BoggleGame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

//I HAVE 1 BUG AND IDK HOW TO SOLVE 

public class Boggle2 extends JFrame {
	// declare static variables
	static boolean playerAI = false;
	static boolean allowPause = true;
	static int score1 = 0;
	static int score2 = 0;
	static int goal = 15;
	static Dice dice = new Dice();
	static char[][] map = dice.getDice();
	static JLabel[][] showMap = new JLabel[map.length][map[0].length];
	static int counter = 0;
	static ArrayList<String> wordsAI = new ArrayList<String>();
	static boolean player1;
	static int player = 1;
	static boolean played = false;
	static ArrayList<String> wordGuessedValid = new ArrayList<String>();
	static boolean paused;
	public static int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
	public static int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
	public static int intStartR = 0;
	public static int intStartC = 0;
	public static boolean pathFound;
	public static ArrayList<int[]> coordinates = new ArrayList<>();
	public static ArrayList<int[]> aiCoordinates = new ArrayList<>();

	private GameTimer t; // create the game timer (a count down timer)

	// INSTRUCTION PANEL COMPONENTS
	JPanel panInstruction = new JPanel();
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
	JLabel instructionLabel10 = new JLabel("However, the word must have at least 3 characters. ");

	// PAUSE PANEL
	JPanel panPause = new JPanel();
	JLabel pauseLabel = new JLabel("Game paused");
	JButton resumeBtn = new JButton("Resume");

	// PLAYING PANELS
	// declare components for playing panels
	JPanel panGuess = new JPanel(new FlowLayout());
	static JPanel panBoggle = new JPanel(new GridLayout(map.length, map[0].length));
	JPanel panAIGuess = new JPanel(new FlowLayout());
	JPanel panScore = new JPanel(new FlowLayout());
	JPanel panTimer = new JPanel(new FlowLayout());
	JPanel panError = new JPanel(new FlowLayout());
	JLabel guessLabel = new JLabel("What is your answer? ");
	JTextField guessIn = new JTextField("Please enter your answer here");
	JButton guessBtn = new JButton("Enter");
	JLabel computerLabel0 = new JLabel("The answer from computer is... ");
	static JLabel computerLabel1 = new JLabel("still thinking... ");
	JLabel scoreLabel0 = new JLabel("Current score of player 1:");
	JLabel scoreLabel1 = new JLabel("0");
	JLabel scoreLabel2 = new JLabel("          Current score of player 2:");
	static JLabel scoreLabel3 = new JLabel("0");
	JLabel winnerLabel = new JLabel();
	JButton homeBtn = new JButton("Back to homepage");
	JLabel timerLabel = new JLabel("Timer: 00:15");
	JLabel errorLabel = new JLabel();

	// CUSTOMISATION PANEL
	JPanel panCustomisation = new JPanel(new GridLayout(9, 2));

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

	// HOME PAGE PANEL
	JPanel panStart = new JPanel();
	JButton playBtn = new JButton("Play");
	JLabel tips = new JLabel("Remember to check the munu for more features and intruction. ");

	public Boggle2() { // constructor
		setTitle("boggle game");// set frame title
		setSize(750, 680);// set frame size

		// INSTRUCTION PANEL
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
		panInstruction.add(instructionLabel10);

		// set visible of panInstruction to true
		panInstruction.setVisible(false);

		// PAUSE PANEL
		// add components to panPause
		pauseLabel.setFont(new Font("Bradley Hand", Font.BOLD, 40)); // set font of the JLabel
		panPause.add(pauseLabel);
		panPause.add(resumeBtn);
		// set visible to false
		panPause.setVisible(false);

		// PLAY PANEL
		panScore.setPreferredSize(new Dimension(3, 550));
		panBoggle.setPreferredSize(new Dimension(600, 550));

		initialisePanBoggle();
		panGuess.add(guessLabel);
		panGuess.add(guessIn);
		panGuess.add(guessBtn);
		panAIGuess.add(computerLabel0);
		panAIGuess.add(computerLabel1);
		panScore.add(scoreLabel0);
		panScore.add(scoreLabel1);
		panScore.add(scoreLabel2);
		panScore.add(scoreLabel3);
		panTimer.add(timerLabel);
		panError.add(errorLabel);

		panScore.setVisible(false);
		panBoggle.setVisible(false);
		panGuess.setVisible(false);
		panAIGuess.setVisible(false);

		panCustomisation.setPreferredSize(new Dimension(650, 550));
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
		menuBar.add(menu);
		menuBar.add(pauseMenu);
		setJMenuBar(menuBar);
		// MENU - add menu items
		menu.add(restart);
		menu.add(instruction);
		menu.add(customisation);

		// PAUSE -- add menu items
		pauseMenu.add(pauseOp);
		pauseMenu.add(resume);

		// START PANEL
		GridLayout gl1 = new GridLayout(2, 1);
		panStart.setLayout(gl1);
		panStart.setPreferredSize(new Dimension(650, 50));
		panStart.add(playBtn);
		panStart.add(tips);
		add(panStart, BorderLayout.NORTH);
		setVisible(true);

		t = new GameTimer();

		ptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goal = Integer.parseInt(ptIn.getText());
				System.out.println("ptBtnPressed pressed, goal stored");
			}

		});

		singlePlayerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerAI = true;
				System.out.println("singlePlayerBtn pressed,  playerAI to true");
			}
		});

		twoPlayerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerAI = false;
				System.out.println("twoPlayerBtn pressed, playerAI to false");
			}

		});

		player1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player = 1;
			}

		});

		player1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player = 1;
			}

		});

		yesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!allowPause) {
					pauseMenu.add(pauseOp);
				}
				allowPause = true;
			}
		});

		noBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allowPause = false;
				pauseMenu.remove(pauseOp);
			}

		});

		instruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("instruction invoked");

				getContentPane().removeAll();
				repaint();
				revalidate();

				add(panInstruction);
				panInstruction.setVisible(true);
				validate();
			}

		});

		restart.addActionListener(restartAction);

		homeBtn.addActionListener(restartAction);

		shakeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("shakeUp invoked");

				changeColor(coordinates, false);
				randomizeBoggle();
			}

		});

		customisation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("customisation invoked");

				getContentPane().removeAll();
				repaint();
				revalidate();

				add(panCustomisation);
				panCustomisation.setVisible(true);
				validate();
			}

		});

		pauseOp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("pauseOp invoked");
				getContentPane().removeAll();
				repaint();
				revalidate();

				paused = true;
				t.pause();
				add(panPause);
				panPause.setVisible(true);
			}

		});

		resume.addActionListener(resumeAction);

		resumeBtn.addActionListener(resumeAction);

		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("playPressed");
				t.setTime();
				played = true;

				menu.remove(customisation);
				menu.remove(instruction);

				getContentPane().removeAll();
				repaint();
				revalidate();

				// Player 1
				add(panGuess, BorderLayout.NORTH);
				panGuess.setVisible(true);

				randomizeBoggle();

				changeColor(coordinates, false);
				add(panTimer, BorderLayout.EAST);
				add(panBoggle, BorderLayout.SOUTH);
				add(panScore, BorderLayout.CENTER);
				add(panError, BorderLayout.WEST);

				panTimer.setVisible(true);
				panScore.setVisible(true);
				panBoggle.setVisible(true);
				panError.setVisible(true);

				if (counter == 2) {
					menu.add(shakeUp);
				}
			}

		});

		guessBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("guessBtn invoked");

				System.out.println();
				String guess = guessIn.getText();
				System.out.println("The player is " + player);
				changeColor(coordinates, false); // turn the previous cell back to white
				coordinates.clear();
				pathFound = false;

				System.out.println("after cleared");
				for (int i = 0; i < coordinates.size(); i++) {
					System.out.println(coordinates.get(i)[0] + " " + coordinates.get(i)[1]);
				}

				if (guess.length() < 3) {
					errorLabel.setText("Enter 3 characters or above!");

				} else {
					errorLabel.setText("");
					if (findWord(guess, map) && !wordGuessedValid.contains(guess)) {
						System.out.println("Valid answer");
						for (int i = 0; i < coordinates.size(); i++) {
							System.out.println(coordinates.get(i)[0] + " " + coordinates.get(i)[1]);
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
						changeColor(coordinates, true);
					} else {
						errorLabel.setText("Invalid answer");

						System.out.println("Invalid answer");
					}
				}

				if (score1 == goal || score2 == goal) {
					getContentPane().removeAll();
					repaint();
					revalidate();

					if (score1 == goal) {
						winnerLabel.setText("Player 1 won! ");
						add(winnerLabel, BorderLayout.CENTER);
					} else {
						if (playerAI) {
							winnerLabel.setText("The AI won! ");
							add(winnerLabel, BorderLayout.CENTER);
						} else {
							winnerLabel.setText("Player 2 won! ");
							add(winnerLabel, BorderLayout.CENTER);
						}
					}

					add(homeBtn, BorderLayout.SOUTH);
				}
				wordGuessedValid.add(guess);
			}
		});
	}

	ActionListener resumeAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("resume invoked");
			getContentPane().removeAll();
			repaint();
			revalidate();

			paused = false;
			t.unpause();

			add(panGuess, BorderLayout.NORTH);
			panGuess.setVisible(true);
			add(panTimer, BorderLayout.EAST);
			panTimer.setVisible(true);
			add(panScore, BorderLayout.CENTER);
			panScore.setVisible(true);
			add(panBoggle, BorderLayout.SOUTH);
			panBoggle.setVisible(true);
			add(panError, BorderLayout.WEST);
			panError.setVisible(true);
		}

	};

	ActionListener restartAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("restart invoked");

			getContentPane().removeAll();
			repaint();
			revalidate();

			if (!customisation.isShowing()) {
				menu.add(customisation);
				menu.add(instruction);
			}

			wordGuessedValid.clear();
			score1 = 0;
			score2 = 0;
			scoreLabel1.setText("0");
			scoreLabel3.setText("0");

			if (played) {
				changeColor(coordinates, false);
				played = false;
			}

			add(panStart);
			panStart.setVisible(true);
			validate();

			t.reset();
		}

	};

	public static void initialisePanBoggle() {
		System.out.println("Initialise panBoggle invoked");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				showMap[i][j] = new JLabel("");
				panBoggle.add(showMap[i][j]);
				showMap[i][j].setBackground(Color.WHITE);
				showMap[i][j].setOpaque(true);
			}
		}
	}

	public static void main(String args[]) {
		Boggle2 frame1 = new Boggle2(); // start gui

	}

	public static void changeColor(ArrayList<int[]> path, boolean showFinal) {
		System.out.println("Change color invoked");

//		for (int i = 0; i < path.size(); i++) {
//			System.out.println(path.get(i)[0] + " " + path.get(i)[1]);
//		}

		if (path.size() != 0) {
			System.out.println("path size does not equal to zero");
			for (int i = 0; i < path.size(); i++) // for loop
			{
				// String rowCol = path.get(i); // get x y coordinate
				int row = path.get(i)[0]; // get y coordinate
				int col = path.get(i)[1]; // get x coordinate

				if (showFinal) {
					System.out.println(" colorToOrange invoked");
					showMap[row][col].setBackground(Color.ORANGE);
				} else {
					System.out.println(" colorToWhite invoked");
					showMap[row][col].setBackground(Color.WHITE);
					showMap[row][col].setOpaque(true);
				}
			}
		} else {
			System.out.println("path size does equal to zero");
		}
	}

	public static void randomizeBoggle() {
		System.out.println("randomizeBoggle invoked");
		dice.shuffle();
		map = dice.getDice();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				showMap[i][j].setText(Character.toString(map[i][j]));
			}
		}
	}

	public static ArrayList<String> aiGuess() {
		System.out.println("aiGuess invoked");

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}

		int difficulty = 5; // 1, 2, 3, 4, or 5

		int wordsToGuess = difficulty + 2;
		ArrayList<String> words = getWords(map, wordsToGuess);

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
				String strNext = inputDic.next();
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
							if (dicWords.size() >= limit) {
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

	public class GameTimer extends Timer {
		Task t; // The task to run
		private long startTime; // What time the game started
		private long pauseStart;

		// Sets up the timer for moving flights
		public GameTimer() {
			super();
			t = new Task();
			scheduleAtFixedRate(t, 0, 1000);
			startTime = System.currentTimeMillis();
		}

		// Restart the timer
		public void reset() {
			startTime = System.currentTimeMillis();
		}

		private int[] point = new int[2];

		// Update the timer label
		public void setTime() {
			if (!paused) {
				long playingTime = System.currentTimeMillis() - startTime;
				long timeLeft = 15000 - playingTime;
				long sec = (timeLeft) / 1000L;

				if (timeLeft < 0) // 15 seconds passed
				{
					if (player == 1) {
						player = 2;
						System.out.println();
						System.out.println("PLAYER 1 --> PLAYER 2");
						System.out.println();

						if (playerAI) {
							changeColor(coordinates, false);
							remove(panGuess);
							add(panAIGuess, BorderLayout.NORTH);
							panAIGuess.setVisible(true);
							// Let ai guess work first
							wordsAI.clear();
							aiCoordinates.clear();
							wordsAI = aiGuess();

							for (int i = 0; i < wordsAI.size(); i++) {
								findWord(wordsAI.get(i), map);
								System.out.println("Add points to aiCoordinates, printing cuurent coordinates");
								for (int j = 0; j < coordinates.size(); j++) {
									point[0] = coordinates.get(j)[0];
									point[1] = coordinates.get(j)[1];

									System.out.println(coordinates.get(j)[0] + "" + coordinates.get(j)[1]);

									aiCoordinates.add(point);
								}

							}

							changeColor(aiCoordinates, true);

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

						reset();
						setTime();

						return;
					} else {

						System.out.println();
						System.out.println("PLAYER 2 --> PLAYER 1");
						player = 1;
						System.out.println();

						if (panAIGuess.isShowing()) {
							System.out.println("panAIGuess is showing");
							getContentPane().remove(panAIGuess);
							panAIGuess.setVisible(false);

							add(panGuess, BorderLayout.NORTH);
							panGuess.setVisible(true);

							revalidate();
							repaint();
						}

						reset();
						setTime();
						return;
					}
				}

				String s = "Time: ";
				if (sec >= 10)
					s += Integer.toString((int) sec);
				else
					s += "0" + Integer.toString((int) sec);

				timerLabel.setText(s);
			}
		}

		public void pause() {
			pauseStart = System.currentTimeMillis();
		}

		public void unpause() {
			startTime += System.currentTimeMillis() - pauseStart;
		}

		// The task to run
		public class Task extends TimerTask {

			// What to do every interval

			public void run() {
				// Update the timer label
				setTime();
			}
		}
	}
} 
