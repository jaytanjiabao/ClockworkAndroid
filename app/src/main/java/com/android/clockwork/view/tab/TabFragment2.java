package com.android.clockwork.view.tab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.clockwork.R;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.view.activity.PreludeActivity;

public class TabFragment2 extends Fragment {

    SessionManager sessionManager;
    View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        final Button logoutButton = (Button) fragmentView.findViewById(R.id.logOutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
                Intent userLogin = new Intent(view.getContext(), PreludeActivity.class);
                startActivity(userLogin);
            }
        });

        return fragmentView;
    }


}