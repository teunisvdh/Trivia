package com.example.teunis.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class TriviaHelper implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;

    public TriviaHelper (Context context) {this.context = context;}

    // methods later to be implemented
    public interface Callback {
        void gotQuestions(Question question);
        void gotQuestionsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        // if error, give error message to user
        String thisError = error.getMessage();
        activity.gotQuestionsError(thisError);
    }

    @Override
    public void onResponse(JSONObject response) {

        // try getting JSONArray
        try {
            JSONArray questionArray = response.getJSONArray("clues");
            ArrayList<String> possibleAnswers = new ArrayList<>();

            // fill questionArray with answers randomly
            for (int i = 1; i < 4; i++) {
                Random randomInt = new Random();
                int randomNumber = randomInt.nextInt(questionArray.length());
                JSONObject otherQuestion = questionArray.getJSONObject(randomNumber);
                String otherAnswerGot = otherQuestion.getString("answer");
                possibleAnswers.add(otherAnswerGot);
                questionArray.remove(randomNumber);
            }

            // get right question
            Random randomInt = new Random();
            JSONObject thisQuestion = questionArray.getJSONObject(randomInt.nextInt(questionArray.length()));
            String questionGot = thisQuestion.getString("question");
            String answerGot = thisQuestion.getString("answer");
            possibleAnswers.add(answerGot);

            // make Question object and give to activity
            Question finalQuestion = new Question(questionGot, answerGot, possibleAnswers);
            activity.gotQuestions(finalQuestion);

        // catch exception and show it to user
        } catch (JSONException e) {
            Log.d("JSONException in catch", "in catch" + e);
            String error = e.getMessage();
            activity.gotQuestionsError(error);
        }
    }

    public void getQuestions(Callback activity) {

        // get questions countries quiz
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://jservice.io/api/category?id=1361", null, this, this);

        // add to que for onResponse
        queue.add(jsonObjectRequest);
    }
}
