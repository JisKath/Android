package com.toolbartabs.toolbartabs.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toolbartabs.toolbartabs.Activities.MainActivity;
import com.toolbartabs.toolbartabs.R;

import java.util.Timer;

import static com.toolbartabs.toolbartabs.Activities.MainActivity.BufferInFlag;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.BufferInW;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.tabPosition;

public class SecondFragment extends Fragment {
    EditText IdBufferIn;
    TextView IdTxtMonitor;
    Button IdBtnEnviar, IdBtnBorrar;
    public static String  Monitor;
    private CountDownTimer TimerSF;

    public SecondFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        TimerSF = new
                CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (tabPosition == 2) {

                            if (BufferInFlag) {
                                String BufferTemp = IdTxtMonitor.getText() + Monitor;
                                IdTxtMonitor.setText(BufferTemp + "\n");
                                BufferInFlag = false;
                            }
                        }

                    }


                    @Override
                    public void onFinish() {
                        TimerSF.start();

                    }
                }.start();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_second, container, false);

        IdBufferIn = (EditText) view.findViewById(R.id.IdBufferIn);
        IdTxtMonitor = (TextView) view.findViewById(R.id.IdTxtMonitor);
        IdBtnEnviar = (Button) view.findViewById(R.id.IdBtnEnviar);
        IdBtnBorrar = (Button) view.findViewById(R.id.IdBtnBorrar);

        IdBtnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IdTxtMonitor.setText(String.valueOf(IdTxtMonitor.getText()) + IdBufferIn.getText() + "\n");
                String Temporal = String.valueOf(IdBufferIn.getText());
                new MainActivity.SendMessageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,String.valueOf(Temporal));

            }
        });
        IdBtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferInW= "";
                IdTxtMonitor.setText("");
            }
        });



        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
        TimerSF.cancel();
    }

}

