package com.example.rteymouri.fifa18prediction;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rteymouri.fifa18prediction.PredictionsFragment.OnListFragmentInteractionListener;
import com.example.rteymouri.fifa18prediction.footballMatchDataModel.FootballMatch;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FootballMatch} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<FootballMatch> mValues;
    private final OnListFragmentInteractionListener mListener;
    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            FootballMatch data = dataSnapshot.getValue(FootballMatch.class);
            Log.d("Fifaa", "DataSnapshot: " + data);
            mValues.add(new FootballMatch(
                    data.getTeam1_name(),
                    data.getTeam1_score(),
                    data.getTeam2_name(),
                    data.getTeam2_score()
            ));
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        mListener = listener;
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("results");
        mDatabaseRef.addChildEventListener(mChildEventListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTeam1NameView.setText(mValues.get(position).getTeam1_name());
        holder.mTeam2NameView.setText(mValues.get(position).getTeam2_name());
        holder.mTeam1ScoreView.setText(mValues.get(position).getTeam1_score().toString());
        holder.mTeam2ScoreView.setText(mValues.get(position).getTeam2_score().toString());

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
        public FootballMatch mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeam1NameView = (TextView) view.findViewById(R.id.team1_name);
            mTeam2NameView = (TextView) view.findViewById(R.id.team2_name);
            mTeam1ScoreView = (TextView) view.findViewById(R.id.team1_score);
            mTeam2ScoreView = (TextView) view.findViewById(R.id.team2_score);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeam1NameView.getText() + mTeam2NameView.getText()  + "'";
        }
    }
}
