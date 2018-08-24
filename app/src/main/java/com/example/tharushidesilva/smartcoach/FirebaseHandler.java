package com.example.tharushidesilva.smartcoach;

import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler {
    public void readValue(String userID,String field,TextView view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.child(userID).child(field).addValueEventListener(new CustomValueEventListener(view));
    }

    public void writeValue(String userID,String field,String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.child(userID).child(field).setValue(value);
    }
}
