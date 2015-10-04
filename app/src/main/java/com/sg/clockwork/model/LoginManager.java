package com.sg.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.clockwork.presenter.LoginListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class LoginManager extends AsyncTask<String, Void, String> {
    HttpResponse httpResponse;
    SessionManager sessionManager;
    int statusCode;
    LoginListener listener;
    Context currentContext;
    ProgressBar progressBar;
    String userEmail, userPassword;
    TextView statusText;

    public LoginManager(Context currentContext,ProgressBar progressBar,TextView statusText) {
        this.currentContext = currentContext;
        this.progressBar = progressBar;
        this.statusText = statusText;
    }

    public void login(final String userEmail, final String userPassword, final LoginListener listener) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        statusText.setVisibility(View.VISIBLE);
        statusText.setText("   Logging In...   ");
    }


    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            List<NameValuePair> pairs = new ArrayList<NameValuePair>();


            pairs.add(new BasicNameValuePair("user[email]", userEmail));
            pairs.add(new BasicNameValuePair("user[password]", userPassword));

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
    protected String doInBackground(String... urls) {
        if(isCancelled()) {
            return "Cancelled";
        }
        return POST(urls[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        progressBar.setVisibility(View.INVISIBLE);
        statusText.setVisibility(View.INVISIBLE);
        statusText.setText("");
        if(statusCode!=401) {
            sessionManager = new SessionManager(currentContext);
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            Double idDouble = (Double)userHash.get("id");
            int id = idDouble.intValue();
            String username = (String)userHash.get("username");
            String email = (String)userHash.get("email");
            String accountType = (String)userHash.get("account_type");
            if(accountType.equalsIgnoreCase("job_seeker")) {
                String authenticationToken = (String) userHash.get("authentication_token");
                String avatar_path = (String) userHash.get("avatar_path");
                String address = (String) userHash.get("address");
                Double contactNo = (Double) userHash.get("contact_number");
                NumberFormat nm = NumberFormat.getNumberInstance();
                String contact = "";
                if (contactNo != null) {
                    contact = nm.format(contactNo);
                    contact = contact.replace(",", "");
                }
                String dob = (String) userHash.get("date_of_birth");
                String nationality = (String) userHash.get("nationality");
                sessionManager.createUserLoginSession(id, username, email, accountType, authenticationToken, avatar_path, address, contact, dob, nationality);
                listener.onSuccess();
            }else {
                statusText.setVisibility(View.VISIBLE);
                statusText.setText("   Employers, please use the web version!  ");
            }
        }else {
            statusText.setVisibility(View.VISIBLE);
            statusText.setText("   Invalid Email/Password!   ");
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
