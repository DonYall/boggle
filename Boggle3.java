import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Timer;

import javax.sound.sampled.*;
import javax.swing.*;

//Added the customisation of difficulty  

public class Boggle3 extends JFrame {
    // declare  variables
    Map<String, ArrayList<String>> mapDic = new HashMap<String, ArrayList<String>>();
    boolean playerAI = false;
    boolean allowPause = true;
    int score1 = 0;
    int score2 = 0;
    int goal = 15;
    Dice dice = new Dice();
    char[][] map = dice.getDice();
    JLabel[][] showMap = new JLabel[map.length][map[0].length];
    int counter = 0;
    ArrayList<String> wordsAI = new ArrayList<String>();
    boolean player1;
    int player = 1;
    boolean played = false;
    ArrayList<String> wordGuessedValid = new ArrayList<String>();
    ArrayList<String> aiWordGuessedValid = new ArrayList<String>();
    boolean paused;
    public int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    public int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };
    public int intStartR = 0;
    public int intStartC = 0;
    public boolean pathFound;
    public ArrayList<int[]> coordinates = new ArrayList<>();
    public int difficulty;
    public boolean inGame = false;
    public boolean justStarted = true;
    public Color color1 = Color.WHITE;
    public Color color1f = Color.BLACK;
    public Color color2 = Color.ORANGE;
    public Color color2f = Color.BLACK;


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
    JLabel instructionLabel11 = new JLabel("Noted that if you choose single player mode, you will always be the person who goes first. "); 
	JLabel instructionLabel12 = new JLabel("************************************************** The scoring rules:**************************************************");
	JLabel instructionLabel13 = new JLabel("                  Word Length | 3  | 4  | 5  | 6  | 7  | 8 or more                  "); 
	JLabel instructionLabel14 = new JLabel("--------------------------------------------------"); 
	JLabel instructionLabel15 = new JLabel("                  Score       | 1  | 1  | 2  | 3  | 5  | 11                         "); 

    // GAME OVER PANEL
    JPanel panOver = new JPanel(new FlowLayout());
    JLabel winnerLabel = new JLabel();
    JButton closeBtn = new JButton("Quit application");

    // PAUSE PANEL
    JPanel panPause = new JPanel();
    JLabel pauseLabel = new JLabel("Game paused");
    JButton resumeBtn = new JButton("Resume");

    // PLAYING PANELS
    // declare components for playing panels
    JPanel panGuess = new JPanel(new FlowLayout());
    JPanel panBoggle = new JPanel(new GridLayout(map.length, map[0].length));
    JPanel panAIGuess = new JPanel(new FlowLayout());
    JPanel panScore = new JPanel(new FlowLayout());
    JPanel panTimer = new JPanel(new FlowLayout());
    JPanel panError = new JPanel(new FlowLayout());
    JLabel guessLabel = new JLabel("What is your answer? ");
    JTextField guessIn = new JTextField("Please enter your answer here", 20);
    JButton guessBtn = new JButton("Enter");
    JLabel computerLabel0 = new JLabel("The answer from computer is... ");
    JLabel computerLabel1 = new JLabel("still thinking... ");
    JLabel scoreLabel0 = new JLabel("Current score of player 1:");
    JLabel scoreLabel1 = new JLabel("0");
    JLabel scoreLabel2 = new JLabel("          Current score of player 2:");
    JLabel scoreLabel3 = new JLabel("0");
    JButton homeBtn = new JButton("Back to homepage");
    JLabel timerLabel = new JLabel("Timer: 00:15");
    JLabel errorLabel = new JLabel();

    // CUSTOMISATION PANEL
    JPanel panCustomisation = new JPanel(new GridLayout(9, 2));

    JLabel customisationLabel0 = new JLabel("CUSTOMISATION");
    JLabel playersMode = new JLabel("Which mdoe would you like to choose? ");
    JButton singlePlayerBtn = new JButton("Single Player");
    JButton twoPlayerBtn = new JButton("Two Player");
    JLabel difficultyLabel = new JLabel("Choose the difficulty for the bot. Please enter 1-5. ");
    JTextField difficultyIn = new JTextField("Please enter 1-5");
    JButton difficultyBtn = new JButton("Enter");
    JLabel allowPauseLabel = new JLabel("Will this game allow pausing? ");
    JButton yesBtn = new JButton("Yes");
    JButton noBtn = new JButton("No");
    JTextField ptIn = new JTextField("Enter points to play");
    JLabel targetScore = new JLabel("Tournament score? Please enter an integer. ");
    JButton ptBtn = new JButton("Enter");
    
    // MENU BAR
    JMenuBar menuBar = new JMenuBar();
    // menu
    JMenu menu = new JMenu("Menu"); // restart, shake up button, instruction, customisation
    JMenu pauseMenu = new JMenu("Pause"); // pause, resume
    JMenu theme = new JMenu("Theme"); // change theme
    // menu menu items
    JMenuItem instruction = new JMenuItem("Instruction");
    JMenuItem restart = new JMenuItem("Home/ Restart");
    JMenuItem shakeUp = new JMenuItem("Shake-up");
    JMenuItem customisation = new JMenuItem("Customisation");
    // pause menu item
    JMenuItem pauseOp = new JMenuItem("Pause");
    JMenuItem resume = new JMenuItem("Resume");
    // theme menu items
    JMenuItem thDefault = new JMenuItem("Default");
    JMenuItem thDark = new JMenuItem("Dark");
    JMenuItem thClassy = new JMenuItem("Classy");
    JMenuItem thModern = new JMenuItem("Modern");
    JMenuItem thGalaxy = new JMenuItem("Galaxy");
    JMenuItem thCandy = new JMenuItem("Candy");
    JMenuItem thSynthwave = new JMenuItem("Synthwave");
    JMenuItem thNight = new JMenuItem("Night Owl");
    JMenuItem thWood = new JMenuItem("Wooden");


    // HOME PAGE PANEL
    JPanel panStart = new JPanel();
    JButton playBtn = new JButton("Play");
    JLabel tips = new JLabel("Remember to check the menu for more features and intruction. ");

    public Boggle3() { // constructor
        setTitle("boggle game");// set frame title
        setSize(750, 680);// set frame size
        setVisible(true);

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
        panInstruction.add(instructionLabel11);
		panInstruction.add(instructionLabel12);
		panInstruction.add(instructionLabel13);
		panInstruction.add(instructionLabel14);
		panInstruction.add(instructionLabel15);

        // set visible of panInstruction to true
        panInstruction.setVisible(false);

        // PAUSE PANEL
        // add components to panPause
        pauseLabel.setFont(new Font("Bradley Hand", Font.BOLD, 40)); // set font of the JLabel
        panPause.add(pauseLabel);
        panPause.add(resumeBtn);
        // set visible to false
        panPause.setVisible(false);

        // GAME OVER PANEL
        panOver.add(winnerLabel, BorderLayout.NORTH);
        panOver.add(homeBtn, BorderLayout.CENTER);
        panOver.add(closeBtn, BorderLayout.SOUTH);
        panOver.setVisible(false);

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
        menuBar.add(theme);
        setJMenuBar(menuBar);
        // MENU - add menu items
        menu.add(restart);
        menu.add(instruction);
        menu.add(customisation);

        // PAUSE -- add menu items
        pauseMenu.add(pauseOp);
        pauseMenu.add(resume);

        // THEME -- add menu items
        theme.add(thDefault);
        theme.add(thDark);
        theme.add(thClassy);
        theme.add(thGalaxy);
        theme.add(thCandy);
        theme.add(thSynthwave);
        theme.add(thNight);
        theme.add(thWood);

        // START PANEL
        GridLayout gl1 = new GridLayout(2, 1);
        panStart.setLayout(gl1);
        panStart.setPreferredSize(new Dimension(650, 50));
        panStart.add(playBtn);
        panStart.add(tips);
        add(panStart, BorderLayout.NORTH);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        t = new GameTimer();

        closeBtn.addActionListener(e -> {
            dispose();
        });

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
                
                if (!difficultyLabel.isShowing())
                {
                    panCustomisation.add(difficultyLabel);
                    panCustomisation.add(new JLabel(""));
                    panCustomisation.add(difficultyIn);
                    panCustomisation.add(difficultyBtn);
                    panCustomisation.repaint(); 
					repaint(); 
                    revalidate();
                }
                
            }
        });

        twoPlayerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerAI = false;
                System.out.println("twoPlayerBtn pressed, playerAI to false");
                if (difficultyLabel.isShowing())
                {
                    panCustomisation.remove(difficultyLabel);
                    panCustomisation.remove(difficultyIn);
                    panCustomisation.remove(difficultyBtn);
                }
            }

        });

        
            difficultyBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                difficulty = Integer.parseInt(difficultyIn.getText()); 
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

        homeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("homeBtn pressed");
                dispose();
                new Boggle3();
            }
        });

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
                t.pause(); 
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

        thDefault.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thDefault invoked");
                color1 = Color.WHITE;
                color1f = Color.BLACK;
                color2 = Color.ORANGE;
                color2f = Color.BLACK;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });
    
        thDark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thDark invoked");
                color1 = Color.BLACK;
                color1f = Color.WHITE;
                color2 = Color.GRAY;
                color2f = Color.BLACK;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });
    
        thClassy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thClassy invoked");
                color1 = Color.WHITE;
                color1f = Color.BLACK;
                color2 = Color.BLACK;
                color2f = Color.WHITE;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });
    
        thGalaxy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thGalaxy invoked");
                color1 = new Color(0, 102, 102);
                color1f = Color.BLACK;
                color2 = new Color(204, 255, 255);
                color2f = Color.BLACK;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });
    
        thCandy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thCandy invoked");
                color1 = Color.PINK;
                color1f = Color.BLACK;
                color2 = new Color(194, 194, 214);
                color2f = Color.BLACK;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });

        thSynthwave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thSynthwave invoked");
                color1 = new Color(102, 0, 102);
                color1f = Color.WHITE;
                color2 = new Color(0, 255, 255);
                color2f = Color.BLACK;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });

        thNight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thNight invoked");
                color1 = new Color(0, 0, 51);
                color1f = Color.WHITE;
                color2 = new Color(102, 102, 255);
                color2f = Color.WHITE;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });

        thWood.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("thWood invoked");
                color1 = new Color(236, 217, 198);
                color1f = Color.BLACK;
                color2 = new Color(19, 13, 6);
                color2f = Color.WHITE;
                changeGridColor();
                panGuess.setBackground(color2);
                guessLabel.setForeground(color2f);
            }
        });


        resume.addActionListener(resumeAction);

        resumeBtn.addActionListener(resumeAction);

        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {        
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
                } catch (FileNotFoundException ee) {
                    ee.printStackTrace();
                }
                inGame = true;
                System.out.println("playPressed");
                try {
					t.setTime();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
                t.reset(); 
                played = true;

                menu.remove(customisation);
                menu.remove(instruction);

                getContentPane().removeAll();
                repaint();
                revalidate();

                // Player 1
                add(panGuess, BorderLayout.NORTH);
                panGuess.setVisible(true);
                justStarted = false;


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
                
                if (!playerAI)
				{
					player = (int) (Math.random() * 2) + 1;
				} else {
					player =1; 
				} 
                
                errorLabel.setText("Player " + player + " goes first");
                
                if (counter == 4) {
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
                    if (findWord(guess, map) && (binarySearch(wordGuessedValid, 0, wordGuessedValid.size()-1, guess) < 0 && inGame)) {
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
            inGame = false;
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
            t.pause(); 
        }

    };

    public void initialisePanBoggle() {
        System.out.println("Initialise panBoggle invoked");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                showMap[i][j] = new JLabel("");
                panBoggle.add(showMap[i][j]);
                showMap[i][j].setBackground(color1);
                showMap[i][j].setForeground(color1f);
                showMap[i][j].setOpaque(true);
                showMap[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                showMap[i][j].setVerticalAlignment(SwingConstants.CENTER);
                
            }
        }
    }

    public static void main(String args[]) {
        Boggle3 frame1 = new Boggle3(); // start gui

    }

    public void changeGridColor() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                showMap[i][j].setBackground(color1);
                showMap[i][j].setForeground(color1f);
            }
        }
    }

    public void changeColor(ArrayList<int[]> path, boolean showFinal) {
        System.out.println("Change color invoked");

        if (path.size() != 0) {
            System.out.println("path size does not equal to zero");
            for (int i = 0; i < path.size(); i++) // for loop
            {
                // String rowCol = path.get(i); // get x y coordinate
                int row = path.get(i)[0]; // get y coordinate
                int col = path.get(i)[1]; // get x coordinate

                if (showFinal) {
                    System.out.println(" colorToOrange invoked");
                    showMap[row][col].setBackground(color2);
                    showMap[row][col].setForeground(color2f);
                } else {
                    System.out.println(" colorToWhite invoked");
                    showMap[row][col].setBackground(color1);
                    showMap[row][col].setForeground(color1f);
                    showMap[row][col].setOpaque(true);
                }
            }
        } else {
            System.out.println("path size equal to zero");
        }
    }

    public void randomizeBoggle() {
        System.out.println("randomizeBoggle invoked");
        dice.shuffle();
        map = dice.getDice();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                showMap[i][j].setText(Character.toString(map[i][j]));
            }
        }
    }

    public ArrayList<String> aiGuess() {
        System.out.println("aiGuess invoked");

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        int wordsToGuess = difficulty + 2;
        ArrayList<String> words = getWords(map, wordsToGuess);

        return words;
    }

    public int score(String x) {
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

    public boolean isSafe(int x, int y, boolean[][] processed, char[][] board, String word, String path) {
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
    public void searchBoggle(char[][] board, String word, ArrayList<String> result, boolean[][] processed, int i,
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

    public ArrayList<String> searchBoggle(char[][] board, String word) {
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

    public ArrayList<String> getWords(char[][] board, int limit) {
        ArrayList<String> dicWords = new ArrayList<String>();
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
                        if (!(dicWords.contains(validWords.get(0).toString())) && (binarySearch(aiWordGuessedValid, 0, aiWordGuessedValid.size()-1, validWords.get(0).toString())) < 0) {
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

    public boolean findWord(String word, char[][] board) {
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
                        if (binarySearch(mapDic.get(word.substring(0,1)), 0, mapDic.get(word.substring(0,1)).size()-1, word) >= 0) {
                            return true;
                        }
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

        // Update the timer label
        public void setTime() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            if (!paused) {
                long playingTime = System.currentTimeMillis() - startTime;
                long timeLeft = 15000 - playingTime;
                long sec = (timeLeft) / 1000L;

                if (timeLeft < 0 && inGame) // 15 seconds passed
                {
                    counter ++; 
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
                            wordsAI = aiGuess();
                            
                            for (int i = 0; i < wordsAI.size(); i++) {
                                if (!justStarted) score2 = score2 + score(wordsAI.get(i));
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

                if (score1 >= goal || score2 >= goal) {
                    t.cancel();

                    getContentPane().removeAll();
                    repaint();
                    revalidate();

                    if (score1 >= goal) {
                        winnerLabel.setText("Player 1 won! ");
                    } else {
                        if (playerAI) {
                            winnerLabel.setText("The AI won! ");
                        } else {
                            winnerLabel.setText("Player 2 won! ");
                        }
                    }
                    add(panOver);
					panOver.setVisible(true);
					File file = new File("Cheers10.wav"); 
					AudioInputStream  audioStream = AudioSystem.getAudioInputStream(file); 
					Clip clip = AudioSystem.getClip(); 
					
					clip.open(audioStream); 
					clip.start(); 
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					
					JOptionPane.showMessageDialog(null, "Hit ok to stop sound effect");
					clip.stop(); 
                }

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
                try {
					setTime();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
            }
        }
    }
    public int binarySearch(ArrayList<String> arr, int l, int r, String word)
    {

        if (r >= l && l <= arr.size() - 1) {
 
            int mid = l + (r - l) / 2;
            int res = word.compareTo(arr.get(mid));

            if (res == 0) {
                return mid;
            }

            if (res < 0 ) {
                return binarySearch(arr, l, mid - 1, word);
            }

            else if (res > 0) {
            	return binarySearch(arr, mid + 1, r, word);
            }
        }

        return -1;
    }
}