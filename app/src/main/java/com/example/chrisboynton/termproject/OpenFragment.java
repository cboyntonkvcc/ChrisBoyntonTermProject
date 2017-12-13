package com.example.chrisboynton.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by chrisboynton on 12/12/17.
 */

public class OpenFragment extends Fragment {

    private Button mContactButton;
    private Button mRandomButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_open,container,false);

        mContactButton = (Button) view.findViewById(R.id.send_contact_button);
        mRandomButton = (Button) view.findViewById(R.id.send_quick_text_button);

        mRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TextActivity.class);
                startActivity(intent);

            }
        });

        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);

            }
        });




        return view;


    }




}
