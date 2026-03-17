/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
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