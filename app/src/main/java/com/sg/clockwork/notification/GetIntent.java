package com.sg.clockwork.notification;

/**
 * Created by jiabao.tan.2012 on 20/10/2015.
 */
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sg.clockwork.R;

/**
 * Created by aneh on 8/16/2014.
 */
public class GetIntent extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getintent);

        String get = getIntent().getStringExtra("Notif");

        Log.e("Msg", "---------------------------"+get);

        TextView txt = (TextView)findViewById(R.id.get);
        txt.setText(get);

    }
}