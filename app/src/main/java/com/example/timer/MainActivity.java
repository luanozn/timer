package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button chronoButton = findViewById(R.id.chronoButton);
        Button timerButton = findViewById(R.id.timerButton);

        chronoButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChronometerActivity.class);
            startActivity(intent);
        });

        timerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        });

    }
}