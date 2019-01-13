package com.example.cyrilwelschen.spendingsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyril on 08.01.19.
 * Adapter to implement RecyclerView
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private Context mContext;

    public MyAdapter(Context mContext, ArrayList<String> mImageNames) {
        this.mImageNames = mImageNames;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,
                parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mImageNames.get(position));
        //holder.parentLayout.setOnClickListener()
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.image_name);
        }
    }
}
