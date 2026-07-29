package io.kestros.cms.components.basic.core.lists.cardlist;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.ContentPageSorter;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Supplies {@link KestrosCardList} built from child pages.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListChildPagesDataSource extends BaseContainerSlingModelDataSource
    implements KestrosCardList {

  private static final Logger LOG =
      LoggerFactory.getLogger(CardListChildPagesDataSource.class);
  private BaseContentPage rootPage;

  @Nullable
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

  /**
   * Read more text.
   *
   * @return Read more text.
   */
  @Nullable
  public String getReadMoreText() {
    return getResource().getValueMap().get("readMoreText", String.class);
  }

  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed ComponentElementRenderingException so the failure"
          + " stays identifiable, per the ruling on DataSourceComponent.")
  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    BaseContentPage root = getRootPage();
    if (root == null) {
      return new ArrayList<>();
    }
    List<BaseContentPage> pages = new ArrayList<>(root.getChildPages());

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", Boolean.FALSE);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    ContentPageSorter.sort(pages, sortBy);

    if (reverse) {
      Collections.reverse(pages);
    }
    if (limit > 0 && pages.size() > limit) {
      pages = pages.subList(0, limit);
    }

    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : pages) {
      try {
        cards.add(new KestrosCardImpl(page, getReadMoreText(), this, "card", page.getName()));
      } catch (final ComponentConfigurationException exception) {
        // One page that cannot be turned into a card should not empty the whole list. This used
        // to rethrow, so a single bad page took the component down with it. Deliberately narrow:
        // ComponentElementRenderingException is unchecked and means the component as a whole
        // cannot render (no resolvable theme or UI framework), which must still reach HTL rather
        // than be logged once per page.
        LOG.warn("Unable to build a card for {} in the list at {}: {}",
            String.valueOf(page.getPath()).replaceAll("[\r\n]", ""),
            String.valueOf(getResource().getPath()).replaceAll("[\r\n]", ""),
            String.valueOf(exception.getMessage()).replaceAll("[\r\n]", ""));
      }
    }
    return new ArrayList<>(cards);
  }

}
