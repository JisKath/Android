package com.toolbartabs.toolbartabs.Adapters;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorLong;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.toolbartabs.toolbartabs.R;

import java.util.List;

import static com.toolbartabs.toolbartabs.Activities.MainActivity.MyConexionBT;
import static com.toolbartabs.toolbartabs.R.color.On;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;

    public MyAdapter(Context context, int layout, List<String> names) {
        this.context = context;
        this.layout = layout;
        this.names = names;

    }


    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.textView);
            holder.Button = (ToggleButton) convertView.findViewById(R.id.IdBtn_OnOff);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        String currentName = names.get(position);

        holder.nameTextView.setText(currentName);

holder.Button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            Toast.makeText(context, "Encendido "+ position,Toast.LENGTH_LONG).show();
            MyConexionBT.write("_sndn["+position+","+"1"+"];");

        }
        else {
            Toast.makeText(context, "Apagado "+ position,Toast.LENGTH_LONG).show();
            MyConexionBT.write("_sndn["+position+","+"0"+"];");
        }

    }
});

        return convertView;
    }

    static class ViewHolder {
        private TextView nameTextView;
        private ToggleButton Button;
    }
}