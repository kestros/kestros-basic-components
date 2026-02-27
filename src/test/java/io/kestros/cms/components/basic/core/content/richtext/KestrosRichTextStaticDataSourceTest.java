package io.kestros.cms.components.basic.core.content.richtext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosRichTextStaticDataSourceTest extends BaseDataSourceTest {

  private KestrosRichTextStaticDataSource richTextStaticDataSource;
  private Map<String, Object> properties = new HashMap<>();
  private Resource resource;

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("text", "<p>Sample Rich Text</p>");
    resource = context.create().resource("/content/richtext/static/richtext", properties);
    context.request().setResource(resource);
  }

  @Override
  public void testToSyntheticResource() {
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);
    assertNotNull(richTextStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetText() {
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);
    assertEquals("<p>Sample Rich Text</p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWhenNotSet() {
    resource = context.create().resource("/content/richtext/static/richtext-empty", new HashMap<>());
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertNull(richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithHeadings() {
    properties.put("text", "<h1>Heading 1</h1><h2>Heading 2</h2>");
    resource = context.create().resource("/content/richtext/static/richtext-headings", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<h1>Heading 1</h1><h2>Heading 2</h2>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithFormatting() {
    properties.put("text", "<p><strong>Bold</strong> and <em>Italic</em> text</p>");
    resource = context.create().resource("/content/richtext/static/richtext-formatting", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p><strong>Bold</strong> and <em>Italic</em> text</p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithImages() {
    properties.put("text", "<p><img src='/path/to/image.jpg' alt='Image'/></p>");
    resource = context.create().resource("/content/richtext/static/richtext-images", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p><img src='/path/to/image.jpg' alt='Image'/></p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithEmptyString() {
    properties.put("text", "");
    resource = context.create().resource("/content/richtext/static/richtext-empty-value", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("", richTextStaticDataSource.getText());
  }
}
