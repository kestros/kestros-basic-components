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

package io.kestros.cms.components.basic.content.buttongroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.kestros.cms.components.basic.content.button.ButtonModel;
import org.apache.commons.lang3.StringUtils;

public class ButtonGroupButton implements ButtonModel {

  private String text;
  private String link;

  public ButtonGroupButton() {
  }

  @JsonProperty("text")
  @Override
  public String getText() {
    return text;
  }

  @JsonProperty("link")
  @Override
  public String getLink() {
    if (link == null) {
      return StringUtils.EMPTY;
    }
    if (link.startsWith("/")) {
      return link + ".html";
    }
    return link;
  }

}
