package com.sg.clockwork.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.Quiz;
import com.sg.clockwork.model.QuizManager;
import com.sg.clockwork.model.Rewards;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.view.activity.CompleteQuizActivity;
import com.sg.clockwork.view.activity.MainActivity;
import com.sg.clockwork.view.activity.QuizActivity;
import com.sg.clockwork.view.activity.QuizCatalogue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hoi Chuen on 27/10/2015.
 */
public class ViewQuizPresenter implements ViewQuizListener {

    CompleteQuizActivity quizActivity;
    Context currentContext;
    SessionManager sessionManager;
    APIManager apiManager;
    HashMap<String, String> usermap;
    QuizManager quizManager;
    ArrayList<Quiz> quizList;
    QuizCatalogue quizCatalogue;
    public final static String PAR_KEY = "KEY";
    QuizActivity quizPage = new QuizActivity();

    public ViewQuizPresenter (QuizCatalogue quizCatalogue) {
        this.quizCatalogue = quizCatalogue;
        apiManager = new APIManager();
        this.currentContext = quizCatalogue.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        this.quizManager = new QuizManager(this);
    }

    public ViewQuizPresenter (CompleteQuizActivity quizActivity) {
        this.quizActivity = quizActivity;
        apiManager = new APIManager();
        this.currentContext = quizActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        this.quizManager = new QuizManager(this);
    }

    public void getQuiz(String category) {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        quizManager.setCredentials(email, authToken,category);
        quizManager.execute (apiManager.getQuiz());
    }

    public void recordQuiz (String rightQuestions) {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        quizManager.setQuizCredentials(email, authToken, rightQuestions, true);
        quizManager.execute (apiManager.recordQuiz());
    }

    @Override
    public void onSuccess(String result) {
        if(result.equalsIgnoreCase("No more quizzes available")){
            Intent startQuiz = new Intent(currentContext, QuizActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            currentContext.startActivity(startQuiz);
        }
        else {
            this.quizList = createGsonFromString(result);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(PAR_KEY, quizList);
            Intent startQuiz = new Intent(currentContext, QuizActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startQuiz.putExtras(bundle);
            currentContext.startActivity(startQuiz);
        }
    }
    @Override
    public void onSubmitSuccess(String result) {
        Intent updateProfile = new Intent(currentContext, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        updateProfile.putExtra("Previous", "rewards");
        currentContext.startActivity(updateProfile);
    }

    public ArrayList<Quiz> createGsonFromString(String string) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Quiz>>(){}.getType();
        quizList = gson.fromJson(string, listType);
        return quizList;
    }

    public ArrayList<Quiz> getQuizList() {
        return quizList;
    }
}
