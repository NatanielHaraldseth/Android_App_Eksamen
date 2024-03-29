package com.example.android_app_eksamen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SpisestedViewHolder> {

    private ArrayList<Spisested> spisestedArrayList;
    private OnNoteListener mOnNoteListener;

    public class SpisestedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //
        private TextView text_view_navn, text_view_post_sted, text_view_total_karakter, text_view_adresse, text_view_post_nr, text_view_org_nr;
        private ImageView image_view_total_karakter;
        private OnNoteListener onNoteListener;

        /**
         * Klasse for a fremvise objekter i et recyclerView
         *
         * @param itemView
         * @param onNoteListener */
        public SpisestedViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            //
            text_view_navn              = itemView.findViewById(R.id.text_view_navn);
            text_view_org_nr            = itemView.findViewById(R.id.text_view_org_nr);
            text_view_adresse           = itemView.findViewById(R.id.text_view_adresse);
            text_view_post_nr           = itemView.findViewById(R.id.text_view_post_nr);
            text_view_post_sted         = itemView.findViewById(R.id.text_view_post_sted);
            text_view_total_karakter    = itemView.findViewById(R.id.text_view_total_karakter);
            image_view_total_karakter   = itemView.findViewById(R.id.image_view_total_karakter);
            //
            this.onNoteListener         = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

    }/**SLUTT PÅ SpisestedViewHolder KLASSE*/

    /**
     * Konstruktør metode for RecyclerViewAdapter
     * Brukes for og få tilgang til lytter og datasett
     *
     * @param spisestedArrayList
     * @param mOnNoteListener
     * */
    public RecyclerViewAdapter(ArrayList<Spisested> spisestedArrayList, OnNoteListener mOnNoteListener) {
        this.spisestedArrayList = spisestedArrayList;
        this.mOnNoteListener = mOnNoteListener;
    }

    /**
     * Metode for og "blåse opp" layout for bruk
     * layout vil bli "blåst opp" på foreldre
     *
     * @param parent
     * @param viewType
     *
     * @return wvh*/
    @Override
    public SpisestedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_v2, parent, false);
        SpisestedViewHolder wvh = new SpisestedViewHolder(view, mOnNoteListener);
        return wvh;
    }

    /**
     * Metode for og sette verdier inn i dets respektive layout objekter.
     * Bruker posisjon for og kunne finne "unike" objekter i et datasett
     *
     * @param holder
     * @param position */
    @Override
    public void onBindViewHolder(SpisestedViewHolder holder, int position) {
        Spisested spisested = spisestedArrayList.get(position);
        //
        holder.text_view_navn.setText(spisested.getNavn());
        holder.text_view_adresse.setText(spisested.getAdrlinje1());
        holder.text_view_post_sted.setText(spisested.getPoststed());
        holder.text_view_post_nr.setText(String.valueOf(spisested.getPostnr()));
        holder.text_view_org_nr.setText(String.valueOf(spisested.getOrgnummer()));
        if (spisested.getTotal_karakter() <= 1) {
            holder.image_view_total_karakter.setImageResource(R.drawable.smiling_face);
        }else if (spisested.getTotal_karakter() == 2) {
            holder.image_view_total_karakter.setImageResource(R.drawable.straight_mouth_line);
        }else if (spisested.getTotal_karakter() == 3) {
            holder.image_view_total_karakter.setImageResource(R.drawable.sad_face);
        }else {
            holder.text_view_total_karakter.setText(String.valueOf(spisested.getTotal_karakter()));
        }
    }

    /**
     * Metode for og få tak i antall elementer i et spesifikt datasett
     *
     * @return spisestedArrayList.size()*/
    @Override
    public int getItemCount() {
        return spisestedArrayList.size();
    }

    /**
     * Metode for og slette spesifikt element i datasettet.
     * Bruker posisjon for og finne elementet
     *
     * @param posisjon */
    public void deleteItem(int posisjon) {
        spisestedArrayList.remove(posisjon);
    }

    public interface OnNoteListener {
        void onNoteClick(int posisjon);
    }

}/**SLUTT PÅ RecyclerViewAdapter KLASSE*/
