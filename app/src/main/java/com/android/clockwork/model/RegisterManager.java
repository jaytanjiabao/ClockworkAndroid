package com.android.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.RegisterListener;
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
 * Created by Hoi Chuen on 29/8/2015.
 */
public class RegisterManager extends AsyncTask<String, Void, String> {

    Context currentContext;
    String email, passWord, passWord_confirmation, account_Type, userName;
    RegisterListener registerListener;
    SessionManager sessionManager;
    HttpResponse httpResponse;
    int statusCode;

    public RegisterManager (Context currentContext) {
        this.currentContext = currentContext;
    }

    public void register(String email, String passWord, String userName, RegisterListener registerListener) {
        this.email = email;
        this.passWord = passWord;
        this.passWord_confirmation = passWord;
        this.userName = userName;
        this.account_Type = "job_seeker";
        this.registerListener = registerListener;
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


            pairs.add(new BasicNameValuePair("user[email]", email));
            pairs.add(new BasicNameValuePair("user[password]", passWord));
            pairs.add(new BasicNameValuePair("user[password_confirmation]", passWord_confirmation));
            pairs.add(new BasicNameValuePair("user[account_type]", account_Type));
            pairs.add(new BasicNameValuePair("user[username]", userName));


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
        return POST(urls[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        if (statusCode == 201) {
            sessionManager = new SessionManager(currentContext);
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            Double idDouble = (Double)userHash.get("id");
            int id = idDouble.intValue();
            String username = (String)userHash.get("username");
            String email = (String)userHash.get("email");
            String accountType = (String)userHash.get("account_type");
            String authenticationToken = (String)userHash.get("authentication_token");
            //String passWord = loginSession.getPassword();
            sessionManager.createUserLoginSession(id, username, email, accountType,authenticationToken);
            registerListener.onSuccess();
        }else {

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
