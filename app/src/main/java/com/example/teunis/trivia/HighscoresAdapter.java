package com.example.teunis.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoresAdapter extends ArrayAdapter<Highscore> {
    public HighscoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
    highscores = objects;
    }

    ArrayList<Highscore> highscores;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_highscore, parent, false);
        }

    // get textViews to fill
    TextView showName = convertView.findViewById(R.id.textViewName);
    TextView showHighscore = convertView.findViewById(R.id.textViewScore);

    // get input (name and score from highscore in ArrayList
    String highscoreInput = Integer.toString(highscores.get(0).score);
    String nameInput = highscores.get(0).name;

    // fill textViews
    showHighscore.setText(highscoreInput);
    showName.setText(nameInput);

    return convertView;
    }
}