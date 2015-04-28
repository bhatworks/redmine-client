package com.bhatworks.redmine.lib;

import android.view.MenuItem;
import android.view.View;

public class RefreshReloader {

    private final MenuItem reloadIcon;

    private final View initActionView;

    private final View progressActionView;

    private final int progressActionViewId;

    public RefreshReloader(MenuItem reloadIcon, int progressActionViewId)
            throws IllegalArgumentException {
        if (reloadIcon == null) {
            throw new IllegalArgumentException("reloadIcon cannot be null");
        }
        this.reloadIcon = reloadIcon;
        this.progressActionViewId = progressActionViewId;
        initActionView = reloadIcon.getActionView();
        progressActionView = null;
    }

    public RefreshReloader(MenuItem reloadIcon, View progressActionView)
            throws IllegalArgumentException {
        if (reloadIcon == null) {
            throw new IllegalArgumentException("reloadIcon cannot be null");
        }
        this.reloadIcon = reloadIcon;
        this.progressActionView = progressActionView;
        progressActionViewId = -1;
        initActionView = reloadIcon.getActionView();
    }

    public void startRefresh() {
        if (progressActionView == null) {
            reloadIcon.setActionView(progressActionViewId);
        } else {
            reloadIcon.setActionView(progressActionView);
        }
    }

    public void stopRefresh() {
        reloadIcon.setActionView(initActionView);
    }

}
