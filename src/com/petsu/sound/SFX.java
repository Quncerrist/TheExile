package com.petsu.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SFX {
    public static Clip clip;

    public static void playSound(String filename) {
        File music = new File(filename);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(music));
        } catch(Exception ignored) {}
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stop() {
        clip.close();
    }

    public static void volume(int volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        double gain = (double) volume / 100;
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}