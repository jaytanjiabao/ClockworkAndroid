package com.sg.clockwork.presenter;

/**
 * Created by jiabao.tan.2012 on 18/8/2015.
 */
public interface JobListingListener {

    public void onNoListingError();

    public void onSuccess(String string);
}