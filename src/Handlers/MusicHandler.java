package Handlers;

import World.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.net.URL;

public class MusicHandler {

    Clip currentClip;
    Level lastLevel;
    URL[] soundList = new URL[6];
    //LevelHandler levelHandler;

    public MusicHandler() {
        soundList[0] = getClass().getResource("/Assets/Sound/Soundtrack1.wav");
        soundList[3] = getClass().getResource("/Assets/Sound/Soundtrack4.wav");
        soundList[5] = getClass().getResource("/Assets/Sound/BossSoundtrack2.wav");

        /*

        soundList[2] = getClass().getResource("/sound/chimeDown.wav");
        soundList[3] = getClass().getResource("/sound/chimeUp.wav");
        soundList[4] = getClass().getResource("/sound/mergeSound.wav");
         */
    }

    public void getClip(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundList[i]);
            currentClip = AudioSystem.getClip();
            currentClip.open(ais);
        } catch(Exception e){
            System.out.println("Error loading sound");
        }
    }

    public void play() {
        currentClip.start();
        currentClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        currentClip.stop();
    }

}
