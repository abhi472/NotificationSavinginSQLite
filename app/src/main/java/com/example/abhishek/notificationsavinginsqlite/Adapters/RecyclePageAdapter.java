package com.example.abhishek.notificationsavinginsqlite.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.notificationsavinginsqlite.Models.*;
import com.example.abhishek.notificationsavinginsqlite.R;

import java.util.ArrayList;

public class RecyclePageAdapter extends RecyclerView.Adapter<RecyclePageAdapter.ViewHolder> {
    Context mContext;
    ArrayList<NotContent> notObject;

    public static class ViewHolder extends RecyclerView.ViewHolder {



        public final View mView;
        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }
    public RecyclePageAdapter(Context mContext, ArrayList<NotContent> notObject)
    {
        this.mContext=mContext;
        this.notObject=notObject;
    }
    @Override
    public RecyclePageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclelist, parent, false);
            return new ViewHolder(view);
        }


    @Override
    public void onBindViewHolder(RecyclePageAdapter.ViewHolder holder, int position) {
        TextView t=(TextView)holder.mView.findViewById(R.id.logo);
        TextView t2=(TextView)holder.mView.findViewById(R.id.logo2);
        TextView t3=(TextView)holder.mView.findViewById(R.id.logo3);
        TextView t4=(TextView)holder.mView.findViewById(R.id.logo4);
        TextView t5=(TextView)holder.mView.findViewById(R.id.logo5);
        TextView t6=(TextView)holder.mView.findViewById(R.id.logo6);
        t.setText(notObject.get(position).title);
        t2.setText(notObject.get(position).alert);
        t3.setText(notObject.get(position).storeId);
        t4.setText(notObject.get(position).img);
        t5.setText(notObject.get(position).uri);
        t6.setText(notObject.get(position).expdate);





    }

    @Override
    public int getItemCount() {
        return notObject.size();
    }
}
