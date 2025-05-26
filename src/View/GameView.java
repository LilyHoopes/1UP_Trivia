package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.*;
import Model.TriviaQuestion;

public class GameView extends JFrame implements PropertyChangeListener {

    private final JFrame myFrame;

    private final JLabel myRoomPlayer; //JLabel for room panel
    private final JButton myUpButton, myDownButton, myLeftButton, myRightButton; //JButtons for room panel

    private final JLabel myQuestionLabel, myOptionA_Label, myOptionB_Label, myOptionC_Label, myOptionD_Label; //JLabels for the questions panel
    private final JButton myA_Button, myB_Button, myC_Button, myD_Button, mySubmitButton;//JButtons for questions panel

    //JLabels for the maze panel
    private final JLabel myMazePlayer;
    private JLabel myA_Room, myB_Room, myC_Room, myD_Room, myE_Room, myF_Room, myG_Room,  myH_Room,
            myI_Room, myJ_Room, myK_Room, myL_Room, myM_Room, myN_Room, myO_Room, myP_Room;
    private JLabel myPipe1, myPipe2, myPipe3, myPipe4, myPipe5, myPipe6, myPipe7, myPipe8,
            myPipe9, myPipe10, myPipe11, myPipe12, myPipe13, myPipe14, myPipe15, myPipe16,
            myPipe17, myPipe18, myPipe19, myPipe20, myPipe21, myPipe22, myPipe23, myPipe24;
    private JLabel myBlank1, myBlank2, myBlank3, myBlank4, myBlank5, myBlank6, myBlank7, myBlank8, myBlank9;

    private Color skyBlue = new Color(135, 206, 235);


    //add for loop to make a bunch of arrow aka door images to add to panel, need 24 of them?

    //constructor for GameView
    public GameView() {

        myFrame = new JFrame("1UP Trivia");
        final Dimension frameSize = new Dimension(900, 800);

        myFrame.setSize(frameSize);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);

        final JLabel iconLabel = new JLabel();
        final ImageIcon image = new ImageIcon("icons.png");
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

        myRoomPlayer = new JLabel(""); //where character will be displayed
        myRoomPlayer.setIcon(getScaledIcon("icons/P1Mario.png", 80, 80));
        //myRoomPlayer.setIcon(getScaledIcon("icons/P2Luigi.png", 80, 80));
        //myRoomPlayer.setIcon(getScaledIcon("icons/P3PrincessPeach.png", 77, 90));
        //myRoomPlayer.setIcon(getScaledIcon("icons/P4Toad.png", 77, 82));


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
        myMazePlayer = new JLabel("");

        createRooms();
        createPipes();
        createEmptyBlocks();

        final JPanel mazePanel = createMazePanel();
        mazePanel.setPreferredSize(new Dimension(450, 450));
        final JPanel roomPanel = createRoomPanel();
        roomPanel.setPreferredSize(new Dimension(300, 300));
        final JPanel questionsPanel = createQuestionsPanel();
        questionsPanel.setPreferredSize(new Dimension(600, 150));

        // Top container for maze and room side-by-side
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(mazePanel);
        topPanel.add(roomPanel);
        topPanel.setBackground(skyBlue);

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

        // ADDED BACKGROUND COLOR (But does not fill everything in)
        // TODO: Change background color??
        myFrame.setBackground(skyBlue);  // sky blue
        mazePanel.setBackground(skyBlue);
        roomPanel.setBackground(skyBlue);
        questionsPanel.setBackground(skyBlue);
    }

    // Helper method for scaling the icons for the room images
    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
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
        buttonsPanel.setBackground(skyBlue); //sky blue
        buttonsPanel.setPreferredSize(new Dimension(50, 400));
        buttonsPanel.add(myA_Button);
        buttonsPanel.add(myB_Button);
        buttonsPanel.add(myC_Button);
        buttonsPanel.add(myD_Button);

        JPanel questionOptionsPanel = new JPanel(new GridLayout(4, 1, 5 ,5));
        questionOptionsPanel.setBackground(skyBlue); //sky blue
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

        final JPanel mazePanel = new JPanel(new GridLayout(7,7, 5, 5));

