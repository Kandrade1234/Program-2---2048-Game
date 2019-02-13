package com.example.a2048project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[4][4];
    private int [][] arr = new int[4][4];
    private Button buttonNewGame;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //storing all of my buttons in an array
        for(int i =0; i < 4; i++){
            for(int j = 0; j<4; j++){
                String buttonID = "button_" + i + j;    //since all my tiles start with button_xy we store the id on a string
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); //the id of the button
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setText("0");
                arr[i][j] = 0;
            }
        }
        Button buttonUp = findViewById(R.id.button_up);
        Button buttonDown = findViewById(R.id.button_down);
        Button buttonLeft = findViewById(R.id.button_left);
        Button buttonRight = findViewById(R.id.button_right);
        buttonNewGame = findViewById(R.id.button_newGame);
        generate(false);
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up();
                searchForWin();
                generate(true);
            }
        });
        buttonDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                down();
                searchForWin();
                generate(true);
            }
        });
        buttonLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                left();
                searchForWin();
                generate(true);
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                right();
                searchForWin();
                generate(true);
            }
        });


    }
    @Override
    public void onClick(View v){

    }
    public void generate(boolean singleGenerate){
        int row,row2, col,col2;
        Random rand = new Random();
        row = rand.nextInt(4);
        col = rand.nextInt(4);
        while(arr[row][col] != 0)
        {
            row = rand.nextInt(4);
            col = rand.nextInt(4);
        }
        int two_four = rand.nextInt(4);

        if (two_four == 0) {
            buttons[row][col].setText("2");
            arr[row][col] = 2;
        }
        else{
            buttons[row][col].setText("4");
            arr[row][col] = 4;
        }
        if(!singleGenerate) {
            row2 = rand.nextInt(4);
            col2 = rand.nextInt(4);
            if ((row == row2) && (col == col2)) {
                while ((row == row2) && (col == col2) && arr[row2][col2] != 0) {
                    row2 = rand.nextInt(3);
                    col2 = rand.nextInt(3);
                }
            }
            two_four = rand.nextInt(4);
            if (two_four > 0) {
                buttons[row2][col2].setText("2");
                arr[row2][col2] = 2;

            } else {
                buttons[row2][col2].setText("4");
                arr[row2][col2] = 4;
            }
        }
    }

    public void restart(){
        finish();
        startActivity(getIntent());
    }
    public void left(){
        moveLeft();
        mergeLeft();
        moveLeft();
    }
    public void right(){
        moveRight();
        mergeRight();
        moveRight();
    }
    public void up(){
        moveUp();
        mergeUp();
        moveUp();
    }
    public void down(){
        moveDown();
        mergeDown();
        moveDown();
    }
    public void moveLeft(){
        for(int i =0; i < 4; i++){
            for(int j = 0; j<4; j++) {
                int track = j;
                if(j > 0 && arr[i][j] != 0)
                {
                    while (track > 0 && arr[i][track] != 0) {
                        if (arr[i][track - 1] == 0)
                        {
                            arr[i][track - 1] = arr[i][track];
                            arr[i][track] = 0;
                        }
                        track--;
                    }
                }
            }
            for(int index = 0; index < 4; index++) buttons[i][index].setText(""+arr[i][index]);
        }
    }
    public void mergeLeft(){
        for(int i =0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(j < 3  && arr[i][j] == arr[i][j+1])
                {
                    arr[i][j] = arr[i][j] + arr[i][j+1];
                    arr[i][j+1] = 0;
                }
            }
            for(int index = 0; index < 4; index++) buttons[i][index].setText(""+arr[i][index]);

        }
    }
    public void moveRight(){
        for(int i =3; i >= 0; i--){
            for(int j = 3; j >= 0; j--) {
                int track = j;
                if(j < 3 && arr[i][j] != 0)
                {
                    while (track < 3 && arr[i][track] != 0) {
                        if (arr[i][track + 1] == 0)
                        {
                            arr[i][track + 1] = arr[i][track];
                            arr[i][track] = 0;
                        }
                        track++;
                    }
                }
            }
            for(int index = 0; index < 4; index++) buttons[i][index].setText(""+arr[i][index]);
        }
    }
    public void mergeRight(){
        for(int i =3; i >= 0; i--){
            for(int j = 3; j >= 0; j--) {
                    if (j > 0 && arr[i][j] == arr[i][j-1]) {
                        arr[i][j] = arr[i][j] + arr[i][j - 1];
                        arr[i][j - 1] = 0;
                    }
            }
            for(int index = 0; index < 4; index++) buttons[i][index].setText(""+arr[i][index]);
        }
    }
    public void moveUp(){
        for(int i =0; i < 4; i++){
            for(int j = 0; j<4; j++) {
                int track = j;
                if(j > 0 && arr[j][i] != 0)
                {
                    while (track > 0 && arr[track][i] != 0) {
                        if (arr[track - 1][i] == 0)
                        {
                            arr[track - 1][i] = arr[track][i];
                            arr[track][i] = 0;
                        }
                        track--;
                    }
                }
            }
            for(int index = 0; index < 4; index++) buttons[index][i].setText(""+arr[index][i]);
        }
    }
    public void mergeUp(){
        for(int i =0; i < 4; i++){
            for(int j = 0; j<4; j++) {
                if (j < 3 && arr[j + 1][i] == arr[j][i])
                {
                    arr[j][i] = arr[j + 1][i] + arr[j][i];
                    arr[j+1][i] = 0;
                }
            }
        for(int index = 0; index < 4; index++) buttons[index][i].setText(""+arr[index][i]);
        }
      }
    public void moveDown(){
        for(int i =3; i >= 0; i--){
            for(int j = 3; j >= 0; j--) {
                int track = j;
                if(j < 3 && arr[j][i] != 0)
                {
                    while (track < 3 && arr[track][i] != 0) {
                        if (arr[track+1][i] == 0)
                        {
                            arr[track + 1][i] = arr[track][i];
                            arr[track][i] = 0;
                        }
                        track++;
                    }
                }
            }
            for(int index = 0; index < 4; index++) buttons[index][i].setText(""+arr[index][i]);
        }
    }
    public void mergeDown(){
        for(int i =3; i >= 0; i--){
            for(int j = 3; j >= 0; j--)
            {
                if (j > 0 && arr[j - 1] [i] == arr [j][i])
                {
                            arr[j][i] = arr[j - 1][i] + arr[j][i];
                            arr[j-1][i] = 0;
                }
            }
            for(int index = 0; index < 4; index++) buttons[index][i].setText(""+arr[index][i]);
        }
    }
    public void searchForWin(){
        for(int i =0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(arr[i][j] == 8){ //change value if you want to see screen
                    Intent intent = new Intent(this, WinGame.class);
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
