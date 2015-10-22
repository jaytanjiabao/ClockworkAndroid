package com.sg.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sg.clockwork.notification.Config;
import com.sg.clockwork.presenter.GCMPresenter;
import com.sg.clockwork.presenter.LoginPresenter;
import com.sg.clockwork.presenter.RegisterPresenter;
import com.sg.clockwork.view.activity.PreludeActivity;
import com.sg.clockwork.view.activity.RegisterActivity;

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
 * Created by jiabao.tan.2012 on 21/10/2015.
 */
public class GCMServerManager extends AsyncTask<String, Void, String> {

    Context currentContext;
    RegisterPresenter registerPresenter;
    RegisterActivity registerActivity;
    LoginPresenter loginPresenter;
    PreludeActivity preludeActivity;
    SessionManager sessionManager;
    String regId;
    HttpResponse httpResponse;
    int statusCode;

    public GCMServerManager(Context currentContext, RegisterPresenter registerPresenter, RegisterActivity registerActivity) {
        this.currentContext = currentContext;
        this.registerPresenter = registerPresenter;
        this.registerActivity = registerActivity;
    }

    public GCMServerManager(Context currentContext, LoginPresenter loginPresenter, PreludeActivity preludeActivity) {
        this.currentContext = currentContext;
        this.loginPresenter = loginPresenter;
        this.preludeActivity = preludeActivity;
    }

    public void prepareId(String id) {
        this.regId = id;
    }

    @Override
    protected void onPreExecute() {

    }

    // post method
    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        sessionManager = new SessionManager(currentContext);
        String deviceId = regId;
        String email = sessionManager.KEY_EMAIL;
        String deviceType = "android";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            Log.d("device_id", deviceId);
            Log.d("email", email);
            Log.d("device_type", deviceType);
            pairs.add(new BasicNameValuePair("device_id", deviceId));
            pairs.add(new BasicNameValuePair("email", email));
            pairs.add(new BasicNameValuePair("device_type", deviceType));

            // 6. set httpPost Entity
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            // 8. Execute POST request to the given URL
            httpResponse = httpclient.execute(httpPost);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {

        }
        // 11. return result
        return result;
    }

    @Override
    protected String doInBackground(String... urls) {
        return POST(urls[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        // return to presenter
        if (registerActivity != null) {
            Toast.makeText(registerActivity.getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (statusCode == 200) {
                registerPresenter.completeRegistration(true);
            } else {
                registerPresenter.completeRegistration(false);
            }
        } else {
            Toast.makeText(preludeActivity.getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (statusCode == 200) {
                loginPresenter.completeRegistration(true);
            } else {
                loginPresenter.completeRegistration(false);
            }
        }
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
