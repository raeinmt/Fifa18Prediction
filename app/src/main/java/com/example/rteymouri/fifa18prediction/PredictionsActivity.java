package com.example.rteymouri.fifa18prediction;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.example.rteymouri.fifa18prediction.dummy.DummyContent;
import com.example.rteymouri.fifa18prediction.footballMatchDataModel.FootballMatch;
import com.google.firebase.auth.FirebaseAuth;

public class PredictionsActivity extends AppCompatActivity implements
        PredictionsFragment.OnListFragmentInteractionListener,
        ResultsFragment.OnListFragmentInteractionListener {

    public  String SIGN_OUT= "Sign out";
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private ActionBar actionBar;
    NavigationView navigationView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictions);

        // Creating PredictionsFragment


        FragmentManager mFragmentManager = getSupportFragmentManager();
        PredictionsFragment mFragment = PredictionsFragment.newInstance(1);
//        ResultsFragment mFragment = ResultsFragment.newInstance(1);
        mFragment.getListAdapter();
//        Bundle args = new Bundle();
//        args.putInt("column-count", 1);
//        mFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_frame, mFragment);
        fragmentTransaction.commit();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);

        TextView header = navigationView.getHeaderView(0).findViewWithTag("123");
        header.setText(mAuth.getCurrentUser().getEmail());

        // Creating a toolbar and using it as an actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                actionBar.setTitle(item.getTitle().toString());
                //If sign out it tapped log out the user from Firebase and start login activity

                mDrawerLayout.closeDrawers();
                
                if (item.getTitle().toString().equals(SIGN_OUT)) {

                    mAuth.signOut();

                    Intent backToLogin = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(backToLogin);
                    finish();
                    return false;
                }
                //TODO: Here must use fragments to switch out the content view displayed in the background
                //TODO: https://developer.android.com/guide/components/fragments.html

                Log.d("Fifaa","title: "+ item.getTitle().toString());
                if(item.getTitle().toString().equals("Results Table")){

                    FragmentManager mFragmentManager = getSupportFragmentManager();
                    ResultsFragment mFragment = ResultsFragment.newInstance(1);
                    mFragment.getListAdapter();

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.content_frame, mFragment);
                    fragmentTransaction.commit();
                    return false;
                }

                if(item.getTitle().toString().equals("Home")){

                    FragmentManager mFragmentManager = getSupportFragmentManager();
                    PredictionsFragment mFragment = PredictionsFragment.newInstance(1);
                    mFragment.getListAdapter();

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.content_frame, mFragment);
                    fragmentTransaction.commit();
                    return false;
                }

                return true;
            }
        });


    }

    @Override
    public void onListFragmentInteraction(FootballMatch item) {
        Log.d("FIFAa","CALLING onListFragmentInteraction" + item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        Log.d("FIFAa","CALLING onListFragmentInteraction results" + item);

    }
}
