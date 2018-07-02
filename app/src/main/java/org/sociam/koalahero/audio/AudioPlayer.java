package org.sociam.koalahero.audio;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {


    private static AudioPlayer INSTANCE;

    public static AudioPlayer getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new AudioPlayer();
        return INSTANCE;
    }

    private AudioPlayer(){

    }


    private MediaPlayer mp = new MediaPlayer();

    boolean isLoaded = false;

    public boolean loadFile( String filePath ){

        try {
            mp.setDataSource(filePath);
            mp.prepare();

            isLoaded = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean play(){
        if( isLoaded && !mp.isPlaying() ) {
            mp.start();
            return true;
        }

        return false;
    }

    public boolean pause(){

        if( mp.isPlaying())
            mp.pause();
        return true;
    }

    public boolean stop(){

        mp.stop();

        return true;
    }

    public boolean setTime(int seconds){
        mp.seekTo(seconds*1000);

        return false;
    }

}
