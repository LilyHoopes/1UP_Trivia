/*
 * The GameView class is responsible for displaying the game window, including the maze grid,
 * player movement, trivia questions, and UI interactions. It observes changes in the model
 * using the PropertyChangeListener interface.
 */

package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Controller.GameController;
import Model.*;
import Model.TriviaQuestion;
import Controller.SoundManager;

/**
 * GameView is responsible for displaying the graphical user interface (GUI)
 * of the trivia maze game. It handles user interactions, displays the maze grid,
 * questions, answers, and manages player movements.
 *
 * @author Lily Hoopes
 * @author Komalpreet Dhaliwal
 * @author Christiannel Maningat
 * @version 5/7/2025
 */

public class GameView extends JFrame implements PropertyChangeListener {

    // --------Controller & Game State----------
    /** Reference to the controller that manages game logic and state transitions. */
    private GameController myController;

    /** The current trivia question displayed to the user. */
    private TriviaQuestion myCurrentQuestion;

    /** The answer currently selected by the player. */
    private String mySelectedAnswer = null;

    /** The button the user last clicked to answer a question. */
    private JButton myClickedButton = null; //just the actual button they clicked, we highlight this one

    // --------Window & Visual Settings----------

    /** The main frame of the game window. */
    private JFrame myFrame = null;

    /** Custom sky blue color used for background and UI elements. */
    //private final Color SKY_BLUE = new Color(135, 206, 235); // What we had before yuh
    private static final Color SKY_BLUE = new Color(46, 141, 229);

    // --------Room Panel----------

    /** Label displaying the current room's icon. */
    private final JLabel myCurrentRoomIcon; //JLabel for room panel

    /** Button for moving the player up, down, left and right in the maze. */
    private final JButton myUpButton, myDownButton, myLeftButton, myRightButton; //JButtons for room panel

    // --------Question Panel--------

    /** Label displaying the current trivia question, and labels for the following answer options A, B, C, or D. */
    private final JLabel myQuestionLabel, myOptionA_Label, myOptionB_Label, myOptionC_Label, myOptionD_Label;
    //JLabels for the questions panel

    /** Buttons to select answer A, b, C, or D and also a submit button to submit selected answer. */
    private final JButton myA_Button, myB_Button, myC_Button, myD_Button, mySubmitButton;
    //JButtons for questions panel


    // --------Maze Panel & Icons--------

    /** 2D array of labels representing the visual grid of the maze. */
    //JLabels for the maze panel
    private final JLabel[][] myMazeIconsGrid = new JLabel[7][7];

    /**
     * Array of various icons used to populate rooms in the maze.
     * Each icon represents a different room.
     */
    private final ImageIcon[] myRoomIcons = new ImageIcon[] {
            getScaledIcon("icons/redmushroom.png", 60, 60),
            getScaledIcon("icons/key.png", 60, 60),
            getScaledIcon("icons/cherries.png", 60, 60),
            getScaledIcon("icons/blueegg.png", 60, 60),
            getScaledIcon("icons/goomba.png", 60, 60),
            getScaledIcon("icons/iceflower.png", 60, 60),
            getScaledIcon("icons/mysterybox.png", 60, 60),
            getScaledIcon("icons/poisonmushroom.png", 60, 60),
            getScaledIcon("icons/star.png", 60, 60),
            getScaledIcon("icons/powbox.png", 60, 60),
            getScaledIcon("icons/greenshell.png", 60, 60),
            getScaledIcon("icons/fireflower.png", 60, 60),
            getScaledIcon("icons/coin.png", 60, 60),
            getScaledIcon("icons/rainbowstar.png", 60, 60),
            getScaledIcon("icons/greenegg.png", 60, 60),
            getScaledIcon("icons/1upmushroom.png", 60, 60)
    };

    // --------Player position & Icon tracking--------

    /** Stores the icon of the room before character moved into it. Used to restore the room state. */
    private ImageIcon myPreviousIcon;  // stores the last icon Mario replaced

    /** The row index of character’s previous position. */
    private int myPreviousRow = -1;    // previous player position

    /** The column index of character’s previous position. */
    private int myPreviousCol = -1;    // previous player position

