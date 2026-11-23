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

import javax.annotation.Nonnull;

/**
 * Thrown when a component is configured in a way it cannot be rendered from.
 */
public class ComponentConfigurationException extends Exception {

  /**
   * Thrown when a component is configured in a way it cannot be rendered from.
   *
   * @param message Description of how the component is misconfigured.
   */
  public ComponentConfigurationException(@Nonnull String message) {
    super(message);
  }

  /**
   * Thrown when a component is configured in a way it cannot be rendered from.
   *
   * @param message Description of how the component is misconfigured.
   * @param cause Exception that caused this one.
   */
  public ComponentConfigurationException(@Nonnull String message, @Nonnull Throwable cause) {
    super(message, cause);
  }

  /**
   * Thrown when a component is configured in a way it cannot be rendered from.
   *
   * @param cause Exception that caused this one.
   */
  public ComponentConfigurationException(@Nonnull Throwable cause) {
    super(cause);
  }
}
