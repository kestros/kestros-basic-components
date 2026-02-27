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
}
