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
        String value;
        int j;
        public Cell(Context THIS, int J){
            j = J;
            bt = new Button(THIS);
            value="";
            bt.setLayoutParams(new LinearLayout.LayoutParams(buttonsize, buttonsize));
            bt.setTextSize(buttonsize/6);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!finish) {
                        int i = 0;
                        boolean found = false;
                        while (i < 6 && !found) {
                            if (table[i][j].bt.getText() != "") {
                                found = true;
                                value = turn;
                                turn = turn == "X" ? "O" : "X";
                                if (i > 0) {
                                    table[i - 1][j].bt.setText(value);
                                    table[i - 1][j].value = value;
                                    finished(i - 1, j);
                                }
                            } else {
                                i++;
                            }
                        }
                        if (!found) {
                            value = turn;
                            turn = turn == "X" ? "O" : "X";
                            table[i - 1][j].bt.setText(value);
                            table[i - 1][j].value = value;
                            finished(i - 1, j);
                        }
                    }
                }
            });
        }
    }

    //modificar l'array pos amb el valor corresponent
    //d'on es troba el bt dintre del grid
    private void gridPosition(int[] pos){
        int aux = pos[0];
        if ( aux < 10){
            pos[0] = 0;
            pos[1] = aux;
        }else {
            pos[0] = aux/10;
            pos[1] = aux%10;
        }
    }

    private void win(int i, int j, int di, int dj){
        for (int step=0; step<4; step++,i+=di, j+=dj){
            table[i][j].bt.setTextColor(Color.RED);
        }
        finish = true;
    }

    private boolean finished(int i, int j, int di, int dj){
        int iini =i;
        int jini=j;
        for (int step=0; step<4; step++,i+=di, j+=dj){
            if ( i<6 && j<7 && i>=0 && j>=0){
                if (table[i][j].value.equals("") || !table[i][j].value.equals(table[iini][jini].value)){
                    return false;
                }
            }else {
                return false;
            }
        }
        win(iini, jini, di, dj);
        return true;
    }

    //bug: wining the 4 on
    private boolean finished(int ipos, int jpos){
        int[] movi={1,1,1,-1,-1,-1,0,0};
        int[] movj={-1,0,1,-1,0,1,1,-1};
        for (int d=0;d<8;d++) {
            if( finished(ipos,jpos,movi[d],movj[d]) ) return true;
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
            //table[i] = new Cell[3];
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
                        table[i][j].value = "";
                        finish = false;

                    }
                }
            }
        });
    }
}