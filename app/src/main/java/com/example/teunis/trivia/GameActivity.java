package com.example.teunis.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback {

    int score = 0;
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // getQuestions to show in this activity
        TriviaHelper helper = new TriviaHelper(this);
        helper.getQuestions(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // put score for restoration
        int scoreValue = score;
        outState.putInt("scoreValue", scoreValue);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // retrieve score after rotating or leaving app
        int scoreValueRetrieved = savedInstanceState.getInt("scoreValue");
        score = scoreValueRetrieved;
    }

    @Override
    public void gotQuestions(Question question) {

        // fill textfield with question (from getQuestions)
        this.question = question;
        TextView questionText = findViewById(R.id.questionText);
        questionText.setText(question.question);

        // fill other answer buttons randomly
        TextView answer1 = findViewById(R.id.answer1);
        TextView answer2 = findViewById(R.id.answer2);
        TextView answer3 = findViewById(R.id.answer3);
        TextView answer4 = findViewById(R.id.answer4);
        List allQuestions = (List) question.answers;
        for (int i = 0; i < 4; i++) {
            Random randomNumber = new Random();
            int randomInt = randomNumber.nextInt(allQuestions.size());
            String thisQuestion = (String) allQuestions.get(randomInt);
            allQuestions.remove(randomInt);
            if (i == 0) {
                answer1.setText(thisQuestion);
            } else if (i == 1) {
                answer2.setText(thisQuestion);
            } else if (i == 2) {
                answer3.setText(thisQuestion);
            } else {
                answer4.setText(thisQuestion);
            }
        }
    }

    @Override
    public void gotQuestionsError(String message) {

        // if error, show error
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void checkAnswer(View view) {
        String rightAnswer = question.correctAnswer;
        Button button = (Button) view;
        String clickedAnswer = (String) button.getText();

        // if correct answer, score up
        if (clickedAnswer.equals(rightAnswer)) {
            score += 1;

        // else go to HighscoresActivity, give score and set score to 0 for new game
        } else {
            HighscoresHelper highscoreNew = new HighscoresHelper(this);
            highscoreNew.postNewHighscore(score);
            Intent intent = new Intent(GameActivity.this, HighscoreActivity.class);
            intent.putExtra("yourScore", score);
            startActivity(intent);
            score = 0;
        }

        // after answer: get new question
        TriviaHelper helper = new TriviaHelper(this);
        helper.getQuestions(this);
    }
}
