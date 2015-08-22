package com.android.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.LogoutListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Hoi Chuen on 22/8/2015.
 */
public class LogoutManager extends AsyncTask<String, Void, String> {

    Context currentContext;
    SessionManager sessionManager;
    LogoutListener listener;

    public LogoutManager (Context currentContext, final LogoutListener listener) {
        this.currentContext = currentContext;
        this.listener = listener;
    }
    public static String DELETE(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make Delete request to the given URL
            HttpDelete httpDelete = new HttpDelete(url);

            // 3. build jsonObject

            httpDelete.setHeader("Accept", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpDelete);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }


    @Override
         protected String doInBackground(String... urls) {

        return DELETE(urls[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        sessionManager = new SessionManager(currentContext);
        sessionManager.logoutUser();
        listener.onSuccess();
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
