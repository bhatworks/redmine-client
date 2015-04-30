package com.bhatworks.redmine.ui.adapter;

import com.bhatworks.redmine.R;
import com.bhatworks.redmine.model.Account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class AccountAdapter extends AbstractSpinnerAdapter {

    // TODO replace with actual accounts
    public static final Account[] ACCOUNTS = new Account[]{
            Account.builder().username("John Doe").accountAlias("Demo").build()
    };

    private final LayoutInflater mInflater;

    public AccountAdapter(Context context) {
        super(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, ACCOUNTS));
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    protected View createView(ViewGroup parent, boolean isDropdown) {
        return mInflater.inflate(isDropdown ? android.R.layout.simple_spinner_dropdown_item
                : R.layout.account_item, parent, false);
    }

    @NonNull
    @Override
    protected ViewHolder<Account> getViewHolder(View view, boolean isDropdown) {
        return new AccountViewHolder(view);
    }

    public static class AccountViewHolder extends ViewHolder<Account> {

        @InjectView(android.R.id.text1)
        TextView mAliasView;

        @Nullable
        @Optional
        @InjectView(android.R.id.text2)
        TextView mUsernameView;

        public AccountViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        @Override
        public void bind(Account item) {
            mAliasView.setText(item.accountAlias());
            if (mUsernameView != null) {
                mUsernameView.setText(item.username());
            }
        }
    }
}
