package com.example.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.fitnessapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    public Context context;
    private int layout;
    private List<Exercise> exercisesList;

    public Adapter(Context context, int layout, List<Exercise> exercisesList) {
        this.context = context;
        this.layout = layout;
        this.exercisesList = exercisesList;
    }


    // EL TAMÑO DE LA LISTA
    @Override
    public int getCount() {
        return exercisesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.element, null);

        String name = exercisesList.get(i).getName();

        TextView txtName = v.findViewById(R.id.txt_name);

        txtName.setText(name.toUpperCase());


        return v;
    }




}
