package io.kestros.cms.components.basic.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public interface KestrosContainerElement extends KestrosBasicComponentElement {

  @JsonIgnore
  @Nonnull
  ResourceResolver getResourceResolver();

  @JsonIgnore
  @Nonnull
  Resource getResource();



  @JsonIgnore
  @Nonnull
  List<Resource> getChildren();

  @JsonIgnore
  @Nonnull
  List<KestrosBasicComponentElement> getChildElements();

}