package com.example.parcialandroid.data.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.parcialandroid.R;
import com.example.parcialandroid.data.ActividadModel;
import com.example.parcialandroid.data.MateriaModel;

import java.util.LinkedList;

public class ActividadAdapter extends ArrayAdapter{
    private LinkedList<ActividadModel> items;
    private Activity context;
    LayoutInflater mInflater = null;

    public ActividadAdapter(Activity context, LinkedList<ActividadModel> objects) {
       super(context, R.layout.item_actividad, objects);
       this.context = context;
       this.items = objects;
    }

    public View getView(int position, View view, ViewGroup parent) {
       LayoutInflater inflater = this.context.getLayoutInflater();
       View rowView = inflater.inflate(R.layout.item_actividad, null, true);

       TextView titleText = rowView.findViewById(R.id.l_name_actividad);
        TextView percentText = rowView.findViewById(R.id.l_name_percent);
        titleText.setText(items.get(position).nombreActividad);
        percentText.setText(items.get(position).Percent + " %");
        return rowView;
    }
}
