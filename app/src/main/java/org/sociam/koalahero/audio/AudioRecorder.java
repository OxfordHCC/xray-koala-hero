package org.sociam.koalahero.audio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.sociam.koalahero.AudioRecordingActivity;
import org.sociam.koalahero.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioRecorder {

    private static AudioRecorder INSTANCE;

    public static AudioRecorder getINSTANCE( Context con ) {
        if (INSTANCE == null) INSTANCE = new AudioRecorder( con );
        return INSTANCE;
    }

    private File recordingDir;
    private Context context;

    private AudioRecorder( Context con ){
        this.context = con;

        recordingDir = new File(con.getFilesDir().getPath() + "/recordings/" );
        if( !recordingDir.exists()) recordingDir.mkdir();


    }

    private MediaRecorder recorder;

    private List<String> files = new ArrayList<String>();

    private boolean isRecording = false;
    public boolean isRecording() {
        return isRecording;
    }

    public boolean startRecording(){

        recorder = new MediaRecorder();

        String filePath = recordingDir.getPath() + File.separator + "recording-" + System.currentTimeMillis();
        files.add(filePath);

        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile( filePath );
            recorder.prepare();
            recorder.start();
            isRecording = true;

            Toast.makeText(context, "Audio Recording Started " + filePath, Toast.LENGTH_SHORT).show();


        } catch (IOException e ){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean toggleRecording(){
        if( isRecording ) return stopRecording();
        else return startRecording();

    }

    public boolean stopRecording(){
        isRecording = false;
        recorder.stop();
        recorder.release();
        Toast.makeText(context, "Audio Recording Stopped", Toast.LENGTH_SHORT).show();

        audioPlayer(files.get(files.size()-1));

        return true;
    }


    public void audioPlayer(String filePath){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
            mp.setDataSource(filePath);

            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRecordings(){
        File[] recordings = recordingDir.listFiles();
        for( int i = 0 ; i < recordings.length; i++ ){
            recordings[i].delete();
        }
        Toast.makeText(context, "Audio Recordings Deleted", Toast.LENGTH_SHORT).show();
    }

    public void updateRecordingUI(Activity activity){

        ImageView recordingDot = (ImageView) activity.findViewById(R.id.recording_dot);
        if( recordingDot != null ) {
            recordingDot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fiber_manual_record_red));

            if (isRecording) {
                recordingDot.setVisibility(View.VISIBLE);
            } else {
                recordingDot.setVisibility(View.INVISIBLE);
            }

            recordingDot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, AudioRecordingActivity.class);
                    context.startActivity(intent);

                }
            });
        }
    }

}
