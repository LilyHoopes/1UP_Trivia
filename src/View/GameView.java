package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import Model.*;

import Model.TriviaQuestion;

public class GameView extends JFrame implements PropertyChangeListener {

    private final JFrame myFrame;

    //JLabel for room panel
    private final JLabel myRoomPlayer;
    //JButtons for room panel
    private final JButton myUpButton, myDownButton, myLeftButton, myRightButton;

    //JLabels for the questions panel
    private final JLabel myQuestionLabel, myOptionA_Label, myOptionB_Label, myOptionC_Label, myOptionD_Label;
    //JButtons for questions panel
    private final JButton myA_Button, myB_Button, myC_Button, myD_Button, mySubmitButton;

    //JLabels for the maze panel
    private final JLabel myStartImage, myEndImage, myMazePlayer;
    private final JLabel myA_Room, myB_Room, myC_Room, myD_Room, myE_Room, myF_Room, myG_Room,  myH_Room,
            myI_Room, myJ_Room, myK_Room, myL_Room, myM_Room, myN_Room, myO_Room, myP_Room;

    //add for loop to make a bunch of arrow aka door images to add to panel

    //constructor for GameView
    public GameView() {

        myFrame = new JFrame("1UP Trivia");
        final Dimension frameSize = new Dimension(900, 800);
        myFrame.setSize(frameSize);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);

        final JLabel iconLabel = new JLabel();
        final ImageIcon image = new ImageIcon("img.png");
        iconLabel.setIcon(image);

        myFrame.setIconImage(image.getImage());
        createMenuBar();

        //NOTE: menu bar goes here
        //TriviaMenu menuBar = new TriviaMenu();

        //labels, buttons, text fields

        //Room Panel components
        myUpButton =  new JButton("Up");
        myDownButton = new JButton("Down");
        myLeftButton = new JButton("Left");
        myRightButton = new JButton("Right");

        myRoomPlayer = new JLabel("Room Player"); //this will be an image


        //Questions Panel components
        myQuestionLabel = new JLabel("Question: ");
        myOptionA_Label = new JLabel("--put question here--");
        myOptionB_Label = new JLabel("--put question here--");
        myOptionC_Label = new JLabel("--put question here--");
        myOptionD_Label = new JLabel("--put question here--");

        myA_Button = new JButton("A");
        myB_Button = new JButton("B");
        myC_Button = new JButton("C");
        myD_Button = new JButton("D");
        mySubmitButton = new JButton("Submit");


        //Maze Panel Components
        myStartImage = new JLabel("");
        myEndImage = new JLabel("");
        myMazePlayer = new JLabel("");

        myA_Room = createMazeLabel("A");
        myB_Room = createMazeLabel("B");
        myC_Room = createMazeLabel("C");
        myD_Room = createMazeLabel("D");
        myE_Room = createMazeLabel("E");
        myF_Room = createMazeLabel("F");
        myG_Room = createMazeLabel("G");
        myH_Room = createMazeLabel("H");
        myI_Room = createMazeLabel("I");
        myJ_Room = createMazeLabel("J");
        myK_Room = createMazeLabel("K");
        myL_Room = createMazeLabel("L");
        myM_Room = createMazeLabel("M");
        myN_Room = createMazeLabel("N");
        myO_Room = createMazeLabel("O");
        myP_Room = createMazeLabel("P");

        final JPanel mazePanel = createMazePanel();
        mazePanel.setPreferredSize(new Dimension(500, 500));
        final JPanel roomPanel = createRoomPanel();
        roomPanel.setPreferredSize(new Dimension(300, 300));
        final JPanel questionsPanel = createQuestionsPanel();
        questionsPanel.setPreferredSize(new Dimension(600, 300));

        // Top container for maze and room side-by-side
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(mazePanel);
        topPanel.add(roomPanel);

        // Main container with vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(topPanel);
        mainPanel.add(questionsPanel);

        myFrame.getContentPane().add(mainPanel);
        myFrame.pack();
        myFrame.setVisible(true);

        createMenuBar();

        //initially set some buttons to false here?

