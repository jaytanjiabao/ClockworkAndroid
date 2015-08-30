package com.android.clockwork.presenter;

import android.content.Context;

import com.android.clockwork.model.FBLoginManager;
import com.android.clockwork.view.activity.PreludeActivity;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Hoi Chuen on 28/8/2015.
 */
public class FBLoginPresenter implements FBLoginListener {
    private PreludeActivity preludeActivity;
    private FBLoginManager fbLoginManager;
    Context currentContext;

    public FBLoginPresenter(PreludeActivity preludeActivity){
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
    }

    public void fbLogin(String email, String fb_Id, String avatar_Path, String account_Type, String userName){
        this.fbLoginManager = new FBLoginManager(currentContext);
        fbLoginManager.execute("https://clockwork-api.herokuapp.com/users.json");
        fbLoginManager.fbLogin(email, fb_Id, avatar_Path, account_Type, userName,this);
    }

    public void onSuccess () {
        preludeActivity.navigateToHome();
    }
}
