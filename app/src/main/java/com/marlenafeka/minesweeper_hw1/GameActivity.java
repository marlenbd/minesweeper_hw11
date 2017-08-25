package com.marlenafeka.minesweeper_hw1;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.ParseException;

public class GameActivity extends AppCompatActivity {
    public static int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // find which level to activate //
        String level = getIntent().getStringExtra("level");

        tickEndlessly();
        switch (level) {
            case "beginner":
                TextView myTime = (( TextView)findViewById(R.id.textBombs));
                myTime.setText("5 bombs");
                GameEngine.setLevel(5, 10 ,10);
                GameEngine.getInstance().createGrid(this);
                break;
            case "skilled":
                myTime = (( TextView)findViewById(R.id.textBombs));
                myTime.setText("10 bombs");
                GameEngine.setLevel(10, 10 ,10);
                GameEngine.getInstance().createGrid(this);
                break;
            case "expert":
                myTime = (( TextView)findViewById(R.id.textBombs));
                myTime.setText("10 bombs");
                GameEngine.setLevel(10, 5 ,5);
                GameEngine.getInstance().createGrid(this);
                break;
        }

        Button buttonSmiley = (Button)findViewById(R.id.buttonSmiley);
        buttonSmiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// New game ////
                GameEngine.getInstance().createGrid(GameActivity.this);
            }
        });
    }

    private void tickEndlessly() {
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                try {
                    tick();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tickEndlessly();
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
        TextView myTime = (TextView)findViewById(R.id.textTimer);
        myTime.setText("" + (((Number) NumberFormat.getInstance().parse(myTime.getText().toString())).intValue()+1));
        time = Integer.parseInt(myTime.getText().toString());
    }
}
