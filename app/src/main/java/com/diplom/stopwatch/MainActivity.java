package com.diplom.stopwatch;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button startButton, stopButton, resetButton;

    private Handler handler = new Handler();
    private long startTime = 0L;
    private long elapsedTime = 0L; //dsdsd

    private boolean isRunning = false;

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long timeInMillis = System.currentTimeMillis() - startTime;
            int seconds = (int) (timeInMillis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            textView.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));

            if (isRunning) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        startButton = findViewById(R.id.button);
        stopButton = findViewById(R.id.button2);
        resetButton = findViewById(R.id.button3);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - elapsedTime;
                    handler.post(updateTimer);
                    isRunning = true;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    handler.removeCallbacks(updateTimer);
                    isRunning = false;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapsedTime = 0L;
                startTime = 0L;
                textView.setText("00 : 00 : 00");
                if (isRunning) {
                    handler.removeCallbacks(updateTimer);
                    isRunning = false;
                }
            }
        });
    }
}
