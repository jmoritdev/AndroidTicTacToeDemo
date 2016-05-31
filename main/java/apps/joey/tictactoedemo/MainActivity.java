package apps.joey.tictactoedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int activePlayer;
    int gameState[] = {0,0,0,0,0,0,0,0,0};
    static int[][] winningCombinations = {
            {1,2,3},
            {4,5,6},
            {7,8,9},
            {1,4,7},
            {2,5,8},
            {3,6,9},
            {1,5,9},
            {3,5,7}
    };
    boolean endOfGame;
    boolean draw;

    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activePlayer = 1;
        resetButton = (Button) findViewById(R.id.resetButton);
    }

    public void showShape(View view){
        ImageView image = (ImageView) view;
        int imageTag = Integer.parseInt(image.getTag().toString());

        if(activePlayer == 1 && gameState[imageTag-1] == 0 && !endOfGame) {
            gameState[imageTag-1] = activePlayer;
            image.setImageResource(R.drawable.kruisje);
            image.animate().alpha(1f).setDuration(250);
            checkForWin();
            activePlayer = 2;

        } else if(activePlayer == 2 && gameState[imageTag-1] == 0 && !endOfGame) {
            gameState[imageTag-1] = activePlayer;
            image.setImageResource(R.drawable.rondje);
            image.animate().alpha(1f).setDuration(250);
            checkForWin();
            activePlayer = 1;
        }
    }

    public void checkForWin(){
        for(int[] combination : winningCombinations){
            boolean hasWon = true;
            for(int x : combination){
                if(gameState[x - 1] != activePlayer){
                    hasWon = false;
                    break;
                }
            }
            if(hasWon){
                endGame();
                return;
            }
        }

        endOfGame = true;
        for(int x : gameState){
            if(x == 0){
                endOfGame = false;
                return;
            }
        }
        draw = true;
        endGame();
    }

    public void endGame(){
        TextView mainText = (TextView) findViewById(R.id.mainText);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        if(draw) {
            mainText.setText("Gelijkspel!");
        } else {
            mainText.setText("Speler " + activePlayer + " heeft gewonnen!");
        }
        resetButton.setEnabled(true);
        endOfGame = true;
    }

    public void resetGame(View view){
        TextView mainText = (TextView) findViewById(R.id.mainText);
        mainText.setText("");
        resetButton.setEnabled(false);

        endOfGame = false;
        draw = false;
        for(int x = 0; x < gameState.length; x++){//reset the gamestate
            Array.setInt(gameState, x, 0);
        }
        for(int x = 1; x < 10; x++){//reset the images.
            ImageView image = (ImageView) findViewById(R.id.imageView+x);
            image.setImageResource(android.R.color.transparent);
            image.animate().alpha(0f);
        }
        activePlayer = 1;
    }

}
