package org.sociam.koalahero.audio;

import android.media.AudioRecord;

import java.util.ArrayList;
import java.util.List;

public class AudioFileStore {


    private static AudioFileStore audioFileStore;

    public static AudioFileStore getInstance(){
        if( audioFileStore == null) audioFileStore = new AudioFileStore();
        return audioFileStore;
    }

    private AudioFileStore(){

        // Load from file
        load();

    }

    private List<AudioRecording> files = new ArrayList<AudioRecording>();

    public void addNew( AudioRecording ar ){
        files.add(ar);
    }

    public int getNumberFiles(){
        return files.size();
    }

    public AudioRecording get( int i){
        return files.get(i);
    }



    public boolean save(){

        return true;
    }

    public boolean load(){

        return true;
    }

}
