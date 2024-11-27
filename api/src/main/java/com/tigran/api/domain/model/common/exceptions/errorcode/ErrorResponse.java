package com.tigran.api.domain.model.common.exceptions.errorcode;

/**
 * Created by Tigran Melkonyan
 * Date: 11/26/24
 * Time: 5:02 PM
 */
public record ErrorResponse(ErrorCode errorCode, String message) {
}
