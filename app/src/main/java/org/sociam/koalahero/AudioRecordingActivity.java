package org.sociam.koalahero;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.sociam.koalahero.audio.AudioRecorder;
import org.sociam.koalahero.gridAdapters.AudioFilesAdapter;

import java.io.IOException;
import java.io.InputStream;

public class AudioRecordingActivity extends AppCompatActivity {

    AudioRecorder audioRecorder;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recording);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Audio Recording");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        audioRecorder = AudioRecorder.getINSTANCE(this);
        updateRecordingButton();

        ImageView recordButton = (ImageView) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioRecorder.toggleRecording();

                updateRecordingButton();
                updateScreen();

            }
        });


        ImageView deleteButton = (ImageView) findViewById(R.id.delete_all_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioRecorder.deleteRecordings();
                updateScreen();

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.file_list);
        //mRecyclerView.setHasFixedSize(true);

        // Use Linear Layout Manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        mAdapter = new AudioFilesAdapter( audioRecorder, this);
        mRecyclerView.setAdapter(mAdapter);
        updateScreen();
    }


    public void updateScreen(){

        TextView noFilesMessage = (TextView) findViewById(R.id.no_recordings_message);
        if( audioRecorder.getAudioFileStore().getNumberFiles() ==0 ){
            noFilesMessage.setVisibility(View.VISIBLE);
        } else {
            noFilesMessage.setVisibility(View.INVISIBLE);
        }

        mAdapter.notifyDataSetChanged();
        updateRecordingButton();
    }

    public void updateRecordingButton(){

        ImageView recordButton = (ImageView) findViewById(R.id.record_button);

        InputStream ims;

        try {
            if (audioRecorder.isRecording()) {
                ims = this.getAssets().open("stopRecording.png");
            } else {
                ims = this.getAssets().open("startRecording.png");
            }

            Drawable d = Drawable.createFromStream(ims, null);
            recordButton.setImageDrawable(d);
            ims.close();

        } catch (IOException e ){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
