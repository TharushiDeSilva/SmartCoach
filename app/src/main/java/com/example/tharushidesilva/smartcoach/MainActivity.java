package com.example.tharushidesilva.smartcoach;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private View mainView;
    private ArrayList<Fragment> fragments;
    int curr_step_cnt = 0;
    int rec_step_cnt = 0;
    String userName = "";
    double curr_calorie_cnt = 0;
    double rec_calorie_cnt = 0;
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    public void notifyChange(){
        ImageView calCountView = (ImageView) fragments.get(0).getActivity().findViewById(R.id.icon_calories);
        ImageView stepCountView = (ImageView) fragments.get(0).getActivity().findViewById(R.id.icon_steps);
        if(curr_calorie_cnt > rec_calorie_cnt){
            EmailSender emailSender = new EmailSender();
            emailSender.sendMail("tharushid.14@cse.mrt.ac.lk",
                    "Calorie count exceeded!","User "+userName+ "has exceeded the recommended calorie count! "+"Current calorie count:"
                    +curr_calorie_cnt,this);
            PlaceholderFragment placeholderFragment = (PlaceholderFragment) fragments.get(4);

            calCountView.setImageResource(R.drawable.negative_icon);
        }else{
            calCountView.setImageResource(R.drawable.positive_icon);
        }
        if(curr_step_cnt < rec_step_cnt){
            stepCountView.setImageResource(R.drawable.negative_icon);
        }else{
            stepCountView.setImageResource(R.drawable.positive_icon);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Adding the Fragments to the Array
        fragments = new ArrayList<>();
        PlaceholderFragment placeholderFragment = PlaceholderFragment.newInstance(0);
        fragments.add(placeholderFragment);

        StepCountFragment fragmentStepCounter = StepCountFragment.newInstance();
        fragments.add(fragmentStepCounter);
        ActivityFragment fragmentActivity = ActivityFragment.newInstance();
        fragments.add(fragmentActivity);
        FoodIntakeFragment fragmentFoodIntake = FoodIntakeFragment.newInstance(this);
        fragments.add(fragmentFoodIntake);
        FirebaseHandler firebaseHandler = new FirebaseHandler();
        firebaseHandler.updateMainActivity("user001",this);

        Intent alarmIntent = new Intent(this, Alarm.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        startAlarm();

    }

    public void startAlarm() {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Date now = Calendar.getInstance().getTime();
        Date tonight = new Date(now.getYear(),now.getMonth(),now.getDate(),23,59,59);
        long interval = tonight.getTime() - now.getTime();
        //interval = 1000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+interval, 60000, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView stepCntTxt = (TextView) rootView.findViewById(R.id.step_count);
            TextView calorieCntTxt = (TextView) rootView.findViewById(R.id.calorie_count);
            FirebaseHandler firebaseHandler = new FirebaseHandler();
            //stepCntTxt.setText("TXTTXT");
            firebaseHandler.readValue("user001","step_cnt",stepCntTxt);
            firebaseHandler.readValue("user001","calorie_cnt",calorieCntTxt);


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            Fragment f = PlaceholderFragment.newInstance(position + 1);
//            if(position==0){
//                if(fragments.size()<4){
//                    f = PlaceholderFragment.newInstance(position + 1);
//                    fragments.add(4,f);
//                }else {
//                    f= fragments.get(4);
//                }
//
//
//            }
//            if(position==1){
//                f = fragments.get(0);
//            }
//            else if(position==2){
//                f = fragments.get(1);
//            }
//            else if(position==3){
//                f = fragments.get(2);
//            }
//            else if(position==4){
//                f = fragments.get(3);
//            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
