package com.toolbartabs.toolbartabs.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.toolbartabs.toolbartabs.Activities.MainActivity;
import com.toolbartabs.toolbartabs.R;

import static com.toolbartabs.toolbartabs.Activities.MainActivity.BufferInW;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.checkBeatW;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.devices;

public class FirstFragment extends Fragment {

    public static Fragment1Listener listener;

    public interface Fragment1Listener{
        void onInput1Conectar(String input);
        void onInput1Check(String input);

    }

    TextView IdTextBTaddrs;
    Button IdBtnScanBT,IdBtnConectarBT,IdBtnRescan;

    public FirstFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Fragment1Listener){
            listener=(Fragment1Listener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + "must implement Fragment1Listener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        IdTextBTaddrs = (TextView) view.findViewById(R.id.IdTextBTaddrs);
        IdBtnConectarBT = (Button) view.findViewById(R.id.IdBtnConectarBT);
        IdBtnScanBT = (Button) view.findViewById(R.id.IdBtnScanBT);
        IdBtnRescan = (Button) view.findViewById(R.id.IdBtnRescan);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();



        IdBtnScanBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInput1Conectar("A");
                listener.onInput1Check("A");
                IdTextBTaddrs.setText(BufferInW);
            }
        });

        IdBtnConectarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
