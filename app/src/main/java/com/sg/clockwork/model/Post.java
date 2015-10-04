package com.sg.clockwork.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiabao.tan.2012 on 2/8/2015.
 */

public class Post implements Parcelable {
    private int id;
    private String header;
    private String company;
    private double salary;
    private String description;
    private String location;
    private String posting_date;
    private String job_date;
    private int applicant_count;
    private String status;
    private String comments;
    private int rating;
    private int duration;
    private String avatar_path;
    private String start_time;
    private String end_date;
    private String end_time;

    public Post(int id, String header, String company, int salary, String description, String location, String posting_date, String job_date, String status, String avatar_path, String start_time, String end_date, String end_time, int duration) {
        this.id = id;
        this.header = header;
        this.company = company;
        this.salary = salary;
        this.description = description;
        this.location = location;
        this.posting_date = posting_date;
        this.job_date = job_date;
        this.setStatus(status);
        this.setAvatar_path(avatar_path);
        this.setStart_time(start_time);
        this.setEnd_date(end_date);
        this.setEnd_time(end_time);
        this.setDuration(duration);
    }

    public Post(String header, String company, int salary, String description, String location, String job_date) {
        this.header = header;
        this.company = company;
        this.salary = salary;
        this.description = description;
        this.location = location;
        this.job_date = job_date;
    }

    public Post(String header, int salary, String description, String location, String job_date) {
        this.header = header;
        this.salary = salary;
        this.description = description;
        this.location = location;
        this.job_date = job_date;
    }

    public Post(String header, String company, String comments, int rating) {
        this.header = header;
        this.company = company;
        this.comments = comments;
        this.rating = rating;
    }

    public Post() {
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobDate() {
        return job_date;
    }

    public void setJobDate(String jobdate) {
        this.job_date = jobdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosting_Date() {
        return posting_date;
    }

    public void setPosting_Date(String posting_Date) {
        this.posting_date = posting_Date;
    }

    public int getApplicant_count() {
        return applicant_count;
    }

    public void setApplicant_count(int applicant_count) {
        this.applicant_count = applicant_count;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(header);
        parcel.writeString(company);
        parcel.writeDouble(salary);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(posting_date);
        parcel.writeString(job_date);
        parcel.writeString(end_date);
        parcel.writeString(avatar_path);
        parcel.writeString(String.valueOf(duration));
        parcel.writeString(start_time);
        parcel.writeString(end_time);
        parcel.writeString(status);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            Post Post = new Post();
            Post.id = source.readInt();
            Post.header = source.readString();
            Post.company = source.readString();
            Post.salary = source.readDouble();
            Post.description = source.readString();
            Post.location = source.readString();
            Post.posting_date = source.readString();
            Post.job_date = source.readString();
            Post.end_date = source.readString();
            Post.avatar_path = source.readString();
            Post.duration = Integer.parseInt(source.readString());
            Post.start_time = source.readString();
            Post.end_time = source.readString();
            Post.status = source.readString();
            return Post;
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}