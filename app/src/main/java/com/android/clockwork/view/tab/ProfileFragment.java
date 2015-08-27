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
import com.android.clockwork.presenter.EditProfilePresenter;

public class ProfileFragment extends Fragment {
    EditProfilePresenter editProfilePresenter;
    ProgressDialog dialog;
    View fragmentView;
    Button editButton, updateButton;
    EditText nameText, addressText, contactText, dateText;
    TextView usernameText, emailText;
    Boolean status = false;
    LinearLayout editProfile;
    GridView grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tab_fragment_3, container, false);
        editProfilePresenter = new EditProfilePresenter(getActivity(), dialog);
        initializeEditProfile();
        updatePersonalDetails();

        return fragmentView;
    }

    public void updatePersonalDetails() {
        usernameText.setText(editProfilePresenter.getUsername());
        emailText.setText(editProfilePresenter.getEmail());
    }

    public void initializeEditProfile() {
        editProfile = (LinearLayout) fragmentView.findViewById(R.id.editProfile);
        grid = (GridView) fragmentView.findViewById(R.id.grid);

        usernameText = (TextView) fragmentView.findViewById(R.id.usernameText);
        emailText = (TextView) fragmentView.findViewById(R.id.emailText);

        nameText = (EditText) fragmentView.findViewById(R.id.nameText);
        addressText = (EditText) fragmentView.findViewById(R.id.addressText);
        contactText = (EditText) fragmentView.findViewById(R.id.contactText);
        dateText = (EditText) fragmentView.findViewById(R.id.dateText);

        editButton = (Button) fragmentView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update EditProfile
                if (!status) {
                    // show
                    editProfile.setVisibility(View.VISIBLE);
                    grid.setVisibility(View.INVISIBLE);
                    editButton.setText("View Past Jobs");

                    status = true;
                } else {
                    // hide
                    editProfile.setVisibility(View.INVISIBLE);
                    grid.setVisibility(View.VISIBLE);
                    editButton.setText("Edit Profile");

                    status = false;
                }
            }
        });

        updateButton = (Button) fragmentView.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update EditProfile
                String name = nameText.getText().toString();
                String address = addressText.getText().toString();
                String contact = contactText.getText().toString();
                String dob = dateText.getText().toString();

                editProfilePresenter.updateProfile(name, address, contact, dob);

                editProfile.setVisibility(View.INVISIBLE);
                grid.setVisibility(View.VISIBLE);
            }
        });
    }
}