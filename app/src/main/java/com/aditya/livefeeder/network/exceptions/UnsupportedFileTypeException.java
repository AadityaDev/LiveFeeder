package com.aditya.livefeeder.network.exceptions;

import com.aditya.livefeeder.constants.AppConstants;

public class UnsupportedFileTypeException extends HttpException {

    public UnsupportedFileTypeException() {
        super(AppConstants.MESSAGE_UNSUPPORTED_FILE_TYPE);
    }

}
