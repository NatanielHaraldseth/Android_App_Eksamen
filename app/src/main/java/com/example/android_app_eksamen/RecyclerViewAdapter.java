package com.example.android_app_eksamen;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WordViewHolder> {

    private ArrayList<Spisested> spisestedArrayList;
    private OnNoteListener mOnNoteListener;

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //
        private static final String TAG = "WordViewHolder";

        //
        private TextView textView1, textView2, textView3, textView4, textView5, textView6;
        private OnNoteListener onNoteListener;

        public WordViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            //
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.textView5);
            textView6 = itemView.findViewById(R.id.textView6);
            //
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //view.getContext().startActivity(new Intent(view.getContext(), ListViewActivity.class));
            onNoteListener.onNoteClick(getAdapterPosition());
            int pos = getAdapterPosition();
            Log.d(TAG, "onClick: " + spisestedArrayList.get(pos).getTilsynid());
        }

    }/**SLUTT PÅ WordViewHolder KLASSE*/

    public RecyclerViewAdapter(ArrayList<Spisested> spisestedArrayList, OnNoteListener mOnNoteListener) {
        this.spisestedArrayList = spisestedArrayList;
        this.mOnNoteListener = mOnNoteListener;
    }

    @Override
    public RecyclerViewAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_v2, parent, false);
        WordViewHolder wvh = new WordViewHolder(view, mOnNoteListener);
        return wvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.WordViewHolder holder, int position) {
        Spisested spisested = spisestedArrayList.get(position);
        holder.textView1.setText(spisested.getNavn());
        holder.textView2.setText(spisested.getAdrlinje1());
        holder.textView3.setText(spisested.getPoststed());
        holder.textView4.setText(String.valueOf(spisested.getOrgnummer()));
        holder.textView5.setText(String.valueOf(spisested.getPostnr()));
        holder.textView6.setText(String.valueOf(spisested.getTotal_karakter()));
    }

    @Override
    public int getItemCount() {
        return spisestedArrayList.size();
    }

    public void deleteItem(int posisjon) {
        spisestedArrayList.remove(posisjon);
    }

    public Spisested getItem(int posisjon) {
        return spisestedArrayList.get(posisjon);
    }

    public interface OnNoteListener {
        void onNoteClick(int posisjon);
    }

}/**SLUTT PÅ RecyclerViewAdapter KLASSE*/
