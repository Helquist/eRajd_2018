package com.example.erajd_2018;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Day2Fragment extends Fragment {

    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";

    public Day2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        ListView listView=(ListView)rootView.findViewById(R.id.listViewDay1);
        populateList();
        ListViewAdapter adapter=new ListViewAdapter((getActivity()), list);
        listView.setAdapter(adapter);

        return rootView;
    }

    private void populateList() {
        // TODO Auto-generated method stub

        list=new ArrayList<HashMap<String,String>>();

        HashMap<String,String> hashmap=new HashMap<String, String>();
        hashmap.put(FIRST_COLUMN, "9:00");
        hashmap.put(SECOND_COLUMN, "Karmienie");

        list.add(hashmap);

        HashMap<String,String> hashmap2=new HashMap<String, String>();
        hashmap2.put(FIRST_COLUMN, "10:30");
        hashmap2.put(SECOND_COLUMN, "Siatkówka");

        list.add(hashmap2);

        HashMap<String,String> hashmap3=new HashMap<String, String>();
        hashmap3.put(FIRST_COLUMN, "13:30");
        hashmap3.put(SECOND_COLUMN, "Karmienie v2");

        list.add(hashmap3);

        HashMap<String,String> hashmap4=new HashMap<String, String>();
        hashmap4.put(FIRST_COLUMN, "15:30");
        hashmap4.put(SECOND_COLUMN, "Piłka nożna");

        list.add(hashmap4);

        HashMap<String,String> hashmap5=new HashMap<String, String>();
        hashmap5.put(FIRST_COLUMN, "18:15");
        hashmap5.put(SECOND_COLUMN, "Karmienie v3");

        list.add(hashmap5);

        HashMap<String,String> hashmap6=new HashMap<String, String>();
        hashmap6.put(FIRST_COLUMN, "19:45");
        hashmap6.put(SECOND_COLUMN, "Karczma");

        list.add(hashmap6);

        HashMap<String,String> hashmap7=new HashMap<String, String>();
        hashmap7.put(FIRST_COLUMN, "22:00");
        hashmap7.put(SECOND_COLUMN, "Cisza nocna xD");

        list.add(hashmap7);

    }

}
