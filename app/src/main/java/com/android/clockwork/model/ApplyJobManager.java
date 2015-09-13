package com.android.clockwork.model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.ApplyJobListener;

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
 * Created by jiabao.tan.2012 on 29/8/2015.
 */
public class ApplyJobManager extends AsyncTask<String, Void, String> {
    ApplyJobListener applyJobListener;
    ProgressDialog dialog;
    String email, authToken, id;

    public ApplyJobManager(ApplyJobListener applyJobListener, ProgressDialog dialog) {
        this.applyJobListener = applyJobListener;
        this.dialog = dialog;
    }

    public void prepareAuthentication(String email, String authToken, int id) {
        this.email = email;
        this.authToken = authToken;
        this.id = String.valueOf(id);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Applying for job");
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
        applyJobListener.onSuccess(result);
        dialog.dismiss();
    }

    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("post_id", id));
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
