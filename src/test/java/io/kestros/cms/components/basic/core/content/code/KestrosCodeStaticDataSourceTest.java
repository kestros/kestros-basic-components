package io.kestros.cms.components.basic.core.content.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosCodeStaticDataSourceTest extends BaseDataSourceTest {

  private KestrosCodeStaticDataSource codeStaticDataSource;
  private Map<String, Object> properties = new HashMap<>();
  private Resource resource;

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("code", "Sample Code");
    resource = context.create().resource("/content/code/static/code", properties);
    context.request().setResource(resource);
  }

  @Override
  public void testToSyntheticResource() {
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);
    assertNotNull(codeStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetCode() {
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);
    assertEquals("Sample Code", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWhenNotSet() {
    resource = context.create().resource("/content/code/static/code-empty", new HashMap<>());
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertNull(codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithJavaScript() {
    properties.put("code", "const x = 5;");
    resource = context.create().resource("/content/code/static/code-js", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("const x = 5;", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithXml() {
    properties.put("code", "<?xml version=\"1.0\"?><root></root>");
    resource = context.create().resource("/content/code/static/code-xml", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("<?xml version=\"1.0\"?><root></root>", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithSpecialCharacters() {
    properties.put("code", "code with <tags> & symbols");
    resource = context.create().resource("/content/code/static/code-special", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("code with <tags> & symbols", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetComponentResourceType() {
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);
    assertEquals("/libs/kestros/commons/components/content/code", codeStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testGetCodeWithEmptyString() {
    properties.put("code", "");
    resource = context.create().resource("/content/code/static/code-empty-value", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithPythonCode() {
    properties.put("code", "def hello():\n    print('Hello World')");
    resource = context.create().resource("/content/code/static/code-python", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("def hello():\n    print('Hello World')", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithJavaCode() {
    properties.put("code", "public class Example {\n    public static void main(String[] args) {}\n}");
    resource = context.create().resource("/content/code/static/code-java", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("public class Example {\n    public static void main(String[] args) {}\n}", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithSqlQuery() {
    properties.put("code", "SELECT * FROM table WHERE id = 1;");
    resource = context.create().resource("/content/code/static/code-sql", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("SELECT * FROM table WHERE id = 1;", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithJsonData() {
    properties.put("code", "{\"name\": \"John\", \"age\": 30, \"items\": [1, 2, 3]}");
    resource = context.create().resource("/content/code/static/code-json", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("{\"name\": \"John\", \"age\": 30, \"items\": [1, 2, 3]}", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithHtmlMarkup() {
    properties.put("code", "<div class='container'><p>HTML content</p></div>");
    resource = context.create().resource("/content/code/static/code-html", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("<div class='container'><p>HTML content</p></div>", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithCssStyles() {
    properties.put("code", ".class { color: red; background: blue; }");
    resource = context.create().resource("/content/code/static/code-css", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals(".class { color: red; background: blue; }", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithRegularExpressions() {
    properties.put("code", "pattern = /[a-z0-9]+@[a-z]+\\.[a-z]+/gi;");
    resource = context.create().resource("/content/code/static/code-regex", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("pattern = /[a-z0-9]+@[a-z]+\\.[a-z]+/gi;", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithMultiLineContent() {
    String multilineCode = "Line 1\nLine 2\nLine 3\nLine 4\nLine 5";
    properties.put("code", multilineCode);
    resource = context.create().resource("/content/code/static/code-multiline", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals(multilineCode, codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithIndentation() {
    properties.put("code", "if (true) {\n    if (nested) {\n        doSomething();\n    }\n}");
    resource = context.create().resource("/content/code/static/code-indent", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("if (true) {\n    if (nested) {\n        doSomething();\n    }\n}", codeStaticDataSource.getCode());
  }

  @Test
  public void testGetCodeWithUnicodeCharacters() {
    properties.put("code", "// Comment in Japanese: コード例");
    resource = context.create().resource("/content/code/static/code-unicode", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals("// Comment in Japanese: コード例", codeStaticDataSource.getCode());
  }

  @Test
  public void testAdaptation() {
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);
    assertNotNull(codeStaticDataSource);
  }

  @Test
  public void testGetCodeMultipleTimes() {
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);
    String code1 = codeStaticDataSource.getCode();
    String code2 = codeStaticDataSource.getCode();
    assertEquals(code1, code2);
    assertEquals("Sample Code", code1);
  }

  @Test
  public void testGetCodeWithVeryLongContent() {
    String longCode = "const x = \"".concat("A".repeat(500)).concat("\";");
    properties.put("code", longCode);
    resource = context.create().resource("/content/code/static/code-long", properties);
    context.request().setResource(resource);
    codeStaticDataSource = context.request().adaptTo(KestrosCodeStaticDataSource.class);

    assertEquals(longCode, codeStaticDataSource.getCode());
  }
}
