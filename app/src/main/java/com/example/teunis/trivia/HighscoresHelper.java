package com.example.teunis.trivia;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class HighscoresHelper {

    Context context;
    Callback activity;
    private DatabaseReference scoreDatabase = FirebaseDatabase.getInstance().getReference();

    public HighscoresHelper (Context context) {this.context = context;}

    // methods later to be implemented
    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscore);
        void gotHighscoresError(String message);
    }

    public void postNewHighscore(final int score) {

        // try getting highscore
        scoreDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Highscore highScore = dataSnapshot.child("highscores").getValue(Highscore.class);

                // if score higher than highscore, update highscore
                if (highScore.score < score) {
                    Highscore newHighScore = new Highscore("Anonymous player", score);
                    scoreDatabase.child("highscores").setValue(newHighScore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // if error, show error
                Log.w("onCancelled", "Failed to read value.", databaseError.toException());
                activity.gotHighscoresError("databaseError");
            }
        });
    }

    public void getHighscores(final Callback activity) {

        // try getting highscore
        scoreDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Highscore> allHighscores = new ArrayList<>();
                Highscore highScore = dataSnapshot.child("highscores").getValue(Highscore.class);
                allHighscores.add(highScore);
                activity.gotHighscores(allHighscores);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // if error, show error
                Log.w("onCancelled", "Failed to read value.", databaseError.toException());
                activity.gotHighscoresError("databaseError");
            }
        });
    }
}
