package com.github.satoshun.example.jobscheduler;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static int jobId = 0;

    private ComponentName componentName;
    private TestJobService testJobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runJob();
    }

    private void runJob() {
        componentName = new ComponentName(this, TestJobService.class);
        Intent intent = new Intent(this, TestJobService.class);
        intent.putExtra("handler", new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage");
                testJobService = (TestJobService) msg.obj;
                scheduleJob();
            }
        }));
        startService(intent);
    }

    private void scheduleJob() {
        findViewById(R.id.root).postDelayed(new Runnable() {
            @Override
            public void run() {
                scheduleJob();
                if (testJobService == null) {
                    return;
                }

                JobInfo.Builder builder = new JobInfo.Builder(jobId++, componentName);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                testJobService.scheduleJob(builder.build());
            }
        }, 5000);
    }
}
