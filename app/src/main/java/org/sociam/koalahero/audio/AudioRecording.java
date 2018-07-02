package org.sociam.koalahero.audio;

import java.io.File;

public class AudioRecording {


    private String filePath;
    private long timeStarted;
    private int duration;

    public AudioRecording( String filePath ){
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isDownloaded(){
        return new File(filePath).exists();
    }

}
