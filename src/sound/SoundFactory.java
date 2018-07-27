package sound;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eli on 20/09/2017.
 */
public class SoundFactory {
    private static final String[] soundFiles = {}; //TODO write down the sound files here
    private static SoundFactory instance;
    private List<Sound> sounds;

    public static SoundFactory getInstance() {
        if (instance == null) {
            instance = new SoundFactory();
        }
        return instance;
    }

    private SoundFactory() {
        this.sounds = new ArrayList<>();
        loadSounds();
    }

    public List<Sound> getSounds() {
        if (sounds.size() == 0) throw new Error("No sounds loaded");
        return sounds;
    }

    private void loadSounds() {
        Arrays.stream(soundFiles).forEach((a) -> {
            try {
                sounds.add(new Sound(a));
            } catch (SlickException e) {
                System.out.printf("Sound file %s not found\n", a);
            }
        });
    }
}
