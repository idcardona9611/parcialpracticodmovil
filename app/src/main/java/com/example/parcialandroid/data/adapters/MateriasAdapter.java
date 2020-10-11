package com.example.parcialandroid.data.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.parcialandroid.R;
import com.example.parcialandroid.data.MateriaModel;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MateriasAdapter extends ArrayAdapter {
    private LinkedList<MateriaModel> items;
    private Activity context;
    LayoutInflater mInflater = null;

    public MateriasAdapter(Activity context, LinkedList<MateriaModel> objects) {
        super(context, R.layout.item_list, objects);
        this.context = context;
        this.items = objects;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_list, null, true);

        TextView titleText = rowView.findViewById(R.id.materiaName);

        titleText.setText(items.get(position).nombre);
        return rowView;
    }
}