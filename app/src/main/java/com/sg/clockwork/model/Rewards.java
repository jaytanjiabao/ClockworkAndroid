package com.sg.clockwork.model;

/**
 * Created by Hoi Chuen on 25/10/2015.
 */
public class Rewards {
    private String name;
    private String criteria;
    private String status;
    private String badge_id;

    public Rewards() {

    }

    public Rewards(String name, String criteria,String status, String badge_id) {

        this.name = name;
        this.criteria = criteria;
        this.status = status;
        this.badge_id = badge_id;

    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCriteria() {
        return criteria;

    }

    public void setCriteria(String criteria) {

        this.criteria = criteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBadge_id() {
        return badge_id;
    }

    public void setBadge_id(String badge_id) {
        this.badge_id = badge_id;
    }
}
