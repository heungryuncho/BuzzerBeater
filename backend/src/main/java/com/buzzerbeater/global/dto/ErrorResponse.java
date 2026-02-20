package com.buzzerbeater.global.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.buzzerbeater.global.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private String code;
    private List<FieldErrorDetail> errors;
    private LocalDateTime timestamp;

    private ErrorResponse(final ErrorCode code, final List<FieldErrorDetail> errors) {
        this.message = code.getMessage();
        this.code = code.getCode();
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldErrorDetail.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldErrorDetail> errors) {
        return new ErrorResponse(code, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldErrorDetail {
        private String field;
        private String value;
        private String reason;

        private FieldErrorDetail(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldErrorDetail> of(final String field, final String value, final String reason) {
            List<FieldErrorDetail> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldErrorDetail(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldErrorDetail> of(final BindingResult bindingResult) {
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldErrorDetail(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
