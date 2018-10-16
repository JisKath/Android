package com.toolbartabs.toolbartabs.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.toolbartabs.toolbartabs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    TextView IdTextBTaddrs;
    Button IdBtnScanBT,IdBtnConectarBT;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_main, container, false);

        IdTextBTaddrs = (TextView) view.findViewById(R.id.IdTextBTaddrs);
        IdBtnConectarBT = (Button) view.findViewById(R.id.IdBtnConectarBT);
        IdBtnScanBT = (Button) view.findViewById(R.id.IdBtnScanBT);

        return view;
    }

}
