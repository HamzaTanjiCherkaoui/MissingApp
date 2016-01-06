package com.apps.hamza.missingapp;

/**
 * Created by Asus on 05/01/2016.
 */
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Todo> {
    private Context mContext;
    private List<Todo> mTasks;

    public TaskAdapter(Context context, List<Todo> objects) {
        super(context, R.layout.task_row_item, objects);
        this.mContext = context;
        this.mTasks = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.task_row_item, null);
        }

        Todo task = mTasks.get(position);

        TextView descriptionView = (TextView) convertView.findViewById(R.id.task_description);

        descriptionView.setText(task.getTask());
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Bold.otf");
        descriptionView.setTypeface(font);

        if(task.isState()){
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }

}