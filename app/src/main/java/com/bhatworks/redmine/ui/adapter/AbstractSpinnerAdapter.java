package com.bhatworks.redmine.ui.adapter;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

public abstract class AbstractSpinnerAdapter<T> implements SpinnerAdapter {

    private final SpinnerAdapter mAdapter;

    public AbstractSpinnerAdapter(SpinnerAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
        return getViewInternal(position, convertView, parent, true);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return mAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView,
            ViewGroup parent) {
        return getViewInternal(position, convertView, parent, false);
    }

    private View getViewInternal(int position, View convertView, ViewGroup parent,
            boolean isDropdown) {
        View view;
        if (convertView == null) {
            view = createView(parent, isDropdown);
        } else {
            view = convertView;
        }
        ViewHolder<T> vh = getViewHolder(view, isDropdown);
        //noinspection unchecked
        T item = (T) getItem(position);
        vh.bind(item);
        return view;
    }

    @NonNull
    protected abstract View createView(ViewGroup parent, boolean isDropdown);

    @NonNull
    protected abstract ViewHolder<T> getViewHolder(View view, boolean isDropdown);

    @Override
    public int getViewTypeCount() {
        return mAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }
}
