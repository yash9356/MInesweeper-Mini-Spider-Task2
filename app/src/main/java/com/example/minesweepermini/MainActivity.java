package com.example.minesweepermini;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.minesweepermini.model.MinesweeperModel;
import com.google.android.material.snackbar.Snackbar;

import com.example.minesweepermini.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    Vibrator vibrator;
    public int point1=0;
    public int Totflags,amine;
    private TextView numflag,points,High_Score;
    private Chronometer chronometer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.snackbar);

        ImageButton btnRestart = (ImageButton) findViewById(R.id.btnRestart);
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        Totflags=MinesweeperModel.getInstance().countMines();
        numflag=findViewById(R.id.numflag);
        numflag.setText(Integer.toString(Totflags));
        points=findViewById(R.id.points);
        High_Score=findViewById(R.id.highscore);

        chronometer=findViewById(R.id.chrono);
        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.setCountDown(false);
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                amine=MinesweeperModel.getInstance().Points();
                Totflags=MinesweeperModel.getInstance().countMines();
                point1=(Totflags-amine)*10;
                points.setText("Points:"+Integer.toString(point1));
                numflag.setText(Integer.toString(amine));
            }
        });

        SharedPreferences settings=getSharedPreferences("Quiz_DATA", Context.MODE_PRIVATE);
        int highscore2=settings.getInt("HIGH_SCORE",0);
        if(point1>highscore2){
            High_Score.setText("High Score:"+Integer.toString(point1));
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE",point1);
            editor.commit();
        }
        else {
            High_Score.setText("High Score:"+Integer.toString(highscore2));
        }




        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings=getSharedPreferences("Quiz_DATA", Context.MODE_PRIVATE);
                amine=MinesweeperModel.getInstance().Points();
                Totflags=MinesweeperModel.getInstance().countMines();
                point1=(Totflags-amine)*10;
                int highscore2=settings.getInt("HIGH_SCORE",0);
                if(point1>highscore2){
                    High_Score.setText("High Score:"+Integer.toString(point1));
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putInt("HIGH_SCORE",point1);
                    editor.commit();
                }
                else {
                    High_Score.setText("High Score:"+Integer.toString(highscore2));
                }

                MinesweeperModel.getInstance().cleanBoard();
                MinesweeperModel.getInstance().setMines();
                MinesweeperModel.getInstance().setMineCount();
                point1=0;
                numflag.setText(Integer.toString(point1));
                Totflags=MinesweeperModel.getInstance().countMines();

                Snackbar restartSnackbar = Snackbar.make(linearLayout, "Game restarted", Snackbar.LENGTH_LONG);
                restartSnackbar.show();
            }
        });

        ImageButton btnFlag = (ImageButton) findViewById(R.id.btnFlag);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Function that sets touches to place flags

                MinesweeperModel.getInstance().actionFlag();


                Snackbar flagSnackbar = Snackbar.make(linearLayout, "Flagging on", Snackbar.LENGTH_LONG);
                flagSnackbar.show();
            }
        });

        ImageButton btnReveal = (ImageButton) findViewById(R.id.btnReveal);
        btnReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Function that sets touches to open tiles
                MinesweeperModel.getInstance().actionReveal();

                Snackbar revealSnackbar = Snackbar.make(linearLayout, "Flagging off", Snackbar.LENGTH_LONG);
                revealSnackbar.show();
            }
        });


    }

    public void showSnackBarWithDelete(String msg) {
        SharedPreferences settings=getSharedPreferences("Quiz_DATA", Context.MODE_PRIVATE);
        amine=MinesweeperModel.getInstance().Points();
        Totflags=MinesweeperModel.getInstance().countMines();
        point1=(Totflags-amine)*10;
        int highscore2=settings.getInt("HIGH_SCORE",0);
        if(point1>highscore2){
            High_Score.setText("High Score:"+Integer.toString(point1));
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE",point1);
            editor.commit();
        }
        else {
            High_Score.setText("High Score:"+Integer.toString(highscore2));
        }
        Snackbar.make(linearLayout, msg,
                Snackbar.LENGTH_LONG).setAction(
                "Restart", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Restart the game
                        MinesweeperModel.getInstance().cleanBoard();
                        MinesweeperModel.getInstance().setMines();
                        MinesweeperModel.getInstance().setMineCount();
                    }
                }
        ).show();
        if(MinesweeperModel.getInstance().gameLost()){
            vibrator.vibrate(1000);
            point1=0;
            numflag.setText(Integer.toString(point1));
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            amine=MinesweeperModel.getInstance().Points();
            point1=(Totflags-amine)*10;
            points.setText(Integer.toString(point1));
            numflag.setText(Integer.toString(amine));
        }
    }

}
