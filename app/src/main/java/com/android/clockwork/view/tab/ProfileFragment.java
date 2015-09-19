package com.android.clockwork.view.tab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.EditProfilePresenter;

import com.android.clockwork.presenter.LogoutPresenter;
import com.android.clockwork.presenter.ProfilePicturePresenter;

import com.android.clockwork.presenter.ViewCompletedJobPresenter;
import com.android.clockwork.view.activity.ChangePasswordActivity;

import com.android.clockwork.view.activity.EditProfileActivity;
import com.android.clockwork.view.activity.MainActivity;
import com.android.clockwork.view.activity.PreludeActivity;
import com.android.clockwork.view.activity.ViewCompletedJobActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileFragment extends Fragment {
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    Button editButton, pwButton, logoutButton, changeProfilePictureButton, viewCompletedJobButton;
    View fragmentView;
    TextView usernameText, emailText;
    ImageView pictureView;
    HashMap<String, String> user;
    HashMap<String,Integer> userID;
    LogoutPresenter logoutPresenter;
    ProfilePicturePresenter profilePicturePresenter;
    ArrayList<Post> postList;
    ViewCompletedJobPresenter viewCompletedJobPresenter;
    int counter_good = 0;
    int counter_neutral = 0;
    int counter_bad = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_3, container, false);
        usernameText = (TextView) fragmentView.findViewById(R.id.usernameText);
        emailText = (TextView) fragmentView.findViewById(R.id.emailText);
        pictureView = (ImageView) fragmentView.findViewById(R.id.imageView);
        dialog = new ProgressDialog(this.getActivity());

        //to display ratings
        postList = new ArrayList<Post>();
        viewCompletedJobPresenter = new ViewCompletedJobPresenter(postList,this,dialog,true);
        viewCompletedJobPresenter.getCompletedJobList();


        // to remove editProfilePresenter
        editProfilePresenter = new EditProfilePresenter(this,dialog);
        logoutPresenter = new LogoutPresenter(this);
        profilePicturePresenter = new ProfilePicturePresenter(pictureView);
        user = editProfilePresenter.getUserMap();
        userID  = editProfilePresenter.getUserID();
        String avatar_path = user.get(SessionManager.KEY_AVATAR);
        if (avatar_path != null) {
            profilePicturePresenter.getProfilePicture(avatar_path);
        }
        updatePersonalDetails();

        changeProfilePictureButton = (Button) fragmentView.findViewById(R.id.changeProfilePictureButton);
        changeProfilePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectImage();

            }
        });


        viewCompletedJobButton = (Button) fragmentView.findViewById(R.id.feedbackButton);
        viewCompletedJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewCompletedJob = new Intent(view.getContext(), ViewCompletedJobActivity.class);
                startActivity(viewCompletedJob);
            }
        });

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
                FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
                logoutPresenter.logOut();
                if(AccessToken.getCurrentAccessToken()!= null) {
                    LoginManager.getInstance().logOut();
                }
            }
        });

        return fragmentView;
    }



    //set ratings
    public void setRatingsCounter(ArrayList<Post> ratingList) {
        for (int i = 0 ;i < ratingList.size();  i++){
            Post p = ratingList.get(i);
            if(p.getRating()==-1) {
                counter_bad += 1;
            }else if(p.getRating()==0) {
                counter_neutral += 1;
            }else {
                counter_good += 1;
            }
        }
        TextView good = (TextView)fragmentView.findViewById(R.id.textView);
        TextView neutral = (TextView)fragmentView.findViewById(R.id.textView2);
        TextView bad = (TextView)fragmentView.findViewById(R.id.textView3);
        good.setText(String.valueOf(counter_good));
        neutral.setText(String.valueOf(counter_neutral));
        bad.setText(String.valueOf(counter_bad));
    }

    public void navigateToLogin() {
        startActivity(new Intent(fragmentView.getContext(), PreludeActivity.class));
    }

    public void updatePersonalDetails() {
        usernameText.setText(user.get(SessionManager.KEY_NAME));
        emailText.setText(user.get(SessionManager.KEY_EMAIL));
    }

    //Change Profile Picture codes
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Log.d("Activity", "Before executing..");
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    float scaleWidth = ((float)1024)/width;
                    float scaleHeight = ((float)1024)/height;
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth,scaleHeight);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,matrix,false);

                    File file = new File(getActivity().getApplicationContext().getCacheDir(), userID.get(SessionManager.KEY_ID) + "_avatar" + ".jpg");

                    OutputStream outFile = null;

                    try {
                        outFile = new FileOutputStream(file);
                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        editProfilePresenter.changeProfilePicture(user.get(SessionManager.KEY_EMAIL), file, user.get(SessionManager.KEY_AUTHENTICATIONTOKEN));
                        Log.d("Activity", "After executing..");
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Log.d("Activity", "Before executing..");
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getApplicationContext().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                int width = thumbnail.getWidth();
                int height = thumbnail.getHeight();
                float scaleWidth = ((float)1024)/width;
                float scaleHeight = ((float)1024)/height;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                Bitmap resizedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, width, height,matrix,false);

                Log.w("path of image from ***", picturePath + "");

                File filesDir = getActivity().getApplicationContext().getFilesDir();
                File imageFile = new File(filesDir, userID.get(SessionManager.KEY_ID) + "_avatar" + ".jpg");

                OutputStream os;
                try {
                    os = new FileOutputStream(imageFile);
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    editProfilePresenter.changeProfilePicture(user.get(SessionManager.KEY_EMAIL), imageFile, user.get(SessionManager.KEY_AUTHENTICATIONTOKEN));
                    Log.d("Activity", "After executing..");
                    os.close();

                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }
            }
        }
    }

    public void navigateToHome() {
        startActivity(new Intent(fragmentView.getContext(), MainActivity.class));
    }


}