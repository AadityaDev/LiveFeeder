package com.aditya.livefeeder.modal;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsFeed implements Parcelable, Comparable<NewsFeed> {

    private Long id;
    private String videoURL;
    private String title;
    private String imageURL;
    private String description;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public NewsFeed(){

    }

    protected NewsFeed(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        videoURL = in.readString();
        title = in.readString();
        imageURL = in.readString();
        description = in.readString();
        playWhenReady = in.readByte() != 0;
        currentWindow = in.readInt();
        playbackPosition = in.readLong();
    }

    public static final Creator<NewsFeed> CREATOR = new Creator<NewsFeed>() {
        @Override
        public NewsFeed createFromParcel(Parcel in) {
            return new NewsFeed(in);
        }

        @Override
        public NewsFeed[] newArray(int size) {
            return new NewsFeed[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlayWhenReady() {
        return playWhenReady;
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }

    public int getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

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
        dest.writeString(videoURL);
        dest.writeString(title);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeByte((byte) (playWhenReady ? 1 : 0));
        dest.writeInt(currentWindow);
        dest.writeLong(playbackPosition);
    }

    @Override
    public int compareTo(NewsFeed o) {
        return 0;
    }
}
