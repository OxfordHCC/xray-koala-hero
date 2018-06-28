package org.sociam.koalahero.additionalInfoActivities;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.sociam.koalahero.R;
import org.sociam.koalahero.appsInspector.AppModel;
import org.sociam.koalahero.csm.CSMAPI;
import org.sociam.koalahero.csm.CSMAppInfo;
import org.sociam.koalahero.xray.XRayAppInfo;

public class AdditionalInfoCMSActivity extends AppCompatActivity {

    private String packageName;
    private CSMAPI csmapi;
    private AppModel appModel;
    private XRayAppInfo xRayAppInfo;
    private CSMAppInfo csmAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info_cms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Common Sense Media");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Context context = this;

        this.appModel = AppModel.getInstance();
        this.csmapi = CSMAPI.getInstance(getApplicationContext());

        this.packageName = appModel.selectedAppPackageName;
        this.xRayAppInfo = appModel.getApp(packageName).getxRayAppInfo();
        this.csmAppInfo = appModel.getApp(packageName).getCsmAppInfo();

        TextView titleTextView = (TextView) findViewById(R.id.per_app_title);
        TextView oneLinerTextView = (TextView) findViewById(R.id.oneLinerTextView);
        ImageView iconImageView = (ImageView) findViewById(R.id.per_app_icon);

        // Set information read from server.
        oneLinerTextView.setText(this.csmAppInfo.oneLiner);

        // Set information read from device
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(xRayAppInfo.app,0);

            // App Name
            titleTextView.setText(getPackageManager().getApplicationLabel(appInfo));
            // App Icon
            Drawable icon = getPackageManager().getApplicationIcon(appInfo);
            iconImageView.setImageDrawable(icon);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
