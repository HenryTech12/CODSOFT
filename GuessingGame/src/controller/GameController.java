package controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Pageable;
import java.util.Random;

public class GameController extends JPanel implements ActionListener {
    //global variables
    private int WIDTH = 420;
    private int HEIGHT = 420;
    private JFrame gameFrame;
    private JButton guess;
    private JButton[] actionButtons;
    private JTextField txtField;
    private int DEFAULT_GUESS_VALUE = 100;
    private JLabel result;
    private int trials = 5;
    private JLabel trialsMsg;
    private JLabel wins;
    private int numOfWins;
    private int scores;
    public GameController() {
        //initializing panel
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setOpaque(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.black);

        actionButtons = new JButton[2]; //buttons size in array
    }

    public void init() {
        paint();

        //creating frame
        gameFrame = new JFrame();
        gameFrame.setSize(WIDTH,HEIGHT);
        gameFrame.setVisible(true);
        gameFrame.setAlwaysOnTop(true);
        gameFrame.setLayout(null);
        gameFrame.setLocationRelativeTo(null); //centers frame
        gameFrame.setTitle("GUESSING GAME");
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);
        gameFrame.setFont(new Font("FANTASY",Font.BOLD,18));
    }

    //adds needed UI to panel window
    private void paint() {
        JLabel headerLabel = new JLabel("GUESSING GAME");
        headerLabel.setBounds(0,20, this.WIDTH,40);
        headerLabel.setLayout(null);
        headerLabel.setFont(new Font("MONOSPACED",Font.BOLD,20));
        headerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        headerLabel.setVerticalTextPosition(SwingConstants.CENTER);
        headerLabel.setForeground(Color.white);
        headerLabel.setBackground(Color.black);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setVerticalAlignment(SwingConstants.CENTER);
        headerLabel.setOpaque(true);

        this.add(headerLabel);

        trialsMsg = createTrials(); //create trail Label


        txtField = new JTextField("ENTER VALUE BTW (1 - 100)");
        txtField.setBounds(30,110, 250,40);
        txtField.setLayout(null);
        txtField.setOpaque(true);
        txtField.setFont(new Font("FANTASY",Font.PLAIN,15));
        txtField.setVisible(true);

        this.add(txtField);

        guess = new JButton("GUESS");
        guess.setBackground(Color.black);
        guess.setForeground(Color.green);
        guess.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        guess.setLayout(null);
        guess.setFocusable(false);
        guess.setBounds(290,110,90,40);
        Border buttonBorder = BorderFactory.createLineBorder(Color.green,5,true);
        guess.setBorder(buttonBorder);
        guess.addActionListener(this);
        guess.setHorizontalTextPosition(SwingConstants.CENTER);
        guess.setVerticalTextPosition(SwingConstants.CENTER);
        guess.setVisible(true);

        this.add(guess);

        result = new JLabel("RESULTS WILL BE SHOWN HERE!");
        result.setBounds(0,160, this.WIDTH,60);
        result.setLayout(null);
        result.setFont(new Font("MONOSPACED",Font.BOLD,15));
        result.setHorizontalTextPosition(SwingConstants.CENTER);
        result.setVerticalTextPosition(SwingConstants.CENTER);
        result.setForeground(Color.white);
        result.setBackground(Color.black);
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setVerticalAlignment(SwingConstants.CENTER);
        result.setOpaque(true);

        this.add(result);

        int actionX = 80;
        int actionY = 235;
        for(int i = 0; i < actionButtons.length; i++) {
            actionButtons[i] = new JButton("GUESS");
            actionButtons[i].setBackground(Color.black);
            actionButtons[i].setForeground(Color.green);
            actionButtons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            actionButtons[i].setLayout(null);
            actionButtons[i].addActionListener(this);
            actionButtons[i].setBounds(actionX,actionY,90,40);
            actionX += 120;
            actionButtons[i].setBorder(buttonBorder);
            actionButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            actionButtons[i].setVerticalTextPosition(SwingConstants.CENTER);
            actionButtons[i].setVisible(true);
            actionButtons[i].setFocusable(false);
            if(i == 0) {
                actionButtons[i].setText("Restart");
            }
            else {
                actionButtons[i].setText("Exit");
            }
            this.add(actionButtons[i]);
        }
        checkWins(); // add  wins UI
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == guess) {
            if(trials >= 1) {
                int system_guess = generateGuess(); //generate random number btw 1 - 100
                System.out.println(system_guess);
                String user_input = txtField.getText();
                if(!(user_input.isEmpty() && user_input.isBlank())) {
                  try {
                      int input = Integer.parseInt(user_input);
                      displayResultMessage(input, system_guess); //adds result message to UI
                      updateTrials();
                  }
                  catch (NumberFormatException exception) {
                      System.out.println("ERROR: "+exception.getMessage());
                      txtField.setText("Enter Value Btw (1 - 100)");
                  }
                }
            }
            else {
              result.setText(formatResult("Maximum Number Of Trials Reached,Click Button Below to start new game"));
            }
        }

        for(int i = 0; i < actionButtons.length; i++) {
            if(e.getSource() == actionButtons[i]) {
                if(i == 0) {
                    toDefault();;
                }
                else {
                    System.exit(0);
                }
            }
        }
        repaint();
        System.out.println("trials: "+trials);
    }

    private void displayResultMessage(int input, int system_guess) {
      if(validateInput(input)) {
          if (input == system_guess) {
              result.setText(formatResult("USER INPUT MATCHES GUESSED VALUE \nYou Win!, \nKeep On Playing."));
              scores+=5;
              numOfWins++;
              updateWins();
          } else if (input < system_guess) {
              result.setText(formatResult("USER INPUT IS LESS THAN GUESSED VALUE, Try Again!"));
              trials--;
          } else {
              result.setText(formatResult("USER INPUT IS GREATER THAN GUESSED VALUE, Try Again!"));
              trials--;
          }
      }
      else {
          txtField.setText("Invalid Input!, Value must be btw ( 1 - 100).");
      }
    }

    //format displayed text using html and css
    private String formatResult(String msg) {
        return
                """
                  <html>
                     <head>
                        <style>
                            div {
                               width: 80%;
                               height: auto;
                               text-align: center;
                            }
                            label {
                               white-space: wrap;
                               width: 150px;
                               display: block;
                               color: red;
                               text-align: center;
                            }
                        </style>
                     </head>   
                     <body>
                      
                      <div>
                         <label>
                           """+msg+"""
                         </label>
                       </div>
                     </body>
                  </html>
                """;
    }

    //create trials UI
    private JLabel createTrials() {
        trialsMsg = new JLabel("Trial Left: "+trials);
        trialsMsg.setBounds(0,58, this.WIDTH,20);
        trialsMsg.setLayout(null);
        trialsMsg.setFont(new Font(Font.SERIF,Font.BOLD,17));
        trialsMsg.setHorizontalTextPosition(SwingConstants.CENTER);
        trialsMsg.setVerticalTextPosition(SwingConstants.CENTER);
        trialsMsg.setForeground(Color.white);
        trialsMsg.setBackground(Color.black);
        trialsMsg.setHorizontalAlignment(SwingConstants.CENTER);
        trialsMsg.setVerticalAlignment(SwingConstants.CENTER);
        trialsMsg.setOpaque(true);

        this.add(trialsMsg);
        return trialsMsg;
    }

    //updates trials message on every guess
    private void updateTrials() {
        trialsMsg.setText("Trial Left: "+trials);
    }

    private int generateGuess() {
        Random random = new Random();
        return random.nextInt(DEFAULT_GUESS_VALUE);
    }

    private void toDefault() {
        trials = 5;
        result.setText("RESULTS WILL BE SHOWN HERE!");
        txtField.setText("ENTER VALUE BTW (1 - 100)");
        numOfWins = 0;
        scores = 0;
        updateTrials();
        updateWins();
    }

    private boolean validateInput(int value) {
         return (value <= DEFAULT_GUESS_VALUE && value == Math.abs(value));
    }

    private void checkWins() {
        wins = new JLabel(formatResult("Your Scores ( #Num Of Wins("+numOfWins+") ) : "+scores+" points."));
        wins.setBounds(30,290, this.WIDTH,50);
        wins.setLayout(null);
        wins.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
        wins.setHorizontalTextPosition(SwingConstants.CENTER);
        wins.setVerticalTextPosition(SwingConstants.CENTER);
        wins.setForeground(Color.white);
        wins.setBackground(Color.black);
        wins.setHorizontalAlignment(SwingConstants.CENTER);
        wins.setVerticalAlignment(SwingConstants.CENTER);
        wins.setOpaque(true);

        this.add(wins);
    }

    private void updateWins() {
        wins.setText(formatResult("Your Scores ( #Num Of Wins("+numOfWins+") ) : "+scores+" points."));
    }
}
