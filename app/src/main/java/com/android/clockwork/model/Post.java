package com.android.clockwork.model;

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

    public Post(int id, String header, String company, int salary, String description, String location, String posting_date, String job_date, String status) {
        this.id = id;
        this.header = header;
        this.company = company;
        this.salary = salary;
        this.description = description;
        this.location = location;
        this.posting_date = posting_date;
        this.job_date = job_date;
        this.setStatus(status);
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
}
