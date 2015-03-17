package com.app.toby.findmycharger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Toby on 12/17/2014.
 */
public class BootReciever extends BroadcastReceiver
{
    public DataSource datasource;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub


        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

}