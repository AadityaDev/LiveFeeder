package com.aditya.livefeeder.network.exceptions;

import com.aditya.livefeeder.constants.AppConstants;

public class ServerErrorException extends HttpException {

    public ServerErrorException() {
        super(AppConstants.MESSAGE_SERVER_ERROR);
    }
}
