package com.example.tharushidesilva.smartcoach;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StepCountFragment extends Fragment implements SensorEventListener{

    private OnFragmentInteractionListener mListener;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private boolean walking = false;
    private TextView stepCountValue, recommendedStepCountValue;
    public StepCountFragment() {
        // Required empty public constructor
    }

    public static StepCountFragment newInstance() {
        StepCountFragment fragment = new StepCountFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step_count, container, false);

        stepCountValue = (TextView) v.findViewById(R.id.step_count_value);
        recommendedStepCountValue = (TextView) v.findViewById(R.id.recommended_step_count_value);
        stepCountValue.setText("0");

        // Read a value from firebase
        FirebaseHandler firebaseHandler = new FirebaseHandler();
        firebaseHandler.readValue("user001","step_cnt",recommendedStepCountValue);

        //Write a value to firebase
        //firebaseHandler.writeValue("user001","step_cnt","AASDD");

        //Send Email
        EmailSender emailSender = new EmailSender();
        emailSender.sendMail("tharushid.14@cse.mrt.ac.lk",
                "Subject Txt","Body Text",this.getContext());
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        walking = true;
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounter != null){
            sensorManager.registerListener(this, stepCounter, sensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this.getActivity(), "Sensor not Found!", Toast.LENGTH_SHORT).show(); //=========== not sure
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        walking = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_STEP_COUNTER){
            stepCountValue.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}