package com.sg.clockwork.model;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.sg.clockwork.presenter.DashboardListener;

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
 * Created by jiabao.tan.2012 on 2/9/2015.
 */
public class AppliedJobsManager extends AsyncTask<String, Void, String> {
    DashboardListener dashboardListener;
    //ProgressDialog dialog;
    String email, authToken;
    ProgressBar progressBar;

    public AppliedJobsManager(DashboardListener dashboardListener, ProgressBar progressBar) {
        this.dashboardListener = dashboardListener;
        this.progressBar = progressBar;
        //this.dialog = dialog;
    }

    public void setCredentials(String email, String authToken) {
        this.email = email;
        this.authToken = authToken;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
/*        dialog.setTitle("Retrieving your applied jobs");
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(false);
        dialog.show();*/
    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        progressBar.setVisibility(View.GONE);
        dashboardListener.onSuccess(result);
        //dialog.dismiss();
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
            Log.d("InputStream", e.getLocalizedMessage());
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
