package com.example.teunis.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Question> {
    public GameAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects) {
        super(context, resource, objects);
    }
}
