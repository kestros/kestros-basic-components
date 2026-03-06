package io.kestros.cms.components.basic.core.content.dialogfieldtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosDialogFieldTest;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class DialogFieldTestDataSourceComponentTest extends BaseDataSourceComponentTest {

  private DialogFieldTestDataSourceComponent component;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosDialogFieldTest.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosDialogFieldTest.RESOURCE_TYPE);
  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {
  }

  @Test
  public void testGetSampleText() {
    properties.put("kes:datasource", "default");
    properties.put("sampleText", "Hello World");
    resource = context.create().resource("/content/dialog-field-test", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("Hello World", component.getSampleText());
  }

  @Test
  public void testGetSampleTextWhenNull() {
    properties.put("kes:datasource", "default");
    resource = context.create().resource("/content/dialog-field-test-null", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertNull(component.getSampleText());
  }

  @Test
  public void testGetSampleTextarea() {
    properties.put("kes:datasource", "default");
    properties.put("sampleTextarea", "Multi-line text content");
    resource = context.create().resource("/content/dialog-field-test-ta", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("Multi-line text content", component.getSampleTextarea());
  }

  @Test
  public void testGetSampleRichtext() {
    properties.put("kes:datasource", "default");
    properties.put("sampleRichtext", "<p>Rich text content</p>");
    resource = context.create().resource("/content/dialog-field-test-rt", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("<p>Rich text content</p>", component.getSampleRichtext());
  }

  @Test
  public void testGetSampleCheckboxTrue() {
    properties.put("kes:datasource", "default");
    properties.put("sampleCheckbox", true);
    resource = context.create().resource("/content/dialog-field-test-cb-t", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertTrue(component.getSampleCheckbox());
  }

  @Test
  public void testGetSampleCheckboxFalse() {
    properties.put("kes:datasource", "default");
    properties.put("sampleCheckbox", false);
    resource = context.create().resource("/content/dialog-field-test-cb-f", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertFalse(component.getSampleCheckbox());
  }

  @Test
  public void testGetSamplePath() {
    properties.put("kes:datasource", "default");
    properties.put("samplePath", "/content/sites/my-site");
    resource = context.create().resource("/content/dialog-field-test-path", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("/content/sites/my-site", component.getSamplePath());
  }

  @Test
  public void testGetSampleSelect() {
    properties.put("kes:datasource", "default");
    properties.put("sampleSelect", "option-b");
    resource = context.create().resource("/content/dialog-field-test-sel", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("option-b", component.getSampleSelect());
  }

  @Test
  public void testGetSampleNumber() {
    properties.put("kes:datasource", "default");
    properties.put("sampleNumber", "42");
    resource = context.create().resource("/content/dialog-field-test-num", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("42", component.getSampleNumber());
  }

  @Test
  public void testGetSampleDate() {
    properties.put("kes:datasource", "default");
    properties.put("sampleDate", "2026-03-06");
    resource = context.create().resource("/content/dialog-field-test-date", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("2026-03-06", component.getSampleDate());
  }

  @Test
  public void testGetSampleDatetime() {
    properties.put("kes:datasource", "default");
    properties.put("sampleDatetime", "2026-03-06T14:30:00");
    resource = context.create().resource("/content/dialog-field-test-datetime", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("2026-03-06T14:30:00", component.getSampleDatetime());
  }

  @Test
  public void testGetSampleToggleTrue() {
    properties.put("kes:datasource", "default");
    properties.put("sampleToggle", true);
    resource = context.create().resource("/content/dialog-field-test-toggle-t", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertTrue(component.getSampleToggle());
  }

  @Test
  public void testGetSampleToggleFalse() {
    properties.put("kes:datasource", "default");
    properties.put("sampleToggle", false);
    resource = context.create().resource("/content/dialog-field-test-toggle-f", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertFalse(component.getSampleToggle());
  }

  @Test
  public void testGetSampleRadio() {
    properties.put("kes:datasource", "default");
    properties.put("sampleRadio", "radio-b");
    resource = context.create().resource("/content/dialog-field-test-radio", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("radio-b", component.getSampleRadio());
  }

  @Test
  public void testGetSampleHidden() {
    properties.put("kes:datasource", "default");
    properties.put("sampleHidden", "hidden-default-value");
    resource = context.create().resource("/content/dialog-field-test-hidden", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("hidden-default-value", component.getSampleHidden());
  }

  @Test
  public void testGetSampleImage() {
    properties.put("kes:datasource", "default");
    properties.put("sampleImage", "/content/assets/sample-image.png");
    resource = context.create().resource("/content/dialog-field-test-image", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("/content/assets/sample-image.png", component.getSampleImage());
  }

  @Test
  public void testGetSampleTag() {
    properties.put("kes:datasource", "default");
    properties.put("sampleTag", "category:sample-tag");
    resource = context.create().resource("/content/dialog-field-test-tag", properties);
    context.request().setResource(resource);
    component = context.request().adaptTo(DialogFieldTestDataSourceComponent.class);
    assertEquals("category:sample-tag", component.getSampleTag());
  }
}
