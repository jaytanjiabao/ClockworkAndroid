package com.sg.clockwork.view.tab;



import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.sg.clockwork.R;
import com.sg.clockwork.adapter.ScoresAdapter;
import com.sg.clockwork.model.Rewards;
import com.sg.clockwork.presenter.ViewBadgesPresenter;
import com.sg.clockwork.view.activity.BadgeActivity;
import com.sg.clockwork.view.activity.QuizActivity;

import java.util.ArrayList;


/**
 * Created by Hoi Chuen on 22/10/2015.
 */
public class RewardsFragment extends Fragment {

    View fragmentView;
    Button improveButton, badgesButton;
    ImageView imageView;
    ListView listView;
    ArrayList<Rewards> scoreList;
    ViewBadgesPresenter viewBadgesPresenter;
    ScoresAdapter scoresAdapter;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        fragmentView = inflater.inflate(com.sg.clockwork.R.layout.tab_fragment_4, container, false);
        improveButton = (Button) fragmentView.findViewById(R.id.improveButton);
        badgesButton = (Button) fragmentView.findViewById(R.id.badgeButton);
        imageView = (ImageView) fragmentView.findViewById(R.id.imageView);
        listView = (ListView) fragmentView.findViewById(R.id.listView);
        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar3);
        scoreList = new ArrayList<Rewards>();
        viewBadgesPresenter = new ViewBadgesPresenter(scoreList,this, progressBar);
        viewBadgesPresenter.getScores();



        improveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent badges = new Intent(view.getContext(), QuizActivity.class);
                startActivity(badges);

            }
        });

        badgesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent badges = new Intent(view.getContext(), BadgeActivity.class);
                startActivity(badges);

            }
        });

        return fragmentView;
    }

    public void displayScoreListing() {
        scoresAdapter = viewBadgesPresenter.getScoresAdapter();
        scoreList = viewBadgesPresenter.badgeList();
        listView.setAdapter(scoresAdapter);

    }

}
