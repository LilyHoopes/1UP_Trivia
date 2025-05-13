package View;

import Model.TriviaQuestion;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

//should this be extends or inherits?
public class GameView extends JFrame implements PropertyChangeListener {

    //JLabels for room panel
    private JLabel myUpLabel, myDownLabel, myLeftLabel, myRightLabel, myRoomPlayer;
    //JButtons for room panel
    private JButton myUpButton, myDownButton, myLeftButton, myRightButton;

    //JLabels for the questions panel
    private JLabel myQuestionLabel, myOptionA, myOptionB, myOptionC, myOptionD;
    private JButton myA_Button, myB_Button, myC_Button, myD_Button, mySubmitButton;

    //JLabels for the maze panel
    private JLabel myStartImage, myEndImage, myMazePlayer;
    private JLabel myA_Room, myB_Room, myC_Room, myD_Room, myE_Room, myF_Room, myG_Room,  myH_Room,
            myI_Room, myJ_Room, myK_Room, myL_Room, myM_Room, myN_Room, myO_Room, myP_Room;

    //add for loop to make a bunch of arrow aka door images to add to panel

    private JButton myMoveUp, myMoveDown, myMoveLeft, myMoveRight, mySubmitAnswer

    public GameView() {
        // constructor initializes components
        JPanel mainPanel = new JPanel();
        //mainPanel.set
    }

    private void createMenuBar() {
        // menu setup
    }

    private void createBoardPanel() {
        // visual board setup
    }

    private void createKeyPanel() {
        // trivia options panel
    }

    private void createRoomPanel() {
        // room icon and door visuals
    }

    public void updateGameDisplay(GameModel model) {
        // refresh display based on current model
    }

    public void displayQuestion(TriviaQuestion question) {
        // show question and options
    }

    public void showWinMessage() { }

    public void showLossMessage() { }

    public void showInvalidMessage() { }

    public void actionPerformed(ActionEvent e) {
        // handle UI actions
    }

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
