package io.kestros.cms.components.basic.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * API for the container element component element.
 */
public interface KestrosContainerElement extends KestrosBasicComponentElement {

  /**
   * Resource resolver.
   *
   * @return Resource resolver.
   */
  @JsonIgnore
  @Nonnull
  ResourceResolver getResourceResolver();

  /**
   * Resource.
   *
   * @return Resource.
   */
  @JsonIgnore
  @Nonnull
  Resource getResource();



  /**
   * Children.
   *
   * @return Children.
   */
  @JsonIgnore
  @Nonnull
  List<Resource> getChildren();

  /**
   * Child elements.
   *
   * @return Child elements.
   */
  @JsonIgnore
  @Nonnull
  List<KestrosBasicComponentElement> getChildElements();

}