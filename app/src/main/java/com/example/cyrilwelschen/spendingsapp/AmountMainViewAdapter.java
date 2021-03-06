package com.example.cyrilwelschen.spendingsapp;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyril on 08.01.19.
 * Adapter to implement RecyclerView
 */

public class AmountMainViewAdapter
        extends RecyclerView.Adapter<AmountMainViewAdapter.AmountViewHolder> {

    private ArrayList<String> mSpendingDescription = new ArrayList<>();
    private ArrayList<String> mSpendingDates = new ArrayList<>();
    private ArrayList<String> mSpendingAmounts = new ArrayList<>();
    private ArrayList<Drawable> mSpendingCatImage = new ArrayList<>();

    AmountMainViewAdapter(ArrayList<String> mSpendingDescription,
                          ArrayList<String> mSpendingDates, ArrayList<String> mSpendingAmounts,
                          ArrayList<Drawable> mSpendingCatImage) {
        this.mSpendingDescription = mSpendingDescription;
        this.mSpendingDates = mSpendingDates;
        this.mSpendingAmounts = mSpendingAmounts;
        this.mSpendingCatImage = mSpendingCatImage;
    }

    @Override
    public AmountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,
                parent, false);
        return new AmountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AmountViewHolder holder, int position) {
        holder.mCategory.setText(mSpendingDescription.get(position));
        holder.mDate.setText(mSpendingDates.get(position));
        holder.mAmount.setText(mSpendingAmounts.get(position));
        // holder.mImage.setImageDrawable(mSpendingCatImage.get(position));
        holder.mImage.setImageDrawable(mSpendingCatImage.get(position));
    }

    @Override
    public int getItemCount() {
        return mSpendingDescription.size();
    }

    class AmountViewHolder extends RecyclerView.ViewHolder {

        TextView mCategory;
        TextView mDate;
        TextView mAmount;
        ImageView mImage;

        AmountViewHolder(View v) {
            super(v);
            mCategory = v.findViewById(R.id.categories);
            mDate = v.findViewById(R.id.spending_date);
            mAmount = v.findViewById(R.id.spending_amount);
            mImage = v.findViewById(R.id.category_image);
        }
    }
}
