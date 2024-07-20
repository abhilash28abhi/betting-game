package com.betting.api.errors;

import lombok.Data;

@Data
public class HttpErrorResponse {

    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
