package com.android.clockwork.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.clockwork.adapter.DashboardAdapter;
import com.android.clockwork.model.AcceptJobManager;
import com.android.clockwork.model.Post;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.model.WithdrawJobManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jiabao.tan.2012 on 8/9/2015.
 */
public class JobActionPresenter implements JobActionListener{
    Activity activity;
    ProgressDialog dialog;
    WithdrawJobManager withdrawJobManager;
    AcceptJobManager acceptJobManager;
    Context currentContext;
    SessionManager sessionManager;
    DashboardAdapter adapter;
    ArrayList<Post> postList;
    String action;
    int position;

    public JobActionPresenter(DashboardAdapter adapter, Activity activity, ProgressDialog dialog) {
        this.adapter = adapter;
        this.activity = activity;
        this.dialog = dialog;
        this.currentContext = activity.getApplicationContext();
        this.withdrawJobManager = new WithdrawJobManager(this, this.dialog);
        this.acceptJobManager = new AcceptJobManager(this, this.dialog);
        this.sessionManager = new SessionManager(currentContext);
    }

    public void withdrawJobApplication(int id, ArrayList<Post> postList, int position){
        HashMap<String, String> usermap = getUserMap();
        action = "withdraw";
        this.postList = postList;
        this.position = position;
        withdrawJobManager.setCredentials(usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN), usermap.get(SessionManager.KEY_EMAIL), id);
        withdrawJobManager.execute("https://clockwork-api.herokuapp.com/api/v1/users/withdraw");
    }

    public void acceptJobOffer(int id, ArrayList<Post> postList, int position){
        HashMap<String, String> usermap = getUserMap();
        action = "accept";
        this.postList = postList;
        this.position = position;
        acceptJobManager.setCredentials(usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN), usermap.get(SessionManager.KEY_EMAIL), id);
        acceptJobManager.execute("https://clockwork-api.herokuapp.com/api/v1/users/accept");
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
            Post acceptedPost = postList.get(position);
            acceptedPost.setStatus("hired");
            adapter.notifyDataSetChanged();
        }
    }
}
