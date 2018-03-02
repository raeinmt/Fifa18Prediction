package com.example.rteymouri.fifa18prediction;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PredictionsActivity extends AppCompatActivity {

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

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);

        TextView header = navigationView.getHeaderView(0).findViewWithTag("123");//.findViewById(R.id.navHeader);
        header.setText(mAuth.getCurrentUser().getEmail());

        Log.d("Fifaa","header: " + header);
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
                //If sign out it tapped log out the user from firebase and start login activity
                if (item.getTitle().toString().equals(SIGN_OUT)) {

                    mAuth.signOut();

                    Intent backToLogin = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(backToLogin);
                    finish();
                    return false;
                }
                mDrawerLayout.closeDrawers();

                //TODO: Here must use fragments to switch out the content view displayed in the background
                //TODO: https://developer.android.com/guide/components/fragments.html


                return true;
            }
        });


    }
}
