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

  @Test
  public void testGetTextWithLinks() {
    properties.put("text", "<p><a href='/path/to/page'>Link Text</a></p>");
    resource = context.create().resource("/content/richtext/static/richtext-links", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p><a href='/path/to/page'>Link Text</a></p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithLists() {
    properties.put("text", "<ul><li>Item 1</li><li>Item 2</li></ul>");
    resource = context.create().resource("/content/richtext/static/richtext-lists", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<ul><li>Item 1</li><li>Item 2</li></ul>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithTables() {
    properties.put("text", "<table><tr><td>Cell 1</td><td>Cell 2</td></tr></table>");
    resource = context.create().resource("/content/richtext/static/richtext-tables", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<table><tr><td>Cell 1</td><td>Cell 2</td></tr></table>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithBlockquotes() {
    properties.put("text", "<blockquote><p>Quoted text</p></blockquote>");
    resource = context.create().resource("/content/richtext/static/richtext-blockquote", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<blockquote><p>Quoted text</p></blockquote>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithCodeBlocks() {
    properties.put("text", "<pre><code>function example() { return true; }</code></pre>");
    resource = context.create().resource("/content/richtext/static/richtext-code", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<pre><code>function example() { return true; }</code></pre>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithHorizontalRule() {
    properties.put("text", "<p>Before</p><hr/><p>After</p>");
    resource = context.create().resource("/content/richtext/static/richtext-hr", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p>Before</p><hr/><p>After</p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithLineBreaks() {
    properties.put("text", "<p>Line 1<br/>Line 2<br/>Line 3</p>");
    resource = context.create().resource("/content/richtext/static/richtext-br", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p>Line 1<br/>Line 2<br/>Line 3</p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithSpecialCharactersInHtml() {
    properties.put("text", "<p>&lt;escaped&gt; &amp; symbols</p>");
    resource = context.create().resource("/content/richtext/static/richtext-special", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p>&lt;escaped&gt; &amp; symbols</p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithNestedFormatting() {
    properties.put("text", "<p><strong><em>Bold and Italic</em></strong></p>");
    resource = context.create().resource("/content/richtext/static/richtext-nested", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p><strong><em>Bold and Italic</em></strong></p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetTextWithDataAttributes() {
    properties.put("text", "<p data-id='123' class='highlight'>Text with attributes</p>");
    resource = context.create().resource("/content/richtext/static/richtext-attrs", properties);
    context.request().setResource(resource);
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);

    assertEquals("<p data-id='123' class='highlight'>Text with attributes</p>", richTextStaticDataSource.getText());
  }

  @Test
  public void testGetComponentResourceType() {
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);
    assertNotNull(richTextStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testAdaptation() {
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);
    assertNotNull(richTextStaticDataSource);
  }

  @Test
  public void testGetTextMultipleTimes() {
    richTextStaticDataSource = context.request().adaptTo(KestrosRichTextStaticDataSource.class);
    String text1 = richTextStaticDataSource.getText();
    String text2 = richTextStaticDataSource.getText();
    assertEquals(text1, text2);
    assertEquals("<p>Sample Rich Text</p>", text1);
  }
}
