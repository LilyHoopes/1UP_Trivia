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
        playSoundAbsolute("sounds/Win.wav", 0.8f);  // full volume
    }

    /** Plays the lose sound. */
    public void playLoseSound() {
        playSoundAbsolute("sounds/Lose.wav", 0.8f);
    }

    /** Plays the incorrect answer sound. */
    public void playIncorrectSound() {
        playSoundAbsolute("sounds/Incorrect.wav", 0.7f);
    }

    /** Plays the correct answer sound. */
    public void playCorrectSound() {
        playSoundAbsolute("sounds/Correct.wav", 0.7f);
    }

    /** Plays the start game sound. */
    public void playStartSound() {
        playSoundAbsolute("sounds/StartGame.wav", 0.6f);
    }

    /** Plays the exit game sound. */
    public void playExitSound() {
        playSoundAbsolute("sounds/Exit.wav", 0.8f);
    }

    /** Plays the invalid move sound. */
    public void playNoMoveSound() {
        playSoundAbsolute("sounds/NoMove.wav", 0.8f);
    }

    /**
     * Plays a sound from the given file path at the given volume.
     *
     * @param filePath the path to the .wav file.
     * @param volume a float from 0.0 (mute) to 1.0 (full volume).
     */
    private void playSoundAbsolute(String filePath, float volume) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Apply volume control
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);
            } else {
                System.err.println("Volume control not supported for: " + filePath);
            }

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
