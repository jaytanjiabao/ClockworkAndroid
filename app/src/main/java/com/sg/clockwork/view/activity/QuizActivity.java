package com.sg.clockwork.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sg.clockwork.R;
import com.sg.clockwork.model.Quiz;
import com.sg.clockwork.presenter.ViewQuizPresenter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    Button backButton;
    TextView question,review;
    Button optionA, optionB, optionC, optionD, nextQuestion;
    ArrayList<Quiz> quizList;
    int flag = 0;
    String userAns;
    public static int correct;
    public static String marks,rightQuestions;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        setSupportActionBar(toolbar);
        quizList = new ArrayList<Quiz>();
        rightQuestions = "[";
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListing = new Intent(view.getContext(), MainActivity.class);
                backToListing.putExtra("Previous", "reward");
                startActivity(backToListing);
            }
        });

        question = (TextView) findViewById(R.id.descriptionText);
        review = (TextView) findViewById(R.id.review);
        optionA = (Button) findViewById(R.id.buttonA);
        optionB = (Button) findViewById(R.id.buttonB);
        optionC = (Button) findViewById(R.id.buttonC);
        optionD = (Button) findViewById(R.id.buttonD);
        nextQuestion = (Button) findViewById(R.id.buttonNext);

        this.quizList = getIntent().getParcelableArrayListExtra(ViewQuizPresenter.PAR_KEY);


        if(quizList != null) {
            relativeLayout.setBackgroundResource(0);
            optionA.setVisibility(View.VISIBLE);
            optionB.setVisibility(View.VISIBLE);
            optionC.setVisibility(View.VISIBLE);
            optionD.setVisibility(View.VISIBLE);
            question.setText(quizList.get(flag).getQuestion());
            optionA.setText(quizList.get(flag).getChoice_a());
            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (quizList.get(flag).getAnswer().equalsIgnoreCase("a")) {
                        review.setTextColor(Color.parseColor("#a4c639"));
                        review.setText("You chose the Correct Answer!");
                        optionA.setBackgroundColor(Color.parseColor("#a4c639"));
                        String question = String.valueOf(quizList.get(flag).getId());
                        rightQuestions = rightQuestions + question + ",";
                        nextQuestion.setVisibility(View.VISIBLE);
                        correct++;
                        flag++;
                    } else {
                        review.setTextColor(Color.parseColor("#eb123a"));
                        review.setText("Uh oh. The Correct Answer is highlighted in Green");
                        optionA.setBackgroundColor(Color.parseColor("#eb123a"));
                        if (quizList.get(flag).getAnswer().equalsIgnoreCase("b")) {
                            optionB.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else if (quizList.get(flag).getAnswer().equalsIgnoreCase("c")) {
                            optionC.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else {
                            optionD.setBackgroundColor(Color.parseColor("#a4c639"));
                        }
                        nextQuestion.setVisibility(View.VISIBLE);
                        flag++;
                    }
                }
            });
            optionB.setText(quizList.get(flag).getChoice_b());
            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (quizList.get(flag).getAnswer().equalsIgnoreCase("b")) {
                        review.setTextColor(Color.parseColor("#a4c639"));
                        review.setText("You chose the Correct Answer!");
                        optionB.setBackgroundColor(Color.parseColor("#a4c639"));
                        String question = String.valueOf(quizList.get(flag).getId());
                        rightQuestions = rightQuestions + question + ",";
                        nextQuestion.setVisibility(View.VISIBLE);
                        correct++;
                        flag++;
                    } else {
                        review.setTextColor(Color.parseColor("#eb123a"));
                        review.setText("Uh oh. The Correct Answer is highlighted in Green");
                        optionB.setBackgroundColor(Color.parseColor("#eb123a"));
                        if (quizList.get(flag).getAnswer().equalsIgnoreCase("a")) {
                            optionA.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else if (quizList.get(flag).getAnswer().equalsIgnoreCase("c")) {
                            optionC.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else {
                            optionD.setBackgroundColor(Color.parseColor("#a4c639"));
                        }
                        nextQuestion.setVisibility(View.VISIBLE);
                        flag++;
                    }
                }
            });
            optionC.setText(quizList.get(flag).getChoice_c());
            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (quizList.get(flag).getAnswer().equalsIgnoreCase("c")) {
                        review.setTextColor(Color.parseColor("#a4c639"));
                        review.setText("You chose the Correct Answer!");
                        optionC.setBackgroundColor(Color.parseColor("#a4c639"));
                        String question = String.valueOf(quizList.get(flag).getId());
                        rightQuestions = rightQuestions + question + ",";
                        nextQuestion.setVisibility(View.VISIBLE);
                        correct++;
                        flag++;
                    } else {
                        review.setTextColor(Color.parseColor("#eb123a"));
                        review.setText("Uh oh. The Correct Answer is highlighted in Green");
                        optionC.setBackgroundColor(Color.parseColor("#eb123a"));
                        if (quizList.get(flag).getAnswer().equalsIgnoreCase("a")) {
                            optionA.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else if (quizList.get(flag).getAnswer().equalsIgnoreCase("b")) {
                            optionB.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else {
                            optionD.setBackgroundColor(Color.parseColor("#a4c639"));
                        }
                        nextQuestion.setVisibility(View.VISIBLE);
                        flag++;
                    }
                }
            });
            optionD.setText(quizList.get(flag).getChoice_d());
            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (quizList.get(flag).getAnswer().equalsIgnoreCase("d")) {
                        review.setTextColor(Color.parseColor("#a4c639"));
                        review.setText("You chose the Correct Answer!");
                        optionD.setBackgroundColor(Color.parseColor("#a4c639"));
                        String question = String.valueOf(quizList.get(flag).getId());
                        rightQuestions = rightQuestions + question + ",";
                        nextQuestion.setVisibility(View.VISIBLE);
                        correct++;
                        flag++;
                    } else {
                        review.setTextColor(Color.parseColor("#eb123a"));
                        review.setText("Uh oh. The Correct Answer is highlighted in Green");
                        optionD.setBackgroundColor(Color.parseColor("#eb123a"));
                        if (quizList.get(flag).getAnswer().equalsIgnoreCase("a")) {
                            optionA.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else if (quizList.get(flag).getAnswer().equalsIgnoreCase("b")) {
                            optionB.setBackgroundColor(Color.parseColor("#a4c639"));
                        } else {
                            optionC.setBackgroundColor(Color.parseColor("#a4c639"));
                        }
                        nextQuestion.setVisibility(View.VISIBLE);
                        flag++;
                    }
                }
            });

            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (flag < quizList.size()) {
                        review.setText(" ");
                        nextQuestion.setVisibility(View.INVISIBLE);
                        optionA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        optionB.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        optionC.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        optionD.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        question.setText(quizList.get(flag).getQuestion());
                        optionA.setText(quizList.get(flag).getChoice_a());
                        optionB.setText(quizList.get(flag).getChoice_b());
                        optionC.setText(quizList.get(flag).getChoice_c());
                        optionD.setText(quizList.get(flag).getChoice_d());
                    } else {
                        marks = "Score: " + correct + "/" + quizList.size();
                        rightQuestions = rightQuestions.substring(0, rightQuestions.length() - 1);
                        rightQuestions = rightQuestions + "]";
                        Intent completeQuiz = new Intent(view.getContext(), CompleteQuizActivity.class);
                        startActivity(completeQuiz);
                    }

                }
            });


        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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
