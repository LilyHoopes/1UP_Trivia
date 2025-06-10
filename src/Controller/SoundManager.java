package Controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The SoundManager class handles all sound effects in the game,
 * such as win, lose, correct, and incorrect answer sounds.
 *
 * It uses a singleton pattern to make sure there's only one instance
 * managing sounds throughout the game.
 *
 * @author Komalpreet Dhaliwal
 * @author Lily Hoopes
 * @author Christiannel Maningat
 * @version 6/7/2025
 */

public class SoundManager {

    /** The single instance of SoundManager. */
    private static SoundManager instance;

    /** Default constructor. */
    public SoundManager() {
    }

    /**
     * Returns the single instance of SoundManager.
     *
     * @return the shared SoundManager instance.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /** Plays the win sound. */
    public void playWinSound() {
        playSoundAbsolute("sounds/Win.wav");
    }

    /** Plays the lose sound. */
    public void playLoseSound() {
        playSoundAbsolute("sounds/Lose.wav");
    }

    /** Plays the incorrect answer sound. */
    public void playIncorrectSound() {
        playSoundAbsolute("sounds/Incorrect.wav");
    }

    /** Plays the correct answer sound. */
    public void playCorrectSound() {
        playSoundAbsolute("sounds/Correct.wav");
    }

    /** Plays the start game sound. */
    public void playStartSound() {
        playSoundAbsolute("sounds/StartGame.wav");
    }

    /** Plays the exit game sound. */
    public void playExitSound() {
        playSoundAbsolute("sounds/Exit.wav");
    }

    /** Plays the invalid move sound. */
    public void playNoMoveSound() {
        playSoundAbsolute("sounds/NoMove.wav");
    }

    /**
     * Plays a sound from the given file path.
     *
     * @param filePath the path to the .wav file.
     */
    private void playSoundAbsolute(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
