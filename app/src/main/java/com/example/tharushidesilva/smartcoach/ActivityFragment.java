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

public class ActivityFragment extends Fragment implements SensorEventListener{

    private OnFragmentInteractionListener mListener;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView linearXValue, linearYValue, linearZValue, activityValue;
    private Double absRoundX, absRoundY, absRoundZ;
    public ActivityFragment() {
        // Required empty public constructor
    }

    public static ActivityFragment newInstance() {
        ActivityFragment fragment = new ActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_activity, container, false);

        linearXValue = (TextView) v.findViewById(R.id.valueX);
        linearYValue = (TextView) v.findViewById(R.id.valueY);
        linearZValue = (TextView) v.findViewById(R.id.valueZ);
        activityValue = (TextView) v.findViewById(R.id.valueActivity);
        //recommendedStepCountValue = (TextView) v.findViewById(R.id.recommended_step_count_value);
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
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            absRoundX = Math.abs(Math.round(event.values[0]*100.0)/100.0);
            absRoundY = Math.abs(Math.round(event.values[1]*100.0)/100.0);
            absRoundZ = Math.abs(Math.round(event.values[2]*100.0)/100.0);

            linearXValue.setText(String.valueOf(absRoundX));
            linearYValue.setText(String.valueOf(absRoundY));
            linearZValue.setText(String.valueOf(absRoundZ));

            //deciding if the user is asleep/ or idle.
            if((absRoundX <0.5) && (absRoundY<0.5) && (absRoundZ<0.5)){
                activityValue.setText("Idle");
            }else{
                activityValue.setText("Active");
            }
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
