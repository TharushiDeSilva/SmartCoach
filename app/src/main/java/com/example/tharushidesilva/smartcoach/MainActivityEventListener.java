package com.example.tharushidesilva.smartcoach;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivityEventListener implements ValueEventListener{
    private MainActivity mainActivity;

    MainActivityEventListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue() == null){return;}
        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
        for (DataSnapshot d:dataSnapshots) {
            String key = d.getKey();
            if(key==null || d.getValue() == null){return;}
            switch (key){
                case "max_calorie_cnt":
                    mainActivity.rec_calorie_cnt = Double.valueOf(d.getValue().toString());
                    break;
                case "calorie_cnt":
                    mainActivity.curr_calorie_cnt = Double.valueOf(d.getValue().toString());
                    break;
                case "step_cnt":
                    mainActivity.curr_step_cnt = (Double.valueOf(d.getValue().toString())).intValue();
                    break;
                case "min_step_cnt":
                    mainActivity.rec_step_cnt = Integer.parseInt(d.getValue().toString());
                    break;
                case "Name":
                    mainActivity.userName = d.getValue().toString();
                    break;
            }

        }
        mainActivity.notifyChange();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
