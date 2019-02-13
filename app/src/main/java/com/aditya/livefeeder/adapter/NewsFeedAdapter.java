package com.aditya.livefeeder.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.livefeeder.R;
import com.aditya.livefeeder.modal.NewsFeed;
import com.aditya.livefeeder.utils.StringUtils;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.DefaultMediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.aditya.livefeeder.constants.AppConstants.SOMETHING_WENT_WRONG;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.PullRequestViewHolder>{

    private Context context;
    private List<NewsFeed> connections;
    private List<SimpleExoPlayer> players;
    private List<DataSource.Factory> dataSourceFactories;
    private List<DefaultBandwidthMeter> defaultBandwidthMeters;

    public NewsFeedAdapter(@NonNull Context context, @NonNull List<NewsFeed> connections,@NonNull List<SimpleExoPlayer> players,
                           @NonNull List<DataSource.Factory> dataSourceFactories,@NonNull List<DefaultBandwidthMeter> defaultBandwidthMeters){
        this.context=context;
        this.connections=connections;
        this.players = players;
        this.dataSourceFactories = dataSourceFactories;
        this.defaultBandwidthMeters = defaultBandwidthMeters;
    }

    @Override
    public PullRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card, parent, false);
        return new PullRequestViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(final PullRequestViewHolder holder, int position) {
        final NewsFeed feed=connections.get(position);
        if(feed!=null){
            if(StringUtils.isNotEmptyOrNull(feed.getTitle())){
                holder.newsFeedTitle.setText(feed.getTitle());
            }
            if(StringUtils.isNotEmptyOrNull(feed.getImageURL())){

            }
            if(StringUtils.isNotEmptyOrNull(feed.getVideoURL())){
                holder.playerView.setPlayer(players.get(position));
                prepareExoPlayerOnCard(feed,position,holder);
            }
        }
    }

    private void prepareExoPlayerOnCard(final NewsFeed feed, int position, final PullRequestViewHolder holder) {
        String hls_url = feed.getVideoURL();
        Uri uri = Uri.parse(hls_url);
        Handler mainHandler = new Handler();
        MediaSource mediaSource = new HlsMediaSource(uri,
                dataSourceFactories.get(position), mainHandler, new DefaultMediaSourceEventListener() {
        });
        players.get(position).prepare(mediaSource);
        players.get(position).setPlayWhenReady(feed.isPlayWhenReady());
        players.get(position).addListener(new Player.EventListener() {
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
                        holder.loading.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        holder.loading.setVisibility(View.VISIBLE);
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
                Toast.makeText(context,feed.getTitle()+SOMETHING_WENT_WRONG,Toast.LENGTH_SHORT)
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
        players.get(position).seekTo(feed.getCurrentWindow(), feed.getPlaybackPosition());
        players.get(position).prepare(mediaSource, true, false);

    }

    @Override
    public int getItemCount() {
        return connections.size();
    }


    public static class PullRequestViewHolder extends RecyclerView.ViewHolder{

        private TextView newsFeedTitle;
        private PlayerView playerView;
        private ProgressBar loading;

        public PullRequestViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);
            newsFeedTitle =(TextView)itemView.findViewById(R.id.title);
            playerView = (PlayerView) itemView.findViewById(R.id.video_view);
            loading = (ProgressBar) itemView.findViewById(R.id.loading);
        }
    }

}
