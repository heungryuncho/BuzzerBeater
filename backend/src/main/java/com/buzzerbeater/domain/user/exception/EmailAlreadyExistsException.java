package com.buzzerbeater.domain.user.exception;

import com.buzzerbeater.global.exception.BusinessException;
import com.buzzerbeater.global.exception.ErrorCode;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
