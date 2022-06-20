import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class BoggleFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton[][] jbMaze;
    char[][] boardMatrix = new char[5][5];
    char[][] computerMatrix1 = new char[4][4];
    char[][] computerMatrix2 = new char[4][4];;
    public static ArrayList<String> arrAllWords = new ArrayList<String>();
    public static ArrayList<String> player1Input = new ArrayList<String>();
    public static ArrayList<String> player2Input = new ArrayList<String>();
    int aiInputIndex = 0;

    private JPanel boardPanel;
    private JPanel playerSelectPanel;
    private JPanel startPanel;
    private JPanel tscorePanel;
    private JLabel lblOrderSelectMode =  new JLabel("");
    private JLabel lblSelectMode = new JLabel("");
    private JLabel lblTScoreMode = new JLabel("");
    private JTextField tbTScoreMode = new JTextField("      ");
    private JButton goalBtn;

    private JRadioButton option1 = new JRadioButton("One Player");
    private JRadioButton option2 = new JRadioButton("Two Players");
    private ButtonGroup groupPlayer = new ButtonGroup();

    JRadioButton order1 = new JRadioButton("Player 1");
    JRadioButton order2 = new JRadioButton("Player 2");
    private ButtonGroup groupOrder = new ButtonGroup();

    //Player panel
    private JPanel player1Panel;

    private JLabel lblPlayer1 = new JLabel("Player 1: ");
    private JTextField tbPlayer1 = new JTextField("                    ");
    private JLabel lblPlayer1Score = new JLabel(" Score: 0");
    private JButton btnPlayer1 = new JButton();

    private JPanel player2Panel;
    private JLabel lblPlayer2 = new JLabel("Player 2: ");
    private JTextField tbPlayer2 = new JTextField("                    ");
    private JLabel lblPlayer2Score = new JLabel(" Score: 0");
    private JButton btnPlayer2 = new JButton();

    // Winner label
    private JPanel winnerPanel = new JPanel();
    private JLabel lblWinner = new JLabel("Winner is: ");

    // Initial value
    private int Score1 = 0;
    private int Score2 = 0;
    private int scoreGoal;
    private int minLength;
    private int player1Pass = 0;
    private int player2Pass = 0;
    private int playerTurn = 1;
    private boolean AIplay = false;

    // Creating the frame
    public BoggleFrame(ArrayList<String> arrDic){
        // Setting the title and size
        setTitle("Boggle Game");
        setSize (500, 400);

        jbMaze = new JButton[5][5];

        // Setting the layout and panel
        BoxLayout layout2 = new BoxLayout (getContentPane(), BoxLayout.Y_AXIS);
        setLayout(layout2);

        JPanel panInstruction = new JPanel();
        panInstruction.setPreferredSize(new Dimension(650, 550));

        boardPanel = new JPanel();
        GridLayout gLayout = new GridLayout(5, 5);
        boardPanel.setLayout(gLayout);
        boardPanel.setMaximumSize(new Dimension(150 , 150));
        jbMaze = new JButton[5][5];

        // Shuffling the die
        Dice myDice = new Dice();
        myDice.shuffle();
        boardMatrix = myDice.getDice();

        // Setting all the grids in the GUI
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                jbMaze[i][j] = new JButton();
                String strText = Character.toString(boardMatrix[i][j]).toUpperCase();
                jbMaze[i][j].setText(strText);
                jbMaze[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jbMaze[i][j].setPreferredSize( new Dimension(30, 30) );
                boardPanel.add(jbMaze[i][j]);
            }
        }

        add(boardPanel);
        arrAllWords = GetWords.findAllWords(boardMatrix);
        System.out.println("Words On Board: ");
        System.out.println(arrAllWords);

        // Stating general instructions
        JPanel infoPanel = new JPanel();
        JLabel lblinfo = new JLabel("Please select the Mode, Players, and the Tournament Score before clicking Start");
        lblinfo.setForeground(Color.BLACK);
        infoPanel.add(lblinfo);
        add(infoPanel);

        // Selecting number of users
        playerSelectPanel = new JPanel();
        lblSelectMode.setText("Select Player Mode: ");
        playerSelectPanel.setLayout(new FlowLayout());
        playerSelectPanel.add(lblSelectMode);
        groupPlayer.add(option1);
        groupPlayer.add(option2);
        playerSelectPanel.add(option1);
        playerSelectPanel.add(option2);

        option1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AIplay = true;
                order1.setSelected(true);
                order2.setSelected(false);
                order2.setEnabled(false);
                System.out.println("One-Player Mode");
            }
        });

        option2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AIplay = false;
                System.out.println("Two-Player Mode");
                order1.setEnabled(true);
                order2.setEnabled(true);
            }
        });

        add(playerSelectPanel);

        // Selecting the playing order
        JPanel playOrderPanel = new JPanel();
        lblOrderSelectMode.setText("Enter the player who goes first: ");
        playOrderPanel.setLayout(new FlowLayout());
        groupOrder.add(order1);
        groupOrder.add(order2);
        playOrderPanel.add(lblOrderSelectMode);
        playOrderPanel.add(order1);
        playOrderPanel.add(order2);

        order1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerTurn = 1;
                /*
					tbPlayer1.setEnabled(true);
					btnPlayer1.setEnabled(true);
					tbPlayer2.setEnabled(false);
					btnPlayer2.setEnabled(false); */
                System.out.println("Player " + playerTurn + " plays first");
            }
        });

        order2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerTurn = 2;
                /*
					tbPlayer2.setEnabled(true);
					btnPlayer2.setEnabled(true);
					tbPlayer1.setEnabled(false);
					btnPlayer1.setEnabled(false);*/
                System.out.println("Player " + playerTurn + " plays first");
            }
        });

        add(playOrderPanel);

        // Setting the tournament score
        tscorePanel = new JPanel();
        lblTScoreMode.setText("Please enter tournament score: ");
        tbTScoreMode.setMinimumSize(new Dimension(100, 20));
        tscorePanel.setLayout(new FlowLayout());
        tscorePanel.add(lblTScoreMode);
        tscorePanel.add(tbTScoreMode);
        goalBtn = new JButton("Set Goal");

        goalBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scoreGoal = Integer.parseInt(tbTScoreMode.getText().trim());
                System.out.println("Tournament goal is " + scoreGoal);
            }

        });

        tscorePanel.add(goalBtn);
        add(tscorePanel);

        // Min word length button

        // Features panel
        startPanel = new JPanel();
        JButton startBtn = new JButton();
        startBtn.setText("Start Game");

        JButton reStartBtn = new JButton();
        reStartBtn.setText("Restart Game");
        reStartBtn.setEnabled(false);

        JButton shakeBtn = new JButton();
        shakeBtn.setText("Shake-up Board");
        shakeBtn.setEnabled(false);

        startPanel.setLayout(new FlowLayout());
        startPanel.add(startBtn);
        startPanel.add(reStartBtn);
        startPanel.add(shakeBtn);

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reStartBtn.setEnabled(true);
                startBtn.setEnabled(false);
                tbTScoreMode.setEnabled(false);
                goalBtn.setEnabled(false);
                option1.setEnabled(false);
                option1.setEnabled(false);
                order1.setEnabled(false);
                order2.setEnabled(false);
                aiInputIndex = 0;
                player1Pass = 0;
                player2Pass = 0;
                Score1 = 0;
                Score2 = 0;
                lblPlayer1Score.setText("Score: 0");
                lblPlayer2Score.setText("Score: 0");

                if(playerTurn == 1) {
                    btnPlayer1.setEnabled(true);
                    tbPlayer1.setEnabled(true);
                    btnPlayer2.setEnabled(false);
                    tbPlayer2.setEnabled(false);
                }

                else {
                    btnPlayer1.setEnabled(false);
                    tbPlayer1.setEnabled(false);
                    btnPlayer2.setEnabled(true);
                    tbPlayer2.setEnabled(true);
                }
            }
        });

        // Restarting the game
        reStartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Shuffling the die
                Dice myDice = new Dice();
                myDice.shuffle();
                boardMatrix = myDice.getDice();
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        String strText = Character.toString(boardMatrix[i][j]).toUpperCase();
                        jbMaze[i][j].setText(strText);
                    }
                }

                // Getting all the words again
                arrAllWords = GetWords.findAllWords(boardMatrix);
                System.out.println("All the words on Board after the restart:");
                System.out.println(arrAllWords);

                // Setting the variables to 0 or empty
                aiInputIndex = 0;
                player1Input = new ArrayList<String>();
                player2Input = new ArrayList<String>();
                player1Pass = 0;
                player2Pass = 0;
                Score1 = 0;
                Score2 = 0;

                // Resetting the buttuons
                shakeBtn.setEnabled(false);
                reStartBtn.setEnabled(false);
                startBtn.setEnabled(true);
                tbTScoreMode.setEnabled(true);
                tbTScoreMode.setText("       ");
                lblPlayer1Score.setText("Score: 0");
                lblPlayer2Score.setText("Score: 0");
                goalBtn.setEnabled(true);
                option1.setEnabled(true);
                option1.setEnabled(true);
                order1.setEnabled(true);
                order2.setEnabled(true);
                groupOrder.clearSelection();
                groupPlayer.clearSelection();
                btnPlayer1.setEnabled(false);
                tbPlayer1.setEnabled(false);
                btnPlayer2.setEnabled(false);
                tbPlayer2.setEnabled(false);
                tbPlayer1.setText("                    ");
                tbPlayer2.setText("                    ");
                lblWinner.setText("   ");
            }
        });

        // Shake up the board
        shakeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Shuffling the die again
                Dice myDice = new Dice();
                myDice.shuffle();
                boardMatrix = myDice.getDice();
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        String strText = Character.toString(boardMatrix[i][j]).toUpperCase();
                        jbMaze[i][j].setText(strText);
                    }
                }

                // Finding all the words again
                arrAllWords = GetWords.findAllWords(boardMatrix);
                System.out.println("All the words on the Board after the shake-up: ");
                System.out.println(arrAllWords);

                // Setting all the variables to zero or empty again
                aiInputIndex = 0;
                player1Input = new ArrayList<String>();
                player2Input = new ArrayList<String>();
                player1Pass = 0 ;
                player2Pass = 0 ;

                shakeBtn.setEnabled(false);
            }
        });

        add(startPanel);

        //Player 1's panel
        player1Panel = new JPanel();
        player1Panel.setLayout(new FlowLayout());
        player1Panel.add(lblPlayer1);
        player1Panel.add(tbPlayer1);
        btnPlayer1 = new JButton("Submit");
        player1Panel.add(btnPlayer1);
        player1Panel.add(lblPlayer1Score);
        btnPlayer1.setEnabled(false);
        tbPlayer1.setEnabled(false);

        btnPlayer1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guess = tbPlayer1.getText().trim().toUpperCase();

                if ((player1Input.indexOf(guess) == -1)
                        && (player2Input.indexOf(guess) == -1)
                        && (GetWords.findWord(guess, boardMatrix))
                        && (arrAllWords.indexOf(guess) > -1)) {
                    System.out.println("Player 1 has inputted a valid answer");

                    player1Input.add(guess);
                    Score1 = Score1 + Score(guess);
                    player1Pass++;
                    System.out.println("Player 1's score is: " + Score1);
                    lblPlayer1Score.setText("Score: " + Integer.toString(Score1));
                }
                else {
                    System.out.println("Player 1 has inputted an invalid answer");
                }

                // Checking if P1's score has reached the tournament score
                if (scoreGoal > 0 && Score1 >= scoreGoal){
                    lblWinner.setVisible(true);
                    lblWinner.setText("Player 1 is the Winner!");
                    winnerPanel.setVisible(true);

                    // Locking the buttons and textboxes
                    btnPlayer1.setEnabled(false);
                    tbPlayer1.setEnabled(false);
                    btnPlayer2.setEnabled(false);
                    tbPlayer2.setEnabled(false);
                }
                else {
                    //Re-shuffle Boggle Board if Player 1 has passed twice

                    if(player1Pass >= 2) {
                        shakeBtn.setEnabled(true);
                    }

                    // Switching over to Player 2
                    btnPlayer1.setEnabled(false);
                    tbPlayer1.setEnabled(false);
                    btnPlayer2.setEnabled(true);
                    tbPlayer2.setEnabled(true);

                    // If Player 2 is the AI, generate a word automatically
                    if (AIplay) {
                        // Will probably randomize later
                        String aiText = arrAllWords.get(aiInputIndex);
                        aiInputIndex++;
                        while(player1Input.indexOf(aiText) >= 0 && aiInputIndex < arrAllWords.size() -1) {
                            aiText = arrAllWords.get(aiInputIndex);
                        }
                        tbPlayer2.setText(aiText);
                        System.out.println("AI Player has inputted a valid answer");
                        player2Input.add(aiText);
                        player2Pass++;
                        Score2 = Score2 + Score(aiText);
                        System.out.println("The AI Player's score is: " + Score2);
                        lblPlayer2Score.setText("Score: " + Integer.toString(Score2));

                        // Checking if the AI player has reached the tournament score
                        if (scoreGoal > 0 && Score2 >= scoreGoal) {
                            lblWinner.setVisible(true);
                            lblWinner.setText("The AI is the Winner!");
                            winnerPanel.setVisible(true);

                            // Locking the textboxes and buttons
                            btnPlayer1.setEnabled(false);
                            tbPlayer1.setEnabled(false);
                            btnPlayer2.setEnabled(false);
                            tbPlayer2.setEnabled(false);
                        }

                        else {
                            // Re-shuffle the Boggle board if AI player has passed twice
                            if(player2Pass >= 2) {
                                shakeBtn.setEnabled(true);
                            }
                            // else {
                                // tbPlayer1.setText("                    ");
                                btnPlayer1.setEnabled(true);
                                tbPlayer1.setEnabled(true);
                                btnPlayer2.setEnabled(false);
                                tbPlayer2.setEnabled(false);
                            // }
                        }
                    }
                }
            }
        });
        add(player1Panel);

        // Player 2's panel
        player2Panel = new JPanel();
        player2Panel.setLayout(new FlowLayout());
        player2Panel.add(lblPlayer2);
        player2Panel.add(tbPlayer2);
        btnPlayer2 = new JButton("Submit");
        player2Panel.add(btnPlayer2);
        player2Panel.add(lblPlayer2Score);
        btnPlayer2.setEnabled(false);
        tbPlayer2.setEnabled(false);

        btnPlayer2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guess = tbPlayer2.getText().trim();

                if (player1Input.indexOf(guess) == -1
                        && player2Input.indexOf(guess) == -1
                        && GetWords.findWord(guess, boardMatrix)
                        && arrAllWords.indexOf(guess) > -1) {
                    System.out.println("Player 2 has inputted a valid answer");
                    tbPlayer2.setText(guess);
                    Score2 = Score2 + Score(guess);
                    player2Pass++;
                    System.out.println("Player 2's score is: " + Score2);
                    lblPlayer2Score.setText("Score: " + Integer.toString(Score2));
                }

                else {
                    System.out.println("Player 2 has inputted an invalid answer");
                }

                // Checking if Player 2 has crossed the tournament score
                if (scoreGoal > 0 && Score2 >= scoreGoal) {
                    lblWinner.setVisible(true);
                    lblWinner.setText("Player 2 is the Winner!");
                    winnerPanel.setVisible(true);
                    btnPlayer2.setEnabled(false);
                    tbPlayer2.setEnabled(false);
                    btnPlayer1.setEnabled(false);
                    tbPlayer1.setEnabled(false);
                }

                else {
                    // Re-shuffle Boggle Board if Player 2 has passed twice
                    if(player2Pass >= 2) {
                        shakeBtn.setEnabled(true);
                    }
                    //else {
                        btnPlayer2.setEnabled(false);
                        tbPlayer2.setEnabled(false);
                        btnPlayer1.setEnabled(true);
                        tbPlayer1.setEnabled(true);
                        // tbPlayer1.setText("                    ");
                    // }
                }
            }
        });
        add(player2Panel);

        // Winner Panel
        winnerPanel.add(lblWinner);
        lblWinner.setVisible(false);
        winnerPanel.setVisible(true);
        add(winnerPanel);
        setVisible(true);
    }

    // Determining the score value of a word
    public static int Score(String word) {
        int score = 0;
        if (word.length() == 3 || word.length() == 4) {
            score += 1;
        }
        else if (word.length() == 5) {
            score += 2;
        }
        else if (word.length() == 6) {
            score += 3;
        }
        else if (word.length() == 7) {
            score += 4;
        }
        else if (word.length() >= 8) {
            score += 11;
        }
        return score;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        // Return the string that you see on the button
		/*
		if(command.equals("Start Game")) {
			while (true) {
					Thread.sleep(10000);
					if (player1Panel.isEnabled()) {
						this.player1Panel.setEnabled(false);
						this.player2Panel.setEnabled(true);
					}

					else {
						this.player1Panel.setEnabled(true);
						this.player2Panel.setEnabled(false);
					}
			}
		}
		*/
    }
}

