package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Button startStopButton = findViewById(R.id.timerStartStop);

        TextView errorView = findViewById(R.id.errorLabel);

        List<EditText> fields = List.of(
                findViewById(R.id.hourInput),
                findViewById(R.id.minuteInput),
                findViewById(R.id.secondInput)
        );

        startStopButton.setOnClickListener(click -> {
            if(Integer.parseInt(String.valueOf(fields.get(1).getText())) >= 60 || Integer.parseInt(String.valueOf(fields.get(2).getText())) >= 60){
                errorView.setText(R.string.error_label);
            } else {
                errorView.setText(R.string.nothing);
                fields.forEach(field -> {
                    field.setFocusable(false);
                    field.setFocusableInTouchMode(false);
                    field.setClickable(false);
                });
            }
        });
    }
}