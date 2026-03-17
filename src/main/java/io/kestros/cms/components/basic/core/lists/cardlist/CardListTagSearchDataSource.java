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
package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListTagSearchDataSource extends BaseContainerSlingModelDataSource implements
                                                                                   KestrosCardList {

  @OSGiService
  @org.apache.sling.models.annotations.Optional
  private TagRetrievalService tagRetrievalService;

  private BaseContentPage containingPage;

  @Nullable
  public String getReadMoreText() {
    return getResource().getValueMap().get("readMoreText", String.class);
  }

  BaseContentPage getContainingPage() {
    if (containingPage == null) {
      try {
        BaseComponent component = getResource().adaptTo(BaseComponent.class);
        if (component != null) {
          containingPage = component.getContainingPage();
        }
      } catch (NoValidAncestorException e) {
        return null;
      }
    }
    return containingPage;
  }

  @Nonnull
  String[] getConfiguredTags() {
    String[] tags = getResource().getValueMap().get("tags", String[].class);
    if (tags == null) {
      return new String[0];
    }
    return tags;
  }

  BaseContentPage getRootPage() {
    BaseContentPage currentPage = getContainingPage();
    if (currentPage != null) {
      try {
        return currentPage.getParent();
      } catch (Exception e) {
        return currentPage;
      }
    }
    return null;
  }

  List<BaseContentPage> getTaggedPages() {
    List<BaseContentPage> taggedPages = new ArrayList<>();
    if (tagRetrievalService == null) {
      return taggedPages;
    }

    String[] configuredTags = getConfiguredTags();
    if (configuredTags.length == 0) {
      return taggedPages;
    }

    Set<String> filterTagPaths = new HashSet<>();
    for (String tagPath : configuredTags) {
      filterTagPaths.add(tagPath);
    }

    BaseContentPage currentPage = getContainingPage();
    BaseContentPage rootPage = getRootPage();
    if (rootPage == null) {
      return taggedPages;
    }

    for (BaseContentPage childPage : rootPage.getChildPages()) {
      if (currentPage != null && childPage.getPath().equals(currentPage.getPath())) {
        continue;
      }

      List<KestrosTag> childTags = tagRetrievalService.getTagsOnResource(
          childPage.getResource());
      for (KestrosTag childTag : childTags) {
        if (filterTagPaths.contains(childTag.getPath())) {
          taggedPages.add(childPage);
          break;
        }
      }
    }

    return taggedPages;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : getTaggedPages()) {
      try {
        cards.add(
            new KestrosCardImpl(page,
                getReadMoreText(),
                this,
                "card",
                page.getName()));
      } catch (Exception e) {
        // Skip cards that fail to construct — null-safe
      }
    }
    return new ArrayList<>(cards);
  }
}
