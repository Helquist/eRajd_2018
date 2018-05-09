package com.example.erajd_2018;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Day1Fragment extends Fragment {

    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";

    public Day1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        ListView listView=(ListView)rootView.findViewById(R.id.listViewDay1);
        ListViewAdapter adapter;

        //TODO dodać listener bazy
        populateList();
        adapter=new ListViewAdapter((getActivity()), list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                String dsc = list.get(position).get(THIRD_COLUMN);
                Toast.makeText(getActivity().getApplicationContext(), dsc, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void populateList() {
        // TODO Auto-generated method stub

        list=new ArrayList<HashMap<String,String>>();

        HashMap<String,String> hashmap=new HashMap<String, String>();
        hashmap.put(FIRST_COLUMN, "9:00");
        hashmap.put(SECOND_COLUMN, "Karmienie");
        hashmap.put(THIRD_COLUMN, "Karmienie opis");

        list.add(hashmap);

        HashMap<String,String> hashmap2=new HashMap<String, String>();
        hashmap2.put(FIRST_COLUMN, "10:30");
        hashmap2.put(SECOND_COLUMN, "Siatkówka");
        hashmap2.put(THIRD_COLUMN, "Siatkówka opis");

        list.add(hashmap2);

        HashMap<String,String> hashmap3=new HashMap<String, String>();
        hashmap3.put(FIRST_COLUMN, "13:30");
        hashmap3.put(SECOND_COLUMN, "Karmienie v2");
        hashmap3.put(THIRD_COLUMN, "Karmienie v2 opis");

        list.add(hashmap3);

        HashMap<String,String> hashmap4=new HashMap<String, String>();
        hashmap4.put(FIRST_COLUMN, "15:30");
        hashmap4.put(SECOND_COLUMN, "Piłka nożna");
        hashmap4.put(THIRD_COLUMN, "Piłka nożna opis");

        list.add(hashmap4);

        HashMap<String,String> hashmap5=new HashMap<String, String>();
        hashmap5.put(FIRST_COLUMN, "18:15");
        hashmap5.put(SECOND_COLUMN, "Karmienie v3");
        hashmap5.put(THIRD_COLUMN, "Karmienie v3 opis");

        list.add(hashmap5);

        HashMap<String,String> hashmap6=new HashMap<String, String>();
        hashmap6.put(FIRST_COLUMN, "19:45");
        hashmap6.put(SECOND_COLUMN, "Karczma");
        hashmap6.put(THIRD_COLUMN, "Karczma opis");

        list.add(hashmap6);

        HashMap<String,String> hashmap7=new HashMap<String, String>();
        hashmap7.put(FIRST_COLUMN, "22:00");
        hashmap7.put(SECOND_COLUMN, "Cisza nocna xD");
        hashmap7.put(THIRD_COLUMN, "Karczma opis");

        list.add(hashmap7);

    }

}
