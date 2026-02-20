package com.buzzerbeater.domain.ticket.exception;

import com.buzzerbeater.global.exception.BusinessException;
import com.buzzerbeater.global.exception.ErrorCode;

public class SeatAlreadyBookedException extends BusinessException {

    public SeatAlreadyBookedException() {
        super(ErrorCode.SEAT_ALREADY_BOOKED);
    }
}
