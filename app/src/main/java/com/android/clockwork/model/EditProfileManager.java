package com.android.clockwork.model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.EditProfileListener;
import com.android.clockwork.presenter.JobListingListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
 * Created by jiabao.tan.2012 on 26/8/2015.
 */
public class EditProfileManager extends AsyncTask<String, Void, String> {
    EditProfileListener editProfileListener;
    ProgressDialog dialog;
    String name, address, contact, dob;

    public EditProfileManager(EditProfileListener editProfileListener, ProgressDialog dialog) {
        this.editProfileListener = editProfileListener;
        this.dialog = dialog;
    }

    public void setProfileDetails(String name, String address, String contact, String dob) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
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
        Log.d("Manager", result);
        editProfileListener.onSuccess(result);
        dialog.dismiss();
    }

    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            // TO GET SESSION DETAILS - EMAIL
            //nvps.add(new BasicNameValuePair("email", email));
            nvps.add(new BasicNameValuePair("username", name));
            nvps.add(new BasicNameValuePair("address", address));
            nvps.add(new BasicNameValuePair("contact_number", contact));
            nvps.add(new BasicNameValuePair("date_of_birth", "" + dob));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            // TO GET SESSION DETAILS - AUTH TOKEN
            //httpPost.setHeader("Authentication-Token", authToken);
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
