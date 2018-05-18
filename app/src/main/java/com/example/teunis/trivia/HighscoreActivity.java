package com.example.teunis.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoresHelper.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // get highscores
        HighscoresHelper helper = new HighscoresHelper(this);
        helper.getHighscores(this);

        // fill textfield with score from GameActivity
        Intent intent = getIntent();
        Integer score = (Integer) intent.getSerializableExtra("yourScore");
        TextView textScore = (TextView) findViewById(R.id.scorePlayer);
        textScore.setText(Integer.toString(score));
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscore) {

        // set adapter, fill gridview with highscore data
        HighscoresAdapter adapter = new HighscoresAdapter(this, R.layout.item_highscore, highscore);
        GridView listHighscores = findViewById(R.id.listHighscores);
        listHighscores.setAdapter(adapter);
    }

    @Override
    public void gotHighscoresError(String message) {

        // if error, show error
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void newGame(View view) {

        // start new game (score 0 already)
        Intent intent = new Intent(HighscoreActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
