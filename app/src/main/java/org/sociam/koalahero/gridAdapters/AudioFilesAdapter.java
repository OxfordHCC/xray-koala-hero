package org.sociam.koalahero.gridAdapters;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.sociam.koalahero.AudioRecordingActivity;
import org.sociam.koalahero.R;

import java.io.File;

public class AudioFilesAdapter extends RecyclerView.Adapter<AudioFilesAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView fileTime, audioDuration;

        public ViewHolder(View v) {
            super(v);

            fileTime = (TextView) v.findViewById(R.id.file_time);
            audioDuration = (TextView) v.findViewById(R.id.audio_duration);


        }

    }



    private AudioRecordingActivity activity;
    private File recordingsDirectory;

    // constructor
    public AudioFilesAdapter(File directory , AudioRecordingActivity act) {
        this.recordingsDirectory = directory;
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

        holder.fileTime.setText( recordingsDirectory.listFiles()[position].getName() );

        MediaPlayer mp = new MediaPlayer();

        try {
            mp.setDataSource(recordingsDirectory.listFiles()[position].getAbsolutePath());

            int duration = mp.getDuration();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + duration);
            holder.audioDuration.setText( duration );
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recordingsDirectory.listFiles().length;
    }
}
