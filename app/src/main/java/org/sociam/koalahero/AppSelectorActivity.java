package org.sociam.koalahero;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.sociam.koalahero.R;
import org.sociam.koalahero.additionalInfoActivities.AdditionalInfoCMSActivity;
import org.sociam.koalahero.additionalInfoActivities.AdditionalInfoForParentsActivity;
import org.sociam.koalahero.additionalInfoActivities.AdditionalInfoMapViewActivity;
import org.sociam.koalahero.additionalInfoActivities.AdditionalInfoTrackersActivity;
import org.sociam.koalahero.appsInspector.AppModel;
import org.sociam.koalahero.gridAdapters.AdditionalInformationAdapter;
import org.sociam.koalahero.gridAdapters.SelectionAdapter;

public class AppSelectorActivity extends AppCompatActivity {

   AppModel appModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_selector);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("App Selection");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appModel = AppModel.getInstance();
        final Context context = this;

        updateGrid();

        GridView gridview = (GridView) findViewById(R.id.selectionGridView);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println("Position: " + position);

                appModel.getAllInstalledApps().get(position).setIsSelectedToDisplay(!appModel.getAllInstalledApps().get(position).isSelectedToDisplay());

                updateGrid();
            }
        });
    }

    private void updateGrid(){
        GridView gridview = (GridView) findViewById(R.id.selectionGridView);
        gridview.setAdapter(new SelectionAdapter( this, appModel));
    }
}
