package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

// VIOLACIÓN Regla 10: todos los mensajes de error están hardcodeados directamente en los métodos
// fábrica, en vez de estar definidos como constantes con nombre descriptivo en la clase.
public final class PersistenceException extends RuntimeException {

  private static final String FAILED_SAVE_FORMAT = "Failed to save user with ID: '%s'.";
  private static final String FAILED_UPDATE_FORMAT = "Failed to update user with ID: '%s'.";
  private static final String FAILED_FIND_BY_ID_FORMAT = "Failed to find user with ID: '%s'.";
  private static final String FAILED_FIND_BY_EMAIL_FORMAT = "Failed to find user with email: '%s'.";
  private static final String FAILED_RETRIEVE_ALL = "Failed to retrieve all users.";
  private static final String FAILED_DELETE_FORMAT = "Failed to delete user with ID: '%s'.";
  private static final String CONNECTION_FAILED = "Could not establish database connection.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(FAILED_SAVE_FORMAT, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(FAILED_UPDATE_FORMAT, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(FAILED_FIND_BY_ID_FORMAT, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(FAILED_FIND_BY_EMAIL_FORMAT, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(FAILED_RETRIEVE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(FAILED_DELETE_FORMAT, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(CONNECTION_FAILED, cause);
  }
}
