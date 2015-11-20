package com.sg.clockwork.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sg.clockwork.R;
import com.sg.clockwork.model.Quiz;
import com.sg.clockwork.presenter.ViewQuizPresenter;

import org.w3c.dom.Text;

public class CompleteQuizActivity extends AppCompatActivity {

    ViewQuizPresenter viewQuizPresenter;
    TextView scoreView;
    Button rewardsButton;
    String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_quiz);
        scoreView = (TextView)findViewById(R.id.quizScore);
        scoreView.setText(QuizActivity.marks);
        genre = getIntent().getExtras().getString("quizScore");
        System.out.println(genre);
        rewardsButton = (Button)findViewById(R.id.badgeButton);
        viewQuizPresenter = new ViewQuizPresenter(this);
        rewardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewQuizPresenter.recordQuiz(genre,QuizActivity.rightQuestions);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complete_quiz, menu);
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
