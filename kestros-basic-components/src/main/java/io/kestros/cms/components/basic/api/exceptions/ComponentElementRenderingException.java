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
