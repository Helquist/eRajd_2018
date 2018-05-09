package com.example.erajd_2018;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Day2Fragment extends Fragment {

    private ArrayList<HashMap<String, String>> list;
    public static final String TIME="Time";
    public static final String NAME="Name";
    public static final String DSC="Description";
    private FirebaseDatabase database;
    private DatabaseReference myRefDay2;

    public Day2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);
        database = FirebaseDatabase.getInstance();
        myRefDay2 = database.getReference("plan_d2");

        final ListView listView=(ListView)rootView.findViewById(R.id.listViewDay1);
        ListViewAdapter adapter;

        // Read from the database
        myRefDay2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                populateList(dataSnapshot);
                ListViewAdapter adapter=new ListViewAdapter((getActivity()), list);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                String dsc = list.get(position).get(DSC);
                Toast.makeText(getActivity().getApplicationContext(), dsc, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void populateList(DataSnapshot dataSnapshot) {
        // TODO Auto-generated method stub

        list=new ArrayList<HashMap<String,String>>();

        long childrenCount = dataSnapshot.getChildrenCount();

        for(long i=0; i<childrenCount; i++){
            HashMap<String,String> hashmap=new HashMap<String, String>();
            DataSnapshot event = dataSnapshot.child(String.valueOf(i));
            String time = event.child("time").getValue(String.class);
            String name = event.child("name").getValue(String.class);
            String dsc = event.child("description").getValue(String.class);
            hashmap.put(TIME, time);
            hashmap.put(NAME, name);
            hashmap.put(DSC, dsc);

            list.add(hashmap);
        }



    }

}
