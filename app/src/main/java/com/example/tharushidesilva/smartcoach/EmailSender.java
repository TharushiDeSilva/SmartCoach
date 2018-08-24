package com.example.tharushidesilva.smartcoach;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class EmailSender {
    public void sendMail(String to, String subject, String body, final Context context){
        BackgroundMail.newBuilder(context)
                .withUsername("smart.coach.001@gmail.com")
                .withPassword("S#Mart#Coach#001")
                .withMailto(to)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(subject)
                .withBody(body)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //
                    }
                })
                .send();
    }
}
