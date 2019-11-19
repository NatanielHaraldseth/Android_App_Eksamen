package com.example.android_app_eksamen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WordViewHolder> {

    private final LinkedList<String> mOrdListe;
    private LayoutInflater mBlåsOpp;

    public RecyclerViewAdapter(Context context, LinkedList<String> mWordList) {
        mBlåsOpp = LayoutInflater.from(context);
        this.mOrdListe = mWordList;
    }

    @Override
    public RecyclerViewAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mBlåsOpp.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.WordViewHolder holder, int position) {
        String current = mOrdListe.get(position);
        holder.wordItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return mOrdListe.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView wordItemView;
        final RecyclerViewAdapter mAdapter;

        public WordViewHolder(View itemView, RecyclerViewAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.ord);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.getContext().startActivity(new Intent(view.getContext(), ListViewActivity.class));
        }
    }/**SLUTT PÅ WordViewHolder KLASSE*/

}/**SLUTT PÅ RecyclerViewAdapter KLASSE*/
