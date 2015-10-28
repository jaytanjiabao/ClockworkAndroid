package com.sg.clockwork.model;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.sg.clockwork.presenter.ViewBadgesListener;
import com.sg.clockwork.presenter.ViewQuizListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoi Chuen on 27/10/2015.
 */
public class QuizManager extends AsyncTask<String, Void, String>{

    ViewQuizListener viewQuizListener;
    String email, authToken,rightQuestions;
    boolean submitScore = false;

    public QuizManager(ViewQuizListener viewQuizListener) {
        this.viewQuizListener = viewQuizListener;
    }



    public void setCredentials(String email, String authToken) {
        this.email = email;
        this.authToken = authToken;
    }

    public void setQuizCredentials(String email, String authToken, String rightQuestions, boolean score) {
        this.email = email;
        this.authToken = authToken;
        this.rightQuestions = rightQuestions;
        this.submitScore = score;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        System.out.println("Quiz result: " +  result);
        if(submitScore) {
            viewQuizListener.onSubmitSuccess(result);
        }  else {
            viewQuizListener.onSuccess(result);
        }

    }

    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("email", email));
            nvps.add(new BasicNameValuePair("genre", "clean_up"));
            if(submitScore) {
                nvps.add(new BasicNameValuePair("questions", rightQuestions));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpPost.setHeader("Authentication-Token", authToken);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {

        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

}
