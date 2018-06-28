package org.sociam.koalahero.additionalInfoActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.sociam.koalahero.R;
import org.sociam.koalahero.appsInspector.AppModel;
import org.sociam.koalahero.audio.AudioRecorder;

public class AdditionalInfoMapViewActivity extends AppCompatActivity {

    private String packageName;
    private AppModel appModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info_map_view);

        this.appModel = AppModel.getInstance();
        this.packageName = this.appModel.selectedAppPackageName;

        AudioRecorder.getINSTANCE(this).updateRecordingUI(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Where In The World");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
