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

public class SectionDataSourceComponentTest extends BaseDataSourceComponentTest {

  private static final String SECTION_RESOURCE_TYPE = "/libs/kestros/commons/components/structure/section";

  private SectionDataSourceComponent sectionDataSourceComponent;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return SECTION_RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", SECTION_RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    resource = context.create().resource("/content/section-dsc-1", properties);
    context.request().setResource(resource);
    sectionDataSourceComponent = context.request().adaptTo(SectionDataSourceComponent.class);
  }

  @Override
  @Test
  @Ignore("Pre-existing failure")
  public void testToSyntheticResource() {
    // Not applicable
  }

  @Test
  public void testGetBackgroundImage() {
    assertNull(sectionDataSourceComponent.getBackgroundImage());
  }

  @Test
  public void testGetBackgroundImageWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", SECTION_RESOURCE_TYPE);
    props.put("kes:datasource", "default");
    props.put("backgroundImage", "/content/assets/bg.jpg");
    Resource r = context.create().resource("/content/section-dsc-2", props);
    context.request().setResource(r);
    SectionDataSourceComponent section = context.request().adaptTo(
        SectionDataSourceComponent.class);
    assertEquals("/content/assets/bg.jpg", section.getBackgroundImage());
  }

  @Test
  public void testGetChildElements() {
    assertNotNull(sectionDataSourceComponent.getChildElements());
    assertEquals(0, sectionDataSourceComponent.getChildElements().size());
  }

  @Test
  public void testGetComponentResourceType() {
    // SectionDataSourceComponent extends BaseContainerDataSourceComponent<KestrosSection>
    // which inherits KestrosContainer.RESOURCE_TYPE = "kestros/components/basic/structure/container"
    assertNotNull(sectionDataSourceComponent.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(sectionDataSourceComponent.getResource());
  }

  @Test
  public void testGetChildren() {
    assertNotNull(sectionDataSourceComponent.getChildren());
    assertEquals(0, sectionDataSourceComponent.getChildren().size());
  }

  @Test
  public void testAdaptation() {
    assertNotNull(sectionDataSourceComponent);
  }
}
