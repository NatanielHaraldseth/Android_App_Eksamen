package com.example.android_app_eksamen;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeForÅSlette extends ItemTouchHelper.SimpleCallback {

    private RecyclerViewAdapter recyclerViewAdapter;

    public SwipeForÅSlette(RecyclerViewAdapter recyclerViewAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /*https://stackoverflow.com/questions/27965662/how-can-i-change-default-dialog-button-text-color-in-android-5?fbclid=IwAR3SsnQD8i7shbdPro03vNtwShVSH3azKYe5JHmp8EekGZzwwogrt35_3gc*/
    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

        new AlertDialog.Builder(viewHolder.itemView.getContext()).setMessage("Ønsker du dette").setPositiveButton("JA!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int posisjon = viewHolder.getAdapterPosition();
                recyclerViewAdapter.deleteItem(posisjon);
                recyclerViewAdapter.notifyItemRemoved(posisjon);
            }
        }).setNegativeButton("NEI!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }).create().show();
    }
}
