package com.android.clockwork.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.android.clockwork.presenter.EditProfileListener;
import com.android.clockwork.presenter.JobListingListener;
import com.android.clockwork.view.activity.MainActivity;
import com.android.clockwork.view.tab.ProfileFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiabao.tan.2012 on 26/8/2015.
 */
public class EditProfileManager extends AsyncTask<String, Void, String> {
    EditProfileListener editProfileListener;
    ProgressDialog dialog;
    String name, address, contact, oldPassword, newPassword, retypedPassword, email, authToken;
    boolean changePassword = false;
    boolean changeProfilePicture = false;
    boolean refresh = false;
    File file;
    Context currentContext;
    SessionManager sessionManager;

    public EditProfileManager(EditProfileListener editProfileListener, ProgressDialog dialog, Context currentContext) {
        this.editProfileListener = editProfileListener;
        this.dialog = dialog;
        this.currentContext = currentContext;
    }

    public void setProfileDetails(String name, String address, String contact, String email, String authToken) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.authToken = authToken;
    }

    public void preparePasswordChange(boolean status) {
        this.changePassword = status;
    }

    public void prepareProfilePicChange(boolean status) {

        this.changeProfilePicture = status;
    }

    public void setPasswordDetails(String oldPassword, String newPassword, String retypedPassword, String email, String authToken) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.retypedPassword = retypedPassword;
        this.email = email;
        this.authToken = authToken;
    }

    public void setProfilePictureDetails (String email, File file, String authToken) {
        this.email = email;
        this.authToken = authToken;
        this.file = file;
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
        sessionManager = new SessionManager(currentContext);
        if(changeProfilePicture){
            Gson gson = new Gson();
            Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
            HashMap userHash = gson.fromJson(result, hashType);
            Double idDouble = (Double)userHash.get("id");
            int id = idDouble.intValue();
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
            sessionManager.updateSession(id,username,email,accountType,authenticationToken,avatar_path,address,contact);
        }
        editProfileListener.onSuccess(result, changePassword, changeProfilePicture);
        changePassword = false;
        changeProfilePicture = false;
        dialog.dismiss();
    }

    public String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            if(changeProfilePicture){

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                builder.addTextBody("email", email, ContentType.TEXT_PLAIN);
                builder.addBinaryBody("avatar", file, ContentType.create("image/jpeg"), file.getName());
                httpPost.setEntity(builder.build());

            } else{
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                nvps.add(new BasicNameValuePair("email", email));

                if (changePassword) {
                    nvps.add(new BasicNameValuePair("password", newPassword));
                    nvps.add(new BasicNameValuePair("password_confirmation", retypedPassword));
                    nvps.add(new BasicNameValuePair("old_password", oldPassword));
                } else {
                    nvps.add(new BasicNameValuePair("username", name));
                    nvps.add(new BasicNameValuePair("address", address));
                    nvps.add(new BasicNameValuePair("contact_number", contact));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            }

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
