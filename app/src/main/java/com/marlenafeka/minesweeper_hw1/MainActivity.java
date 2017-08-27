package com.marlenafeka.minesweeper_hw1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marlenafeka.minesweeper_hw1.views.grid.Grid;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        Button buttonBeginner = (Button)findViewById(R.id.buttonBeginner);
        Button buttonSkilled = (Button)findViewById(R.id.buttonSkilled);
        Button buttonExpert = (Button)findViewById(R.id.buttonExpert);

        buttonBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBeginner = new Intent(MainActivity.this, GameActivity.class);
                intentBeginner.putExtra("level", "beginner");
                startActivity(intentBeginner);
            }
        });

        buttonSkilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSkilled = new Intent(MainActivity.this, GameActivity.class);
                intentSkilled.putExtra("level", "skilled");
                startActivity(intentSkilled);
            }
        });

        buttonExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentExpert = new Intent(MainActivity.this, GameActivity.class);
                intentExpert.putExtra("level", "expert");
                startActivity(intentExpert);
            }
        });
    }
}
