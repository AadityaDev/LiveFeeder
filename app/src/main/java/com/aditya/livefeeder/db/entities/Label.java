package com.aditya.livefeeder.db.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Label implements Parcelable, Comparable<Label> {

    @NonNull
    @PrimaryKey
    private Long id;
    @ColumnInfo(name = "node_id")
    private String node_id;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "color")
    private String color;
    @ColumnInfo(name = "isdefault")
    private boolean isdefault;

    protected Label(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        node_id = in.readString();
        url = in.readString();
        name = in.readString();
        color = in.readString();
        isdefault = in.readByte() != 0;
    }

    public static final Creator<Label> CREATOR = new Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel in) {
            return new Label(in);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(node_id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(color);
        dest.writeByte((byte) (isdefault ? 1 : 0));
    }

    @Override
    public int compareTo(Label o) {
        return this.id.compareTo(o.id);
    }
}
