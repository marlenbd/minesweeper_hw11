package com.marlenafeka.minesweeper_hw1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Button buttonBack = (Button)findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentIntro = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intentIntro);
            }
        });

        // Save the player's time
        int newTime = getIntent().getIntExtra("time", 0);
        String isWinner = getIntent().getStringExtra("winner"); // receives 'yes' or 'no'

        // Get the time label on Activity and set to player's time
        TextView timeLabel = (TextView)findViewById(R.id.mytime);
        timeLabel.setText(newTime + " seconds");

        // Get the record label on Activity
        TextView recordLabel = (TextView)findViewById(R.id.record);

        // Read the saved record for the level - default = 0
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int record = settings.getInt(GameEngine.getInstance().getLevel(), 0);

        // Check if the record was broken
        if( isWinner.equals("yes") ) {
            if( newTime < record || record == 0) {
                recordLabel.setText(newTime + " seconds");
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(GameEngine.getInstance().getLevel(), newTime);
                editor.commit();

                Toast.makeText(EndGameActivity.this, "You made a new record!!", Toast.LENGTH_SHORT).show();
            } else {
                recordLabel.setText(record + " seconds");
            }
        } else {
            if( record == 0 ) {
                recordLabel.setText("--");
            } else {
                recordLabel.setText(record + " seconds");
            }
            Toast.makeText(EndGameActivity.this, "You lose", Toast.LENGTH_SHORT).show();
        }
    }
}
