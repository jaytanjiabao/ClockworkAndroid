package com.android.clockwork.model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.JobActionListener;

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
 * Created by jiabao.tan.2012 on 1/10/2015.
 */
public class CheckJobManager extends AsyncTask<String, Void, String> {

    String authToken, email;
    int id;
    ProgressDialog dialog;
    JobActionListener jobActionListener;

    public CheckJobManager(JobActionListener jobActionListener, ProgressDialog dialog) {
        this.jobActionListener = jobActionListener;
        this.dialog = dialog;
    }

    public void setCredentials(String authToken, String email, int id) {
        this.authToken = authToken;
        this.email = email;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Getting job information");
        dialog.setMessage("Please wait");
        dialog.setIndeterminate(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        return POST(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        jobActionListener.onSuccess(result);
        dialog.dismiss();
    }

    // withdraw and pull both in here, to be separated
    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("email", email));
            nvps.add(new BasicNameValuePair("post_id", String.valueOf(id)));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpPost.setHeader("Authentication-Token", authToken);

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 9. return result
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
