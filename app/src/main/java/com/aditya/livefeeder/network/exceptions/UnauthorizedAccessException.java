package com.aditya.livefeeder.network.exceptions;

import com.aditya.livefeeder.constants.AppConstants;

public class UnauthorizedAccessException extends HttpException {

    public UnauthorizedAccessException() {
        super(AppConstants.UNAUTHORIZED_ACCESS);
    }
}
