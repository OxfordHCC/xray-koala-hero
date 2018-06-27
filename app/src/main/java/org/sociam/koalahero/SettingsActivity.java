package org.sociam.koalahero;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.sociam.koalahero.appsInspector.AppDisplayMode;
import org.sociam.koalahero.appsInspector.AppModel;

public class SettingsActivity extends AppCompatActivity {

    AppModel appModel;
    private RadioGroup radioDisplayModeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appModel = AppModel.getInstance();

        // Set the radio button to the current displaymode
        radioDisplayModeGroup = (RadioGroup) findViewById(R.id.settings_display_mode);
        AppDisplayMode displayMode = appModel.getDisplayMode();
        RadioButton b;
        switch (displayMode){
            case All:
                b = (RadioButton) findViewById(R.id.view_all);
                b.setChecked(true);
                break;
            case TOP_TEN:
                b = (RadioButton) findViewById(R.id.view_top_10);
                b.setChecked(true);
                break;
            case SELECTED:
                b = (RadioButton) findViewById(R.id.view_selected);
                b.setChecked(true);
                break;
        }
    }

    public void save(View veiw){
        int checkedId = radioDisplayModeGroup.getCheckedRadioButtonId();

        Intent intent = new Intent();

        switch( checkedId ){
            case R.id.view_top_10:
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TOP 10");
                intent.putExtra("DISPLAY_MODE","TOP_10");
                setResult(RESULT_OK,intent);
                break;
            case R.id.view_all:
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ALL");
                intent.putExtra("DISPLAY_MODE","ALL");
                setResult(RESULT_OK,intent);
                break;
            case R.id.view_selected:
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SELECTED");
                intent.putExtra("DISPLAY_MODE","SELECTED");
                setResult(RESULT_OK,intent);
                break;
            default:
                setResult(RESULT_CANCELED,intent);

        }

        finish();
    }




//    public void setupDisplayModeListeners(){
//
//
//
//        // Setup listeners
//        radioDisplayModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // checkedId is the RadioButton selected
//
//                switch( checkedId ){
//                    case R.id.view_top_10:
//                        appModel.setDisplayMode(AppDisplayMode.TOP_TEN);
//                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TOP 10");
//                        break;
//                    case R.id.view_all:
//                        appModel.setDisplayMode(AppDisplayMode.All);
//                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ALL");
//                        break;
//                    case R.id.view_selected:
//                        appModel.setDisplayMode(AppDisplayMode.SELECTED);
//                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SELECTED");
//                        break;
//
//                }
//
//            }
//        });
//
//    }

}
