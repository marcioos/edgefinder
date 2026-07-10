package com.marcioos.edgefinder.common.api.error

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(ex: BindException): ApiError =
        ApiError(
            code = ErrorCode.VALIDATION_ERROR,
            message = "Request validation failed",
            details =
                ex.bindingResult.fieldErrors.map {
                    ApiErrorDetail(
                        field = it.field,
                        rejectedValue = it.rejectedValue,
                        message = it.defaultMessage ?: "Invalid value",
                    )
                },
        )
}

enum class ErrorCode {
    VALIDATION_ERROR,
}

data class ApiError(
    val code: ErrorCode,
    val message: String,
    val details: List<ApiErrorDetail> = emptyList(),
)

data class ApiErrorDetail(
    val field: String?,
    val message: String,
    val rejectedValue: Any? = null,
)
