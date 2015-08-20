package com.android.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.LoginListener;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hoi Chuen on 20/8/2015.
 */
public class LoginManager extends AsyncTask<String, Void, String> {
    HttpResponse httpResponse;
    Session loginSession;
    SessionManager sessionManager;
    int statusCode;
    LoginListener listener;
    Context currentContext;


    public void login(final String userEmail, final String userPassword, final LoginListener listener) {
        loginSession = new Session(userEmail, userPassword);
        this.listener = listener;
    }
    public String POST(String url, Session loginSession){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            List<NameValuePair> pairs = new ArrayList<NameValuePair>();


            pairs.add(new BasicNameValuePair("user[email]", loginSession.getEmail()));
            pairs.add(new BasicNameValuePair("user[password]", loginSession.getPassword()));

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
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    protected String doInBackground(String... urls) {
        if(isCancelled()) {
            return "Cancelled";
        }
        return POST(urls[0], loginSession);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        if(statusCode!=401) {
            sessionManager = new SessionManager(currentContext.getApplicationContext());
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            Double idDouble = (Double)userHash.get("id");
            int id = idDouble.intValue();
            String username = (String)userHash.get("username");
            String email = (String)userHash.get("email");
            String accountType = (String)userHash.get("account_type");
            String authenticationToken = (String)userHash.get("authentication_token");
            String passWord = loginSession.getPassword();
            sessionManager.createUserLoginSession(id, username, email, accountType, passWord, authenticationToken);
            listener.onSuccess();
        }else {

        }
/*        if(statusCode == 401) {
            //tx1.setText("The email / password is invalid, please try again.");
        }else {
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            Double idDouble = (Double)userHash.get("id");
            int id = idDouble.intValue();
            String username = (String)userHash.get("username");
            String email = (String)userHash.get("email");
            String accountType = (String)userHash.get("account_type");
            String authenticationToken = (String)userHash.get("authentication_token");
            String passWord = userPassWord.getText().toString();

            if(accountType.equals("job_seeker")) {
                session.createUserLoginSession(id, username, email, accountType, passWord, authenticationToken);
                // Starting MainActivity
                Intent i = new Intent(getApplicationContext(), JobListsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                //finish();
            }else {
                session.createUserLoginSession(id, username, email, accountType, passWord, authenticationToken);
                // Starting MainActivity
                Intent i = new Intent(getApplicationContext(), EmployerDashboardActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //finish();

            }
        }*/
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
