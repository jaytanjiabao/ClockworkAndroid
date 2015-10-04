package com.sg.clockwork.presenter;

import android.widget.ImageView;

import com.sg.clockwork.model.ProfilePictureManager;

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
