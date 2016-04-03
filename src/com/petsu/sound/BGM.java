package com.petsu.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.FileNotFoundException;

public class BGM {
    public static Player Player;
    public static Clip clip;
    public static int getvolume;

    public static void play(String filename) throws FileNotFoundException, JavaLayerException {
        File music = new File(filename);
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(music));
        } catch(Exception ignored) {}
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stop() {
        for (int i = 0; i < 50; i++) {
            volume(getvolume - i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        clip.close();
        volume(getvolume);
    }

    public static void volume(int volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        double gain = (double) volume / 100;
        getvolume = volume;
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}