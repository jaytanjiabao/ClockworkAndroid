package com.sg.clockwork.model;

import android.os.AsyncTask;

import com.sg.clockwork.presenter.ViewBadgesListener;
import com.sg.clockwork.presenter.ViewCompletedJobListener;

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
 * Created by Hoi Chuen on 25/10/2015.
 */
public class ViewBadgesManager extends AsyncTask<String, Void, String> {

    ViewBadgesListener viewBadgesListener;
    String email, authToken;

    public ViewBadgesManager(ViewBadgesListener viewBadgesListener) {
        this.viewBadgesListener = viewBadgesListener;
    }

    public void setCredentials(String email, String authToken) {
        this.email = email;
        this.authToken = authToken;
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
        System.out.println("hoi hoi" + result);
        viewBadgesListener.onSuccess(result);

    }

    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("email", email));
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
