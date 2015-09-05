package com.android.clockwork.presenter;

/**
 * Created by jiabao.tan.2012 on 26/8/2015.
 */
public interface EditProfileListener {

    public void onSuccess(String string, boolean passwordChange, boolean profilePicChange);

    public void onError(String string);
}
