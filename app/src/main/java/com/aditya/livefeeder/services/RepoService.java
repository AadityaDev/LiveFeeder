package com.aditya.livefeeder.services;

import com.aditya.livefeeder.concurrency.ExecutorUtils;
import com.aditya.livefeeder.constants.AppAPI;
import com.aditya.livefeeder.network.RequestGenerator;
import com.aditya.livefeeder.network.RequestHandler;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import okhttp3.Request;

public class RepoService {

    public ListenableFuture<JSONArray> getClosedRepositories(@NonNull final String orgName, @NonNull final String repoName) {
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.CLOSED_REQUEST + orgName + AppAPI.SLASH + repoName + AppAPI.CLOSED_STATE);
                String body = RequestHandler.makeRequestAndValidate(request);
                JSONArray jsonArray = null;
//                = Utility.getResultJSONArray(body);
                return jsonArray;
            }
        });
    }

    public ListenableFuture<JSONArray> getOpenRepositories(@NonNull final String orgName, @NonNull final String repoName) {
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.OPEN_REQUEST + orgName+ AppAPI.SLASH + repoName + AppAPI.OPEN_STATE);
                String body = RequestHandler.makeRequestAndValidate(request);
                JSONArray jsonArray = null;
//                        Utility.getResultJSONArray(body);
                return jsonArray;
            }
        });
    }

}
