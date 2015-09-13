package com.android.clockwork.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.CompleteProfileListener;


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
 * Created by Hoi Chuen on 12/9/2015.
 */
public class CompleteProfileManager extends AsyncTask<String, Void, String> {

    CompleteProfileListener completeProfileListener;
    ProgressDialog dialog;
    String email, nationality, gender, mobileNo, dob, authToken;
    Context currentContext;
    int postID = 0;

    public CompleteProfileManager(CompleteProfileListener completeProfileListener, ProgressDialog dialog, Context currentContext) {
        this.completeProfileListener = completeProfileListener;
        this.dialog = dialog;
        this.currentContext = currentContext;
    }

    public void completeProfile(int postID, String email, String nationality,String gender, String mobileNo, String dob, String authToken) {
        this.postID = postID;
        this.email = email;
        this.nationality = nationality;
        this.gender = gender;
        this.mobileNo = mobileNo;
        this.dob = dob;
        this.authToken = authToken;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Updating your profile");
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
        completeProfileListener.onSuccess(result,postID);
        dialog.dismiss();
    }

    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);


            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("email", email));
            nvps.add(new BasicNameValuePair("date_of_birth", dob));
            nvps.add(new BasicNameValuePair("gender", gender));
            nvps.add(new BasicNameValuePair("contact_number", mobileNo));
            nvps.add(new BasicNameValuePair("nationality", nationality));
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
