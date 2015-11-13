package com.github.atdi.news.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by aurelavramescu on 13/11/15.
 */
@Getter
@Builder(toBuilder = true)
public class HttpError {

    private String message;

    private int status;

    private String exception;
}
