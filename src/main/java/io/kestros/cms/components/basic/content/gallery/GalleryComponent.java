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

package io.kestros.cms.components.basic.content.gallery;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import java.util.Arrays;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Basic gallery component.
 */
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/gallery")
public class GalleryComponent extends BaseComponent {

  /**
   * Image paths.
   *
   * @return Image paths.
   */
  public List<String> getImagePaths() {
    return Arrays.asList("https://images.unsplash.com/photo-1515859005217-8a1f08870f59?ixid"
                         + "=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aXRhbHl8ZW58MHx8MHx8&ixlib=rb-1.2"
                         + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1523906834658-6e24ef2386f9?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8M3x8aXRhbHl8ZW58MHx8MHx8&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1514896856000-91cb6de818e0?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8NXx8aXRhbHl8ZW58MHx8MHx8&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1516108317508-6788f6a160e4?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8NHx8aXRhbHl8ZW58MHx8MHx8&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1520175480921-4edfa2983e0f?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8OXx8aXRhbHl8ZW58MHx8MHx8&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1533676802871-eca1ae998cd5?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8aXRhbHl8ZW58MHx8MHx8&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1518730518541-d0843268c287?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fGl0YWx5fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1529260830199-42c24126f198?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fGl0YWx5fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1453747063559-36695c8771bd?ixlib=rb-1.2"
        + ".1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjd8fGl0YWx5fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop"
        + "&w=500&q=60", "https://images.unsplash.com/photo-1480548004877-593316be2bd5?ixlib=rb-1.2"
                         + ".1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjh8fGl0YWx5fGVufDB8fDB8fA%3D%3D&auto"
                         + "=format&fit=crop" + "&w=500&q=60",
        "https://images.unsplash.com/photo-1528114039593-4366cc08227d?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8MzJ8fGl0YWx5fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1492110182377-95eee14016e1?ixid"
        + "=MnwxMjA3fDB8MHxzZWFyY2h8Mzl8fGl0YWx5fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?ixid"
        + "=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2"
        + ".1&auto=format&fit=crop&w=633&q=80");
  }
}
