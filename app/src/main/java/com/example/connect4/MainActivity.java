package com.example.connect4;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private class Cell {
        Button bt;
        int j;
        public Cell(Context THIS, int J){
            j = J;
            bt = new Button(THIS);
            bt.setLayoutParams(new LinearLayout.LayoutParams(buttonsize, buttonsize));
            bt.setTextSize(buttonsize/6);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = 5;
                    boolean found = false;
                    if (!finish) {
                        while (i >= 0 && !found) {
                            if (table[i][j].bt.getText() == "" && !finished()) {
                                turn = turn.equals("X") ? "O" : "X";
                                table[i][j].bt.setText(turn);
                                found = true;
                                finished();
                            } else {
                                i--;
                            }
                        }
                    }
                }
            });
        }
    }

    private void win(int i, int j, int di, int dj){
        for (int step=0; step<4; step++,i+=di, j+=dj){
            table[i][j].bt.setTextColor(Color.RED);
            finish = true;
        }
    }

    private boolean finished(int i, int j, int di, int dj){
        int iini =i;
        int jini=j;
        int step = 0;
        String ant = "";
        while (step<3 && i<6 && j<7 && i>=0 && j>=0){
            if (table[i][j].bt.getText().equals("") || !table[i][j].bt.getText().equals(ant)){
                step = 0;
                iini = i;
                jini = j;
                ant = table[iini][jini].bt.getText().toString();
            }else{
                step++;
            }
            i+=di;
            j+=dj;

        }
        if (step == 3){
            win(iini, jini, di, dj);
            return true;
        }
        return false;
    }

    private boolean finished(){
        for(int i = 0; i<6; i++){
            if (finished(i, 0, 0, 1)  ||  //hor
                finished(0, i, 1, 0)  ||  //vert
                finished(i, 0, 1, 1)  ||  //diagdret
                finished(0, i, 1, 1)  || //diagdret
                finished(i, 0, -1, -1)|| //diagesq
                finished(i, 6, 1, -1))   //diagesq
            return true;
        }
        return false;
    }



    Button btreset;
    Cell[][] table;
    LinearLayout linlay;
    LinearLayout linlay2;
    GridLayout gr;
    String turn;
    int buttonsize;
    boolean finish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        buttonsize = point.x/7;
        linlay = new LinearLayout(this);
        linlay.setGravity(Gravity.CENTER_HORIZONTAL);
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay2 = new LinearLayout(this);
        gr = new GridLayout(this);
        gr.setColumnCount(7);
        table= new Cell[6][7];
        for (int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                table[i][j]= new Cell(this, j);
                gr.addView(table[i][j].bt);
            }
        }
        turn="X";
        finish=false;
        btreset = new Button(this);
        btreset.setText("Restart");
        restart(btreset);
        linlay.addView(gr);
        linlay2.addView(btreset);
        linlay.addView(linlay2);
        setContentView(linlay);
    }

    private void restart(Button bt){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 7; j++) {
                        table[i][j].bt.setText("");
                        table[i][j].bt.setTextColor(Color.BLACK);
                        finish = false;

                    }
                }
            }
        });
    }
}