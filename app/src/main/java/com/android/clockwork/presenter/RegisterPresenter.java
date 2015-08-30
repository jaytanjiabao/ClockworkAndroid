package com.android.clockwork.presenter;

import android.content.Context;

import com.android.clockwork.model.FBLoginManager;
import com.android.clockwork.model.RegisterManager;
import com.android.clockwork.view.activity.PreludeActivity;
import com.android.clockwork.view.activity.RegisterActivity;

/**
 * Created by Hoi Chuen on 29/8/2015.
 */
public class RegisterPresenter implements RegisterListener {

    private RegisterActivity registerActivity;
    private RegisterManager registerManager;
    Context currentContext;

    public RegisterPresenter(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
        this.currentContext = registerActivity.getApplicationContext();
    }

    public void register(String email, String passWord, String name) {
        this.registerManager = new RegisterManager(currentContext);
        registerManager.execute("https://clockwork-api.herokuapp.com/users.json");
        registerManager.register(email,passWord,name,this);

    }


    public void onSuccess () {
        registerActivity.navigateToHome();
    }
}