//        mazePanel.add(myMazePlayer); not sure where these go

        //first row
        mazePanel.add(myA_Room);
        mazePanel.add(myPipe1);
        mazePanel.add(myB_Room);
        mazePanel.add(myPipe2);
        mazePanel.add(myC_Room);
        mazePanel.add(myPipe3);
        mazePanel.add(myD_Room);

        //second row
        mazePanel.add(myPipe4);
        mazePanel.add(myBlank1);
        mazePanel.add(myPipe5);
        mazePanel.add(myBlank2);
        mazePanel.add(myPipe6);
        mazePanel.add(myBlank3);
        mazePanel.add(myPipe7);

        //third row
        mazePanel.add(myE_Room);
        mazePanel.add(myPipe8);
        mazePanel.add(myF_Room);
        mazePanel.add(myPipe9);
        mazePanel.add(myG_Room);
        mazePanel.add(myPipe10);
        mazePanel.add(myH_Room);

        //fourth row
        mazePanel.add(myPipe11);
        mazePanel.add(myBlank4);
        mazePanel.add(myPipe12);
        mazePanel.add(myBlank5);
        mazePanel.add(myPipe13);
        mazePanel.add(myBlank6);
        mazePanel.add(myPipe14);

        //fifth row
        mazePanel.add(myI_Room);
        mazePanel.add(myPipe15);
        mazePanel.add(myJ_Room);
        mazePanel.add(myPipe16);
        mazePanel.add(myK_Room);
        mazePanel.add(myPipe17);
        mazePanel.add(myL_Room);

        //sixth row
        mazePanel.add(myPipe18);
        mazePanel.add(myBlank7);
        mazePanel.add(myPipe19);
        mazePanel.add(myBlank8);
        mazePanel.add(myPipe20);
        mazePanel.add(myBlank9);
        mazePanel.add(myPipe21);

        //seventh row
        mazePanel.add(myM_Room);
        mazePanel.add(myPipe22);
        mazePanel.add(myN_Room);
        mazePanel.add(myPipe23);
        mazePanel.add(myO_Room);
        mazePanel.add(myPipe24);
        mazePanel.add(myP_Room);

        return mazePanel;
    }

    private void createRooms() {
        myA_Room = createMazeLabelWithBorder(""); // A
        myB_Room = createMazeLabelWithBorder(""); // B
        myC_Room = createMazeLabelWithBorder(""); // C
        myD_Room = createMazeLabelWithBorder(""); // D
        myE_Room = createMazeLabelWithBorder(""); // E
        myF_Room = createMazeLabelWithBorder(""); // F
        myG_Room = createMazeLabelWithBorder(""); // G
        myH_Room = createMazeLabelWithBorder(""); // H
        myI_Room = createMazeLabelWithBorder(""); // I
        myJ_Room = createMazeLabelWithBorder(""); // J
        myK_Room = createMazeLabelWithBorder(""); // K
        myL_Room = createMazeLabelWithBorder(""); // L
        myM_Room = createMazeLabelWithBorder(""); // M
        myN_Room = createMazeLabelWithBorder(""); // N
        myO_Room = createMazeLabelWithBorder(""); // O
        myP_Room = createMazeLabelWithBorder(""); // P


        myA_Room.setIcon(getScaledIcon("icons/greenegg.png", 60, 60));
        myB_Room.setIcon(getScaledIcon("icons/key.png", 60, 60));
        myC_Room.setIcon(getScaledIcon("icons/cherries.png", 60, 60));
        myD_Room.setIcon(getScaledIcon("icons/blueegg.png", 60, 60));
        myE_Room.setIcon(getScaledIcon("icons/goomba.png", 60, 60));
        myF_Room.setIcon(getScaledIcon("icons/iceflower.png", 60, 60));
        myG_Room.setIcon(getScaledIcon("icons/mysterybox.png", 60, 60));
        myH_Room.setIcon(getScaledIcon("icons/poisonmushroom.png", 60, 60));
        myI_Room.setIcon(getScaledIcon("icons/star.png", 60, 60));
        myJ_Room.setIcon(getScaledIcon("icons/powbox.png", 60, 60));
        myK_Room.setIcon(getScaledIcon("icons/greenshell.png", 60, 60));
        myL_Room.setIcon(getScaledIcon("icons/fireflower.png", 60, 60));
        myM_Room.setIcon(getScaledIcon("icons/coin.png", 60, 60));
        myN_Room.setIcon(getScaledIcon("icons/rainbowstar.png", 60, 60));
        myO_Room.setIcon(getScaledIcon("icons/redmushroom.png", 60, 60));
        myP_Room.setIcon(getScaledIcon("icons/1upmushroom.png", 60, 60));
    }

    private void createPipes() {
        myPipe1 = createMazeLabel("");
        myPipe2 = createMazeLabel("");
        myPipe3 = createMazeLabel("");
        myPipe4 = createMazeLabel("");
        myPipe5 = createMazeLabel("");
        myPipe6 = createMazeLabel("");
        myPipe7 = createMazeLabel("");
        myPipe8 = createMazeLabel("");
        myPipe9 = createMazeLabel("");
        myPipe10 = createMazeLabel("");
        myPipe11 = createMazeLabel("");
        myPipe12 = createMazeLabel("");
        myPipe13 = createMazeLabel("");
        myPipe14 = createMazeLabel("");
        myPipe15 = createMazeLabel("");
        myPipe16 = createMazeLabel("");
        myPipe17 = createMazeLabel("");
        myPipe18 = createMazeLabel("");
        myPipe19 = createMazeLabel("");
        myPipe20 = createMazeLabel("");
        myPipe21 = createMazeLabel("");
        myPipe22 = createMazeLabel("");
        myPipe23 = createMazeLabel("");
        myPipe24 = createMazeLabel("");

        ImageIcon horizontalPipe = getScaledIcon("icons/HorizontalGreenPipe.png", 60, 35);
        ImageIcon verticalPipe = getScaledIcon("icons/VerticalGreenPipe.png", 35, 60);

        myPipe1.setIcon(horizontalPipe);
        myPipe2.setIcon(horizontalPipe);
        myPipe3.setIcon(horizontalPipe);

        myPipe4.setIcon(verticalPipe);
        myPipe5.setIcon(verticalPipe);
        myPipe6.setIcon(verticalPipe);
        myPipe7.setIcon(verticalPipe);

        myPipe8.setIcon(horizontalPipe);
        myPipe9.setIcon(horizontalPipe);
        myPipe10.setIcon(horizontalPipe);

        myPipe11.setIcon(verticalPipe);
        myPipe12.setIcon(verticalPipe);
        myPipe13.setIcon(verticalPipe);
        myPipe14.setIcon(verticalPipe);

        myPipe15.setIcon(horizontalPipe);
        myPipe16.setIcon(horizontalPipe);
        myPipe17.setIcon(horizontalPipe);

        myPipe18.setIcon(verticalPipe);
        myPipe19.setIcon(verticalPipe);
        myPipe20.setIcon(verticalPipe);
        myPipe21.setIcon(verticalPipe);

        myPipe22.setIcon(horizontalPipe);
        myPipe23.setIcon(horizontalPipe);
        myPipe24.setIcon(horizontalPipe);
    }

    //used as filler rooms between the vertical pipes in the maze
    private void createEmptyBlocks() {

        myBlank1 = createMazeLabel("");
        myBlank2 = createMazeLabel("");
        myBlank3 = createMazeLabel("");
        myBlank4 = createMazeLabel("");
        myBlank5 = createMazeLabel("");
        myBlank6 = createMazeLabel("");
        myBlank7 = createMazeLabel("");
        myBlank8 = createMazeLabel("");
        myBlank9 = createMazeLabel("");
    }

    //used by createRooms and createPipes
    private JLabel createMazeLabel(String theName) {
        JLabel label = new JLabel(theName, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(50, 50));
        //label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    //used by createRooms
    private JLabel createMazeLabelWithBorder(String theName) {
        JLabel label = new JLabel(theName, SwingConstants.CENTER);
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

    private void setupKeyBindings(JPanel panel) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        //wasd
        inputMap.put(KeyStroke.getKeyStroke('w'), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke('a'), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke('s'), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke('d'), "moveRight");

        //arrows
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        //map action
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                //movePlayer(-1, 0);
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                //movePlayer(0, -1);
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                //movePlayer(1, 0);
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                //movePlayer(0, 1);
            }
        });
    }

    //public void updateGameDisplay(GameModel model) {
        // refresh display based on current model
    //}

    //sql thing
//    public void displayQuestion(TriviaQuestion question) {
//        myQuestionLabel.setText("Question: " + question.getQuestionText());
//        String[] choices = question.getMultipleChoices();
//
//        myOptionA_Label.setText("A. " + choices[0]);
//        myOptionB_Label.setText("B. " + choices[1]);
//        myOptionC_Label.setText("C. " + choices[2]);
//        myOptionD_Label.setText("D. " + choices[3]);
//    }

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