    /** Icon representing the Mario player avatar. */
    private final ImageIcon myMarioIcon = getScaledIcon("icons/P1Mario.png", 60, 60);

    /** The Maze model object representing the current layout and room state. */
    private Maze myMaze;
    private Player myPlayer;

    /** Creates a Sound Manager Instance */
    private final SoundManager mySoundManager = new SoundManager();

    /**
     * Constructs the main game window for the Trivia Maze.
     * Initializes the frame, panels, buttons, and listeners,
     * and displays the UI.
     *
     * @param theMaze the Maze model that this view will visualize.
     */
    public GameView(final Maze theMaze) {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        myMaze = theMaze;

        myFrame = new JFrame("1UP Trivia");
        final Dimension frameSize = new Dimension(900, 800);
        // 900 800

        myFrame.setSize(frameSize);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);

        // ICON for game
        final JLabel iconLabel = new JLabel();
        final ImageIcon image = new ImageIcon("icons/1UpMushroom.png");
        iconLabel.setIcon(image);

        myFrame.setIconImage(image.getImage());
        createMenuBar();

        //labels, buttons, text fields

        //Room Panel components
        myUpButton =  new JButton("Up");
        myDownButton = new JButton("Down");
        myLeftButton = new JButton("Left");
        myRightButton = new JButton("Right");

        //action listeners for up down left right buttons
        myUpButton.addActionListener(theEvent -> myController.attemptMove(Direction.UP));
        myDownButton.addActionListener(theEvent -> myController.attemptMove(Direction.DOWN));
        myLeftButton.addActionListener(theEvent -> myController.attemptMove(Direction.LEFT));
        myRightButton.addActionListener(theEvent -> myController.attemptMove(Direction.RIGHT));


        myCurrentRoomIcon = new JLabel(""); //where current room will be displayed
        //myCurrentRoomIcon.setIcon()

        //Questions Panel components
        myQuestionLabel = new JLabel("Question: ");
        myOptionA_Label = new JLabel("");
        myOptionB_Label = new JLabel("");
        myOptionC_Label = new JLabel("");
        myOptionD_Label = new JLabel("");

        final ImageIcon A_Mario = getScaledIcon("icons/AMario.png", 30, 30);
        final ImageIcon B_Luigi = getScaledIcon("icons/BLuigi.png", 30, 30);
        final ImageIcon C_PrincessPeach =  getScaledIcon("icons/Cprincesspeach.png", 30, 30);
        final ImageIcon D_Toad = getScaledIcon("icons/DToad.png", 30, 30);

        myA_Button = new JButton(A_Mario);
        myB_Button = new JButton(B_Luigi);
        myC_Button = new JButton(C_PrincessPeach);
        myD_Button = new JButton(D_Toad);
        mySubmitButton = new JButton("Submit");

        myA_Button.setPreferredSize(new Dimension(50, 50));
        myB_Button.setPreferredSize(new Dimension(50, 50));
        myC_Button.setPreferredSize(new Dimension(50, 50));
        myD_Button.setPreferredSize(new Dimension(50, 50));

        myA_Button.setBackground(SKY_BLUE);
        myA_Button.setBorder(BorderFactory.createLineBorder(SKY_BLUE));

        myB_Button.setBackground(SKY_BLUE);
        myB_Button.setBorder(BorderFactory.createLineBorder(SKY_BLUE));

        myC_Button.setBackground(SKY_BLUE);
        myC_Button.setBorder(BorderFactory.createLineBorder(SKY_BLUE));

        myD_Button.setBackground(SKY_BLUE);
        myD_Button.setBorder(BorderFactory.createLineBorder(SKY_BLUE));

        //create the panels
        final JPanel mazePanel = createMazePanel();
        mazePanel.setPreferredSize(new Dimension(450, 450));
        final JPanel roomPanel = createRoomPanel();
        roomPanel.setPreferredSize(new Dimension(300, 300));

        final JPanel longBrickPanel = createLongBrickPanel();
        longBrickPanel.setPreferredSize(new Dimension(750, 80));

