package com.example.erajd_2018;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class VolleyballApplicationFragment extends Fragment implements View.OnClickListener{

    private EditText teamName;
    private EditText name1; private EditText surname1; private EditText phone1;
    private EditText name2; private EditText surname2; private EditText phone2;
    private EditText name3; private EditText surname3; private EditText phone3;
    private EditText name4; private EditText surname4; private EditText phone4;
    private EditText name5; private EditText surname5; private EditText phone5;
    private EditText name6; private EditText surname6; private EditText phone6;
    private Button sendButton;
    private FirebaseDatabase database;
    private DatabaseReference teamRef;

    public VolleyballApplicationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_volleyball_application, container, false);
        database = FirebaseDatabase.getInstance();

        sendButton = (Button) rootView.findViewById(R.id.submit_volleyball_team);
        sendButton.setOnClickListener(this);

        teamName = rootView.findViewById(R.id.nazwa_text_field);
        name1 = rootView.findViewById(R.id.editText_name1);
        surname1 = rootView.findViewById(R.id.editText_surname1);
        phone1 = rootView.findViewById(R.id.editText_phone1);
        name2 = rootView.findViewById(R.id.editText_name2);
        surname2 = rootView.findViewById(R.id.editText_surname2);
        phone2 = rootView.findViewById(R.id.editText_phone2);
        name3 = rootView.findViewById(R.id.editText_name3);
        surname3 = rootView.findViewById(R.id.editText_surname3);
        phone3 = rootView.findViewById(R.id.editText_phone3);
        name4 = rootView.findViewById(R.id.editText_name4);
        surname4 = rootView.findViewById(R.id.editText_surname4);
        phone4 = rootView.findViewById(R.id.editText_phone4);
        name5 = rootView.findViewById(R.id.editText_name5);
        surname5 = rootView.findViewById(R.id.editText_surname5);
        phone5 = rootView.findViewById(R.id.editText_phone5);
        name6 = rootView.findViewById(R.id.editText_name6);
        surname6 = rootView.findViewById(R.id.editText_surname6);
        phone6 = rootView.findViewById(R.id.editText_phone6);

        return rootView;
    }

    public boolean validateForm(){
        boolean valid = true;
        if(!validateField(teamName)) valid=false;
        if(!validateField(name1)) valid=false;
        if(!validateField(surname1)) valid=false;
        if(!validateField(phone1)) valid=false;
        if(!validateField(name2)) valid=false;
        if(!validateField(surname2)) valid=false;
        if(!validateField(phone2)) valid=false;
        if(!validateField(name3)) valid=false;
        if(!validateField(surname3)) valid=false;
        if(!validateField(phone3)) valid=false;
        if(!validateField(name4)) valid=false;
        if(!validateField(surname4)) valid=false;
        if(!validateField(phone4)) valid=false;
        if(!validateField(name5)) valid=false;
        if(!validateField(surname5)) valid=false;
        if(!validateField(phone5)) valid=false;
        if(!validateField(name6)) valid=false;
        if(!validateField(surname6)) valid=false;
        if(!validateField(phone6)) valid=false;
        return valid;
    }

    public boolean validateField(TextView field){
        boolean valid = true;
        String text = field.getText().toString();
        if (TextUtils.isEmpty(text)) {
            field.setError("Required.");
            valid = false;
        } else {
            field.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.submit_volleyball_team:
                if(validateForm()){
                    teamRef = database.getReference(String.valueOf(teamName.getText()));
                    teamRef.setValue("aaa");
                    Toast.makeText(getActivity().getApplicationContext(), "valid", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



}
