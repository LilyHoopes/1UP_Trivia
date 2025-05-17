//package View;
//
//import javax.swing.*;
//
//public class TriviaMenu {
//
//    private final JMenuBar myMenuBar;
//
//    public TriviaMenu(JFrame window, Runnable startGameAction, Runnable resetGameAction, Runnable exitGameAction,
//                      Runnable saveAction, Runnable loadAction, Runnable aboutAction, Runnable rulesAction) {
//
//        myMenuBar = new JMenuBar();
//
//        //Create game menu
//        JMenu myGameMenu = new JMenu("Game");
//        JMenuItem myStartItem = new JMenuItem("Start");
//        JMenuItem myResetItem = new JMenuItem("Reset");
//        JMenuItem myExitItem = new JMenuItem("Exit");
//
//        myStartItem.addActionListener(event -> startGameAction.run());
//
//
////        myResetItem.addActionListener(event -> {
////            int confirm = JOptionPane.YES_NO_OPTION)
////            if (confirm == JOptionPane.YES_OPTION) {
////                Craps.getInstance().resetGame();
////                myRoll.setEnabled(false);
////                startItem.setEnabled(true);
////                resetLabels();
//
//
//        myExitItem.addActionListener(event -> {
//            int choice = JOptionPane.showConfirmDialog(
//                    window,
//                    "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION
//            );
//            if (choice == JOptionPane.YES_OPTION) exitGameAction.run();
//        });
//
//        myGameMenu.add(myStartItem);
//        myGameMenu.add(myResetItem);
//        myGameMenu.add(myExitItem);
//
//        //Create file menu
//        JMenu myFileMenu = new JMenu("File");
//        JMenuItem myFileSave = new JMenuItem("Save");
//        JMenuItem myFileLoad = new JMenuItem("Load");
//
//        myFileSave.addActionListener(event -> saveAction.run());
//        myFileLoad.addActionListener(event -> loadAction.run());
//
//        myFileMenu.add(myFileSave);
//        myFileMenu.add(myFileLoad);
//
//        //Create help menu
//        JMenu myHelpMenu = new JMenu("Help");
//        JMenuItem myAboutItem = new JMenuItem("About");
//        JMenuItem myRulesItem = new JMenuItem("Rules");
//
//        myAboutItem.addActionListener(event -> aboutAction.run());
//
//
//
//        myRulesItem.addActionListener(event -> rulesAction.run());
//
//        myHelpMenu.add(myAboutItem);
//        myHelpMenu.add(myRulesItem);
//
//        myMenuBar.add(myGameMenu);
//        myMenuBar.add(myFileMenu);
//        myMenuBar.add(myHelpMenu);
//    }
//
//    public JMenuBar getMenuBar() {
//        return myMenuBar;
//    }
//}
