package Controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private static SoundManager instance;

    public SoundManager() {
    }

    /**
     * Retrieves the singleton instance of SoundManager.
     *
     * @return SoundManager instance
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playWinSound() {
        playSoundAbsolute("sounds/Win.wav");
    }

    public void playLoseSound() {
        playSoundAbsolute("sounds/Lose.wav");
    }

    public void playIncorrectSound() {
        playSoundAbsolute("sounds/Incorrect.wav");
    }

    public void playCorrectSound() {
        playSoundAbsolute("sounds/Correct.wav");
    }

    public void playStartSound() {
        playSoundAbsolute("sounds/StartGame.wav");
    }

    public void playExitSound() {
        playSoundAbsolute("sounds/Exit.wav");
    }

    public void playNoMoveSound() {
        playSoundAbsolute("sounds/NoMove.wav");
    }

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
