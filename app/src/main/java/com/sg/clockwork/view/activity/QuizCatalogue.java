package com.sg.clockwork.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.sg.clockwork.R;
import com.sg.clockwork.adapter.CatalogueAdapter;
import com.sg.clockwork.model.Rewards;
import com.sg.clockwork.presenter.ViewQuizPresenter;

import java.util.ArrayList;

public class QuizCatalogue extends AppCompatActivity {

    ViewQuizPresenter viewQuizPresenter;
    Button backButton;
    ArrayList<Rewards> catalouge;
    ListView listView;
    CatalogueAdapter catalogueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_catalogue);

        listView = (ListView)findViewById(R.id.listView2);

        viewQuizPresenter = new ViewQuizPresenter(this);
        this.catalouge =  getIntent().getParcelableArrayListExtra("KEY");
        catalogueAdapter = new  CatalogueAdapter(this, catalouge);
        listView.setAdapter(catalogueAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adptView, View view, int position, long arg3) {
                Rewards quizType = (Rewards)catalogueAdapter.getItem(position);
                String quizCategory = quizType.getType();
                quizCategory = quizCategory.replaceAll(" ", "_");
                quizCategory = quizCategory.toLowerCase();
                viewQuizPresenter.getQuiz(quizCategory);
            }
        });

        backButton = (Button) findViewById(com.sg.clockwork.R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                backToListing.putExtra("Previous", "rewards");
                startActivity(backToListing);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_catalogue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
