package com.aditya.livefeeder.network.exceptions;


import com.aditya.livefeeder.constants.AppConstants;

public class AccessDeniedException extends HttpException {
    public AccessDeniedException() {
        super(AppConstants.ACCESS_DENIED);
    }
}
