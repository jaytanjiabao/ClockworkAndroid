package com.sg.clockwork.presenter;

import android.content.Context;
import android.widget.ProgressBar;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sg.clockwork.adapter.BadgesAdapter;
import com.sg.clockwork.adapter.ScoresAdapter;
import com.sg.clockwork.model.APIManager;
import com.sg.clockwork.model.Post;
import com.sg.clockwork.model.Rewards;
import com.sg.clockwork.model.SessionManager;
import com.sg.clockwork.model.ViewBadgesManager;
import com.sg.clockwork.view.activity.BadgeActivity;
import com.sg.clockwork.view.tab.RewardsFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hoi Chuen on 25/10/2015.
 */
public class ViewBadgesPresenter implements ViewBadgesListener {

    ArrayList<Rewards> badgeList;
    BadgeActivity badgeActivity;
    SessionManager sessionManager;
    Context currentContext;
    ViewBadgesManager viewBadgesManager;
    HashMap<String, String> usermap;
    BadgesAdapter badgesAdapter;
    ScoresAdapter scoresAdapter;
    APIManager apiManager;
    RewardsFragment rewardsFragment;

    public ViewBadgesPresenter (ArrayList<Rewards> badgeList, BadgeActivity badgeActivity){
        this.badgeList = badgeList;
        this.badgeActivity = badgeActivity;
        apiManager = new APIManager();
        currentContext = badgeActivity.getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        this.viewBadgesManager = new ViewBadgesManager(this);
    }

    public ViewBadgesPresenter (ArrayList<Rewards> badgeList, RewardsFragment rewardsFragment) {
        this.badgeList = badgeList;
        this.rewardsFragment = rewardsFragment;
        apiManager = new APIManager();
        currentContext = rewardsFragment.getActivity().getApplicationContext();
        this.sessionManager = new SessionManager(currentContext);
        this.viewBadgesManager = new ViewBadgesManager(this,true);
    }

    public void getBadges() {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        viewBadgesManager.setCredentials(email, authToken);
        viewBadgesManager.execute (apiManager.getBadges());
    }

    public void getScores() {
        usermap = sessionManager.getUserDetails();
        String email = usermap.get(SessionManager.KEY_EMAIL);
        String authToken = usermap.get(SessionManager.KEY_AUTHENTICATIONTOKEN);
        viewBadgesManager.setCredentials(email, authToken);
        viewBadgesManager.execute (apiManager.getScores());
    }

    public ArrayList<Rewards> createGsonFromString(String string) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Rewards>>(){}.getType();
        badgeList = gson.fromJson(string, listType);
        return badgeList;
    }

    @Override
    public void onSuccess(String result) {
        this.badgeList = createGsonFromString(result);
        setListingAdapter(new BadgesAdapter(badgeActivity, badgeList, currentContext));
        badgeActivity.displayBadgeListing();

    }

    @Override
    public void onSuccessScore(String result) {
        this.badgeList = createGsonFromString(result);
        setScoresAdapter(new ScoresAdapter(rewardsFragment.getActivity(), badgeList));
        rewardsFragment.displayScoreListing();
    }


    public ArrayList<Rewards> badgeList() {
        return badgeList;
    }

    public void setListingAdapter(BadgesAdapter badgesAdapter) {
        this.badgesAdapter = badgesAdapter;
    }

    public void setScoresAdapter(ScoresAdapter scoresAdapter) {
        this.scoresAdapter = scoresAdapter;
    }

    public BadgesAdapter getBadgesAdapter() {
        return badgesAdapter;
    }

    public ScoresAdapter getScoresAdapter() { return scoresAdapter; }

}
