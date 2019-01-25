package com.toolbartabs.toolbartabs.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.toolbartabs.toolbartabs.Activities.MainActivity;
import com.toolbartabs.toolbartabs.Adapters.MyAdapter2;
import com.toolbartabs.toolbartabs.R;

import java.util.ArrayList;
import java.util.List;

import static com.toolbartabs.toolbartabs.Activities.Comandos.Listrall;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.BufferInFlag;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.BufferInW;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.CmdSnd;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.Step;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.Trans;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.altoTest;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.indice;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.intentosEstado;

public class ThirdFragment extends Fragment {

    static public List<String> names;
    static public List<String> estadoDisp;
    static String CmdRcvBuffer;
    public int dispSta;
    private ListView listView;
    private CountDownTimer Tick;
    private boolean triggerRcvn=true;



    public ThirdFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        estadoDisp = new ArrayList<String>();

        View view = inflater.inflate(R.layout.fragment_third, container, false);


        listView = (ListView) view.findViewById(R.id.IdListView);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



        Tick = new CountDownTimer(5000, 150) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (BufferInFlag & Step == 0) {
                    BufferInFlag = false;

                    if (CmdSnd & altoTest) {

                        CmdRcvBuffer = BufferInW;
                        names = Listrall(CmdRcvBuffer);


                        CmdSnd = false;
                        BufferInW = "";
                        altoTest = false;
                        Step = 1;
                    }
                }

                if (Step == 1) {


                    //BufferInFlag = false;

                    dispSta=2;

                    if (names.get(indice).length() > 4) {

                        if(triggerRcvn)
                        {
                            String Adrr="_rcvn["+indice+"]";
                            new MainActivity.SendMessageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,String.valueOf(Adrr));

                            triggerRcvn=false;
                        }

                        if (BufferInFlag) {
                            BufferInFlag = false;
                            String Temp;
                            if(BufferInW.contains("Falla")){
                                Temp = "Resp del Dispositivo: 3";
                            }
                            else {
                                Temp = BufferInW.substring(BufferInW.indexOf("Resp del Dispositivo: "), BufferInW.length());
                            }
                            if(Temp.contains(" 1")){
                                dispSta=1;
                            }
                            if(Temp.contains(" 0")){
                                dispSta=0;
                            }

                            if(Temp.contains(" 3")){
                                dispSta=3;
                            }

                            triggerRcvn=true;

                            estadoDisp.add(String.valueOf(dispSta));
                            indice++;
                            BufferInW="";
                        }


                    }
                    else{
                        estadoDisp.add(String.valueOf(dispSta));
                        indice++;
                    }

                        if (indice == names.size()) {
                            Step = 2;
                        }

                    CmdSnd = false;
                    BufferInW = "";

                }

                if (Step == 2) {

                    MyAdapter2 myAdapter2 = new MyAdapter2(getContext(), R.layout.list_item1, names);
                    listView.setAdapter(myAdapter2);

                    Step = 0;

                }

                if (Step == 3) {
                    intentosEstado++;

                    if (BufferInFlag) {
                        BufferInFlag = false;

                        if(BufferInW.contains("ok...")){
                            //Toast.makeText(getContext(), "Conexion Ok", Toast.LENGTH_LONG).show();


                            Step = 2;
                            BufferInW="";
                        }
                    }

                    if (intentosEstado>35){
                        Step=2; BufferInW=""; intentosEstado=0;Trans=0;
                    }



                    }

                if (Step == 4) {
                    intentosEstado++;

                    if (BufferInFlag) {
                        BufferInFlag = false;

                        if(BufferInW.contains("ok...")){
                            //Toast.makeText(getContext(), "Conexion Ok", Toast.LENGTH_LONG).show();

                            Step = 2;
                            BufferInW="";
                        }

                    }

                    if (intentosEstado>35){
                        Step=2; BufferInW=""; intentosEstado=0; Trans=0;
                    }
                }
            }

            @Override
            public void onFinish() {
                Tick.start();

            }
        }.start();


    }
}
