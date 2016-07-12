package com.example.fortino.whatscarlolipop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;
/*Esta clase muestra el splash de la aplicación.
* @Author: César Eduardo Celedonio García
* @Version:07/07/2016
* */
public class splashActivity extends AppCompatActivity {
    private long tRetraso=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent newLayout;
                newLayout= new Intent(splashActivity.this, MainActivity.class);
                startActivity(newLayout);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
                splashActivity.this.finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,tRetraso);
    }
}

