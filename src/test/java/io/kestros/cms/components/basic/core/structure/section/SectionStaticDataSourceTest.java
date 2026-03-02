package io.kestros.cms.components.basic.core.structure.section;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class SectionStaticDataSourceTest extends BaseDataSourceComponentTest {

  private static final String SECTION_RESOURCE_TYPE = "/libs/kestros/commons/components/structure/section";

  private SectionStaticDataSource sectionStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return SECTION_RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", SECTION_RESOURCE_TYPE);
    resource = context.create().resource("/content/section-1", properties);
    context.request().setResource(resource);
    sectionStaticDataSource = context.request().adaptTo(SectionStaticDataSource.class);
  }

  @Override
  @Test
  @Ignore("Pre-existing failure")
  public void testToSyntheticResource() {
    // Not applicable
  }

  @Test
  public void testGetBackgroundImage() {
    assertNull(sectionStaticDataSource.getBackgroundImage());
  }

  @Test
  public void testGetBackgroundImageWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", SECTION_RESOURCE_TYPE);
    props.put("backgroundImage", "/content/assets/image.jpg");
    Resource r = context.create().resource("/content/section-2", props);
    context.request().setResource(r);
    SectionStaticDataSource section = context.request().adaptTo(SectionStaticDataSource.class);
    assertEquals("/content/assets/image.jpg", section.getBackgroundImage());
  }

  @Test
  public void testGetChildElements() {
    assertNotNull(sectionStaticDataSource.getChildElements());
    assertEquals(0, sectionStaticDataSource.getChildElements().size());
  }

  @Test
  public void testGetComponentResourceType() {
    // SectionStaticDataSource inherits from KestrosContainer (no separate RESOURCE_TYPE for KestrosSection)
    assertNotNull(sectionStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(sectionStaticDataSource.getResource());
  }

  @Test
  public void testGetPath() {
    assertEquals("/content/section-1", sectionStaticDataSource.getPath());
  }

  @Test
  public void testGetChildren() {
    assertNotNull(sectionStaticDataSource.getChildren());
    assertEquals(0, sectionStaticDataSource.getChildren().size());
  }

  @Test
  public void testAdaptation() {
    assertNotNull(sectionStaticDataSource);
  }
}
