package com.tigran.oauth.domain.model.rest.response.exception;

import com.tigran.oauth.domain.model.exception.ErrorCode;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 12:20 PM
 */
public record ErrorResponse(ErrorCode errorCode, String message) {
}
