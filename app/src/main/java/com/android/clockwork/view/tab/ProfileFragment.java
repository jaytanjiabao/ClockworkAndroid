package com.android.clockwork.view.tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.EditProfilePresenter;
import com.android.clockwork.view.activity.ChangePasswordActivity;
import com.android.clockwork.view.activity.EditProfileActivity;
import com.facebook.login.widget.ProfilePictureView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class ProfileFragment extends Fragment {
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    Button editButton, pwButton, logoutButton;
    View fragmentView;
    TextView usernameText, emailText;
    ImageView pictureView;
    HashMap<String, String> user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_3, container, false);
        usernameText = (TextView) fragmentView.findViewById(R.id.usernameText);
        emailText = (TextView) fragmentView.findViewById(R.id.emailText);
        pictureView = (ImageView) fragmentView.findViewById(R.id.imageView);

        // to remove editProfilePresenter
        editProfilePresenter = new EditProfilePresenter(getActivity());
        user = editProfilePresenter.getUserMap();
        String avatar_path = user.get(SessionManager.KEY_AVATAR);
        GetImageTask task = new GetImageTask();
        task.execute(new String[]{avatar_path});
        updatePersonalDetails();

        editButton = (Button) fragmentView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(view.getContext(), EditProfileActivity.class);
                startActivity(editProfile);
            }
        });

        pwButton = (Button) fragmentView.findViewById(R.id.pwButton);
        pwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePassword = new Intent(view.getContext(), ChangePasswordActivity.class);
                startActivity(changePassword);
            }
        });

        logoutButton = (Button) fragmentView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        return fragmentView;
    }

    public void updatePersonalDetails() {

        usernameText.setText(user.get(SessionManager.KEY_NAME));
        emailText.setText(user.get(SessionManager.KEY_EMAIL));
    }

    private class GetImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            pictureView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

    }
}