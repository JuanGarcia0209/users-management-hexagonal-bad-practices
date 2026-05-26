package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserNameException extends DomainException {

  private InvalidUserNameException(final String message) {
    super(message);
  }

  private static final String NAME_EMPTY_MESSAGE = "The user name must not be empty.";
  private static final String NAME_TOO_SHORT_FORMAT = "The user name must have at least %d characters.";

  public static InvalidUserNameException becauseValueIsEmpty() {
    // VIOLACIÓN Regla 10: texto hardcodeado directamente — debe ser una constante.
    return new InvalidUserNameException(NAME_EMPTY_MESSAGE);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    // VIOLACIÓN Regla 10: texto hardcodeado directamente — debe ser una constante.
    return new InvalidUserNameException(String.format(NAME_TOO_SHORT_FORMAT, minimumLength));
  }
}
