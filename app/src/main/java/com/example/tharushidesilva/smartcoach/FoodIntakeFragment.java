package com.example.tharushidesilva.smartcoach;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodIntakeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Double totalCalories = 0.0;
    private Double effectiveCalories;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String value;
    private String date, time;
    //private Spinner spinnerMain, spinnerMeat, spinnerFruit;
    private TextView caloryValue;
    private OnFragmentInteractionListener mListener;
    View v;

    private Map<String, Double> foodDictionary = new HashMap<String, Double>(){{ //the dictionary where all the food items are stored
        put("Apple", 0.52);
        put("Avocado", 1.60);
        put("Banana",0.89);
        put("Beet-root",0.43);
        put("Beef",2.29);
        put("Bread",2.65);
        put("Butter", 7.34);
        put("Cabbage",0.25);
        put("Carrot",0.41);
        put("Cheese",2.49);
        put("Chicken",0.99);
        put("Corn",0.86);
        put("Egg",1.55);
        put("Eggplant",0.25);
        put("Fish",2.06);
        put("Grapes",0.67);
        put("Green-Beans",0.31);
        put("Kidney-Beans",3.33);
        put("Mango",0.60);
        put("Milk",0.67);
        put("Mutton",1.28);
        put("Noodles",1.38);
        put("Orange",0.47);
        put("Papaya",0.43);
        put("Peas",0.81);
        put("Pineapple",0.50);
        put("Pork",1.70);
        put("Potato",0.77);
        put("Rice",1.30);
        put("Spinach",0.23);
        put("Turnip",0.29);
        put("Yogurt",0.97);
        put("Watermelon",0.30);
    }};

    public FoodIntakeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FoodIntakeFragment newInstance(MainActivity mainActivity) {
        FoodIntakeFragment fragment = new FoodIntakeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        date = timeStamp.substring(0,10);
        time = timeStamp.substring(11);
        if(time =="00.00.00"){
            totalCalories =0.0;
        }
        v= inflater.inflate(R.layout.fragment_food_intake, container, false);
        caloryValue = (TextView) v.findViewById(R.id.caloriesValue);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerFoodItems);
        spinner.setOnItemSelectedListener(this);
        FirebaseHandler firebaseHandler = new FirebaseHandler();
        firebaseHandler.readValue("user001","calorie_cnt",caloryValue);
        //Adding Manual Values of the calories intake
        final EditText manualInputCalories = (EditText) v.findViewById(R.id.CaloriesPerItem); //---------------manual input
        Button button = (Button) v.findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText manualInputCalories = (EditText) ((View)v.getParent()).findViewById(R.id.CaloriesPerItem);
                TextView view = ((View)v.getParent()).findViewById(R.id.caloriesValue);
                Integer temp1 = Integer.parseInt(manualInputCalories.getText().toString());
                totalCalories += effectiveCalories*temp1;
                //view.setText(manualInputCalories.getText());
                view.setText((totalCalories).toString());
                FirebaseHandler firebaseHandler = new FirebaseHandler();
                firebaseHandler.writeValue("user001","calorie_cnt",Double.toString(totalCalories));
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(), R.array.main_staple_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String food = (String) parent.getItemAtPosition(position);
        effectiveCalories = foodDictionary.get(food);
        //caloryValue.setText(String.valueOf(calories)); //----------------------------------------this is where total calories are displayed
        //caloryValue.setText(String.valueOf(value));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
