package View;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

//should this be extends or inherits?
public class GameView extends JFrame implements PropertyChangeListener {
    private JLabel myKeyInstruction, mySelectA, mySelectB, mySelectC, mySelectD;
    private JButton myMoveUp, myMoveDown, myMoveLeft, myMoveRight, mySubmitAnswer;
    private JMenuBar myGameMenu;
    private JMenuItem myStart, mySave, myLoad, myReset;
    private JMenuBar myHelp;
    private JMenuItem myAbout, myInstructions;

    public GameView() {
        // constructor initializes components
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
