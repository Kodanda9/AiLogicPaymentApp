package com.ailogic.ailogicpayment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NotificationDemo extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSend, clear;
    private static final int NOT_ID = 1;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_demo_activity);
        buttonSend = (Button) this.findViewById( R.id.send);
        clear = (Button) this.findViewById( R.id.cancel);
        buttonSend.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if(v == buttonSend){

            Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);



            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                notification = new Notification();
                notification.icon = R.mipmap.ic_launcher;
                try {
                    Method deprecatedMethod = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                    deprecatedMethod.invoke(notification, this, "ASDF", null, pendingIntent);
                } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    Log.w("Notif", "Method not found", e);
                }
            } else {
                // Use new API
                Notification.Builder builder = new Notification.Builder(this)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("ASDF");
                notification = builder.build();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Cancelling", Toast.LENGTH_SHORT).show();

        }


    }
}
