package io.kestros.cms.components.basic.api.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown when a component element cannot be built because its configuration is incomplete or
 * invalid.
 */
public class ComponentConfigurationException extends Exception {

  /**
   * Constructs a component configuration exception.
   *
   * @param message Message.
   */
  public ComponentConfigurationException(@Nonnull String message) {
    super(message);
  }

  /**
   * Constructs a component configuration exception.
   *
   * @param message Message.
   * @param cause Cause.
   */
  public ComponentConfigurationException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a component configuration exception.
   *
   * @param cause Cause.
   */
  public ComponentConfigurationException(@Nonnull Throwable cause) {
    super(cause);
  }
}
