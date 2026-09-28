package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
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
   * Orders pages by creation date, with pages that have none sorting first.
   *
   * <p>Subclasses sort with this rather than a method reference to the sort key: javac 15 and
   * later compile a reference to an inherited protected method from another package
   * into a synthetic lambda with no nullability annotations, which SpotBugs reports.</p>
   *
   * @return Comparator on jcr:created.
   */
  @Nonnull
  protected static Comparator<BaseContentPage> byCreatedTime() {
    return Comparator.comparing(BaseContainerSlingModelDataSource::getCreatedTime);
  }

  /**
   * Orders pages by last-modified date, with pages that have none sorting first.
   *
   * @return Comparator on jcr:lastModified.
   */
  @Nonnull
  protected static Comparator<BaseContentPage> byModifiedTime() {
    return Comparator.comparing(BaseContainerSlingModelDataSource::getModifiedTime);
  }

  /**
   * Sort key for a page's creation date.
   *
   * @param page Page to read the date from.
   * @return Epoch milliseconds, or 0 when the page has no jcr:created.
   */
  @Nonnull
  private static Long getCreatedTime(@Nonnull final BaseContentPage page) {
    return getJcrTime(page, "jcr:created");
  }

  /**
   * Sort key for a page's last-modified date.
   *
   * @param page Page to read the date from.
   * @return Epoch milliseconds, or 0 when the page has no jcr:lastModified.
   */
  @Nonnull
  private static Long getModifiedTime(@Nonnull final BaseContentPage page) {
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

}
