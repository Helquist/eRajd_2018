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


public class VolleyballResultsFragment extends Fragment {


    public View text;
    public View currentGame;
    public View rootView;



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

        container.addView(new DrawView(this.getActivity()));



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

            underlineView(rootView.findViewById(R.id.quarter_team1), canvas);
            underlineView(rootView.findViewById(R.id.quarter_team2), canvas);
            underlineView(rootView.findViewById(R.id.semi_team1), canvas);
            connectView(rootView.findViewById(R.id.quarter_team1), rootView.findViewById(R.id.semi_team1), canvas);
            connectView(rootView.findViewById(R.id.quarter_team2), rootView.findViewById(R.id.semi_team1), canvas);


        }

        public void underlineView(View view, Canvas canvas){
            int startX = view.getLeft();
            int startY = view.getBottom() + currentGame.getHeight();
            int stopX = view.getRight();
            int stopY = view.getBottom() + currentGame.getHeight();

            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        public void connectView(View view1, View view2, Canvas canvas){

            int startX = view1.getRight();
            int startY = view1.getBottom() + currentGame.getHeight();
            int stopX = view2.getLeft();
            int stopY = view1.getBottom() + currentGame.getHeight();
            canvas.drawLine(startX, startY, stopX, stopY, paint);

            startX = view2.getLeft();
            startY = view1.getBottom() + currentGame.getHeight();
            stopX = view2.getLeft();
            stopY = view2.getBottom() + currentGame.getHeight();
            canvas.drawLine(startX, startY, stopX, stopY, paint);




        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentWidth, parentHeight);
        }




    }

}
