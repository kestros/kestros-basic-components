package io.kestros.cms.components.basic.api.exceptions;

public class ComponentConfigurationException extends Exception {

  public ComponentConfigurationException(String message) {
    super(message);
  }

  public ComponentConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ComponentConfigurationException(Throwable cause) {
    super(cause);
  }
}
