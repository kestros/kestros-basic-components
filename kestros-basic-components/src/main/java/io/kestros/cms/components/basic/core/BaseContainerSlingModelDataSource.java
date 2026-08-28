package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Base Sling Model Data Source for Kestros Container Elements.
 */
public abstract class BaseContainerSlingModelDataSource extends BaseSlingModelDataSource
    implements KestrosContainerElement {

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    List<Resource> children = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getChildElements()) {
      if (element.isSynthetic()) {
        children.add(element.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        children.add(element.getResource());
      }
    }
    return children;
  }

  @Nonnull
  public <T extends KestrosBasicComponentElement> List<T> getChildrenAsType(
      @Nonnull String resourceType, @Nonnull Class<T> clazz) {
    List<T> items = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (!childResource.isResourceType(resourceType)) {
        continue;
      }
      T item = childResource.adaptTo(clazz);
      if (item != null) {
        items.add(item);
      }
    }
    return new ArrayList<>(items);
  }

  @Nonnull
  public <T extends KestrosBasicComponentElement> List<T> getChildrenOfType(
      @Nonnull Class<T> clazz) {
    List<T> children = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getChildElements()) {
      if (clazz.isInstance(element)) {
        children.add(clazz.cast(element));
      }
    }
    return children;
  }

  /**
   * Sort key for a page's creation date, with pages that have none sorting first.
   *
   * @param page Page to read the date from.
   * @return Epoch milliseconds, or 0 when the page has no jcr:created.
   */
  @Nonnull
  protected static Long getCreatedTime(@Nonnull final BaseContentPage page) {
    return getJcrTime(page, "jcr:created");
  }

  /**
   * Sort key for a page's last-modified date, with pages that have none sorting first.
   *
   * @param page Page to read the date from.
   * @return Epoch milliseconds, or 0 when the page has no jcr:lastModified.
   */
  @Nonnull
  protected static Long getModifiedTime(@Nonnull final BaseContentPage page) {
    return getJcrTime(page, "jcr:lastModified");
  }

  @Nonnull
  private static Long getJcrTime(@Nonnull final BaseContentPage page,
      @Nonnull final String propertyName) {
    final Resource jcrContent = page.getResource().getChild("jcr:content");
    if (jcrContent == null) {
      return 0L;
    }
    final Calendar calendar = jcrContent.getValueMap().get(propertyName, Calendar.class);
    return calendar != null ? calendar.getTimeInMillis() : 0L;
  }

  /**
   * Strips CR and LF from a value before it is logged, so an author-controlled path or an exception
   * message cannot forge a log line.
   *
   * @param value Value about to be logged.
   * @return The value with CR and LF removed, or null unchanged.
   */
  @Nullable
  protected static String forLog(@Nullable final String value) {
    return value == null ? null : value.replaceAll("[\r\n]", "");
  }

}
