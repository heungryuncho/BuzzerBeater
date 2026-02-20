package com.buzzerbeater.domain.game.exception;

import com.buzzerbeater.global.exception.BusinessException;
import com.buzzerbeater.global.exception.ErrorCode;

public class GameNotFoundException extends BusinessException {

    public GameNotFoundException() {
        super(ErrorCode.GAME_NOT_FOUND);
    }
}
