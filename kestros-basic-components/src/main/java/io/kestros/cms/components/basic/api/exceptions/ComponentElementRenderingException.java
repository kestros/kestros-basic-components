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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.annotation.Nonnull;

/**
 * Thrown when a basic component cannot build one of its child elements for rendering.
 *
 * <p>Unchecked, because these getters are called from HTL, which cannot handle a checked
 * exception. Typed rather than a bare {@code RuntimeException} so a rendering failure can be told
 * apart from any other runtime failure, and so the message names the component and the element
 * that failed rather than surfacing a bare cause.</p>
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_EQUALS")
public class ComponentElementRenderingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Thrown when a basic component cannot build one of its child elements for rendering.
   *
   * @param message Which element could not be built, and for which component.
   * @param cause Underlying failure.
   */
  public ComponentElementRenderingException(@Nonnull final String message,
      @Nonnull final Throwable cause) {
    super(message, cause);
  }

  /**
   * Thrown when a basic component cannot build one of its child elements for rendering.
   *
   * @param message Which element could not be built, and for which component.
   */
  public ComponentElementRenderingException(@Nonnull final String message) {
    super(message);
  }
}
