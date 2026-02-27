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

  @Test
  public void testGetTextWithUnicodeCharacters() {
    properties.put("text", "Unicode: ñ € 中文 العربية");
    resource = context.create().resource("/content/text/static/text-unicode", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("Unicode: ñ € 中文 العربية", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithWhitespace() {
    properties.put("text", "  Text with spaces  ");
    resource = context.create().resource("/content/text/static/text-spaces", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("  Text with spaces  ", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithNumbers() {
    properties.put("text", "12345.67890");
    resource = context.create().resource("/content/text/static/text-numbers", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("12345.67890", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithMarkdownCharacters() {
    properties.put("text", "# Heading\n**bold** *italic* `code`");
    resource = context.create().resource("/content/text/static/text-markdown", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("# Heading\n**bold** *italic* `code`", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithJsonContent() {
    properties.put("text", "{\"key\": \"value\", \"number\": 123}");
    resource = context.create().resource("/content/text/static/text-json", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("{\"key\": \"value\", \"number\": 123}", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithXmlContent() {
    properties.put("text", "<?xml version=\"1.0\"?><root><element>content</element></root>");
    resource = context.create().resource("/content/text/static/text-xml", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("<?xml version=\"1.0\"?><root><element>content</element></root>", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithRegexCharacters() {
    properties.put("text", "Text with regex: [a-z]+ \\d+ ^start$ end.*");
    resource = context.create().resource("/content/text/static/text-regex", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("Text with regex: [a-z]+ \\d+ ^start$ end.*", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithVeryLongContent() {
    String longText = "A".repeat(1000);
    properties.put("text", longText);
    resource = context.create().resource("/content/text/static/text-long", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals(longText, textStaticDataSource.getText());
  }

  @Test
  public void testGetComponentResourceType() {
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);
    assertNotNull(textStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testAdaptation() {
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);
    assertNotNull(textStaticDataSource);
  }

  @Test
  public void testGetTextMultipleTimes() {
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);
    String text1 = textStaticDataSource.getText();
    String text2 = textStaticDataSource.getText();
    assertEquals(text1, text2);
    assertEquals("Test Text", text1);
  }

  @Test
  public void testGetTextWithCarriageReturns() {
    properties.put("text", "Line1\r\nLine2\r\nLine3");
    resource = context.create().resource("/content/text/static/text-crlf", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("Line1\r\nLine2\r\nLine3", textStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithTabs() {
    properties.put("text", "Column1\tColumn2\tColumn3");
    resource = context.create().resource("/content/text/static/text-tabs", properties);
    context.request().setResource(resource);
    textStaticDataSource = context.request().adaptTo(TextStaticDataSource.class);

    assertEquals("Column1\tColumn2\tColumn3", textStaticDataSource.getText());
  }
}
