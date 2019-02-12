package com.toolbartabs.toolbartabs.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.toolbartabs.toolbartabs.Adapters.PagerAdapter;
import com.toolbartabs.toolbartabs.Fragments.ThirdFragment;
import com.toolbartabs.toolbartabs.R;

import java.util.ArrayList;

import static com.toolbartabs.toolbartabs.Activities.TcpClient.mRun;
import static com.toolbartabs.toolbartabs.Fragments.SecondFragment.Monitor;

public class MainActivity extends AppCompatActivity implements ThirdFragment.Fragment3Listener{
    //implements FirstFragment.Datalistener{

    // Declaracion de campos
    public static boolean BufferInFlag, BT_Connected;
    public static StringBuilder DataStringIN = new StringBuilder();
    public static String BufferInW;
    public static boolean CmdSnd, altoTest = true;
    public static int Step = 0, Trans = 0, lastPos, indice = 0, intentosEstado = 0;
    public static ArrayList<String> devices = new ArrayList<>();
    public static int tabPosition = 0, tabPosition0 = 99, tabPositionTF = 99;
    boolean sinRegreso;
    private CountDownTimer Timer;

    public TcpClient mTcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

/*//   Pendiente dar de alta Menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);

        }*/


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Config"));
        tabLayout.addTab(tabLayout.newTab().setText("Dispositivos"));
        tabLayout.addTab(tabLayout.newTab().setText("Disp BT"));
//        tabLayout.addTab(tabLayout.newTab().setText("Escenas"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        new ConnectTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                //if(listaBT){position=2;}

                tabPosition = tab.getPosition();
                if (!mRun){
                    position=0;
                    tabPosition=0;
                    sinRegreso = false;
                    Toast.makeText(getBaseContext(), "Conectar a controlador", Toast.LENGTH_SHORT).show();
                }

                if ((position == 1) & mRun == true) {
                    sinRegreso = true;
                }

                if (position == 0 & sinRegreso == true) {
                    //position=1;
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
    public void onInput3Sent(String input) {
        new SendMessageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,String.valueOf(input));

    }

    @Override
    public void onResume() {
        super.onResume();

        Timer = new CountDownTimer(30000, 150) {
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

//                                new SendMessageTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"_rall");
                        CmdSnd = true;
                        tabPositionTF = tabPosition;
                    }
                }

                if (tabPosition==2){

                    Monitor=BufferInW;
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

    public class SendMessageTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            // send the message
            mTcpClient.sendMessage(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {
            super.onPostExecute(nothing);
            // clear the data set
            //arrayList.clear();
            // notify the adapter that the data set has changed.
            //mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Disconnects using a background task to avoid doing long/network operations on the UI thread
     */
    public class DisconnectTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... voids) {

            // disconnect
            mTcpClient.stopClient();
            mTcpClient = null;

            return null;
        }


        protected void onPostExecute(Void nothing) {
            super.onPostExecute(nothing);
            // clear the data set
            //arrayList.clear();
            // notify the adapter that the data set has changed.
            //mAdapter.notifyDataSetChanged();
        }
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object and
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                    if (message.toString().contains(";")) {
                        BufferInFlag = true;
                    }
                }
            });
            mTcpClient.run();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            BufferInW = BufferInW + values[0];
        }
    }
}
