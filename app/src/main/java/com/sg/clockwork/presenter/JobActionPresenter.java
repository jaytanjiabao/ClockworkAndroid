package com.sg.clockwork.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.sg.clockwork.adapter.DashboardAdapter;
import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.AcceptJobManager;
import com.sg.clockwork.model.CheckJobManager;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.model.WithdrawJobManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jiabao.tan.2012 on 8/9/2015.
 */
public class JobActionPresenter implements JobActionListener {
    Activity activity;
    ProgressDialog dialog;
    WithdrawJobManager withdrawJobManager;
    AcceptJobManager acceptJobManager;
    CheckJobManager checkJobManager;
    Context currentContext;
    SessionManager sessionManager;
    DashboardAdapter adapter;
    ArrayList<Post> postList;
    String action, status = "null";
    int position;
    APIManager apiManager;

    public JobActionPresenter(DashboardAdapter adapter, Activity activity, ProgressDialog dialog) {
        this.adapter = adapter;
        this.activity = activity;
        this.dialog = dialog;
        this.currentContext = activity.getApplicationContext();
        this.withdrawJobManager = new WithdrawJobManager(this, this.dialog);
        this.acceptJobManager = new AcceptJobManager(this, this.dialog);
        this.checkJobManager = new CheckJobManager(this, this.dialog);
        this.sessionManager = new SessionManager(currentContext);
        apiManager = new APIManager();
    }

    public JobActionPresenter(Activity activity, ProgressDialog dialog) {
        this.activity = activity;
        this.adapter = new DashboardAdapter(activity,postList);
        this.dialog = dialog;
        this.currentContext = activity.getApplicationContext();
        this.withdrawJobManager = new WithdrawJobManager(this, this.dialog);
        this.acceptJobManager = new AcceptJobManager(this, this.dialog);
        this.checkJobManager = new CheckJobManager(this, this.dialog);
        this.sessionManager = new SessionManager(currentContext);
        apiManager = new APIManager();
    }

    public void withdrawJobApplication(int id, ArrayList<Post> postList, int position){
        HashMap<String, String> usermap = getUserMap();
        action = "withdraw";
        this.postList = postList;
        this.position = position;
        withdrawJobManager.setCredentials(usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN), usermap.get(SessionManager.KEY_EMAIL), id);
        withdrawJobManager.execute(apiManager.withdrawJob());
    }

    public void withdrawJobApplication(int id){
        HashMap<String, String> usermap = getUserMap();
        action = "withdrawFromViewJobActivity";
        withdrawJobManager.setCredentials(usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN), usermap.get(SessionManager.KEY_EMAIL), id);
        withdrawJobManager.execute(apiManager.withdrawJob());
    }

    public void checkJobApplication(int id){
        HashMap<String, String> usermap = getUserMap();
        action = "check";
        checkJobManager.setCredentials(usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN), usermap.get(SessionManager.KEY_EMAIL), id);
        checkJobManager.execute(apiManager.checkJobStatus());
    }

    public void acceptJobOffer(int id){
        HashMap<String, String> usermap = getUserMap();
        action = "accept";
        acceptJobManager.setCredentials(usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN), usermap.get(SessionManager.KEY_EMAIL), id);
        acceptJobManager.execute(apiManager.acceptJob());
    }

    public String getStatus() {
        return status;
    }

    public HashMap<String, String> getUserMap() {
        return sessionManager.getUserDetails();
    }

    @Override
    public void onNoListingError() {

    }

    @Override
    public void onSuccess(String string) {
        if (action.equalsIgnoreCase("withdraw")) {
            Toast.makeText(activity.getBaseContext(), "Successfully withdrawn from job", Toast.LENGTH_LONG).show();
            postList.remove(position);
            adapter.notifyDataSetChanged();
        } else if (action.equalsIgnoreCase("accept")) {
            Toast.makeText(activity.getBaseContext(), "You have accepted the job and will be contacted by the employer shortly!", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        } else if (action.equalsIgnoreCase("check")) {
            status = string;
        }
    }
}
