package my.edu.tarc.armenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import my.edu.tarc.armenu.Login.loginFunction;

public class SplashScreen extends Activity {
    private ProgressBar progressBar;
    private TextView progressText;
    private int nProgressStatus = 0;
    private Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.progressview);
        mhandler.postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(getApplicationContext(), loginFunction.class);
                startActivity(intent);
                finish();


            }


        }, 3100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (nProgressStatus < 100) {
                    nProgressStatus++;
                    android.os.SystemClock.sleep(30);
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(nProgressStatus);
                            progressText.setText("Loading " + nProgressStatus + "%" );
                        }
                    });
                }
            }
        }).start();


    }

}