package com.marlenafeka.minesweeper_hw1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marlenafeka.minesweeper_hw1.game.GameConfiguration;

import java.text.ParseException;

public class GameActivity extends AppCompatActivity {
    private int gameTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameTime = 0;
        // find which level to activate //
        GameConfiguration.Level level = GameConfiguration.Level.valueOf(getIntent().getStringExtra(GameConfiguration.GameIntentLevelKey));

        tickForEachGameRun();
        String myBombsText = "";

        switch (level) {
            case beginner:
                myBombsText = "5 bombs";
                GameEngine.setLevel(5, 10 ,10);
                break;
            case skilled:
                myBombsText = "10 bombs";
                GameEngine.setLevel(10, 10 ,10);
                break;
            case expert:
                myBombsText = "10 bombs";
                GameEngine.setLevel(10, 5 ,5);
                break;
        }

        GameEngine.getInstance().setRunning(true);
        GameEngine.getInstance().createGrid(this);

        setContentView(R.layout.activity_game);
        TextView myBombs = (( TextView)findViewById(R.id.textBombs));
        myBombs.setText(myBombsText);

        Button buttonSmiley = (Button)findViewById(R.id.buttonSmiley);
        buttonSmiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// New game ////
                GameEngine.getInstance().createGrid(GameActivity.this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameTime = 0;
    }

    private void tickForEachGameRun() {
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                try {
                    tick();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (GameEngine.getInstance().isRunning()) {
                    tickForEachGameRun();
                } else {
                    gameTime = 0;
                }
                return false;
            }
        });
        Message message = new Message();
        Bundle messageData = new Bundle();
        messageData.putString("time", "1");
        message.setData(messageData);
        handler.sendMessageDelayed(message, 1000);
    }

    private void tick() throws ParseException {
        gameTime++;
        GameEngine.getInstance().setGameTime(gameTime);

        TextView myTime = (TextView)findViewById(R.id.textTimer);
        myTime.setText("" + gameTime);
    }
}
