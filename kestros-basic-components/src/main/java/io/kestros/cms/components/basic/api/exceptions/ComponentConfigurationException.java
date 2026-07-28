package io.kestros.cms.components.basic.api.exceptions;

import javax.annotation.Nonnull;
public class ComponentConfigurationException extends Exception {

  public ComponentConfigurationException(@Nonnull String message) {
    super(message);
  }

  public ComponentConfigurationException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }

  public ComponentConfigurationException(@Nonnull Throwable cause) {
    super(cause);
  }
}
