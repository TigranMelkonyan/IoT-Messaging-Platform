package com.tigran.oauth.domain.model.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 12/3/24
 * Time: 3:15 PM
 */
@RequiredArgsConstructor
@Getter
public class RecordConflictException extends RuntimeException {

    private final String message;
    private final ErrorCode errorCode;
}
