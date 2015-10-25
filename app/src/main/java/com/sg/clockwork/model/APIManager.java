package com.sg.clockwork.model;

/**
 * Created by Hoi Chuen on 23/9/2015.
 */
public class APIManager {

    final String baseURL = "https://staging-clockwork-api.herokuapp.com/";

    public String applyJob() {
        return baseURL + "api/v1/users/apply";
    }

    public String completeProfile() {
        return baseURL + "api/v1/users/complete_profile";
    }

    public String dashboard() {
        return baseURL + "api/v1/users/get_applied_jobs";
    }

    public String editProfile() {
        return baseURL + "api/v1/users/update";
    }

    public String fbLogin_Register() {
        return baseURL + "users.json";
    }

    public String withdrawJob() {
        return baseURL + "api/v1/users/withdraw";
    }

    public String acceptJob() {
        return baseURL + "api/v1/users/accept";
    }

    public String jobListing() {
        return baseURL + "api/v1/posts/all.json";
    }

    public String searchJob() {
        return baseURL + "api/v1/posts/search?query=";
    }

    public String login() {
        return baseURL + "users/sign_in.json";
    }

    public String logout() {
        return baseURL + "users/sign_out.json";
    }

    public String getCompletedJobs() {
        return baseURL + "api/v1/users/get_completed_jobs";
    }

    public String checkJobStatus() { return baseURL + "/api/v1/posts/get_status"; }

    public String registerGCMIDToServer() { return baseURL + "/api/v1/notifications/register"; }

    public String getBadges () {return baseURL + "/api/v1/gamify/get_badges";}

    public String getScores () {return baseURL + "/api/v1/gamify/get_score";}

}
