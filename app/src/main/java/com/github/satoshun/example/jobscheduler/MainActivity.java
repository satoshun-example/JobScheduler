package com.github.satoshun.example.jobscheduler;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runJob();
    }

    private void runJob() {
        componentName = new ComponentName(this, TestJobService.class);
        Intent intent = new Intent(this, TestJobService.class);
        startService(intent);
    }
}
