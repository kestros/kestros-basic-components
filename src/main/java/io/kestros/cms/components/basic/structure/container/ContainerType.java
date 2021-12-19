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

package io.kestros.cms.components.basic.structure.container;

/**
 * Enum of the different types of containers.
 */
public enum ContainerType {

  ARTICLE("article", "Article",
      "Defines an independent, self-contained content."),
  ASIDE("aside", "Aside",
      "Defines a section with additional information related to the content around the aside "
          + "element."), BDI("bdi", "Bi-Directional Isolation",
      "Isolates a part of text that might"
          + " be formatted in a different direction from other text outside it."), DETAILS(
      "details", "Details",
      "Specifies additional details that the user can open and close on demand."), DIV("div", "Div",
      "Division or a section in an HTML document."),
  FOOTER("footer", "Footer", "Document or section footer."), HEADER("header", "Header",
      "Document or section header."), MAIN("main", "Main",
      "Main content area of the document or section."), SECTION("section", "Section",
      "General content section."), SUMMARY("summary", "Summary",
      "Visible heading for a details element.");


  private String name;
  private String description;
  private String title;

  /**
   * Constructor.
   *
   * @param name        Element type name.
   * @param title       Element type title.
   * @param description Element type description.
   */
  ContainerType(String name, String title, String description) {
    this.name = name;
    this.title = title;
    this.description = description;
  }

  /**
   * Element type name.
   *
   * @return Element type name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Element type description.
   *
   * @return Element type description.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Element type title.
   *
   * @return Element type title.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Returns the enum value for the given name.
   *
   * @param name Name of the enum value.
   * @return Enum value for the given name.
   */
  public static ContainerType lookup(String name) {
    ContainerType[] containerTypes = values();
    int var2 = containerTypes.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      ContainerType type = containerTypes[var3];
      if (type.getName().equals(name)) {
        return type;
      }
    }
    return ContainerType.DIV;
  }

}
