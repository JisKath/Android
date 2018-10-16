package com.toolbartabs.toolbartabs.Fragments;


import android.content.Context;
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
import android.widget.Toast;

import com.toolbartabs.toolbartabs.Activities.MainActivity;
import com.toolbartabs.toolbartabs.R;

import static com.toolbartabs.toolbartabs.Activities.MainActivity.devices;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.tabPosition;

public class FirstFragment extends Fragment {
    private ListView IdLista;
    private Datalistener callback;
    TextView IdTextBTaddrs;
    Button IdBtnScanBT,IdBtnConectarBT,IdBtnRescan;

    public FirstFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (Datalistener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "Should implement DataListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        IdLista = (ListView) view.findViewById(R.id.IdListViewA);
        IdLista.setOnItemClickListener(mDeviceClickListener);
        IdTextBTaddrs = (TextView) view.findViewById(R.id.IdTextBTaddrs);
        IdBtnConectarBT = (Button) view.findViewById(R.id.IdBtnConectarBT);
        IdBtnScanBT = (Button) view.findViewById(R.id.IdBtnScanBT);
        IdBtnRescan = (Button) view.findViewById(R.id.IdBtnRescan);

        //ArrayAdapter<String> mPairedDevicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, devices);
        //IdLista.setAdapter(mPairedDevicesArrayAdapter);

        IdTextBTaddrs.setText("Ehhh");
        //IdLista.setEnabled(false);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();



        IdBtnScanBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //IdLista.setEnabled(true);
                ArrayAdapter<String> mPairedDevicesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, devices);
                IdLista.setAdapter(mPairedDevicesArrayAdapter);

            }
        });

        IdBtnConectarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.sendData((String) IdTextBTaddrs.getText().toString());

            }
        });
    }

    // Configura un (on-click) para la lista
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {

            // Obtener la dirección MAC del dispositivo, que son los últimos 17 caracteres en la vista
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            //callback.sendData(address);
            IdTextBTaddrs.setText(address);

        }

    };


    public interface Datalistener {
        void sendData(String address);
    }

}
