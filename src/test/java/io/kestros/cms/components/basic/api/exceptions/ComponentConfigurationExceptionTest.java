package io.kestros.cms.components.basic.api.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComponentConfigurationExceptionTest {

  @Test
  public void testExceptionMessage() {
    String message = "Test exception message";
    ComponentConfigurationException exception = new ComponentConfigurationException(message);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testExceptionWithCause() {
    String message = "Test exception with cause";
    Throwable cause = new Throwable("Root cause");
    ComponentConfigurationException exception = new ComponentConfigurationException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void testExceptionWithOnlyCause() {
    Throwable cause = new Throwable("Only cause");
    ComponentConfigurationException exception = new ComponentConfigurationException(cause);
    assertEquals(cause, exception.getCause());
  }

}