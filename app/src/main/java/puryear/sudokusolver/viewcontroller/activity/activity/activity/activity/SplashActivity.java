package puryear.sudokusolver.viewcontroller.activity.activity.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import puryear.sudokusolver.R;


import static puryear.sudokusolver.AppDefines.SPLASH_SCREEN_TIMER_MS;

/**
 * Created by puryear on 2/21/17.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread() {
            public void run() {
                try{
                    sleep(SPLASH_SCREEN_TIMER_MS);
                }catch(InterruptedException ie) {
                    ie.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
