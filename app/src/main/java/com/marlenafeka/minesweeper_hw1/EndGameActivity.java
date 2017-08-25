package com.marlenafeka.minesweeper_hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EndGameActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Toast.makeText(EndGameActivity.this, "Back button is disabled", Toast.LENGTH_SHORT).show();
    }

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

        TextView time = (TextView)findViewById(R.id.mytime);
            time.setText(GameActivity.time + " seconds");
    }
}
