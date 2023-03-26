package com.example.timer;

import static com.example.timer.enums.ButtonState.STOPPED;
import static com.example.timer.enums.ButtonState.RUNNING;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.timer.enums.ButtonState;

import java.util.Locale;
import java.util.Objects;

public class ChronometerActivity extends AppCompatActivity{

    ButtonState currentState;
    int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);

        Button startStopButton = findViewById(R.id.startStopButton);
        Button zeroButton = findViewById(R.id.resetButton);

        if (savedInstanceState == null) {
            currentState = ButtonState.STOPPED;
            seconds = 0;
        } else {
            currentState = ButtonState.from(savedInstanceState.getString("state"));
            seconds = savedInstanceState.getInt("seconds");
            startStopButton.setText(currentState.getMessage());
        }

        TextView chronoTime = findViewById(R.id.chronoTime);


        startStopButton.setOnClickListener(view -> {
            switch (currentState) {
                case STOPPED:
                    startStopButton.setText(RUNNING.getMessage());
                    currentState = RUNNING;
                    break;
                case RUNNING:
                    startStopButton.setText(STOPPED.getMessage());
                    currentState = STOPPED;
                    break;
            }
        });

        zeroButton.setOnClickListener(view -> {
            currentState = STOPPED;
            startStopButton.setText(STOPPED.getMessage());
            seconds = 0;
            chronoTime.setText(R.string.hh_mm_ss);
        });

        runTimer();
    }

    void runTimer() {
        final TextView chronoTime = findViewById(R.id.chronoTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, secs);

                if(currentState.equals(RUNNING)){
                    chronoTime.setText(time);
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putString("state", String.valueOf(currentState));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}