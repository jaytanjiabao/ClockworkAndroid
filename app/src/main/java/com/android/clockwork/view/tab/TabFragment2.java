package com.android.clockwork.view.tab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.clockwork.R;
import com.android.clockwork.presenter.LogoutPresenter;
import com.android.clockwork.view.activity.PreludeActivity;

public class TabFragment2 extends Fragment implements View.OnClickListener {

    View fragmentView;
    LogoutPresenter logoutPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        logoutPresenter = new LogoutPresenter(this);
        fragmentView.findViewById(R.id.logOutButton).setOnClickListener(this);

        return fragmentView;
    }

    public void navigateToLogin() {
        startActivity(new Intent(fragmentView.getContext(), PreludeActivity.class));
    }

    @Override
    public void onClick(View v) {
        logoutPresenter.logOut();
    }

}