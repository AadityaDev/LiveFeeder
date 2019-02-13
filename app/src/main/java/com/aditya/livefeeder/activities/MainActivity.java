package com.aditya.livefeeder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aditya.livefeeder.Factory;
import com.aditya.livefeeder.R;
import com.aditya.livefeeder.adapter.NewsFeedAdapter;
import com.aditya.livefeeder.constants.AppConstants;
import com.aditya.livefeeder.modal.NewsFeed;
import com.aditya.livefeeder.uicomponents.ItemClickSupport;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private List<String> videoURL = Arrays.asList(
            "https://player.vimeo.com/external/286837767.m3u8?s=42570e8c4a91b98cdec7e7bfdf0eccf54e700b69"
            ,"https://player.vimeo.com/external/286837810.m3u8?s=610b4fee49a71c2dbf22c01752372ff1c6459b9e"
            ,"https://player.vimeo.com/external/286837723.m3u8?s=3df60d3c1c6c7a11df4047af99c5e05cc2e7ae96"
            ,"https://player.vimeo.com/external/286837649.m3u8?s=9e486e9b932be72a8875afc6eaae21bab124a35a"
            ,"https://player.vimeo.com/external/286837529.m3u8?s=20f83af6ea8fbfc8ce0c2001f32bf037f8b0f65f"
            ,"https://player.vimeo.com/external/286837402.m3u8?s=7e01c398e2f01c29ecbd46e5e2dd53e0d6c1905d");

    private RecyclerView newsFeedView;
    private LinearLayoutManager linearLayoutManager;
    private List<NewsFeed> newsFeeds;
    private NewsFeedAdapter newsFeedAdapter;
    private NewsFeed newsFeed;

    private List<DataSource.Factory> dataSourceFactories;
    private List<DefaultBandwidthMeter> defaultBandwidthMeters;
    private TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
    private List<SimpleExoPlayer> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Factory.setUpThreadPolicy();
        newsFeedView = (RecyclerView) findViewById(R.id.newsFeed);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        newsFeedView.setLayoutManager(linearLayoutManager);
        newsFeedView.setHasFixedSize(false);

        players = new ArrayList<SimpleExoPlayer>();
        dataSourceFactories = new ArrayList<DataSource.Factory>();
        defaultBandwidthMeters = new ArrayList<DefaultBandwidthMeter>();

        for(int index=0;index<videoURL.size();index++){
            players.add(ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getApplicationContext()),
                    new DefaultTrackSelector(adaptiveTrackSelection),
                    new DefaultLoadControl()));

            defaultBandwidthMeters.add(new DefaultBandwidthMeter());

            dataSourceFactories.add(new DefaultDataSourceFactory(getApplicationContext(),
                    Util.getUserAgent(getApplicationContext(), "LiveFeeder"), defaultBandwidthMeters.get(index)));

        }
        newsFeeds = new ArrayList<NewsFeed>();
        newsFeedAdapter = new NewsFeedAdapter(getApplicationContext(),newsFeeds,players,dataSourceFactories,defaultBandwidthMeters);
        fillNewsFeed();

        newsFeedView.setAdapter(newsFeedAdapter);


        ItemClickSupport.addTo(newsFeedView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.d(TAG,"Detail clicked");
                Bundle feedBundle = new Bundle();
                feedBundle.putParcelable(AppConstants.FEED_BUNDLE,newsFeeds.get(position));
                Intent intent = new Intent(getApplicationContext(),FeedDetail.class);
                intent.putExtras(feedBundle);
                startActivity(intent);
            }
        });

    }

    private void fillNewsFeed() {
//        if(newsFeeds==null){
//            newsFeeds = new ArrayList<NewsFeed>();
//        }
        for(int index=0;index<videoURL.size();index++){
            newsFeed = new NewsFeed();
            newsFeed.setId((long) index);
            newsFeed.setTitle("News Title "+index);
            newsFeed.setDescription("News Description "+index);
            newsFeed.setVideoURL(videoURL.get(index));
            newsFeeds.add(newsFeed);
        }
        newsFeedAdapter.notifyDataSetChanged();
    }


    private void releasePlayer(@NonNull NewsFeed newsFeed,@NonNull Player player) {
        if (player != null) {
            newsFeed.setPlaybackPosition(player.getCurrentPosition());
            newsFeed.setCurrentWindow(player.getCurrentWindowIndex());
            newsFeed.setPlayWhenReady(player.getPlayWhenReady());
            player.release();
//            players = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT<=23){
            for(int index=0;index<videoURL.size();index++) {
                releasePlayer(newsFeeds.get(index),players.get(index));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>23){
            for(int index=0;index<videoURL.size();index++) {
                if (newsFeed != null) {
                    releasePlayer(newsFeeds.get(index), players.get(index));
                }
            }
        }
    }
}
