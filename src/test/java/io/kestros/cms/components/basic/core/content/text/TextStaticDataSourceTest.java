package io.kestros.cms.components.basic.core.content.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class TextStaticDataSourceTest extends BaseDataSourceTest {

  private TextStaticDataSource textStaticDataSource;
  private Map<String, Object> properties = new HashMap<>();
  private Resource resource;

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("text", "Test Text");
    resource = context.create().resource("/content/text/static/text", properties);
    context.request().setResource(resource);
  }

  @Override
  public void testToSyntheticResource() {
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);
    assertNotNull(textStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetText() {
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);
    assertEquals("Test Text", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWhenNotSet() {
    resource = context.create().resource("/content/text/static/text-empty", new HashMap<>());
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertNull(textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithHtmlContent() {
    properties.put("text", "<h1>HTML Text</h1>");
    resource = context.create().resource("/content/text/static/text-html", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("<h1>HTML Text</h1>", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithLineBreaks() {
    properties.put("text", "Line 1\nLine 2\nLine 3");
    resource = context.create().resource("/content/text/static/text-multiline", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("Line 1\nLine 2\nLine 3", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithSpecialCharacters() {
    properties.put("text", "Text with \"quotes\" and 'apostrophes'");
    resource = context.create().resource("/content/text/static/text-special", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("Text with \"quotes\" and 'apostrophes'", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithEmptyString() {
    properties.put("text", "");
    resource = context.create().resource("/content/text/static/text-empty-value", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("", textStaticDataSource.getText());
  }
}
