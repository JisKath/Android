package com.toolbartabs.toolbartabs.Activities;

import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.toolbartabs.toolbartabs.Adapters.PagerAdapter;
import com.toolbartabs.toolbartabs.Fragments.FirstFragment;
import com.toolbartabs.toolbartabs.Fragments.SecondFragment;
import com.toolbartabs.toolbartabs.Fragments.ThirdFragment;
import com.toolbartabs.toolbartabs.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static com.toolbartabs.toolbartabs.Fragments.SecondFragment.Monitor;

public class MainActivity extends AppCompatActivity implements FirstFragment.Datalistener {
    public static final int handlerState = 0;
    //1) ********************* Lineas para dar de alta BT ********************************
    // Depuración de LOGCAT
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static BluetoothSocket btSocket = null;

    // Declaracion de campos
    public static Handler bluetoothIn;
    public static boolean BufferInFlag, BT_Connected;
    public static StringBuilder DataStringIN = new StringBuilder();
    public static String BufferIn;
    public static boolean CmdSnd, altoTest=true;
    public static int Step=0, Trans=0,lastPos, indice = 0;
    public static BluetoothAdapter mBtAdapter;
    public static ConnectedThread MyConexionBT;
    boolean sinRegreso;

    public static ArrayList<String> devices = new ArrayList<>();
    public static int tabPosition=0, tabPosition0=99, tabPositionTF=99;
    private CountDownTimer Timer;

    // **********************************************************************************

    //1) ********************* Lineas para dar de alta BT ********************************
    public static BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1) ********************* Lineas para dar de alta BT ********************************
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBtAdapter == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Dispositivo soporta BT", Toast.LENGTH_SHORT).show();

            if (!mBtAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }

        }

        // **********************************************************************************

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

/*//   Pendiente dar de alta Menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);

        }*/



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Lista BT"));
        tabLayout.addTab(tabLayout.newTab().setText("Dispositivos"));
        tabLayout.addTab(tabLayout.newTab().setText("Monitor"));
//        tabLayout.addTab(tabLayout.newTab().setText("Escenas"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tabPosition = tab.getPosition();
                if (BT_Connected==false){
                    position=0;
                    tabPosition=0;
                    sinRegreso = false;
                    Toast.makeText(getBaseContext(), "Conectar a controlador", Toast.LENGTH_SHORT).show();
                }

                if((position== 1) & BT_Connected==true){
                    sinRegreso = true;
                }

                if(position==0 & sinRegreso==true){
                    position=1;
                }

                    viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    // **********************************************************************************

    @Override
    public void onResume() {
        super.onResume();

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf(";");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        BufferIn = (dataInPrint);//<-<- PARTE A MODIFICAR >->->
                        DataStringIN.delete(0, DataStringIN.length());
                        BufferInFlag = true;
                    }
                }
            }
        };

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {

            // Datos a Mostrar
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device.getName() + "\n" + device.getAddress());
            }
        }

        Timer = new

                CountDownTimer(30000, 50) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        if (tabPosition0!=tabPosition) {
//                            Toast.makeText(getBaseContext(), "Pagina " + tabPosition, Toast.LENGTH_SHORT).show();
                            tabPosition0=tabPosition;
                        }

                        if (tabPosition==0){

                            tabPositionTF = tabPosition;

                        }

                        if (tabPosition==1){

                            if (tabPositionTF!=tabPosition) {

                                MyConexionBT.write("_rall");
                                CmdSnd = true;
                                tabPositionTF = tabPosition;
                            }
                        }

                        if (tabPosition==2){

                            Monitor=BufferIn;
                            tabPositionTF = tabPosition;

                        }




                    }

                    @Override
                    public void onFinish() {

                        Timer.start();

                    }
                }.start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Timer.cancel();
    }

    // Crea la conexion BT
    public void conexionBT(String address) {

        int bandera = 0;
        BT_Connected=false;
        BluetoothDevice device = mBtAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
            bandera = 1;
        }
        // Establece la conexión con el socket Bluetooth.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
                Toast.makeText(getBaseContext(), "La conexion fallo", Toast.LENGTH_LONG).show();
                bandera = 1;

            } catch (IOException e2) {
            }
        }

        if (bandera != 1) {
            MyConexionBT = new ConnectedThread(btSocket);
            MyConexionBT.start();
            Toast.makeText(getBaseContext(), address, Toast.LENGTH_LONG).show();
            BT_Connected=true;
        }
    }

    @Override
    public void sendData(String address) {
        conexionBT(address);

    }

public void datos(String Datos){


}


    //Crea la clase que permite crear el evento de conexion
    public class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //Envio de trama
        public void write(String input) {
            try {
                mmOutStream.write(input.getBytes());
            } catch (IOException e) {
                // si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión falloo", Toast.LENGTH_LONG).show();
                //BT_Connected=false;
                finish();
            }
        }
    }
}
