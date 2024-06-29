package dev.bestzige.hotelbooking.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val status: Int,
    val message: String,
    var stackTrace: String? = null,
    var errors: MutableList<ValidationError>? = null
) {
    data class ValidationError(
        val field: String,
        val message: String
    )

    fun addValidationError(field: String, message: String) {
        if (errors == null) {
            errors = mutableListOf()
        }
        errors?.add(ValidationError(field, message))
    }
}