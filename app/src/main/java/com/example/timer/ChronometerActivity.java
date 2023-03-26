package com.example.timer;

import static com.example.timer.enums.ButtonState.START;
import static com.example.timer.enums.ButtonState.STOP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.timer.enums.ButtonState;

public class ChronometerActivity extends AppCompatActivity {

    ButtonState state;
    int second = 0;
    int minute = 0;
    int hour = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        if(savedInstanceState == null) {
            state = ButtonState.START;
        }else {
            state = ButtonState.from(String.valueOf(savedInstanceState.get("state")));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button startStopButton = findViewById(R.id.startStopButton);
        TextView chronoLabel = findViewById(R.id.chronoTime);

        startStopButton.setOnClickListener(view -> {

            switch (state){
                case START:
                    startStopButton.setText(STOP.getState());
                    state = STOP;
                    break;
                case STOP:
                    startStopButton.setText(START.getState());
                    state = START;
                    break;
            }

            
        });


    }
}