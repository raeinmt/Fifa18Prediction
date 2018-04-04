package com.example.rteymouri.fifa18prediction;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rteymouri.fifa18prediction.PredictionsFragment.OnListFragmentInteractionListener;
import com.example.rteymouri.fifa18prediction.footballMatchDataModel.FootballMatch;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FootballMatch} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<FootballMatch> mValues;
    private ConcurrentHashMap<String, FootballMatch> mValuesHashMap;
    private final OnListFragmentInteractionListener mListener;
    private final DatabaseReference mDatabaseRef;

    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Log.d("Fifaa", "DataSnapshot: " + dataSnapshot);
            FootballMatch data = dataSnapshot.getValue(FootballMatch.class);
            Log.d("Fifaa", "Data: " + data);
            mValues.add(data);
            mValuesHashMap.put(data.toString(),data);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            FootballMatch data = dataSnapshot.getValue(FootballMatch.class);
            Log.d("Fifaa", "DataSnapshot---: " + data);
            mValuesHashMap.put(data.toString(),data);
            notifyDataSetChanged();

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mValuesHashMap = new ConcurrentHashMap<>();
        mListener = listener;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseRef.child("predictions").child(userId).addChildEventListener(mChildEventListener);
//        createData();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValuesHashMap.get(mValues.get(position).toString());
        holder.mTeam1NameView.setText(holder.mItem.getTeam1_name());
        holder.mTeam2NameView.setText(holder.mItem.getTeam2_name());
        holder.mTeam1ScoreView.setText(holder.mItem.getTeam1_score().toString());
        holder.mTeam2ScoreView.setText(holder.mItem.getTeam2_score().toString());
        holder.mTeam1ActualScoreView.setText(holder.mItem.getTeam1_actual_score().toString());
        holder.mTeam2ActualScoreView.setText(holder.mItem.getTeam2_actual_score().toString());

        holder.mTestPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fifaa", "HELLO" + position);
//                createData();
                mDatabaseRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("Fifaa","In Button: "+snapshot);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        holder.mSubmitScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fifaa","In Button: "+holder.mTeam1ScoreView.getText());

                FootballMatch prediction = new FootballMatch(holder.mItem.getTeam1_name(),
                        Integer.parseInt(holder.mTeam1ScoreView.getText().toString()),
                        holder.mItem.getTeam2_name(),
                        Integer.parseInt(holder.mTeam2ScoreView.getText().toString()),
                        Integer.parseInt(holder.mTeam1ActualScoreView.getText().toString()),
                        Integer.parseInt(holder.mTeam2ActualScoreView.getText().toString()));


                Map<String, Object> predictionUpdates = new HashMap<>();
                String predictionKey = FirebaseAuth.getInstance().getCurrentUser().getUid() +"/"+ prediction.toString();
                predictionUpdates.put(predictionKey,prediction);

                mDatabaseRef.child("predictions").updateChildren(predictionUpdates);

            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private void createData (){

        FootballMatch predictionGame = new FootballMatch("England",3,"Japan",12, 1,4);
        FootballMatch resultGame = new FootballMatch("England",14,"Japan",15, 4,1);

        Map<String, Object> predictionsUpdates = new HashMap<>();
        String predictionKey = FirebaseAuth.getInstance().getCurrentUser().getUid() +"/"+ predictionGame.toString();
        predictionsUpdates.put(predictionKey,predictionGame);
        mDatabaseRef.child("predictions").updateChildren(predictionsUpdates);

        Map<String, Object> resultsUpdates = new HashMap<>();
        String resultsKey = resultGame.toString();
        resultsUpdates.put(resultsKey, resultGame);
        mDatabaseRef.child("results").updateChildren(resultsUpdates);
    }
    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mTeam1NameView;
        public final TextView mTeam2NameView;
        public final TextView mTeam1ScoreView;
        public final TextView mTeam2ScoreView;
        public final TextView mTeam1ActualScoreView;
        public final TextView mTeam2ActualScoreView;
        public final Button mSubmitScoreButton;
        //TODO: Move this to admin application
        public final Button mTestPush;
        public FootballMatch mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeam1NameView = view.findViewById(R.id.team1_name);
            mTeam2NameView = view.findViewById(R.id.team2_name);
            mTeam1ScoreView = view.findViewById(R.id.team1_score);
            mTeam2ScoreView = view.findViewById(R.id.team2_score);
            mTeam1ActualScoreView = view.findViewById(R.id.team1_actual_results);
            mTeam2ActualScoreView = view.findViewById(R.id.team2_actual_results);
            mSubmitScoreButton = view.findViewById(R.id.submit_score_button);
            mTestPush = view.findViewById(R.id.test_push);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeam1NameView.getText() + mTeam2NameView.getText()  + "'";
        }
    }
}
