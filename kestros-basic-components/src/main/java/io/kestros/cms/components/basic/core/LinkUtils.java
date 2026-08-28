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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/**
 * Helpers for reading link properties off a resource.
 */
public class LinkUtils {
  @Nonnull
  public static boolean isLinkExternal(@Nonnull String value) {
    return StringUtils.isNotEmpty(value) && !value.startsWith("/");
  }

  @Nullable
  public static String getLink(@Nullable String value) {
    if (value == null) {
      return null;
    }
    if (isLinkExternal(value)) {
      return value;
    } else {
      if (value.endsWith(".html")) {
        return value;
      } else {
        return value + ".html";
      }
    }
  }

}
