package com.android.clockwork.presenter;

import android.content.Context;

import com.android.clockwork.model.FBLoginManager;
import com.android.clockwork.model.RegisterManager;
import com.android.clockwork.view.activity.PreludeActivity;

/**
 * Created by Hoi Chuen on 29/8/2015.
 */
public class RegisterPresenter implements RegisterListener {

    private PreludeActivity preludeActivity;
    private RegisterManager registerManager;
    Context currentContext;

    public RegisterPresenter(PreludeActivity preludeActivity){
        this.preludeActivity = preludeActivity;
        this.currentContext = preludeActivity.getApplicationContext();
    }

    public void register(String email, String passWord, String name) {
        this.registerManager = new RegisterManager(currentContext);
        registerManager.execute("https://clockwork-api.herokuapp.com/users.json");
        registerManager.register(email,passWord,name,this);

    }


    public void onSuccess () {
        preludeActivity.navigateToHome();
    }
}
