package com.example.android_app_eksamen;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posisjon = viewHolder.getAdapterPosition();
        recyclerViewAdapter.deleteItem(posisjon);
    }
}
