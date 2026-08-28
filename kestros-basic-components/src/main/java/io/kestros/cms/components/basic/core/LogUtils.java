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

package io.kestros.cms.components.basic.core;

import javax.annotation.Nullable;

/**
 * Helpers for putting untrusted values into a log line.
 */
public final class LogUtils {

  private LogUtils() {
    // static helpers only.
  }

  /**
   * Strips CR and LF from a value before it is logged. Applied to every untrusted value, not only
   * the JCR path: a JCR property is author-controlled and an exception message can carry anything,
   * so those are the ones that can actually forge a log line.
   *
   * @param value Value about to be logged.
   * @return The value with CR and LF removed, or null unchanged.
   */
  @Nullable
  public static String forLog(@Nullable final String value) {
    return value == null ? null : value.replaceAll("[\r\n]", "");
  }
}
