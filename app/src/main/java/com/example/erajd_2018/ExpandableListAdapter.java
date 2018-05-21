package com.example.erajd_2018;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.erajd_2018.TestActivity.PICK_IMAGE;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, String> _listDataChild;
    private ChallengeActivity activity;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, String> listChildData, ChallengeActivity activity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.activity = activity;
    }



    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final Button submit_button = convertView.findViewById(R.id.submit_photo);


        final Button select_button = convertView.findViewById(R.id.select_photo);
        select_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                activity.selectPicture();
                submit_button.setEnabled(true);

            }
        });



        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                EditText teamTextField = parent.findViewById(R.id.team);
                String team = teamTextField.getText().toString();
                if (TextUtils.isEmpty(team)) {
                    teamTextField.setError("Required.");
                } else {
                    teamTextField.setError(null);
                    activity.uploadPicture(team);
                }


            }
        });
        //TODO Zawartość!!!!!
        //TextView txtListChild = (TextView) convertView
        //        .findViewById(R.id.lblListItem);
        //txtListChild.setText(childText);


        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        String points = _listDataChild.get(headerTitle);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        //TODO NAGŁÓWEK!!!!!
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.challenge_name);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        TextView pointsTextView = (TextView) convertView
                .findViewById(R.id.points);
        pointsTextView.setText(points + "pkt");

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}