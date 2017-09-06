package com.marlenafeka.minesweeper_hw1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;

import com.marlenafeka.minesweeper_hw1.game.GameConfiguration;
import com.marlenafeka.minesweeper_hw1.util.Generator;
import com.marlenafeka.minesweeper_hw1.util.PrintGrid;
import com.marlenafeka.minesweeper_hw1.views.grid.Cell;

public class GameEngine {
    private static GameEngine instance;
    private int gameTime = 0;
    private boolean isRunning = false;
    private GameConfiguration.Level level;


    public static int bomb_number = 10;
    public static int width = 10;
    public static int height = 10;

    private Context context;

    private Cell[][] MinesweeperGrid = new Cell[width][height];

    public static GameEngine getInstance() {
        if ( instance == null ){
            instance = new GameEngine();
        }
        return instance;
    }

    private GameEngine(){}

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void createGrid(Context context) {
        this.context  = context;
        // Create the grid and store it
        int[][] GeneratedGrid = Generator.generate(bomb_number, width, height);
        PrintGrid.print(GeneratedGrid, width, height);
        setGrid(context, GeneratedGrid);

    }

    private void setGrid( final Context context, final int[][] grid ) {
        for(int x = 0; x < width; x++ ) {
            for(int y = 0; y < height; y++ ) {
                if( MinesweeperGrid[x][y] == null ) {
                    MinesweeperGrid[x][y] = new Cell( context, x, y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % width;
        int y = position / width;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt( int x, int y ) {
        return MinesweeperGrid[x][y];
    }

    public void click( int x , int y ){
        if( x >= 0 && y >= 0 && x < width && y < height && !getCellAt(x,y).isClicked() ){
            getCellAt(x,y).setClicked();

            if( getCellAt(x,y).getValue() == 0 ){
                for( int xt = -1 ; xt <= 1 ; xt++ ){
                    for( int yt = -1 ; yt <= 1 ; yt++){
                        if( xt != yt ){
                            click(x + xt , y + yt);
                        }
                    }
                }
            }

            if( getCellAt(x,y).isBomb() ){
                onGameLost();
            }
        }
        checkEnd();
    }

    private boolean checkEnd() {
        int bombNotFound = bomb_number;
        int notRevealed = width * height;
        for(int x = 0; x < width; x++ ) {
            for(int y = 0; y < height; y++ ) {
                if( getCellAt(x,y).isRevealed() || getCellAt(x,y).isFlagged() ) {
                    notRevealed --;
                }
                if( getCellAt(x,y).isFlagged() && getCellAt(x,y).isBomb() ) {
                    bombNotFound--;
                }
            }
        }
        if( bombNotFound == 0) {//) && notRevealed != 0 ) {
            AlertDialog.Builder gameWonAlert = new AlertDialog.Builder(context);
            gameWonAlert.setMessage("You're Awesome!")
                    .setCancelable(false)
                    .setPositiveButton("I know..", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(context, EndGameActivity.class);
                            intent.putExtra("time", gameTime);
                            intent.putExtra("winner", "yes");
                            context.startActivity(intent);
                            //context.startActivity(new Intent(context, EndGameActivity.class));
                        }
                    });
            AlertDialog alert = gameWonAlert.create();
            alert.getWindow().setGravity(Gravity.TOP);
            alert.show();

            GameEngine.getInstance().setRunning(false);
        }
        return false;
    }

    public void flag( int x, int y ) {
        boolean isFlagged = getCellAt(x,y).isFlagged();
        getCellAt(x,y).setFlagged(!isFlagged);
        getCellAt(x,y).invalidate();

        checkEnd();
    }

    private void onGameLost() {

        for(int x = 0; x < width; x++ ) {
            for(int y = 0; y < height; y++ ) {
                getCellAt(x,y).setRevealed();
            }
        }

        AlertDialog.Builder gameLostAlert = new AlertDialog.Builder(context);
        gameLostAlert.setMessage("Game Over")
                .setCancelable(false)
                .setPositiveButton("Aaawwww..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(context, EndGameActivity.class);
                intent.putExtra("time", gameTime);
                intent.putExtra("winner", "no");
                context.startActivity(intent);
                //context.startActivity(new Intent(context, EndGameActivity.class).putExtra("time",""));
            }
        });
        AlertDialog alert = gameLostAlert.create();
        alert.getWindow().setGravity(Gravity.TOP);
        alert.show();

        GameEngine.getInstance().setRunning(false);
    }

    public static void setLevel(int bomb_number, int width, int height) {
        GameEngine.bomb_number = bomb_number;
        GameEngine.width = width;
        GameEngine.height = height;

        if( bomb_number == 5 ) {
            getInstance().level = GameConfiguration.Level.beginner;
        } else {
            if( width == 10 ) {
                getInstance().level = GameConfiguration.Level.skilled;
            } else {
                getInstance().level = GameConfiguration.Level.expert;
            }
        }
    }

    public String getLevel() {
        return level.name();
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

/*    public int getGameTime() {
        return gameTime;
    }*/
}
