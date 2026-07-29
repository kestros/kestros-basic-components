package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

public interface KestrosTopNavigationItem extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/top-navigation-item";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }


  @Nonnull
  List<KestrosTopNavigationItem> getNavigationItems();

  @Nonnull
  default List<Resource> getNavigationItemLinks() {
    List<Resource> resources = new ArrayList<>();
    for (KestrosTopNavigationItem item : getNavigationItems()) {
      resources.add(item.getResource());
    }
    return resources;
  }

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationItems());
  }

  @Nonnull
  default Boolean isActive() {
    if (getRequest() != null) {
      String currentPath = getRequest().getRequestURI();
      String linkPath = getHref();
      if (linkPath != null && !linkPath.isBlank()) {
        return currentPath.equals(linkPath);
      }
    }
    return Boolean.FALSE;
  }

  /**
   * Visible link text. This should normally be present and meaningful.
   */
  @Nullable
  String getText();

  /**
   * Destination URL. Should be null if the link is not navigable.
   */
  @Nullable
  String getHref();

  /**
   * Target attribute (e.g. "_self", "_blank").
   */
  @Nonnull
  AnchorTarget getTarget();

  /**
   * Get the target as a string.
   *
   * @return Target attribute value.
   */
  @Nonnull
  default String getTargetAsString() {
    if (getTarget() != null) {
      return getTarget().getTargetValue();
    }
    return AnchorTarget.SAME_WINDOW.getTargetValue();
  }

  /**
   * ARIA label. Used when visible text is missing or insufficient.
   */
  @Nullable
  String getAriaLabel();

  /**
   * Optional title attribute. Use sparingly – should add meaning, not duplicate text.
   */
  @Nullable
  String getTitle();

  /**
   * Relationship attribute (e.g. "noopener", "noreferrer", "nofollow").
   */
  @Nullable
  String getRel();

  /**
   * ARIA described-by ID reference.
   */
  @Nullable
  String getAriaDescribedBy();

  /**
   * Language of the link text, if different from the page language. Example: "fr", "de", "en-GB"
   */
  @Nullable
  String getLang();

}