        //addListeners() method?
    }

    private JPanel createRoomPanel() {
        JPanel roomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between buttons

        // Up Button
        gbc.gridx = 1;
        gbc.gridy = 0;
        roomPanel.add(myUpButton, gbc);

        // Left Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        roomPanel.add(myLeftButton, gbc);

        // Center (Player)
        gbc.gridx = 1;
        gbc.gridy = 1;
        myRoomPlayer.setPreferredSize(new Dimension(100, 100));
        myRoomPlayer.setHorizontalAlignment(SwingConstants.CENTER);
        myRoomPlayer.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        roomPanel.add(myRoomPlayer, gbc);

        // Right Button
        gbc.gridx = 2;
        gbc.gridy = 1;
        roomPanel.add(myRightButton, gbc);

        // Down Button
        gbc.gridx = 1;
        gbc.gridy = 2;
        roomPanel.add(myDownButton, gbc);

        return roomPanel;
    }

    private JPanel createQuestionsPanel() {
        final JPanel questionsPanel = new JPanel(new BorderLayout());

        //sub panel for the A,B,C,D buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonsPanel.setPreferredSize(new Dimension(50, 400));
        buttonsPanel.add(myA_Button);
        buttonsPanel.add(myB_Button);
        buttonsPanel.add(myC_Button);
        buttonsPanel.add(myD_Button);

        JPanel questionOptionsPanel = new JPanel(new GridLayout(4, 1, 5 ,5));
        questionOptionsPanel.setPreferredSize(new Dimension(700, 400));
        questionOptionsPanel.add(myOptionA_Label);
        questionOptionsPanel.add(myOptionB_Label);
        questionOptionsPanel.add(myOptionC_Label);
        questionOptionsPanel.add(myOptionD_Label);

        questionsPanel.add(myQuestionLabel, BorderLayout.NORTH);
        questionsPanel.add(buttonsPanel, BorderLayout.WEST);
        questionsPanel.add(questionOptionsPanel, BorderLayout.EAST);
        questionsPanel.add(mySubmitButton, BorderLayout.SOUTH);

        questionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return questionsPanel;
    }

    private JPanel createMazePanel() {
        final JPanel mazePanel = new JPanel(new GridLayout(4,4, 10, 10));

//        mazePanel.add(myStartImage); not sure where these go
//        mazePanel.add(myEndImage);  not sure where these go
//        mazePanel.add(myMazePlayer); not sure where these go

        mazePanel.add(myA_Room);
        mazePanel.add(myB_Room);
        mazePanel.add(myC_Room);
        mazePanel.add(myD_Room);
        mazePanel.add(myE_Room);
        mazePanel.add(myF_Room);
        mazePanel.add(myG_Room);
        mazePanel.add(myH_Room);
        mazePanel.add(myI_Room);
        mazePanel.add(myJ_Room);
        mazePanel.add(myK_Room);
        mazePanel.add(myL_Room);
        mazePanel.add(myM_Room);
        mazePanel.add(myN_Room);
        mazePanel.add(myO_Room);
        mazePanel.add(myP_Room);

        return mazePanel;
    }

    private JLabel createMazeLabel(String name) {
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    private void createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();

        //Game Menu
        final JMenu gameMenu = new JMenu("Game");
        final JMenuItem startItem = new JMenuItem("Start");
        final JMenuItem resetItem = new JMenuItem("Reset");
        final JMenuItem exitItem = new JMenuItem("Exit");

        //Add items to Game menu
        gameMenu.add(startItem);
        gameMenu.add(resetItem);
        gameMenu.add(exitItem);


        //Help Menu
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutItem = new JMenuItem("About");
        final JMenuItem rulesItem = new JMenuItem("Rules");

        //Add items to Help menu
        helpMenu.add(aboutItem);
        helpMenu.add(rulesItem);


        //File Menu
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem fileSave = new JMenuItem("Save");
        final JMenuItem fileLoad = new JMenuItem("Load");

        //Add items to File Menu
        fileMenu.add(fileSave);
        fileMenu.add(fileLoad);

        //Add eac menu section to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        myFrame.setJMenuBar(menuBar);

        // Start
        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                startItem.setEnabled(false);
            }
        });

        resetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                startItem.setEnabled(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                // About the game and creators
                JOptionPane.showMessageDialog(null,
                        "1UP Trivia Maze\nWritten in Java\nAuthors: Lily, Christiannel, Komalpreet",
                        "About 1UP Trivia Maze", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        rulesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                // The rules of trivia game
                JOptionPane.showMessageDialog(null,
                        "1UP Trivia Game Rules:\n1. Choose a direction to move\n2. Once at a door, " +
                                "answer the trivia question and submit\n3. If correct, you may open that door and move" +
                                "to a new room and keep answering questions\n4. THIS IS NOT DONE FINISH RULES",
                        "1UP Trivia Game Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    //public void updateGameDisplay(GameModel model) {
        // refresh display based on current model
    //}

    public void displayQuestion(TriviaQuestion question) {
        // show question and options
    }

    public void showWinMessage() { }

    public void showLossMessage() { }

    public void showInvalidMessage() { }

    public void actionPerformed(ActionEvent e) {
        // handle UI actions
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
