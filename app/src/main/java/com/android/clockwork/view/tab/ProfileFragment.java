package com.android.clockwork.view.tab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.clockwork.R;
import com.android.clockwork.model.SessionManager;
import com.android.clockwork.presenter.EditProfilePresenter;
import com.android.clockwork.view.activity.EditProfileActivity;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    Button editButton;
    View fragmentView;
    TextView usernameText, emailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_3, container, false);
        usernameText = (TextView) fragmentView.findViewById(R.id.usernameText);
        emailText = (TextView) fragmentView.findViewById(R.id.emailText);

        // to remove editProfilePresenter
        editProfilePresenter = new EditProfilePresenter(getActivity());
        updatePersonalDetails();

        editButton = (Button) fragmentView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(view.getContext(), EditProfileActivity.class);
                startActivity(editProfile);
            }
        });

        return fragmentView;
    }

    public void updatePersonalDetails() {
        HashMap<String, String> user = editProfilePresenter.getUserMap();
        usernameText.setText(user.get(SessionManager.KEY_NAME));
        emailText.setText(user.get(SessionManager.KEY_EMAIL));
    }
}