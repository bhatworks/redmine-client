package com.bhatworks.redmine.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class Account implements Parcelable {

    public abstract String username();

    public abstract String accountAlias();

    @NonNull
    public static Builder builder() {
        return new AutoParcel_Account.Builder();
    }

    @AutoParcel.Builder
    public interface Builder {

        Builder username(String username);

        Builder accountAlias(String alias);

        Account build();
    }
}
