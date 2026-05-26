package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserRoleException extends DomainException {

  private InvalidUserRoleException(final String message) {
    super(message);
  }

  private static final String ROLE_INVALID_FORMAT = "The user role '%s' is not valid.";

  public static InvalidUserRoleException becauseValueIsInvalid(final String role) {
    // VIOLACIÓN Regla 10: texto hardcodeado directamente — debe ser una constante.
    return new InvalidUserRoleException(String.format(ROLE_INVALID_FORMAT, role));
  }
}
