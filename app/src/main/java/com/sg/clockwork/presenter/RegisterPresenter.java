package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.RegisterManager;
import com.sg.clockwork.view.activity.RegisterActivity;

/**
 * Created by Hoi Chuen on 29/8/2015.
 */
public class RegisterPresenter implements RegisterListener {

    private RegisterActivity registerActivity;
    private RegisterManager registerManager;
    Context currentContext;
    APIManager apiManager;
    ProgressBar progressBar;
    TextView statusText;

    public RegisterPresenter(RegisterActivity registerActivity, ProgressBar progressBar, TextView statusText){
        this.registerActivity = registerActivity;
        this.currentContext = registerActivity.getApplicationContext();
        apiManager = new APIManager();
        this.progressBar = progressBar;
        this.statusText = statusText;
    }

    public void register(String email, String passWord, String name) {
        this.registerManager = new RegisterManager(currentContext,progressBar,statusText);
        registerManager.execute(apiManager.fbLogin_Register());
        registerManager.register(email,passWord,name,this);

    }


    public void onSuccess () {
        registerActivity.navigateToHome();
    }
}
