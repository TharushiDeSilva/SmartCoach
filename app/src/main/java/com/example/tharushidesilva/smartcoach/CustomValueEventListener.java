package com.example.tharushidesilva.smartcoach;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CustomValueEventListener implements ValueEventListener{
    private TextView textView;

    CustomValueEventListener(TextView textView){
        this.textView = textView;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue() == null){return;}
        textView.setText((dataSnapshot.getValue()).toString());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
