package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class LoginService implements LoginUseCase {

  private final GetUserByEmailPort getUserByEmailPort;
  private final Validator validator;

  @Override
  public UserModel execute(final LoginCommand command) {
    validateCommand(command);

    final UserEmail email = new UserEmail(command.email());
    // Clean Code - Regla 8: violación CQS — este método mezclaba consulta y comandos.
    // Separar la consulta (obtener el usuario) de las validaciones/efectos (verificar contraseña,
    // comprobar estado) mejora la claridad y respeta CQS. Aquí hacemos la consulta primero
    // y luego ejecutamos las validaciones como operaciones "command" que pueden lanzar
    // excepciones sin ocultar efectos.
    final UserModel user = findUserOrThrowInvalidCredentials(email);

    // Validaciones separadas: comandos que no devuelven datos, sólo lanzan en caso de fallo.
    verifyPasswordOrThrowInvalidCredentials(user, command.password());
    ensureUserIsActive(user);

    return user;
  }

  // Nota: la implementación anterior contenía el método getAndValidateUser que mezclaba
  // consulta y comandos. Para preservar los comentarios originales y dejar claro el
  // razonamiento, las validaciones ahora se realizan en métodos separados:
  // - findUserOrThrowInvalidCredentials()  -> consulta (query)
  // - verifyPasswordOrThrowInvalidCredentials() -> comando (puede lanzar)
  // - ensureUserIsActive() -> comando (puede lanzar)

  private UserModel findUserOrThrowInvalidCredentials(final UserEmail email) {
    final UserModel user = getUserByEmailPort.getByEmail(email).orElse(null);
    if (user == null) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
    return user;
  }

  private void verifyPasswordOrThrowInvalidCredentials(
      final UserModel user, final String plainPassword) {
    if (!user.getPassword().verifyPlain(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
  }

  private void ensureUserIsActive(final UserModel user) {
    if (!isAllowedToLogin(user)) {
      throw InvalidCredentialsException.becauseUserIsNotActive();
    }
  }

  private boolean isAllowedToLogin(final UserModel user) {
    return user.getStatus() == UserStatus.ACTIVE;
  }

  private void validateCommand(final LoginCommand command) {
    final Set<ConstraintViolation<LoginCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
