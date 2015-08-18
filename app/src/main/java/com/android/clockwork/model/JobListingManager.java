package com.android.clockwork.model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.JobListingListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jiabao.tan.2012 on 18/8/2015.
 */
public class JobListingManager extends AsyncTask<String, Void, String> {
    JobListingListener jobListingListener;
    ProgressDialog dialog;

    public JobListingManager(JobListingListener jobListingListener, ProgressDialog dialog) {
        this.jobListingListener = jobListingListener;
        this.dialog = dialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Retrieving job listings");
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Log.d("Manager", result);
        jobListingListener.onSuccess(result);
        dialog.dismiss();
    }

    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
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
