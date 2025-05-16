package View;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import Model.*;

import Model.TriviaQuestion;

//should this be extends or inherits?
public class GameView extends JFrame implements PropertyChangeListener {

    public static void main(String[] args) {
        //EventQueue.invokeLater(new Runnable() {})
        final GameView mainPanel = new GameView();
        final Dimension frameSize = new Dimension(500, 500);

        //line to add property chagne liseneters

        final JFrame window = new JFrame("1UP Trivia!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(mainPanel);

        //wondow.setmenu bar to add menu bar bar, calls method

        window.setSize(frameSize);
        window.pack();
        window.setVisible(true);

    }

    //private final JFrame myFrame;


    //JLabels for room panel
    private final JLabel myUpLabel, myDownLabel, myLeftLabel, myRightLabel, myRoomPlayer;
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

        //to add an icon for game
//        final JLabel iconLabel = new JLabel();
//        final ImageIcon image = new ImageIcon("img.png");
//        iconLabel.setIcon(image);
//        myFrame.setIconImage(image.getImage());

        //menu bar here

        //labels, buttons, text fields

        //Room Panel components
        myUpLabel = new JLabel("Up");
        myDownLabel = new JLabel("Down");
        myLeftLabel = new JLabel("Left");
        myRightLabel = new JLabel("Right");
        myRoomPlayer = new JLabel("Room Player"); //this will be an image

        myUpButton =  new JButton("Up");
        myDownButton = new JButton("Down");
        myLeftButton = new JButton("Left");
        myRightButton = new JButton("Right");


        //Questions Panel components
        myQuestionLabel = new JLabel("Question");
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

        myA_Room = new JLabel("");
        myB_Room = new JLabel("");
        myC_Room = new JLabel("");
        myD_Room = new JLabel("");
        myE_Room = new JLabel("");
        myF_Room = new JLabel("");
        myG_Room = new JLabel("");
        myH_Room = new JLabel("");
        myI_Room = new JLabel("");
        myJ_Room = new JLabel("");
        myK_Room = new JLabel("");
        myL_Room = new JLabel("");
        myM_Room = new JLabel("");
        myN_Room = new JLabel("");
        myO_Room = new JLabel("");
        myP_Room = new JLabel("");

        JPanel roomPanel = createRoomPanel();
        JPanel questionsPanel = createQuestionsPanel();
        JPanel mazePanel = createMazePanel();

        add(roomPanel, BorderLayout.CENTER);
        add(questionsPanel, BorderLayout.SOUTH);
        add(mazePanel, BorderLayout.EAST);

        //addListeners()
    }

    private JPanel createRoomPanel() {
        final JPanel roomPanel = new JPanel(); //formatting layout goes in the ()

        roomPanel.add(myUpLabel);
        roomPanel.add(myDownLabel);
        roomPanel.add(myLeftLabel);
        roomPanel.add(myRightLabel);

        roomPanel.add(myUpButton);
        roomPanel.add(myDownButton);
        roomPanel.add(myLeftButton);
        roomPanel.add(myRightButton);

        roomPanel.add(myRoomPlayer);

        return roomPanel;
    }

    private JPanel createQuestionsPanel() {
        final JPanel questionsPanel = new JPanel(); //formatting layout goes in the ()

        questionsPanel.add(myQuestionLabel);

        questionsPanel.add(myOptionA_Label);
        questionsPanel.add(myOptionB_Label);
        questionsPanel.add(myOptionC_Label);
        questionsPanel.add(myOptionD_Label);

        questionsPanel.add(myA_Button);
        questionsPanel.add(myB_Button);
        questionsPanel.add(myC_Button);
        questionsPanel.add(myD_Button);

        questionsPanel.add(mySubmitButton);

        return questionsPanel;
    }

    private JPanel createMazePanel() {
        final JPanel mazePanel = new JPanel();

        mazePanel.add(myStartImage);
        mazePanel.add(myEndImage);
        mazePanel.add(myMazePlayer);
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



//    private void createMenuBar(JFrame theWindow) {
//        TriviaMenu menu = new TriviaMenu(
//                theWindow,
//                () -> ModelgetGameInstance().startGame();
//        )
//    }

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
