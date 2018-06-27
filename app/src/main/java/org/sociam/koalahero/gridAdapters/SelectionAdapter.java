package org.sociam.koalahero.gridAdapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sociam.koalahero.R;
import org.sociam.koalahero.appsInspector.App;
import org.sociam.koalahero.appsInspector.AppModel;
import org.sociam.koalahero.xray.XRayAppInfo;

import java.io.IOException;
import java.io.InputStream;

public class SelectionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflator;

    private AppModel appModel;

    public SelectionAdapter(Context c, AppModel appModel) {
        context = c;
        layoutInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.appModel = appModel;

    }

    public int getCount() {
        return appModel.getTotalNumberApps();
    }

    public Object getItem(int position) {
        return appModel.getAllInstalledApps().get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        App app = appModel.getAllInstalledApps().get(position);
        XRayAppInfo xRayAppInfo = app.getxRayAppInfo();

        ImageView appIconView, selectedIcon;
        TextView appNameView;

        View grid;
        if( convertView == null) {
            grid = layoutInflator.inflate(R.layout.app_selection_grid_item, null);
        }else{
            grid = convertView;
        }


        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(xRayAppInfo.app,0);

            // Name
            appNameView =  (TextView) grid.findViewById(R.id.appName);
            if( xRayAppInfo.appStoreInfo.title.equals("Unknown"))
                appNameView.setText( context.getPackageManager().getApplicationLabel(appInfo) );
            else
                appNameView.setText( xRayAppInfo.appStoreInfo.title );

            // Icon
            appIconView =  (ImageView) grid.findViewById(R.id.appIcon);
            Drawable icon = context.getPackageManager().getApplicationIcon(appInfo);
            appIconView.setImageDrawable(icon);

            // ticked
            // Image
            selectedIcon =  (ImageView) grid.findViewById(R.id.selected_icon);

            try {
                InputStream ims;
                if( app.isSelectedToDisplay() ) ims = context.getAssets().open( "circleTick.png");
                else ims = context.getAssets().open( "circleNoTick.png");
                Drawable d = Drawable.createFromStream(ims, null);
                selectedIcon.setImageDrawable(d);
                ims.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
        catch (PackageManager.NameNotFoundException e) { e.printStackTrace(); }


        return grid;
    }

}
