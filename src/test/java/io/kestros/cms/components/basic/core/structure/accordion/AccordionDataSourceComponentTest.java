package io.kestros.cms.components.basic.core.structure.accordion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class AccordionDataSourceComponentTest extends BaseDataSourceComponentTest {
  private AccordionDataSourceComponent accordion;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosAccordion.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosAccordion.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/accordion",
        properties);
    context.create().resource("/content/sites/page/child-3/jcr:content/accordion/panel-1",
        properties);
    context.create().resource("/content/sites/page/child-3/jcr:content/accordion/panel-2",
        properties);
    context.create().resource("/content/sites/page/child-3/jcr:content/accordion/panel-3",
        properties);
    context.request().setResource(resource);

    accordion = context.request().adaptTo(AccordionDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(accordion.toSyntheticResource(context.resourceResolver(),
        "/synthetics/content/sites/page/child-3/jcr:content"));
  }

  @Test
  public void testGetPanelElements() {
    assertEquals(3, accordion.getPanelElements().size());
  }

  @Test
  public void testGetPanelElementsNotNull() {
    assertNotNull(accordion.getPanelElements());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosAccordion.RESOURCE_TYPE, accordion.getComponentResourceType());
  }

  @Test
  public void testGetChildren() {
    assertEquals(3, accordion.getChildren().size());
  }

  @Test
  public void testGetChildElements() {
    assertEquals(3, accordion.getChildElements().size());
  }

  @Test
  public void testGetChildElementsNotNull() {
    assertNotNull(accordion.getChildElements());
  }

  @Test
  public void testGetPathNotNull() {
    assertNotNull(accordion.getPath());
  }

  @Test
  public void testGetResource() {
    assertNotNull(accordion.getResource());
  }

}