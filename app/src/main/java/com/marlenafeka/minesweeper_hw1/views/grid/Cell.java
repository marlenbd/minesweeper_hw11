package com.marlenafeka.minesweeper_hw1.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.marlenafeka.minesweeper_hw1.GameEngine;
import com.marlenafeka.minesweeper_hw1.R;

public class Cell extends BaseCell implements View.OnClickListener, View.OnLongClickListener{

    public Cell(Context context, int x, int y) {
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override // Tell the measure to make square buttons
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        GameEngine.getInstance().click( getXPos(), getYPos() );
    }

    @Override
    public boolean onLongClick(View v) {
        GameEngine.getInstance().flag( getXPos(), getYPos() );

        return true;
    }

    @Override
    public void setRevealed() {
        super.setRevealed();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawButton(canvas);

        if( isFlagged() ) {
            drawFlag(canvas);
        } else if( isRevealed() && isBomb() && !isClicked() ) {
            drawNormalBomb(canvas);
        } else {
            if( isClicked() ) {
                if( getValue() == -1 ) {
                    drawBombExploted(canvas);
                } else {
                    drawNumber(canvas);
                }
            } else {
                drawButton(canvas);
            }
        }
    }

    private void drawFlag( Canvas canvas ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flag );
        drawable.setBounds( 0, 0, getWidth(), getHeight() );
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.button );
        drawable.setBounds( 0, 0, getWidth(), getHeight() );
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb );
        drawable.setBounds( 0, 0, getWidth(), getHeight() );
        drawable.draw(canvas);
    }

    private void drawBombExploted(Canvas canvas ) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bombexploted );
        drawable.setBounds( 0, 0, getWidth(), getHeight() );
        drawable.draw(canvas);
    }

    private void drawNumber( Canvas canvas ) {
        Drawable drawable = null;

        switch( getValue() ) {
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number8);
                break;
        }

        drawable.setBounds( 0, 0, getWidth(), getHeight() );
        drawable.draw(canvas);
    }
}