        final JPanel questionsPanel = createQuestionsPanel();
        questionsPanel.setPreferredSize(new Dimension(600, 200));

        // Top container for maze and room side-by-side
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(mazePanel);
        topPanel.add(roomPanel);
        topPanel.setBackground(SKY_BLUE);

        // Main container with vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(topPanel);
        mainPanel.add(longBrickPanel);
        mainPanel.add(questionsPanel);

        myFrame.getContentPane().add(mainPanel);
        myFrame.pack();
        myFrame.setVisible(true);

        createMenuBar();

        myFrame.setBackground(SKY_BLUE);
        mazePanel.setBackground(SKY_BLUE);
        roomPanel.setBackground(SKY_BLUE);
        longBrickPanel.setBackground(SKY_BLUE);
        questionsPanel.setBackground(SKY_BLUE);

        myA_Button.addActionListener(_ -> clickedAnswerButton(myA_Button));
        myB_Button.addActionListener(_ -> clickedAnswerButton(myB_Button));
        myC_Button.addActionListener(_ -> clickedAnswerButton(myC_Button));
        myD_Button.addActionListener(_ -> clickedAnswerButton(myD_Button));

        mySubmitButton.addActionListener(_ -> {

            // Load ImageIcons for the JOptionPanes
            ImageIcon correctIcon = new ImageIcon("icons/thumbsUpJumpingMar.png");
            ImageIcon incorrectIcon = new ImageIcon("icons/shockedMario.png");
            //ImageIcon chooseAnswerFirst = new ImageIcon("icons/bruhMario.png"); // Meme mario
            ImageIcon chooseAnswerFirst = new ImageIcon("icons/smhMario.png");

            if (mySelectedAnswer == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer first.\nMario is shaking his head.",
                        "Wait a second!", JOptionPane.INFORMATION_MESSAGE, chooseAnswerFirst);
                return;
            }

            String userSelected = mySelectedAnswer;
            //System.out.println("userSelected: " + userSelected);
            boolean correct = myController.checkAnswerAndMove(userSelected);

            // Show result to user
            if (correct) {
                mySoundManager.playCorrectSound();
                JOptionPane.showMessageDialog(this, "Correct!",
                        "Correct!", JOptionPane.INFORMATION_MESSAGE, correctIcon);
            } else {
                mySoundManager.playIncorrectSound();
                JOptionPane.showMessageDialog(this,
                        "Incorrect!\nCorrect answer: " + myCurrentQuestion.getCorrectAnswer(),
                        "Wrong Answer", JOptionPane.ERROR_MESSAGE, incorrectIcon);
            }

            //set color back to null once they click submit
            myA_Button.setBackground(SKY_BLUE);
            myB_Button.setBackground(SKY_BLUE);
            myC_Button.setBackground(SKY_BLUE);
            myD_Button.setBackground(SKY_BLUE);

            mySelectedAnswer = null;

            myController.checkGameWinLossStatus(this);

        });
    }

    /**
     * Shows the title screen for the 1UP Trivia game.
     * Displays a title image and a start button.
     *
     * @param onStartGame the action to run when the start button is clicked.
     */
    public static void showTitleScreen(Runnable onStartGame) {
        SoundManager soundManager = new SoundManager(); // Instantiate

        //make separate frame for the title screen
        JFrame titleFrame = new JFrame("Welcome to 1UP Trivia!");
        titleFrame.setSize(800, 600);
        titleFrame.setLocationRelativeTo(null);
        titleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titleFrame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());

        //load in & resize the title image
        ImageIcon originalIcon = new ImageIcon("icons/1UPTitleScreen.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        //set title image into label
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        //start button
        ImageIcon startIcon = new ImageIcon("icons/startIcon.png");
        JButton startButton = new JButton(startIcon);
        startButton.setBackground(SKY_BLUE);
        startButton.setBorder(BorderFactory.createLineBorder(SKY_BLUE));
        startButton.addActionListener(e -> {
            soundManager.playStartSound();
            titleFrame.dispose();  //closes the title screen
            onStartGame.run();     //launches the main game
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.setBackground(SKY_BLUE);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // ICON for game
        final JLabel iconLabel = new JLabel();
        final ImageIcon image = new ImageIcon("icons/1UpMushroom.png");
        iconLabel.setIcon(image);

        titleFrame.setIconImage(image.getImage());
        titleFrame.getContentPane().add(panel);
        titleFrame.setVisible(true);
    }

    public void displayTriviaQuestion(TriviaQuestion theQuestion) {
        //System.out.println("displayTriviaQuestion method question: " + theQuestion);
        setQuestion(theQuestion);
        // Show trivia panel
        //questionsPanel.setVisible(true);
    }

    /**
     * Extracts the answer text from a labeled option.
     *
     * @param theLabel the JLabel containing text like "A: Mushroom".
     * @return the answer portion (e.g. "Mushroom").
     */
    private String getOptionText(final JLabel theLabel) {
        String text = theLabel.getText();
        return text.substring(text.indexOf(":") + 1).trim();  // removes "A: " etc.
    }

    /**
     * Highlights the clicked answer button and stores the selected answer.
     *
     * @param theButton the JButton the user clicked.
     */
    private void clickedAnswerButton(final JButton theButton) {
        // Reset old button background
        if (myClickedButton != null) {
            myClickedButton.setBackground(SKY_BLUE); // reset to default
        }

        // Highlight new button
        theButton.setBackground(Color.GREEN);
        myClickedButton = theButton;

        // Determine which answer was selected
        String answer = null;
        if (theButton == myA_Button) answer = getOptionText(myOptionA_Label);
        else if (theButton == myB_Button) answer = getOptionText(myOptionB_Label);
        else if (theButton == myC_Button) answer = getOptionText(myOptionC_Label);
        else if (theButton == myD_Button) answer = getOptionText(myOptionD_Label);

        //System.out.println("Answer selected: " + answer);
        mySelectedAnswer = answer;
    }

    /**
     * Loads an ImageIcon from disk and scales it smoothly.
     *
     * @param thePath   file path to the image.
     * @param theWidth  desired icon width.
     * @param theHeight desired icon height.
     * @return a new ImageIcon scaled to the given dimensions.
     */
    public ImageIcon getScaledIcon(final String thePath, final int theWidth, final int theHeight) {
        // Helper method for scaling the icons for the room images
        ImageIcon icon = new ImageIcon(thePath);
        Image scaledImage = icon.getImage().getScaledInstance(theWidth, theHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Builds and returns the panel containing the directional room controls.
     *
     * @return a JPanel laid out with Up/Down/Left/Right buttons and the current room icon.
     */
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
        myCurrentRoomIcon.setPreferredSize(new Dimension(100, 100));
        myCurrentRoomIcon.setHorizontalAlignment(SwingConstants.CENTER);
        myCurrentRoomIcon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        roomPanel.add(myCurrentRoomIcon, gbc);

        // Right Button
        gbc.gridx = 2;
        gbc.gridy = 1;
        roomPanel.add(myRightButton, gbc);

        // Down Button
        gbc.gridx = 1;
        gbc.gridy = 2;
        roomPanel.add(myDownButton, gbc);

        setupKeyBindings(roomPanel);

        return roomPanel;
    }

    /**
     * Builds and returns the panel showing the current trivia question and answer buttons.
     *
     * @return a JPanel containing the question label, option labels, and submit button.
     */
    private JPanel createQuestionsPanel() {
        final JPanel questionsPanel = new JPanel(new BorderLayout());

        //sub panel for the A,B,C,D buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonsPanel.setBackground(SKY_BLUE); //sky blue
        buttonsPanel.setPreferredSize(new Dimension(50, 400));
        buttonsPanel.add(myA_Button);
        buttonsPanel.add(myB_Button);
        buttonsPanel.add(myC_Button);
        buttonsPanel.add(myD_Button);

        JPanel questionOptionsPanel = new JPanel(new GridLayout(4, 1, 5 ,5));
        questionOptionsPanel.setBackground(SKY_BLUE); //sky blue
        questionOptionsPanel.setPreferredSize(new Dimension(700, 400));
        questionOptionsPanel.add(myOptionA_Label);
        questionOptionsPanel.add(myOptionB_Label);
        questionOptionsPanel.add(myOptionC_Label);
        questionOptionsPanel.add(myOptionD_Label);

        questionsPanel.add(myQuestionLabel, BorderLayout.NORTH);
        questionsPanel.add(buttonsPanel, BorderLayout.WEST);
        questionsPanel.add(questionOptionsPanel, BorderLayout.EAST);
        questionsPanel.add(mySubmitButton, BorderLayout.SOUTH);


        return questionsPanel;
    }

    /**
     * Builds and returns the maze grid panel populated with room and pipe icons.
     *
     * @return a JPanel arranged in a 7×7 GridLayout for the maze display.
     */
    private JPanel createMazePanel() {

        final JPanel mazePanel = new JPanel(new GridLayout(7,7, 5, 5));
        populateMazeGrid(); // Fill the myMazeIconsGrid with icons
        initializeMazeContents(); //Place mario icon at start and store the normal rom icon


        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                mazePanel.add(myMazeIconsGrid[row][col]);
            }
        }
        return mazePanel;
    }

    /**
     * Populates the internal 7×7 JLabel grid with room and pipe icons.
     * Called once during view initialization.
     */
    private void populateMazeGrid() {
        int roomIndex = 0;  // index for the room icons

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                JLabel label = new JLabel();


                if (row % 2 == 0 && col % 2 == 0) {
                    // Room position
                    label.setIcon(myRoomIcons[roomIndex++]);
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                } else if (row % 2 == 0 && col % 2 == 1) {
                    // Horizontal pipe between rooms
                    ImageIcon pipeIcon = new ImageIcon("icons/HorizontalGreenPipe.png");
                    Image scaledImage = pipeIcon.getImage().getScaledInstance(60, 35, Image.SCALE_SMOOTH);
                    pipeIcon = new ImageIcon(scaledImage);
                    pipeIcon.setDescription("icons/HorizontalGreenPipe");  // Set description here, after scaling
                    label.setIcon(pipeIcon);
                } else if (row % 2 == 1 && col % 2 == 0) {
                    // Vertical pipe between rows
                    // Horizontal pipe between rooms
                    ImageIcon pipeIcon = new ImageIcon("icons/VerticalGreenPipe.png");
                    Image scaledImage = pipeIcon.getImage().getScaledInstance(35, 60, Image.SCALE_SMOOTH);
                    pipeIcon = new ImageIcon(scaledImage);
                    pipeIcon.setDescription("icons/VerticalGreenPipe");  // Set description here, after scaling
                    label.setIcon(pipeIcon);
                } else {
                    // Blank space (no room or pipe)
                    label.setIcon(getScaledIcon("", 60, 60));
                }

                // Store in the grid
                myMazeIconsGrid[row][col] = label;

                myMazeIconsGrid[row][col].setHorizontalAlignment(JLabel.CENTER);
                myMazeIconsGrid[row][col].setVerticalAlignment(JLabel.CENTER);

            }
        }
    }

    /**
     * Creates a JPanel with a scaled "long brick" image centered inside it.
     * The image is loaded from disk, scaled to match the panel size, and added via JLabel.
     *
     * @return JPanel containing the scaled image.
     */
    private JPanel createLongBrickPanel() {
        final JPanel longBrickPanel = new JPanel();
        longBrickPanel.setLayout(new BorderLayout());

        //longBrickPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Add scaled icon to JLabel
        final ImageIcon longBrickIcon = getScaledIcon("icons/looongBrick.png", 1150, 140);
        JLabel brickLabel = new JLabel(longBrickIcon);

        longBrickPanel.add(brickLabel, BorderLayout.CENTER);

        return longBrickPanel;
    }

    /**
     * Builds and installs the application menu bar with Game, File, and Help menus.
     */
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

        //Add each menu section to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        myFrame.setJMenuBar(menuBar);

        // Start
        startItem.addActionListener(_ -> startItem.setEnabled(false));

        resetItem.addActionListener(_ -> startItem.setEnabled(true));

        resetItem.addActionListener(_ -> {

                myController.restartGame();
        });

        exitItem.addActionListener(_ -> {

            ImageIcon icon = new ImageIcon(getScaledIcon("icons/newHidingMario.png", 100, 100).getImage());

            final int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit?\nMario will be sad!",
                    "Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    icon
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        aboutItem.addActionListener(_ -> {

            ImageIcon icon = new ImageIcon(getScaledIcon("icons/gameBoyMario.png", 100, 100).getImage());

            // About the game and creators
            JOptionPane.showMessageDialog(null,
                    "This is a recreation of the project Trivia Maze with a twist involving the Mario Bros" +
                            " universe,\ngetting renamed as the 1-UP Trivia Maze! This program was written in Java with" +
                            " versions 21 and 23.\nThis trivia maze game was written by Lily Hoopes, Christiannel Maningat," +
                            " and Komalpreet Dhaliwal.\nAll three who are students whom attend the University of Washington Tacoma.",
                    "About 1-UP Trivia Maze", JOptionPane.INFORMATION_MESSAGE, icon);
        });

        rulesItem.addActionListener(_ -> {
            // The rules of trivia game

            // added 1up mushroom image to add to pane
            ImageIcon icon = new ImageIcon(getScaledIcon("icons/jumpUpMario.png", 100, 100).getImage());


            JOptionPane.showMessageDialog(null,
                    "Welcome to 1-UP Trivia Maze!\n\nThis game will test your knowledge" +
                            " on the Mario Bros Universe!\n\nHere are the rules:\n1. Choose a direction to move," +
                            " either LEFT, RIGHT, UP, or DOWN.\n2. Once at a door, answer the trivia question " +
                            "and press submit.\n3. If correct, the door will unlock, allowing you travel through the pipes to a new room.\n" +
                            "4. From this point you must keep answering questions. But if answered wrong, the door\n" +
                            " will lock, pushing you back to where you were before!\n5. " +
                            "Answer questions carefully and find your way out of the maze!\n\nGood luck, friends. Lets-a-go!",
                    "1-UP Trivia Game Rules",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon);

        });
    }

    /**
     * Sets up key bindings for WASD and arrow keys.
     * Binds each key to movement actions using an InputMap and ActionMap.
     *
     * @param thePanel The main panel component used for binding key actions.
     */
    private void setupKeyBindings(final JPanel thePanel) {
        InputMap inputMap = thePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = thePanel.getActionMap();

        //wasd
        inputMap.put(KeyStroke.getKeyStroke('w'), "attemptMoveUp");
        inputMap.put(KeyStroke.getKeyStroke('a'), "attemptMoveLeft");
        inputMap.put(KeyStroke.getKeyStroke('s'), "attemptMoveDown");
        inputMap.put(KeyStroke.getKeyStroke('d'), "attemptMoveRight");

        //arrows
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "attemptMoveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "attemptMoveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "attemptMoveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "attemptMoveRight");

        //map action
        actionMap.put("attemptMoveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                myController.attemptMove(Direction.UP);
            }
        });
        actionMap.put("attemptMoveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                myController.attemptMove(Direction.LEFT);
            }
        });
        actionMap.put("attemptMoveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                myController.attemptMove(Direction.DOWN);
            }
        });
        actionMap.put("attemptMoveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                myController.attemptMove(Direction.RIGHT);
            }
        });
    }

    /**
     * Handles the player's movement in the specified direction.
     * Updates the visual grid to reflect the new position of the player,
     * restores the icon of the previous room, and updates the room and question displays.
     *
     * @param theDirection The direction in which the player attempts to move.
     */
    public void handleMoveThroughOpenDoor(final Direction theDirection) {

        Player player = myMaze.getPlayer();

        //boolean moved = player.move(theDirection);

        int newRow = player.getRow() * 2;
        int newCol = player.getCol() * 2;

        //System.out.println("Player moved " + theDirection);
        //System.out.println("Current player position: " + newRow + ", " + newCol);

        // Restore the previous icon
        if (myPreviousRow != -1 && myPreviousCol != -1 && myPreviousIcon != null) {
            myMazeIconsGrid[myPreviousRow][myPreviousCol].setIcon(myPreviousIcon);
        }

        //Save the icon at the new location
        myPreviousIcon = (ImageIcon) myMazeIconsGrid[newRow][newCol].getIcon();

        //Set Mario icon at the new location
        myMazeIconsGrid[newRow][newCol].setIcon(myMarioIcon);
        myCurrentRoomIcon.setIcon(myPreviousIcon);

        //Update previous position
        myPreviousRow = newRow;
        myPreviousCol = newCol;

        //Update movement buttons based on new position
        updateMovementButtons();
    }

    /**
     * Updates the state of movement buttons (UP, DOWN, LEFT, RIGHT).
     * Buttons are enabled only if the corresponding door exists and is unlocked.
     */
    public void updateMovementButtons() {

        Player player = myMaze.getPlayer();
        Room currentRoom = myMaze.getCurrentRoom();

        int row = player.getRow();
        int col = player.getCol();

        //System.out.println("Current room position: " + row + ", " + col);

        // UP
        Door upDoor = currentRoom.getDoor(Direction.UP);
        myUpButton.setEnabled(myMaze.isInBounds(row - 1, col) && upDoor != null && !upDoor.isLocked());
        if (currentRoom.hasDoor(Direction.UP)) {
            //System.out.println("Up door state: " + upDoor.getState());

        } else {
            //System.out.println("Current room has no door in direction: UP");
        }

        // DOWN
        Door downDoor = currentRoom.getDoor(Direction.DOWN);
        //System.out.println("enable or disable???????: " + (myMaze.isInBounds(row - 1, col) && downDoor != null && !downDoor.isLocked()));
        myDownButton.setEnabled(myMaze.isInBounds(row + 1, col) && downDoor != null && !downDoor.isLocked());
        if (currentRoom.hasDoor(Direction.DOWN)) {
            //System.out.println("Down door state: " + downDoor.getState());

        } else {
            //System.out.println("Current room has no door in direction: DOWN");
        }

        // LEFT
        Door leftDoor = currentRoom.getDoor(Direction.LEFT);
        myLeftButton.setEnabled(myMaze.isInBounds(row, col - 1) && leftDoor != null && !leftDoor.isLocked());
        if (currentRoom.hasDoor(Direction.LEFT)) {
            //System.out.println("Left door state: " + leftDoor.getState());

        } else {
            //System.out.println("Current room has no door in direction: LEFT");
        }

        // RIGHT
        Door rightDoor = currentRoom.getDoor(Direction.RIGHT);
        myRightButton.setEnabled(myMaze.isInBounds(row, col + 1) && rightDoor != null && !rightDoor.isLocked());
        if (currentRoom.hasDoor(Direction.RIGHT)) {
            //System.out.println("Right door state: " + rightDoor.getState());

        } else {
            //System.out.println("Current room has no door in direction: RIGHT");
        }
    }

