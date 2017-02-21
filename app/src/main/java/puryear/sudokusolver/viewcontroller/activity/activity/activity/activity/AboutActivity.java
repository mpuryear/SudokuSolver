package puryear.sudokusolver.viewcontroller.activity.activity.activity.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import puryear.sudokusolver.R;

import static puryear.sudokusolver.AppDefines.ABOUT_TITLE;

/**
 * Created by puryear on 2/21/17.
 */
public class AboutActivity extends Dialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    public AboutActivity(Context context) {
        super(context);
        setTitle(ABOUT_TITLE);

    }
}