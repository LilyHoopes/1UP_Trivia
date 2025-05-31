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

    private Direction currentDirection;
    private Door currentDoor;

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
    private final JFrame myFrame;

    /** Custom sky blue color used for background and UI elements. */
    private final Color SKY_BLUE = new Color(135, 206, 235);



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

    // Load Icons
    ImageIcon correctIcon = new ImageIcon("icons/thumbsUPMario.png");
    ImageIcon incorrectIcon = new ImageIcon("icons/sadMario.png");


    // BRICK ICON
    final ImageIcon longBrickIcon = getScaledIcon("icons/looongBrick.png", 400, 100);

    // --------Player position & Icon tracking--------

    /** Stores the icon of the room before character moved into it. Used to restore the room state. */
    private ImageIcon myPreviousIcon;  // stores the last icon Mario replaced

    /** The row index of character’s previous position. */
    private int myPreviousRow = -1;    // previous player position

    /** The column index of character’s previous position. */
    private int myPreviousCol = -1;    // previous player position

    /** Icon representing the Mario player avatar. */
    private final ImageIcon myMarioIcon = getScaledIcon("icons/P1Mario.png", 60, 60);
    // TODO: Include character selection code variable

    /** The Maze model object representing the current layout and room state. */
    private final Maze myMaze;

    /**
     * Constructs the main game window for the Trivia Maze.
     * Initializes the frame, panels, buttons, and listeners,
     * and displays the UI.
     *
     * @param theMaze the Maze model that this view will visualize.
     */
    public GameView(final Maze theMaze) {
        //constructor for GameView

        myMaze = theMaze;

        myFrame = new JFrame("1UP Trivia");
        final Dimension frameSize = new Dimension(900, 800);

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
        myUpButton.addActionListener(e -> myController.attemptMove(Direction.UP));
        myDownButton.addActionListener(e -> myController.attemptMove(Direction.DOWN));
        myLeftButton.addActionListener(e -> myController.attemptMove(Direction.LEFT));
        myRightButton.addActionListener(e -> myController.attemptMove(Direction.RIGHT));

        myCurrentRoomIcon = new JLabel(""); //where current room will be displayed
        //myCurrentRoomIcon.setIcon()

        //Questions Panel components
        myQuestionLabel = new JLabel("Question: ");
        myOptionA_Label = new JLabel("");
        myOptionB_Label = new JLabel("");
        myOptionC_Label = new JLabel("");
        myOptionD_Label = new JLabel("");

        myA_Button = new JButton("A");
        myB_Button = new JButton("B");
        myC_Button = new JButton("C");
        myD_Button = new JButton("D");
        mySubmitButton = new JButton("Submit");

        //Maze Panel Components
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                JLabel label = new JLabel();

            }
        }

        //myMazePlayer = new JLabel("");
        //myMazePlayer.setIcon(getScaledIcon("icons/P1Mario.png", 80, 80));


        //create the panels
        final JPanel mazePanel = createMazePanel();
        mazePanel.setPreferredSize(new Dimension(450, 450));
        final JPanel roomPanel = createRoomPanel();
        roomPanel.setPreferredSize(new Dimension(300, 300));

        final JPanel longBrickPanel = createLongBrickPanel();
        longBrickPanel.setPreferredSize(new Dimension(600, 60));

        final JPanel questionsPanel = createQuestionsPanel();
        questionsPanel.setPreferredSize(new Dimension(600, 150));

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
        // TODO: Adding longggg brick panel
        mainPanel.add(longBrickPanel);
        mainPanel.add(questionsPanel);

        myFrame.getContentPane().add(mainPanel);
        myFrame.pack();
        myFrame.setVisible(true);

        createMenuBar();

        //initially set some buttons to false here?

        //addListeners() method?

        myFrame.setBackground(SKY_BLUE);  // sky blue
        mazePanel.setBackground(SKY_BLUE);
        roomPanel.setBackground(SKY_BLUE);
        longBrickPanel.setBackground(SKY_BLUE); // TODO: Added!!
        questionsPanel.setBackground(SKY_BLUE);

        myA_Button.addActionListener(e -> clickedAnswerButton(myA_Button));
        myB_Button.addActionListener(e -> clickedAnswerButton(myB_Button));
        myC_Button.addActionListener(e -> clickedAnswerButton(myC_Button));
        myD_Button.addActionListener(e -> clickedAnswerButton(myD_Button));


        mySubmitButton.addActionListener(e -> {
            if (mySelectedAnswer == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer first.");
                return;
            }

            //TODO my selected answer is not correct!
            String userSelected = mySelectedAnswer;
            System.out.println("userSelected: " + userSelected);
            boolean correct = myController.checkAnswerAndMove(userSelected);
            System.out.println("correct boolean: " + correct);

            // Show result to user
            if (correct) {

                JOptionPane.showMessageDialog(this, "Correct! You may now move.",
                        "Correct!", JOptionPane.INFORMATION_MESSAGE, correctIcon);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Incorrect!\nCorrect answer: " + myCurrentQuestion.getCorrectAnswer(),
                        "Wrong Answer", JOptionPane.ERROR_MESSAGE, incorrectIcon);
            }

            //set color back to null once they click submit
            myA_Button.setBackground(null);
            myB_Button.setBackground(null);
            myC_Button.setBackground(null);
            myD_Button.setBackground(null);

            // Check and capture correctness


//            // Load next question
//            TriviaQuestion next = myController.getCurrentQuestion();
//            if (next != null) {
//                setQuestion(next);
//            } else {
//                JOptionPane.showMessageDialog(this, "🎉 You've answered all questions!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//                mySubmitButton.setEnabled(false);
//            }

            mySelectedAnswer = null;
        });

    }

    public void displayTriviaQuestion(TriviaQuestion theQuestion) {
        System.out.println("displayTriviaQuestion method question: " + theQuestion);
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

    //TODO change this to cute mario icons we select
    //TODO change this to radio buttons
    /**
     * Highlights the clicked answer button and stores the selected answer.
     *
     * @param theButton the JButton the user clicked.
     */
    private void clickedAnswerButton(final JButton theButton) {
        // Reset previous highlight
        if (myClickedButton != null) {
            myClickedButton.setBackground(null);
        }

        // Highlight new selection
        theButton.setBackground(Color.YELLOW);
        myClickedButton = theButton;

        // Determine which answer was selected
        String answer = null;
        if (theButton == myA_Button) answer = getOptionText(myOptionA_Label);
        else if (theButton == myB_Button) answer = getOptionText(myOptionB_Label);
        else if (theButton == myC_Button) answer = getOptionText(myOptionC_Label);
        else if (theButton == myD_Button) answer = getOptionText(myOptionD_Label);

        System.out.println("Answer selected: " + answer);
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
    private ImageIcon getScaledIcon(final String thePath, final int theWidth, final int theHeight) {
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
        myCurrentRoomIcon.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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

        questionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
                    label.setIcon(getScaledIcon("icons/HorizontalGreenPipe.png", 60, 35));
                } else if (row % 2 == 1 && col % 2 == 0) {
                    // Vertical pipe between rows
                    label.setIcon(getScaledIcon("icons/VerticalGreenPipe.png", 35, 60));
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

    private JPanel createLongBrickPanel() {
        final JPanel longBrickPanel = new JPanel();
        //longBrickPanel.setAlignmentX(Component.CENTER_ALIGNMENT); //centers long bwickk

        longBrickPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));


        // loading looong brick into icon
        //ImageIcon longBrickIcon = new ImageIcon("icons/looongBrick.png");

        // put icon into jlabel and add to panel
        JLabel brickLabel = new JLabel(longBrickIcon);
        longBrickPanel.add(brickLabel);

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

                ImageIcon icon = new ImageIcon(getScaledIcon("icons/hidingMario.png", 100, 100).getImage());

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
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                ImageIcon icon = new ImageIcon(getScaledIcon("icons/mysterybox.png", 100, 100).getImage());

                // About the game and creators
                JOptionPane.showMessageDialog(null,
                        "This is a recreation of the project Trivia Maze with a twist involving the Mario Bros" +
                                " universe,\ngetting renamed as the 1-UP Trivia Maze! This program was written in Java with" +
                                " versions 21 and 23.\nThis trivia maze game was written by Lily Hoopes, Christiannel Maningat," +
                                " and Komalpreet Dhaliwal.\nAll three who are students whom attend the University of Washington Tacoma.",
                        "About 1-UP Trivia Maze", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });

        rulesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                // The rules of trivia game

                // added 1up mushroom image to add to pane
                ImageIcon icon = new ImageIcon(getScaledIcon("icons/jumpingMario.png", 100, 100).getImage());


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

            }
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
                handleMoveThroughOpenDoor(Direction.UP);
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                handleMoveThroughOpenDoor(Direction.LEFT);
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                handleMoveThroughOpenDoor(Direction.DOWN);
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                handleMoveThroughOpenDoor(Direction.RIGHT);
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

        boolean moved = player.moveThroughOpenDoor(theDirection);

        if (moved) {

            int newRow = player.getRow() * 2;
            int newCol = player.getCol() * 2;

            System.out.println("Player moved " + theDirection);
            System.out.println("Current player position: " + newRow + ", " + newCol);

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

            //check if is game won or lost
            if (player.isGameWon()) {
                JOptionPane.showMessageDialog(this, "You won the game!");
            }
            if  (player.isGameLost()) {
                System.out.println("Game lost! this is in handleMove method");
                JOptionPane.showMessageDialog(this, "You're trapped! Game over!'");
            }


        } else {
            System.out.println("Move blocked in direction: " + theDirection);
        }
    }

    /**
     * Updates the state of movement buttons (UP, DOWN, LEFT, RIGHT).
     * Buttons are enabled only if the corresponding door exists and is unlocked.
     */
    private void updateMovementButtons() {
        Player player = myMaze.getPlayer();
        Room currentRoom = myMaze.getCurrentRoom();

        int row = player.getRow();
        int col = player.getCol();

        System.out.println("Current room position: " + row + ", " + col);

        // UP
        Door upDoor = currentRoom.getDoor(Direction.UP);
        myUpButton.setEnabled(myMaze.isInBounds(row - 1, col) && upDoor != null && !upDoor.isLocked());
        if (currentRoom.hasDoor(Direction.UP)) {
            System.out.println("Up door state: " + upDoor.getState());

        } else {
            System.out.println("Current room has no door in direction: UP");
        }

        // DOWN
        Door downDoor = currentRoom.getDoor(Direction.DOWN);
        myDownButton.setEnabled(myMaze.isInBounds(row + 1, col) && downDoor != null && !downDoor.isLocked());
        if (currentRoom.hasDoor(Direction.DOWN)) {
            System.out.println("Down door state: " + downDoor.getState());
        } else {
            System.out.println("Current room has no door in direction: DOWN");
        }

        // LEFT
        Door leftDoor = currentRoom.getDoor(Direction.LEFT);
        myLeftButton.setEnabled(myMaze.isInBounds(row, col - 1) && leftDoor != null && !leftDoor.isLocked());
        if (currentRoom.hasDoor(Direction.LEFT)) {
            System.out.println("Left door state: " + leftDoor.getState());

        } else {
            System.out.println("Current room has no door in direction: LEFT");
        }

        // RIGHT
        Door rightDoor = currentRoom.getDoor(Direction.RIGHT);
        myRightButton.setEnabled(myMaze.isInBounds(row, col + 1) && rightDoor != null && !rightDoor.isLocked());
        if (currentRoom.hasDoor(Direction.RIGHT)) {
            System.out.println("Right door state: " + rightDoor.getState());

        } else {
            System.out.println("Current room has no door in direction: RIGHT");
        }
    }

    /**
     * Initializes the maze visual grid with the player's starting position.
     * Saves the initial icon and sets the Mario icon on the grid.
     * Disables impossible initial movement directions.
     */
    // TODO: rename this method to set up start maybe?
    private void initializeMazeContents() {
        int row = myMaze.getPlayer().getRow();
        int col = myMaze.getPlayer().getCol();
        myPreviousIcon = (ImageIcon) myMazeIconsGrid[row][col].getIcon();
        myMazeIconsGrid[row][col].setIcon(myMarioIcon);
        myCurrentRoomIcon.setIcon(myPreviousIcon);

        myUpButton.setEnabled(false);
        myLeftButton.setEnabled(false);

        updateMovementButtons();

        myPreviousRow = row;
        myPreviousCol = col;
    }

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
        this.myController = theController;
    }

    /**
     * Sets and displays a new trivia question and its answer options on the UI.
     * Enables the submit button for the user to provide an answer.
     *
     * @param theQuestion The TriviaQuestion object containing the question
     *                   and its answer options.
     */
    public void setQuestion(final TriviaQuestion theQuestion) {
        this.myCurrentQuestion = theQuestion;

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
}
