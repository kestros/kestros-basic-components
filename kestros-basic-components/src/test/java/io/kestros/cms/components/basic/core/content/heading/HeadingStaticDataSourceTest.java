package io.kestros.cms.components.basic.core.content.heading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class HeadingStaticDataSourceTest extends BaseDataSourceTest {

  private HeadingStaticDataSource heading;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {
  }

  @Test
  public void testGetHeadingTypeReadsTheConfiguredValue() {
    properties.put("headingText", "Sample Heading");
    properties.put("headingType", "h3");
    resource = context.create().resource("/content/heading/static/configured", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingStaticDataSource.class);

    assertEquals("h3", heading.getHeadingType());
  }

  /**
   * getHeadingType() is declared @Nonnull by KestrosHeading, and it used to hand back the property
   * straight out of the value map, so a heading saved with no headingType returned null. h1 is
   * both the option the dialog marks selected and the element the HTL falls back to.
   */
  @Test
  public void testGetHeadingTypeDefaultsToH1() {
    properties.put("headingText", "Sample Heading");
    resource = context.create().resource("/content/heading/static/no-type", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingStaticDataSource.class);

    assertEquals("h1", heading.getHeadingType());
  }

  @Test
  public void testGetHeadingTextIsNullWhenNoneIsSet() {
    resource = context.create().resource("/content/heading/static/no-text", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingStaticDataSource.class);

    assertNull(heading.getHeadingText());
  }
}
