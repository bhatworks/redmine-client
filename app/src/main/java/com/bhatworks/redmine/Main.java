package com.bhatworks.redmine;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.bhatworks.redmine.lib.RefreshReloader;

public class Main extends FragmentActivity implements ActionBar.OnNavigationListener,
        OnQueryTextListener {

    private static final String TAG_UI = "Redmine-UI";

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    private Thread progressThread;

    private RefreshReloader reloader;

    private MenuItem reloadIcon;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            reloader.stopRefresh();
            return true;
        }
    });

    private MenuItem searchIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<Object>(actionBar.getThemedContext(), R.layout.dropdown_menu,
                        R.id.dropdown_menu, new String[]{getString(R.string.issues_section),
                        getString(R.string.projects_section)}), this);
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Don't care about this.
        return true;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action_bar, menu);
        reloadIcon = menu.findItem(R.id.menu_reload);
        reloader = new RefreshReloader(reloadIcon, R.layout.refresh_menu_item);
        searchIcon = menu.findItem(R.id.menu_search);
        SearchView sv = new SearchView(this);
        sv.setOnQueryTextListener(this);
        searchIcon.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d(TAG_UI, "reload-icon is hidden now");
                reloadIcon.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d(TAG_UI, "reload-icon is visible now");
                reloadIcon.setVisible(true);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Log.d(TAG_UI, "search using menu key long-press");
            return searchIcon.expandActionView();
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return searchIcon.expandActionView();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        if (progressThread != null && progressThread.isAlive() && !progressThread.isInterrupted()) {
            progressThread.interrupt();
        }

        reloader.startRefresh();

        progressThread = new Thread(new Runnable() {

            @Override
            public void run() {
                int progressVal = 0;
                while (progressVal > -1 && progressVal < 100) {
                    progressVal += (int) (Math.random() * 20);
                    try {
                        Thread.sleep((int) (Math.random() * 200));
                    } catch (InterruptedException e) {
                    }
                }
                handler.sendEmptyMessage(progressVal);
            }
        });
        progressThread.start();

        return true;
    }
}
