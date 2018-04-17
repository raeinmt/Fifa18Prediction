package com.example.rteymouri.fifa18prediction;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rteymouri.fifa18prediction.R;
import com.example.rteymouri.fifa18prediction.footballMatchDataModel.FootballMatch;
import com.example.rteymouri.fifa18prediction.footballMatchDataModel.ResultsMatch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private Button mAdminCreate;
    private EditText mFirstTeamView;
    private EditText mFirstScoreView;
    private EditText mSecondTeamView;
    private EditText mSecondScoreView;
    private EditText mMatchDateTimeView;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private ResultsMatch resultGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAdminCreate = findViewById(R.id.admin_create);
        mFirstTeamView = findViewById(R.id.first_team);
        mFirstScoreView = findViewById(R.id.first_score);
        mSecondTeamView = findViewById(R.id.second_team);
        mSecondScoreView = findViewById(R.id.second_score);
        mMatchDateTimeView = findViewById(R.id.match_datetime);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("admin_raein@gmail.com","123456");

        mAdminCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first_team_name = mFirstTeamView.getText().toString();
                String second_team_name = mSecondTeamView.getText().toString();
                int first_team_score = Integer.parseInt(mFirstScoreView.getText().toString());
                int second_team_score = Integer.parseInt(mSecondScoreView.getText().toString());
                String match_date_time = mMatchDateTimeView.getText().toString();



                resultGame = new ResultsMatch(
                        first_team_name,
                        first_team_score,
                        second_team_name,
                        second_team_score,
                        match_date_time
                );

                Map<String, Object> resultsUpdates = new HashMap<>();
                String resultsKey = resultGame.toString();
                resultsUpdates.put(resultsKey, resultGame);
                mDatabaseRef.child("results").updateChildren(resultsUpdates);
                Log.d("Fifaa","Check db");

//                mDatabaseRef.child("users").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot d: dataSnapshot.getChildren()) {
//
//                            Map<String, Object> predictionsUpdates = new HashMap<>();
//
//                            String predictionKey = d.getKey() +"/"+ resultGame.toString();
//                            Log.d("Fifaa", "Prediction:" + mDatabaseRef.child("predictions").child(predictionKey).child("team1_actual_score"));
////                            if (d.child(predictionKey).exists()){
//                                mDatabaseRef.child("predictions").child(predictionKey).child("team1_actual_score").setValue(resultGame.getTeam1_score());
//                                mDatabaseRef.child("predictions").child(predictionKey).child("team2_actual_score").setValue(resultGame.getTeam2_score());
////                            }
////                            else {
////                                predictionsUpdates.put(predictionKey,resultGame);
////                                mDatabaseRef.child("predictions").updateChildren(predictionsUpdates);
//////                                Log.d("Fifaa","==>" + mDatabaseRef.child("predictions").child(predictionKey));
////                            }
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
//        private void createData (){
//
//            FootballMatch predictionGame = new FootballMatch("Australia",3,"Spain",2, 1,4,"2018-01-17 11:00");
//            FootballMatch resultGame = new FootballMatch("Australia",14,"Spain",5, 4,1,"2018-01-18 17:00");
//
//            Map<String, Object> predictionsUpdates = new HashMap<>();
//            String predictionKey = FirebaseAuth.getInstance().getCurrentUser().getUid() +"/"+ predictionGame.toString();
//            predictionsUpdates.put(predictionKey,predictionGame);
//            mDatabaseRef.child("predictions").updateChildren(predictionsUpdates);
//
//            Map<String, Object> resultsUpdates = new HashMap<>();
//            String resultsKey = resultGame.toString();
//            resultsUpdates.put(resultsKey, resultGame);
//            mDatabaseRef.child("results").updateChildren(resultsUpdates);
//
//        }

    }

}
