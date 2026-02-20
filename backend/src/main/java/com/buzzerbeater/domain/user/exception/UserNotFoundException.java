package com.buzzerbeater.domain.user.exception;

import com.buzzerbeater.global.exception.BusinessException;
import com.buzzerbeater.global.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
