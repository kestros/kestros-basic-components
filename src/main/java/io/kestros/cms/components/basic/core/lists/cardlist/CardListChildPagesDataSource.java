package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListChildPagesDataSource extends BaseContainerSlingModelDataSource implements
                                                                                    KestrosCardList {
  private BaseContentPage rootPage;

  BaseContentPage getRootPage() {
    if (rootPage == null) {
      String pagesPath = getResource().getValueMap().get("pagesPath", String.class);
      if (pagesPath != null) {
        Resource pageResource = getResourceResolver().getResource(pagesPath);
        if (pageResource != null) {
          try {
            rootPage = pageResource.adaptTo(BaseContentPage.class);
          } catch (Exception e) {
            return null;
          }
        }
      } else {
        try {
          rootPage = getResource().adaptTo(BaseComponent.class).getContainingPage();
        } catch (NoValidAncestorException e) {
          return null;
        }
      }
    }
    return rootPage;
  }

  @Nullable
  public String getReadMoreText() {
    return getResource().getValueMap().get("readMoreText", String.class);
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    List<BaseContentPage> pages = new ArrayList<>(getRootPage().getChildPages());

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", false);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      switch (sortBy) {
        case "createdDate":
          pages.sort(Comparator.comparing(p -> {
            Calendar cal = p.getResource().getChild("jcr:content") != null
                ? p.getResource().getChild("jcr:content").getValueMap()
                    .get("jcr:created", Calendar.class) : null;
            return cal != null ? cal.getTimeInMillis() : 0L;
          }));
          break;
        case "lastModified":
          pages.sort(Comparator.comparing(p -> {
            Calendar cal = p.getResource().getChild("jcr:content") != null
                ? p.getResource().getChild("jcr:content").getValueMap()
                    .get("jcr:lastModified", Calendar.class) : null;
            return cal != null ? cal.getTimeInMillis() : 0L;
          }));
          break;
        default:
          pages.sort(Comparator.comparing(p -> {
            switch (sortBy) {
              case "name":
                return p.getName() != null ? p.getName() : "";
              default:
                return p.getDisplayTitle() != null ? p.getDisplayTitle() : p.getName();
            }
          }));
          break;
      }
    }

    if (reverse) {
      Collections.reverse(pages);
    }
    if (limit > 0 && pages.size() > limit) {
      pages = pages.subList(0, limit);
    }

    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : pages) {
      try {
        cards.add(
            new KestrosCardImpl(page,
                getReadMoreText(),
                this,
                "card",
//                getElementVariations("titleVariations", KestrosImage.RESOURCE_TYPE),
//                getLayout("title"),
//                getElementVariations("imageVariations", KestrosImage.RESOURCE_TYPE),
//                getLayout("image"),
//                getElementVariations("buttonGroupVariations", KestrosButtonGroup.RESOURCE_TYPE),
//                getLayout("buttonGroupLayout"),
//                getElementVariations("buttonVariations", KestrosButton.RESOURCE_TYPE),
//                getLayout("button"),
//                null,
                page.getName()));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ArrayList<>(cards);
  }

}
