package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.GameController;
import Model.*;
import Model.TriviaQuestion;

public class GameView extends JFrame implements PropertyChangeListener {

    private GameController myController;
    private TriviaQuestion currentQuestion;
    private String selectedAnswer = null;

    private final JFrame myFrame;
    private final Color SKY_BLUE = new Color(135, 206, 235);

    private final JLabel myCurrentRoomIcon; //JLabel for room panel
    private final JButton myUpButton, myDownButton, myLeftButton, myRightButton; //JButtons for room panel

    private final JLabel myQuestionLabel, myOptionA_Label, myOptionB_Label, myOptionC_Label, myOptionD_Label; //JLabels for the questions panel
    private final JButton myA_Button, myB_Button, myC_Button, myD_Button, mySubmitButton; //JButtons for questions panel

    //JLabels for the maze panel
    private final JLabel[][] myMazeIconsGrid = new JLabel[7][7];

    private final ImageIcon[] myRoomIcons = new ImageIcon[] {
            getScaledIcon("icons/greenegg.png", 60, 60),
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
            getScaledIcon("icons/redmushroom.png", 60, 60),
            getScaledIcon("icons/1upmushroom.png", 60, 60)
    };

    private ImageIcon myPreviousIcon;  // stores the last icon Mario replaced
    private int myPreviousRow = -1;    // previous player position
    private int myPreviousCol = -1;    // previous player position
    private final ImageIcon myMarioIcon = getScaledIcon("icons/P1Mario.png", 60, 60);

    //instance of maze
    private final Maze myMaze;

    //constructor for GameView
    public GameView(Maze theMaze) {

        myMaze = theMaze;

        myFrame = new JFrame("1UP Trivia");
        final Dimension frameSize = new Dimension(900, 800);

        myFrame.setSize(frameSize);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);

        final JLabel iconLabel = new JLabel();
        final ImageIcon image = new ImageIcon("icons/P1Mario.png");
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
        myUpButton.addActionListener(e -> handleMove(Direction.UP));
        myDownButton.addActionListener(e -> handleMove(Direction.DOWN));
        myLeftButton.addActionListener(e -> handleMove(Direction.LEFT));
        myRightButton.addActionListener(e -> handleMove(Direction.RIGHT));


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
        questionsPanel.setBackground(SKY_BLUE);

        myA_Button.addActionListener(e -> selectedAnswer = getOptionText(myOptionA_Label));
        myB_Button.addActionListener(e -> selectedAnswer = getOptionText(myOptionB_Label));
        myC_Button.addActionListener(e -> selectedAnswer = getOptionText(myOptionC_Label));
        myD_Button.addActionListener(e -> selectedAnswer = getOptionText(myOptionD_Label));

        mySubmitButton.addActionListener(e -> {
            if (selectedAnswer == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer first.");
                return;
            }

            // ✅ Check and capture correctness
            boolean correct = myController.checkAnswer(selectedAnswer);

            // ✅ Show result to user
            if (correct) {
                JOptionPane.showMessageDialog(this, "✅ Correct! You may now move.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Incorrect!\nCorrect answer: " + currentQuestion.getCorrectAnswer(),
                        "Wrong Answer", JOptionPane.ERROR_MESSAGE);
            }

            // Load next question
            TriviaQuestion next = myController.getCurrentQuestion();
            if (next != null) {
                setQuestion(next);
            } else {
                JOptionPane.showMessageDialog(this, "🎉 You've answered all questions!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                mySubmitButton.setEnabled(false);
            }

            selectedAnswer = null;
        });

    }

    private String getOptionText(JLabel label) {
        String text = label.getText();
        return text.substring(text.indexOf(":") + 1).trim();  // removes "A: " etc.
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

    private void setupKeyBindings(JPanel thePanel) {
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
                handleMove(Direction.UP);
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                handleMove(Direction.LEFT);
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                handleMove(Direction.DOWN);
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent theEvent) {
                handleMove(Direction.RIGHT);
            }
        });
    }

    private void handleMove(Direction theDirection) {
        Player player = myMaze.getPlayer();

        boolean moved = player.move(theDirection);

        if (moved) {
            int newRow = player.getRow() * 2;
            int newCol = player.getCol() * 2;

            System.out.println("Player moved " + theDirection);
            System.out.println("Current player position: " + newRow + ", " + newCol);

            // Restore the previous icon
            if (myPreviousRow != -1 && myPreviousCol != -1 && myPreviousIcon != null) {
                myMazeIconsGrid[myPreviousRow][myPreviousCol].setIcon(myPreviousIcon);
            }

            // Save the icon at the new location
            myPreviousIcon = (ImageIcon) myMazeIconsGrid[newRow][newCol].getIcon();

            // Set Mario icon at the new location
            myMazeIconsGrid[newRow][newCol].setIcon(myMarioIcon);
            myCurrentRoomIcon.setIcon(myPreviousIcon);

            // Update previous position
            myPreviousRow = newRow;
            myPreviousCol = newCol;

            // ✅ Update movement buttons based on new position
            updateMovementButtons();
        } else {
            System.out.println("Move blocked in direction: " + theDirection);
        }
    }

    private void updateMovementButtons() {
        Player player = myMaze.getPlayer();
        Room currentRoom = myMaze.getCurrentRoom();

        int row = player.getRow();
        int col = player.getCol();

        // UP
        Door upDoor = currentRoom.getDoor(Direction.UP);
        myUpButton.setEnabled(myMaze.isInBounds(row - 1, col) && upDoor != null && !upDoor.isLocked());

        // DOWN
        Door downDoor = currentRoom.getDoor(Direction.DOWN);
        myDownButton.setEnabled(myMaze.isInBounds(row + 1, col) && downDoor != null && !downDoor.isLocked());

        // LEFT
        Door leftDoor = currentRoom.getDoor(Direction.LEFT);
        myLeftButton.setEnabled(myMaze.isInBounds(row, col - 1) && leftDoor != null && !leftDoor.isLocked());

        // RIGHT
        Door rightDoor = currentRoom.getDoor(Direction.RIGHT);
        myRightButton.setEnabled(myMaze.isInBounds(row, col + 1) && rightDoor != null && !rightDoor.isLocked());
    }


    //rename this method to set up start maybe?
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

    public void showWinMessage() { }

    public void showLossMessage() { }

    public void showInvalidMessage() { }

    public void actionPerformed(ActionEvent e) {
        // handle UI actions
    }

    public void setController(GameController theController) {
        this.myController = theController;
    }

    public void setQuestion(TriviaQuestion question) {
        this.currentQuestion = question;

        myQuestionLabel.setText("Question: " + question.getQuestionText());

        String[] options = question.getMultipleChoices();
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

    @Override
    public void propertyChange(PropertyChangeEvent theEvt) {

    }
}
