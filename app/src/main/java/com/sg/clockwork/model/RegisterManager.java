package com.sg.clockwork.model;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.clockwork.presenter.RegisterListener;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
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
 * Created by Hoi Chuen on 29/8/2015.
 */
public class RegisterManager extends AsyncTask<String, Void, String> {

    Context currentContext;
    String email, passWord, passWord_confirmation, account_Type, userName, nric;
    RegisterListener registerListener;
    SessionManager sessionManager;
    HttpResponse httpResponse;
    int statusCode;
    ProgressBar progressBar;
    TextView statusText;

    public RegisterManager (Context currentContext, ProgressBar progressBar, TextView statusText) {
        this.currentContext = currentContext;
        this.progressBar = progressBar;
        this.statusText = statusText;
    }

    public void register(String nric, String email, String passWord, String userName, RegisterListener registerListener) {
        this.nric = nric;
        this.email = email;
        this.passWord = passWord;
        this.passWord_confirmation = passWord;
        this.userName = userName;
        this.account_Type = "job_seeker";
        this.registerListener = registerListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        statusText.setVisibility(View.VISIBLE);
        statusText.setText("   Registering Account...   ");
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

            pairs.add(new BasicNameValuePair("user[nric]", nric));
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
        if (statusCode == 201) {
            sessionManager = new SessionManager(currentContext);
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            Double idDouble = (Double)userHash.get("id");
            int id = idDouble.intValue();
            String nric = (String)userHash.get("nric");
            String username = (String)userHash.get("username");
            String email = (String)userHash.get("email");
            String accountType = (String)userHash.get("account_type");
            String authenticationToken = (String)userHash.get("authentication_token");
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
            sessionManager.createUserLoginSession(nric, id, username, email, accountType,authenticationToken, avatar_path,address,contact,dob,nationality);
            registerListener.onSuccess();
        }else {
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            LinkedTreeMap<String, Object> errors = (LinkedTreeMap<String, Object>)userHash.get("errors");
            ArrayList<String> a_email = (ArrayList<String>)errors.get("email");
            String errorEmail = "";
            String errorPassword = "";
            if(a_email != null) {
                errorEmail = a_email.get(0);
            }
            ArrayList<String> a_passWord = (ArrayList<String>)errors.get("password");
            if(a_passWord != null) {
                errorPassword = a_passWord.get(0);
            }

            if(errorEmail.equalsIgnoreCase("can't be blank")|| errorPassword.equalsIgnoreCase("can't be blank")){
                statusText.setVisibility(View.VISIBLE);
                statusText.setText("   Email/Password can't be blank!  ");
            }else if(errorEmail.equalsIgnoreCase("has already been taken")){
                statusText.setVisibility(View.VISIBLE);
                statusText.setText("   Email has already been taken!  ");
            }else if(errorPassword.equalsIgnoreCase("is too short (minimum is 8 characters)")) {
                statusText.setVisibility(View.VISIBLE);
                statusText.setText("   Password must be at least 8 characters!  ");
            }else{
                statusText.setVisibility(View.VISIBLE);
                statusText.setText("   Invalid Email!  ");
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
