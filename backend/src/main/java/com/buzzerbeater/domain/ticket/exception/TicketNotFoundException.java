package com.buzzerbeater.domain.ticket.exception;

import com.buzzerbeater.global.exception.BusinessException;
import com.buzzerbeater.global.exception.ErrorCode;

public class TicketNotFoundException extends BusinessException {

    public TicketNotFoundException() {
        super(ErrorCode.TICKET_NOT_FOUND);
    }
}
