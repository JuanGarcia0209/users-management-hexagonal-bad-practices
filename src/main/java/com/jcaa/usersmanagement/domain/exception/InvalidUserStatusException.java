package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserStatusException extends DomainException {

  private InvalidUserStatusException(final String message) {
    super(message);
  }

  private static final String STATUS_INVALID_FORMAT = "The user status '%s' is not valid.";

  public static InvalidUserStatusException becauseValueIsInvalid(final String status) {
    // VIOLACIÓN Regla 10: texto hardcodeado directamente — debe ser una constante.
    return new InvalidUserStatusException(String.format(STATUS_INVALID_FORMAT, status));
  }
}
