package com.android.clockwork.presenter;

import com.android.clockwork.adapter.ListingAdapter;

/**
 * Created by jiabao.tan.2012 on 18/8/2015.
 */
public interface JobListingListener {

    public void onNoListingError();

    public void onSuccess(String string);
}