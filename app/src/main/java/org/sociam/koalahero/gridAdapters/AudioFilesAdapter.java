package org.sociam.koalahero.gridAdapters;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.sociam.koalahero.AudioRecordingActivity;
import org.sociam.koalahero.R;
import org.sociam.koalahero.audio.AudioFileStore;
import org.sociam.koalahero.audio.AudioRecorder;
import org.sociam.koalahero.audio.AudioRecording;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class AudioFilesAdapter extends RecyclerView.Adapter<AudioFilesAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView fileTime, audioDuration;
        public ImageView playButton;

        public ViewHolder(View v) {
            super(v);

            fileTime = (TextView) v.findViewById(R.id.file_time);
            audioDuration = (TextView) v.findViewById(R.id.audio_duration);
            playButton = (ImageView) v.findViewById(R.id.play_button);
        }

    }


    private AudioRecordingActivity activity;
    private AudioFileStore audioFileStore;
    private AudioRecorder audioRecorder;

    // constructor
    public AudioFilesAdapter(AudioRecorder audioRecorder , AudioRecordingActivity act) {
        this.audioFileStore = audioRecorder.getAudioFileStore();
        this.audioRecorder = audioRecorder;
        this.activity = act;
    }

    @Override
    public AudioFilesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_file_entry, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final AudioRecording ar = audioFileStore.get(position);

        Timestamp stamp = new Timestamp(ar.getTimeStarted());
        Date date = new Date(stamp.getTime());

        holder.fileTime.setText( date + "" );
        holder.audioDuration.setText( secondsToTime(ar.getDuration()/1000) );

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioRecorder.audioPlayer( ar.getFilePath() );
            }
        });

    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return audioFileStore.getNumberFiles();
    }


    public String secondsToTime( int seconds ){

        return (seconds/60) + ":" + String.format("%02d", (seconds%60));
    }
}