//    /**
//     * Updates the pipe icon at the specified location to the locked (greyed-out) version.
//     *
//     * @param theRow the row of the pipe to update
//     * @param theCol the column of the pipe to update
//     */
//    public void updatePipeToLocked(final Direction theDir, int theRow, int theCol) {
//        System.out.println("inside updatePipeToLocked");
//        System.out.println("theRow theCol location: " + theRow + ", " + theCol);
//
//        //+1 -1 is not the right logic here figure something else out
//        switch (theDir) {
//            case DOWN -> theRow += 1;  // one row down
//            case UP -> theRow -= 1;  // one row up
//            case LEFT  -> theCol -= 1;  // one column left
//            case RIGHT  -> theCol += 1;  // one column right
//            default -> {
//                // optional: handle unexpected directions or do nothing
//            }
//        }
//
//        System.out.println("theRow theCol after +1 or -1: " + theRow + ", " + theCol);
//
//        final JLabel label = myMazeIconsGrid[theRow][theCol];
//        final Icon icon = label.getIcon();
//
//        if (icon instanceof ImageIcon) {
//            System.out.println("inside icon instanceof ImageIcon");
//            final ImageIcon imageIcon = (ImageIcon) icon;
//            final String description = imageIcon.getDescription();
//            System.out.println("description: " + description);
//
//            if ("icons/VerticalGreenPipe".equals(description)) {
//                System.out.println("Inside verticalGreenPipe");
//                ImageIcon locked = new ImageIcon("icons/VerticalGrayPipe.png");
//                locked.setDescription("icons/VerticalGrayPipe");
//                Image scaled = locked.getImage().getScaledInstance(30, 65, Image.SCALE_SMOOTH);
//                locked = new ImageIcon(scaled);
//                locked.setDescription("icons/VerticalGrayPipe");
//                label.setIcon(locked);
//            } else if ("icons/HorizontalGreenPipe".equals(description)) {
//                System.out.println("Inside horizontalGreenPipe");
//                ImageIcon locked = new ImageIcon("icons/HorizontalGrayPipe.png");
//                locked.setDescription("icons/HorizontalGrayPipe");
//                Image scaled = locked.getImage().getScaledInstance(65, 30, Image.SCALE_SMOOTH);
//                locked = new ImageIcon(scaled);
//                locked.setDescription("icons/HorizontalGrayPipe");
//                label.setIcon(locked);
//            }
//        }
//    }

    /**
     * Initializes the maze visual grid with the player's starting position.
     * Saves the initial icon and sets the Mario icon on the grid.
     * Disab    les impossible initial movement directions.
     */
    public void initializeMazeContents() {
        int row = myMaze.getPlayer().getRow();
        int col = myMaze.getPlayer().getCol();
        myPreviousIcon = getScaledIcon("icons/redmushroom.png", 60, 60);
        myMazeIconsGrid[row][col].setIcon(myMarioIcon);
        myCurrentRoomIcon.setIcon(myPreviousIcon);

        myUpButton.setEnabled(false);
        myLeftButton.setEnabled(false);
        myDownButton.setEnabled(true);
        myRightButton.setEnabled(true);

        updateMovementButtons();

        myPreviousRow = row;
        myPreviousCol = col;
    }

    //TODO need?
    /**
     * Handles generic UI actions triggered by GUI components (e.g., buttons).
     * Should route the event to the appropriate game logic or UI update.
     *
     * @param theE The ActionEvent triggered by a user interaction.
     */
    public void actionPerformed(ActionEvent theE) {
        // handle UI actions
    }

    /**
     * Sets the controller for the GUI to delegate control logic.
     *
     * @param theController The GameController instance to connect with the view.
     */
    public void setController(final GameController theController) {
        myController = theController;
    }

    /**
     * Sets and displays a new trivia question and its answer options on the UI.
     * Enables the submit button for the user to provide an answer.
     *
     * @param theQuestion The TriviaQuestion object containing the question
     *                   and its answer options.
     */
    public void setQuestion(final TriviaQuestion theQuestion) {
        myCurrentQuestion = theQuestion;

        myQuestionLabel.setText("Question: " + theQuestion.getQuestionText());

        String[] options = theQuestion.getMultipleChoices();
        if (options.length >= 4) {
            myOptionA_Label.setText("A: " + options[0]);
            myOptionB_Label.setText("B: " + options[1]);
            myOptionC_Label.setText("C: " + options[2]);
            myOptionD_Label.setText("D: " + options[3]);
        }

        // Reset state
        //selectedAnswer = null;
        mySubmitButton.setEnabled(true); // Re-enable submit in case it was disabled
    }

    /**
     * Called when a bound property is changed. Used to update the view based on changes
     * in the model or controller. Implementation to be customized based on application logic.
     *
     * @param theEvt A PropertyChangeEvent object describing the event source and the property
     *              that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        // method implementation
    }

    //Can use for futher implementation to fix the menu bugs
//    public void closeWindow() {
//        if (myFrame != null) {
//            myFrame.dispose();
//        } else {
//            this.dispose(); // in case GameView *is* a JFrame
//        }
//    }

    /**
     * Sets the player for the game.
     *
     * @param thePlayer the Player object to be used in the game.
     */
    public void setPlayer(Player thePlayer) {
        myPlayer = thePlayer;
    }

    /**
     * Sets the maze for the game.
     *
     * @param theMaze the Maze object representing the game layout.
     */
    public void setMaze(Maze theMaze) {
        myMaze = theMaze;
    }

}
