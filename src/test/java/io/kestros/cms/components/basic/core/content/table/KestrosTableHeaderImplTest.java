package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosTableHeaderImplTest extends BaseSyntheticTest {

  private KestrosTableHeaderImpl header;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);
    header = new KestrosTableHeaderImpl("Points", dataSource, "header", "headerElement");
  }

  @Override
  public void testToSyntheticResource() throws ComponentConfigurationException {
    Resource syntheticResource = header.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/headerElement", syntheticResource.getPath());
  }

  @Test
  public void testGetText() {
    assertEquals("Points", header.getText());
  }
}
