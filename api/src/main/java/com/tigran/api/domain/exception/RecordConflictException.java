package com.tigran.api.domain.exception;

import com.tigran.api.domain.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 1:15 PM
 */
@RequiredArgsConstructor
@Getter
public class RecordConflictException extends RuntimeException {

    private final String message;
    private final ErrorCode errorCode;

}
