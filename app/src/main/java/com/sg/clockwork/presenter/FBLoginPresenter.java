package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.FBLoginManager;
import com.sg.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 28/8/2015.
 */
public class FBLoginPresenter implements FBLoginListener {
    private PreludeActivity preludeActivity;
    private FBLoginManager fbLoginManager;
    Context currentContext;
    ProgressBar progressBar;
    TextView statusText;
    APIManager apiManager;

    public FBLoginPresenter(PreludeActivity preludeActivity, ProgressBar progressBar, TextView statusText){
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
        this.progressBar = progressBar;
        this.statusText = statusText;
        apiManager = new APIManager();
    }

    public void fbLogin(String email, String fb_Id, String avatar_Path, String account_Type, String userName){
        this.fbLoginManager = new FBLoginManager(currentContext,progressBar,statusText);
        fbLoginManager.execute(apiManager.fbLogin_Register());
        fbLoginManager.fbLogin(email, fb_Id, avatar_Path, account_Type, userName, this);
    }

    public void onSuccess () {
        preludeActivity.navigateToHome();
    }

}
