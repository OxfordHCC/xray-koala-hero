package org.sociam.koalahero.additionalInfoActivities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.sociam.koalahero.R;
import org.sociam.koalahero.appsInspector.App;
import org.sociam.koalahero.appsInspector.AppModel;
import org.sociam.koalahero.trackerMapper.TrackerMapperCompany;
import org.sociam.koalahero.xray.AppGenre;
import org.sociam.koalahero.xray.AppGenreHostInfo;
import org.sociam.koalahero.xray.XRayAPI;
import org.sociam.koalahero.xray.XRayJsonParser;
import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.sociam.koalahero.audio.AudioRecorder;

public class AdditionalInfoTrackersActivity extends AppCompatActivity {

    private AppModel appModel;
    private String packageName;
    private App selectedApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info_trackers);

        this.appModel = AppModel.getInstance();
        this.packageName = this.appModel.selectedAppPackageName;
        this.selectedApp = this.appModel.getApp(this.packageName);

        AudioRecorder.getINSTANCE(this).updateRecordingUI(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Who is Watching?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        XRayAPI xRayAPI = XRayAPI.getInstance();

        HashMap<AppGenre, AppGenreHostInfo> appGenreHostInfos = xRayAPI.readGenreHostInfo(getApplicationContext());

        HashMap<String, TrackerMapperCompany> companyMap = this.selectedApp.companies;

        ArrayList<TrackerMapperCompany> companyNames = new ArrayList<>(companyMap.values());

        ArrayList<String> hostNames = this.selectedApp.getxRayAppInfo().hosts;

        AppGenreHostInfo thisAppsGenreInfo = appGenreHostInfos.get(this.selectedApp.getxRayAppInfo().appStoreInfo.getAppGenre());


        TextView thisAppHostCount = (TextView) findViewById(R.id.thisAppValueTextView);
        TextView genreAvgHostCount = (TextView) findViewById(R.id.genreAverageValue);
        TextView genreAvgHostLabel = (TextView) findViewById(R.id.genreAverageLabel);

        TextView appTitle = (TextView) findViewById(R.id.per_app_title);
        TextView devTitle = (TextView) findViewById(R.id.trackerDevNameTextView);

        ImageView appIcon = (ImageView) findViewById(R.id.per_app_icon);

        appTitle.setText(this.selectedApp.getxRayAppInfo().appStoreInfo.title);
        devTitle.setText(this.selectedApp.getxRayAppInfo().developerInfo.devName);

        thisAppHostCount.setText(String.valueOf(hostNames.size()));
        genreAvgHostCount.setText(String.valueOf((int) thisAppsGenreInfo.genreAvgHosts));
        genreAvgHostLabel.setText(thisAppsGenreInfo.getAppGenre().toLabel());


        ArrayList<Integer> barValues = new ArrayList<>();
        barValues.add(hostNames.size());
        barValues.add((int) thisAppsGenreInfo.genreAvgHosts);
        barValues.add(0);

        ArrayList<String> axisLabels = new ArrayList<String>(Arrays.asList("This App", "Genre Avg", "All Apps Avg"));

        BarData bd = this.buildBarData(barValues);
        this.buildHostBarChart(bd ,axisLabels);


        // Set information read from device
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(this.selectedApp.getxRayAppInfo().app,0);

            // App Icon
            Drawable icon = getPackageManager().getApplicationIcon(appInfo);
            appIcon.setImageDrawable(icon);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void buildHostBarChart(BarData barData, ArrayList<String> axisValues){
        BarChart barChart = (BarChart) findViewById(R.id.hostBarChart);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setLabelCount(barData.getEntryCount());
        barChart.setScaleEnabled(false);

        barChart.invalidate();
    }

    private BarData buildBarData(ArrayList<Integer> data) {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        for(Integer point : data) {
            barEntries.add(new BarEntry(barEntries.size(), point));
        }
        BarDataSet bds = new BarDataSet(barEntries, "Host Counts");
        bds.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData bd = new BarData(bds);
        bd.setBarWidth(0.9f);

        return bd;
    }
}
