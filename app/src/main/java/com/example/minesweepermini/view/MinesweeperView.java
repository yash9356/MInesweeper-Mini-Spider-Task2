package com.example.minesweepermini.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.minesweepermini.MainActivity;
import com.example.minesweepermini.R;
import com.example.minesweepermini.model.MinesweeperModel;

//import com.example.minesweepermini.MinesweeperModel;



public class MinesweeperView extends View  {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintBomb;
    private Paint paintOpen;

    private Bitmap untouched3;
    private Bitmap untouched;
    private Bitmap flagBitmap;
    private Bitmap bombBitmap;
    private Bitmap oneBitmap;
    private Bitmap twoBitmap;
    private Bitmap threeBitmap;
    private Bitmap fourBitmap;
    private Bitmap fiveBitmap;
    private Bitmap sixBitmap;
    private Bitmap sevenBitmap;
    private Bitmap eightBitmap;

    private boolean gameEnd = false;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.GREEN);
        paintBg.setStyle(Paint.Style.FILL);

        paintBomb = new Paint();

        paintLine = new Paint();
        paintLine.setStrokeWidth(4);
        paintLine.setColor(Color.CYAN);

        paintOpen = new Paint();
        paintOpen.setARGB(0, 0, 0, 0);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inSampleSize = 2;

        untouched3 = BitmapFactory.decodeResource(getResources(), R.drawable.untouched11, options);
        untouched = BitmapFactory.decodeResource(getResources(), R.drawable.untouched10, options);
        flagBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag8, options);
        bombBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb, options);
        oneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num1, options);
        twoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num2, options);
        threeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num3, options);
        fourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num4, options);
        fiveBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num5, options);
        sixBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num6, options);
        sevenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num7, options);
        eightBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.num8, options);

        MinesweeperModel.getInstance().setMines(1);
        MinesweeperModel.getInstance().setMineCount();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draws tiles
        drawGameArea(canvas);
        drawModel(canvas);
        drawCover(canvas);
    }

    private void drawModel(Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                float centerX = (i) * getWidth() / 8;
                float centerY = (j) * getHeight() / 8;

                if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.MINE) {
                    // draws bomb
                    canvas.drawBitmap(bombBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.ONE) {
                    canvas.drawBitmap(oneBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.TWO) {
                    canvas.drawBitmap(twoBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.THREE) {
                    canvas.drawBitmap(threeBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FOUR) {
                    canvas.drawBitmap(fourBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FIVE) {
                    canvas.drawBitmap(fiveBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.SIX) {
                    canvas.drawBitmap(sixBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.SEVEN) {
                    canvas.drawBitmap(sevenBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.EIGHT) {
                    canvas.drawBitmap(eightBitmap, centerX, centerY, paintBomb);
                } else {
                    canvas.drawBitmap(untouched, centerX, centerY, paintBomb);
                }
            }
        }
        Log.i("MODEL_TAG", "Bombs and Numbers displayed");
    }


    private void drawCover(Canvas canvas) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                float centerX = (i) * getWidth() / 8;
                float centerY = (j) * getHeight() / 8;

                if (MinesweeperModel.getInstance().getCoverContent(i, j) == MinesweeperModel.UNTOUCHED) {
                    canvas.drawRect(centerX, centerY, centerX + (getWidth() / 8), centerY + (getHeight() / 8), paintBg);
                    canvas.drawBitmap(untouched3,centerX,centerY,paintBomb);
                } else if (MinesweeperModel.getInstance().getCoverContent(i, j) == MinesweeperModel.TOUCHED) {
                    canvas.drawRect(centerX, centerY, centerX + getWidth() / 8, centerY + getHeight() / 8, paintOpen);
                    Log.i("COVER_TAG", "Tile [" + i + "] [" + j + "] is opened.");
                } else if (MinesweeperModel.getInstance().getCoverContent(i, j) == MinesweeperModel.FLAG) {
                    canvas.drawRect(centerX, centerY, centerX + getWidth() / 8, centerY + getHeight() / 8, paintBg);
                    canvas.drawBitmap(flagBitmap, centerX, centerY, paintBomb);
                    Log.i("COVER_TAG", "Tile [" + i + "] [" + j + "] is flagged.");
                }

            }
        }
        Log.i("MODEL_TAG", "Cover displayed");

        for(int i=1;i<8;i++){
            canvas.drawLine(0, i*getHeight() / 8, getWidth(), i*getHeight() / 8,
                    paintLine);
        }



        for(int i=1;i<8;i++){
            canvas.drawLine(i *getWidth() / 8, 0, i*getWidth() / 8, getHeight(),
                    paintLine);
        }

        Log.i("MODEL_TAG", "Lines drawn");
        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 6; j++) {

                float centerX = (i) * getWidth() / 8;
                float centerY = (j) * getHeight() / 8;

                if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.MINE) {
                    // draws bomb
                    short k=(short) 11;
                    MinesweeperModel.getInstance().setCoverContent(i,j,k);
                    canvas.drawBitmap(flagBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.ONE) {
                    canvas.drawBitmap(oneBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.TWO) {
                    canvas.drawBitmap(twoBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.THREE) {
                    canvas.drawBitmap(threeBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FOUR) {
                    canvas.drawBitmap(fourBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.FIVE) {
                    canvas.drawBitmap(fiveBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.SIX) {
                    canvas.drawBitmap(sixBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.SEVEN) {
                    canvas.drawBitmap(sevenBitmap, centerX, centerY, paintBomb);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j) == MinesweeperModel.EIGHT) {
                    canvas.drawBitmap(eightBitmap, centerX, centerY, paintBomb);
                } else {
                    canvas.drawBitmap(untouched, centerX, centerY, paintBomb);
                }
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameEnd) {
                MinesweeperModel.getInstance().cleanBoard();
                MinesweeperModel.getInstance().setMines(1);
                MinesweeperModel.getInstance().setMineCount();
                invalidate();
                gameEnd = false;
            } else {
                int tX = ((int) event.getX()) / (getWidth() / 8);
                int tY = ((int) event.getY()) / (getHeight() / 8);
                handleCoverTouch(tX, tY);
                winningModel();
            }

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void handleCoverTouch(int tX, int tY) {
        if (tX < 8 && tY < 8 &&
                MinesweeperModel.getInstance().getCoverContent(tX, tY) == MinesweeperModel.UNTOUCHED &&
                MinesweeperModel.getInstance().getActionType() == MinesweeperModel.REVEAL) {
            MinesweeperModel.getInstance().setCoverContent(tX, tY, MinesweeperModel.getInstance().getTouched());
        } else if (tX < 8 && tY < 8 &&
                MinesweeperModel.getInstance().getCoverContent(tX, tY) == MinesweeperModel.UNTOUCHED &&
                MinesweeperModel.getInstance().getActionType() == MinesweeperModel.FLAG) {
            MinesweeperModel.getInstance().setCoverContent(tX, tY, MinesweeperModel.getInstance().getFlagged());
        }
    }

    private void drawGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        // seven horizontal lines
        for(int k=1;k<8;k++){
            canvas.drawLine(0, k*getHeight() / 8, getWidth(), k*getHeight() / 8,
                    paintLine);
        }
        // seven vertical lines
        for(int k=1;k<8;k++){
            canvas.drawLine(k*getWidth() / 8, 0, k*getWidth() / 8, getHeight(),
                    paintLine);
        }
    }

    private void winningModel() {
        if (MinesweeperModel.getInstance().checkAllTiles() && !(MinesweeperModel.getInstance().gameLost())) {
            //game won
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "Congratulations you win!");
            gameEnd = true;
        } else if (MinesweeperModel.getInstance().gameLost()) {
            ((MainActivity) getContext()).showSnackBarWithDelete(
                    "Oh no! You lost!");
            gameEnd = true;
        } else if (!(MinesweeperModel.getInstance().checkAllTiles()) && !(MinesweeperModel.getInstance().gameLost())) {

        }
    }
}
