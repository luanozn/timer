package com.example.timer;

import static com.example.timer.enums.ButtonState.RUNNING;
import static com.example.timer.enums.ButtonState.STOPPED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timer.enums.ButtonState;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TimerActivity extends AppCompatActivity {

    ButtonState currentState;
    boolean alreadyRunnedOnce = false;
    int totalOfSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);

        Button startStopButton = findViewById(R.id.timerStartStop);
        Button zeroButton = findViewById(R.id.timerReset);

        TextView errorView = findViewById(R.id.errorLabel);

        List<EditText> fields = List.of(
                findViewById(R.id.hourInput),
                findViewById(R.id.minuteInput),
                findViewById(R.id.secondInput)
        );

        if (savedInstanceState == null) {
            currentState = ButtonState.STOPPED;
            totalOfSeconds = -1;
        } else {
            currentState = ButtonState.from(savedInstanceState.getString("state"));
            totalOfSeconds = savedInstanceState.getInt("seconds");
            startStopButton.setText(currentState.getMessage());
        }

        zeroButton.setOnClickListener(view -> {
            currentState = STOPPED;
            startStopButton.setText(STOPPED.getMessage());
            totalOfSeconds = -1;
            fields.forEach(field -> {
                field.setText(R.string.nothing);
                field.setFocusable(true);
                field.setFocusableInTouchMode(true);
                field.setClickable(true);
            });
        });


        startStopButton.setOnClickListener(click -> {
            if (
                    String.valueOf(fields.get(1).getText()).equals("") || String.valueOf(fields.get(2).getText()).equals("") ||
                    Integer.parseInt(String.valueOf(fields.get(1).getText())) >= 60 || Integer.parseInt(String.valueOf(fields.get(2).getText())) >= 60
            ) {
                errorView.setText(R.string.error_label);
            } else {
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
                if (!alreadyRunnedOnce) {
                    errorView.setText(R.string.nothing);
                    fields.forEach(field -> {
                        field.setFocusable(false);
                        field.setFocusableInTouchMode(false);
                        field.setClickable(false);
                    });
                    int hours = Integer.parseInt(String.valueOf(fields.get(0).getText()));
                    int minutes = Integer.parseInt(String.valueOf(fields.get(1).getText()));
                    int seconds = Integer.parseInt(String.valueOf(fields.get(2).getText()));

                    totalOfSeconds = (hours * 3600) + (minutes * 60) + seconds;

                    runTimer();
                }
                alreadyRunnedOnce = totalOfSeconds != -1;
            }
        });
    }

    private void runTimer() {
        final EditText hourText = findViewById(R.id.hourInput);
        final EditText minuteText = findViewById(R.id.minuteInput);
        final EditText secondText = findViewById(R.id.secondInput);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = totalOfSeconds / 3600;
                int minutes = (totalOfSeconds % 3600) / 60;
                int secs = totalOfSeconds % 60;

                if (currentState.equals(RUNNING)) {
                    if(totalOfSeconds >= 0) {
                        hourText.setText(String.format(Locale.getDefault(), "%02d", hours));
                        minuteText.setText(String.format(Locale.getDefault(), "%02d", minutes));
                        secondText.setText(String.format(Locale.getDefault(), "%02d", secs));
                        totalOfSeconds--;
                    } else {
                        totalOfSeconds = -1;
                    }
                }

                if(totalOfSeconds == 0) {
                    MediaPlayer player = MediaPlayer.create(TimerActivity.this, R.raw.finish_sound);
                    player.start();
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", totalOfSeconds);
        savedInstanceState.putString("state", String.valueOf(currentState));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}