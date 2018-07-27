package sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eli on 20/09/2017.
 */
public class SoundManager {

    private int trackNum = 0;
    private List<Music> bgMusic;
    private List<Sound> sounds;
    private float globalVolume = 0.5f, musicVolume = 1f;

    public SoundManager() {
        SoundFactory soundFactory = SoundFactory.getInstance();
        bgMusic = new ArrayList<>();
        sounds = soundFactory.getSounds();
    }

    /**
     * Set the track to play in the background
     * @param trackNum the track number
     **/
    public void setTrack(int trackNum) {
        if (trackNum < bgMusic.size()) {
            this.trackNum = trackNum;
        }
    }

    /**
     * Set the track to this level's background music and play
     * @param level the level required
     */
    public void playBgMusic(int level) {
        level = level % bgMusic.size();
        setTrack(level);
        playBgMusic();
    }

    /**
     * Start playing the currently selected background track (call this at the start of the level)
     */
    public void playBgMusic() {
        if (bgMusic.size() == 0) return;
        float vol = globalVolume * musicVolume;
        bgMusic.get(trackNum).loop(1, vol);
    }

    public void playSound(String sound) {

    }

}
