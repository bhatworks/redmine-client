package com.bhatworks.redmine.ui.adapter;

import android.view.View;

public abstract class ViewHolder<T> {
    public final View itemView;

    public ViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public abstract void bind(T item);
}
