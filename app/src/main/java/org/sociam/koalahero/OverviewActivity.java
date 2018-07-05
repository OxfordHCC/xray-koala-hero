package org.sociam.koalahero;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import org.sociam.koalahero.appsInspector.App;
import org.sociam.koalahero.appsInspector.AppModel;
import org.sociam.koalahero.xray.XRayAppInfo;

import java.lang.reflect.Field;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Overview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        appModel = AppModel.getInstance();


        for( int i = 0 ; i < 10; i++ ){

            try {

                ImageView iv = (ImageView) findViewById(getResId("icon_" + i, R.id.class));

                App app = appModel.getApp(i);
                XRayAppInfo xRayAppInfo = app.getxRayAppInfo();
                ApplicationInfo appInfo = getPackageManager().getApplicationInfo(xRayAppInfo.app, 0);

                // Icon
                Drawable icon = getPackageManager().getApplicationIcon(appInfo);
                iv.setImageDrawable(icon);

            } catch (PackageManager.NameNotFoundException e){

            }

        }


        WebView wv = (WebView) findViewById(R.id.web_view);
        wv.loadUrl("file:///android_asset/geo.html");
        //wv.loadUrl("https://www.google.co.uk");
        wv.getSettings().setJavaScriptEnabled(true);
    }

    private AppModel appModel;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
