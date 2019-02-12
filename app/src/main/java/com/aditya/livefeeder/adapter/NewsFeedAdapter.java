package com.aditya.livefeeder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aditya.livefeeder.R;
import com.aditya.livefeeder.modal.NewsFeed;
import com.aditya.livefeeder.utils.StringUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.PullRequestViewHolder>{

    private Context context;
    private List<NewsFeed> connections;

    public NewsFeedAdapter(@NonNull Context context, @NonNull List<NewsFeed> connections){
        this.context=context;
        this.connections=connections;
    }

    @Override
    public PullRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card, parent, false);
        return new PullRequestViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(PullRequestViewHolder holder, int position) {
        final NewsFeed pullRequest=connections.get(position);
        if(pullRequest!=null){
            if(StringUtils.isNotEmptyOrNull(pullRequest.getTitle())){
                holder.repoTitle.setText(pullRequest.getTitle());
            }
            if(StringUtils.isNotEmptyOrNull(pullRequest.getImageURL())){
                holder.createdAt.setText(pullRequest.getImageURL());
            }
//            if(pullRequest.getUser()!=null&&StringUtils.isNotEmptyOrNull(pullRequest.getUser().getLogin())){
//                holder.userName.setText("User: "+pullRequest.getUser().getLogin());
//            }
//            if(pullRequest.getPull_request()!=null) {
//                holder.repoURL.setText("Patch URL => " + pullRequest.getPull_request().getPatch_url());
//            }
//            if(pullRequest.getPull_request()!=null&&StringUtils.isNotEmptyOrNull(pullRequest.getPull_request().getPatch_url())){
//                holder.repoURL.setText("Patch URL => "+pullRequest.getPull_request().getPatch_url());
//            }
        }
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }


    public static class PullRequestViewHolder extends RecyclerView.ViewHolder{

        private TextView repoTitle;
        private TextView createdAt;
        private TextView repoURL;
        private TextView prNumber;
        private TextView userName;

        public PullRequestViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);
            repoTitle =(TextView)itemView.findViewById(R.id.title);
            createdAt=(TextView)itemView.findViewById(R.id.createdAt);
            repoURL=(TextView)itemView.findViewById(R.id.url);
            prNumber = (TextView) itemView.findViewById(R.id.pr);
            userName = (TextView) itemView.findViewById(R.id.userName);
        }
    }

}