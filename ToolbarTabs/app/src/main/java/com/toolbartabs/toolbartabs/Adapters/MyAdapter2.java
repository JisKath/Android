package com.toolbartabs.toolbartabs.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.toolbartabs.toolbartabs.R;

import java.util.List;

import static com.toolbartabs.toolbartabs.Activities.MainActivity.BufferInFlag;
import static com.toolbartabs.toolbartabs.Activities.MainActivity.MyConexionBT;
import static com.toolbartabs.toolbartabs.Fragments.ThirdFragment.estadoDisp;

public class MyAdapter2 extends BaseAdapter{

    private Context context;
    private int layout;
    private List<String> names;

    public MyAdapter2(Context context, int layout, List<String> names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {return this.names.size();}

    @Override
    public Object getItem(int position) {return this.names.get(position);}

    @Override
    public long getItemId(int id) {return id;}

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(R.layout.list_item1, null);

            holder = new ViewHolder();

            holder.TextView1 = (TextView) convertView.findViewById(R.id.IdTextView);
            holder.ButtonOn = (Button) convertView.findViewById(R.id.IdBtnOn);
            holder.ButtonOff = (Button) convertView.findViewById(R.id.IdBtnOff);
            holder.ListViewAdapter = (ListView) convertView.findViewById(R.id.IdListView);
            convertView.setTag(holder);

        } else {
            holder = (MyAdapter2.ViewHolder) convertView.getTag();
        }

        String currentName = names.get(position);

        if(estadoDisp.get(position).contains("1")) {
            holder.TextView1.setTextColor(Color.GREEN);
        }

        if(estadoDisp.get(position).contains("0")) {
            holder.TextView1.setTextColor(Color.RED);
        }

        if(estadoDisp.get(position).contains("3")) {
            holder.ButtonOff.setEnabled(false);
            holder.ButtonOn.setEnabled(false);
            holder.TextView1.setEnabled(false);
        }

        if(currentName.length()<4){
            holder.ButtonOff.setEnabled(false);
            holder.ButtonOn.setEnabled(false);
            holder.TextView1.setEnabled(false);
            holder.ButtonOn.setText(" ");
            holder.ButtonOff.setText(" ");

        }

        holder.TextView1.setText(currentName);

        holder.ButtonOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyConexionBT.write("_sndn["+position+","+"1"+"];");
                holder.TextView1.setTextColor(Color.GREEN);
//                holder.ListViewAdapter.setBackgroundColor(Color.GREEN);


            }
        });

        holder.ButtonOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyConexionBT.write("_sndn["+position+","+"0"+"];");
                holder.TextView1.setTextColor(Color.RED);
                //holder.ListViewAdapter.setBackgroundColor(Color.RED);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView TextView1;
        private Button ButtonOn;
        private Button ButtonOff;
        private ListView ListViewAdapter;
    }

}
