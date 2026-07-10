package com.marcioos.edgefinder.common.api.error

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindException
import org.springframework.validation.FieldError

class ApiExceptionHandlerTest {
    private val handler = ApiExceptionHandler()

    @Test
    fun `should convert bind exception to api error`() {
        val bindingResult = BeanPropertyBindingResult(Any(), "request")

        bindingResult.addError(
            FieldError(
                "request",
                "amount",
                "-100",
                false,
                null,
                null,
                "must be greater than 0",
            ),
        )

        bindingResult.addError(
            FieldError(
                "request",
                "currency",
                "ABC",
                false,
                null,
                null,
                "must be a valid currency",
            ),
        )

        val exception = BindException(bindingResult)

        val error = handler.handleBindException(exception)

        assertThat(error.code)
            .isEqualTo(ErrorCode.VALIDATION_ERROR)

        assertThat(error.message)
            .isEqualTo("Request validation failed")

        assertThat(error.details)
            .containsExactly(
                ApiErrorDetail(
                    field = "amount",
                    rejectedValue = "-100",
                    message = "must be greater than 0",
                ),
                ApiErrorDetail(
                    field = "currency",
                    rejectedValue = "ABC",
                    message = "must be a valid currency",
                ),
            )
    }

    @Test
    fun `should use default message when field error has no message`() {
        val bindingResult = BeanPropertyBindingResult(Any(), "request")

        bindingResult.addError(
            FieldError(
                "request",
                "amount",
                "-100",
                false,
                null,
                null,
                null,
            ),
        )

        val exception = BindException(bindingResult)

        val error = handler.handleBindException(exception)

        assertThat(error.details)
            .containsExactly(
                ApiErrorDetail(
                    field = "amount",
                    rejectedValue = "-100",
                    message = "Invalid value",
                ),
            )
    }
}
