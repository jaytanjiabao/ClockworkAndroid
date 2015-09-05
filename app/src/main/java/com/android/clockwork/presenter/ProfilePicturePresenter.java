package com.android.clockwork.presenter;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.clockwork.model.ProfilePictureManager;

import java.io.File;

/**
 * Created by Hoi Chuen on 1/9/2015.
 */
public class ProfilePicturePresenter {

    ProfilePictureManager profilePictureManager;


    public ProfilePicturePresenter(ImageView picture) {
        this.profilePictureManager = new ProfilePictureManager(picture);
    }


    public void getProfilePicture(String avatar_path) {
        profilePictureManager.execute(new String[]{avatar_path});
    }

}
