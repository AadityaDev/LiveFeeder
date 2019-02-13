package com.aditya.livefeeder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.livefeeder.R;
import com.aditya.livefeeder.constants.AppConstants;
import com.aditya.livefeeder.modal.NewsFeed;
import com.aditya.livefeeder.utils.StringUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.aditya.livefeeder.constants.AppConstants.SOMETHING_WENT_WRONG;

public class FeedDetail extends AppCompatActivity {

    private final String TAG = FeedDetail.class.getSimpleName();
    private Bundle bundle;
    private NewsFeed feedDetail;
    private TextView newsFeedTitle;
    private TextView newsFeedDescription;
    private PlayerView playerView;
    private ProgressBar loading;
    private SimpleExoPlayer player;
    private TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        newsFeedTitle =(TextView) findViewById(R.id.title);
        playerView = (PlayerView) findViewById(R.id.video_view);
        loading = (ProgressBar) findViewById(R.id.loading);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getApplicationContext()),
                new DefaultTrackSelector(adaptiveTrackSelection),
                new DefaultLoadControl());

        bundle = getIntent().getExtras();
        if(bundle!=null){
           feedDetail = bundle.getParcelable(AppConstants.FEED_BUNDLE);
           fillViewData(feedDetail);
        }

    }

    private void fillViewData(final NewsFeed feed){
        if(StringUtils.isNotEmptyOrNull(feed.getTitle())){
            newsFeedTitle.setText(feed.getTitle());
        }
        if(StringUtils.isNotEmptyOrNull(feed.getImageURL())){

        }
        if(StringUtils.isNotEmptyOrNull(feed.getVideoURL())){
            playerView.setPlayer(player);
            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "LiveFeeder"), defaultBandwidthMeter);
            //Create media source
            String hls_url = feed.getVideoURL();
            Uri uri = Uri.parse(hls_url);
            Handler mainHandler = new Handler();
            MediaSource mediaSource = new HlsMediaSource(uri,
                    dataSourceFactory, mainHandler, null);
            player.prepare(mediaSource);


            player.setPlayWhenReady(feed.isPlayWhenReady());
            player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    switch (playbackState) {
                        case ExoPlayer.STATE_READY:
                            loading.setVisibility(View.GONE);
                            break;
                        case ExoPlayer.STATE_BUFFERING:
                            loading.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    Toast.makeText(getApplicationContext(),feed.getTitle()+SOMETHING_WENT_WRONG,Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });
            player.seekTo(feed.getCurrentWindow(), feed.getPlaybackPosition());
            player.prepare(mediaSource, true, false);

        }
    }
}
