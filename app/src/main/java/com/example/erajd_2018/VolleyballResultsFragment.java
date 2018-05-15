package com.example.erajd_2018;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


public class VolleyballResultsFragment extends Fragment {


    public View text;
    public View currentGame;
    public View rootView;
    public ViewGroup tournamentBracketView;
    private FirebaseDatabase database;


    public VolleyballResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_volleyball_results, container, false);
        currentGame = rootView.findViewById(R.id.currentGame);
        tournamentBracketView = rootView.findViewById(R.id.tournament_bracket);
        tournamentBracketView.addView(new DrawView(this.getActivity()));

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gamesVolleyball");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cGame =  dataSnapshot.child("currentGame").getValue().toString();
                TextView cTeam1 = rootView.findViewById(R.id.currentTeam1);
                cTeam1.setText(dataSnapshot.child(cGame).child("team1").getValue().toString());
                TextView cTeam2 = rootView.findViewById(R.id.currentTeam2);
                cTeam2.setText(dataSnapshot.child(cGame).child("team2").getValue().toString());
                TextView cTeam1Score = rootView.findViewById(R.id.score1);
                cTeam1Score.setText(dataSnapshot.child(cGame).child("score1").getValue().toString());
                TextView cTeam2Score = rootView.findViewById(R.id.score2);
                cTeam2Score.setText(dataSnapshot.child(cGame).child("score2").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //container.addView(new DrawView(this.getActivity()));



        return rootView;
    }


    private class DrawView extends View {
        Paint paint = new Paint();

        public DrawView(Context context) {
            super(context);
            paint.setColor(Color.BLACK);
        }
        public DrawView(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setColor(Color.BLACK);
        }
        public DrawView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            paint.setColor(Color.BLACK);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            paint.setStrokeWidth(8);
            super.onDraw(canvas);

            underlineView(tournamentBracketView.findViewById(R.id.quarter_team1), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team2), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team3), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team4), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team5), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team6), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team7), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.quarter_team8), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.semi_team1), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.semi_team2), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.semi_team3), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.semi_team4), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.final_team1), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.final_team2), canvas);
            underlineView(tournamentBracketView.findViewById(R.id.winner_team), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team1), tournamentBracketView.findViewById(R.id.semi_team1), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team2), tournamentBracketView.findViewById(R.id.semi_team1), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team3), tournamentBracketView.findViewById(R.id.semi_team2), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team4), tournamentBracketView.findViewById(R.id.semi_team2), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team5), tournamentBracketView.findViewById(R.id.semi_team3), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team6), tournamentBracketView.findViewById(R.id.semi_team3), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team7), tournamentBracketView.findViewById(R.id.semi_team4), canvas);
            connectView(tournamentBracketView.findViewById(R.id.quarter_team8), tournamentBracketView.findViewById(R.id.semi_team4), canvas);
            connectView(tournamentBracketView.findViewById(R.id.semi_team1), tournamentBracketView.findViewById(R.id.final_team1), canvas);
            connectView(tournamentBracketView.findViewById(R.id.semi_team2), tournamentBracketView.findViewById(R.id.final_team1), canvas);
            connectView(tournamentBracketView.findViewById(R.id.semi_team3), tournamentBracketView.findViewById(R.id.final_team2), canvas);
            connectView(tournamentBracketView.findViewById(R.id.semi_team4), tournamentBracketView.findViewById(R.id.final_team2), canvas);
            connectView(tournamentBracketView.findViewById(R.id.final_team1), tournamentBracketView.findViewById(R.id.winner_team), canvas);
            connectView(tournamentBracketView.findViewById(R.id.final_team2), tournamentBracketView.findViewById(R.id.winner_team), canvas);



        }

        public void underlineView(View view, Canvas canvas){
            int startX = view.getLeft();
            int startY = view.getBottom();
            int stopX = view.getRight();
            int stopY = view.getBottom();

            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        public void connectView(View view1, View view2, Canvas canvas){

            //Pozioma linia
            int startX = view1.getRight();
            int startY = view1.getBottom();
            int stopX = view2.getLeft()-10;
            int stopY = view1.getBottom();
            canvas.drawLine(startX, startY, stopX, stopY, paint);

            //pionowa linia
            startX = view2.getLeft()-10;
            startY = view1.getBottom();
            stopX = view2.getLeft()-10;
            stopY = view2.getBottom();
            canvas.drawLine(startX, startY, stopX, stopY, paint);

            //Pozioma linia
            startX = view2.getLeft()-10;
            startY = view2.getBottom();
            stopX = view2.getLeft();
            stopY = view2.getBottom();
            canvas.drawLine(startX, startY, stopX, stopY, paint);


        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int parentWidth = MeasureSpec.getSize(widthMeasureSpec)+1000;
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentWidth, parentHeight);
        }




    }

}
