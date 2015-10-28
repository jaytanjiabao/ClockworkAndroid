package com.sg.clockwork.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hoi Chuen on 27/10/2015.
 */
public class Quiz implements Parcelable {
    private String genre;
    private String question;
    private String choice_a;
    private String choice_b;
    private String choice_c;
    private String choice_d;
    private String answer;
    private int id;

    public Quiz (int id, String question, String choice_a, String choice_b,
                 String choice_c, String choice_d , String answer, String genre) {
        this.id = id;
        this.question = question;
        this.choice_a = choice_a;
        this.choice_b = choice_b;
        this.choice_c = choice_c;
        this.choice_d = choice_d;
        this.answer = answer;
        this.genre = genre;
    }

    public Quiz() {}

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getQuestion () {
        return question;
    }

    public void setQuestion (String question) {
        this.question = question;
    }

    public String getChoice_a () {
        return choice_a;
    }

    public void setChoice_a (String choice_a) {
        this.choice_a = choice_a;
    }

    public String getChoice_b () {
        return choice_b;
    }

    public void setChoice_b (String choice_b) {
        this.choice_b = choice_b;
    }

    public String getChoice_c () {
        return choice_c;
    }

    public void setChoice_c (String choice_c) {
        this.choice_c = choice_c;
    }

    public String getChoice_d () {
        return choice_d;
    }

    public void setChoice_d (String choice_d) {
        this.choice_d = choice_d;
    }

    public String getAnswer () {
        return answer;
    }

    public void setAnswer (String answer) {
        this.answer = answer;
    }

    public String getGenre () {
        return genre;
    }

    public void setGenre (String genre) {
        this.genre = genre;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(question);
        parcel.writeString(choice_a);
        parcel.writeString(choice_b);
        parcel.writeString(choice_c);
        parcel.writeString(choice_d);
        parcel.writeString(answer);
        parcel.writeString(genre);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        public Quiz createFromParcel(Parcel source) {
            Quiz Quiz = new Quiz();
            Quiz.id = source.readInt();
            Quiz.question = source.readString();
            Quiz.choice_a = source.readString();
            Quiz.choice_b = source.readString();
            Quiz.choice_c = source.readString();
            Quiz.choice_d = source.readString();
            Quiz.answer = source.readString();
            Quiz.genre = source.readString();

            return Quiz;
        }

        public Quiz[] newArray(int size) {

            return new Quiz[size];
        }
    };

}
