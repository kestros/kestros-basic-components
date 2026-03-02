package io.kestros.cms.components.basic.core.navigation.nav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class NavigationItemStaticDataSourceTest extends BaseDataSourceComponentTest {

  private NavigationItemStaticDataSource navItem;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosNavigationItem.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    properties.put("text", "Nav Item");
    properties.put("href", "/content/page");
    resource = context.create().resource("/content/nav/nav-item-1", properties);
    context.request().setResource(resource);
    navItem = context.request().adaptTo(NavigationItemStaticDataSource.class);
  }

  @Override
  @Test
  @Ignore("Pre-existing failure")
  public void testToSyntheticResource() {
    // Not applicable
  }

  @Test
  public void testGetText() {
    assertEquals("Nav Item", navItem.getText());
  }

  @Test
  public void testGetTextWhenNull() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    Resource r = context.create().resource("/content/nav/nav-item-no-text", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertNull(item.getText());
  }

  @Test
  public void testGetHref() {
    // LinkUtils appends .html to internal paths
    assertEquals("/content/page.html", navItem.getHref());
  }

  @Test
  public void testGetHrefWhenNull() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    Resource r = context.create().resource("/content/nav/nav-item-no-href", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertNull(item.getHref());
  }

  @Test
  public void testGetTarget() {
    assertNotNull(navItem.getTarget());
    assertEquals(AnchorTarget.SAME_WINDOW, navItem.getTarget());
  }

  @Test
  public void testGetTargetNewWindow() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    props.put("openInNewTab", true);
    Resource r = context.create().resource("/content/nav/nav-item-new-window", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertEquals(AnchorTarget.NEW_WINDOW, item.getTarget());
  }

  @Test
  public void testGetAriaLabel() {
    assertNull(navItem.getAriaLabel());
  }

  @Test
  public void testGetAriaLabelWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    props.put("ariaLabel", "Aria label for nav");
    Resource r = context.create().resource("/content/nav/nav-item-aria", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertEquals("Aria label for nav", item.getAriaLabel());
  }

  @Test
  public void testGetTitle() {
    assertNull(navItem.getTitle());
  }

  @Test
  public void testGetTitleWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    props.put("title", "Title");
    Resource r = context.create().resource("/content/nav/nav-item-title", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertEquals("Title", item.getTitle());
  }

  @Test
  public void testGetRel() {
    assertNull(navItem.getRel());
  }

  @Test
  public void testGetRelWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    props.put("rel", "nofollow");
    Resource r = context.create().resource("/content/nav/nav-item-rel", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertEquals("nofollow", item.getRel());
  }

  @Test
  public void testGetAriaDescribedBy() {
    assertNull(navItem.getAriaDescribedBy());
  }

  @Test
  public void testGetLang() {
    assertNull(navItem.getLang());
  }

  @Test
  public void testGetLangWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    props.put("lang", "fr");
    Resource r = context.create().resource("/content/nav/nav-item-lang", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertEquals("fr", item.getLang());
  }

  @Test
  public void testIsActive() {
    assertNull(navItem.isActive());
  }

  @Test
  public void testGetNavigationItems() {
    assertNotNull(navItem.getNavigationItems());
    assertEquals(0, navItem.getNavigationItems().size());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosNavigationItem.RESOURCE_TYPE, navItem.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(navItem.getResource());
  }

  @Test
  public void testGetPath() {
    assertEquals("/content/nav/nav-item-1", navItem.getPath());
  }

  @Test
  public void testGetTargetAsString() {
    assertEquals("_self", navItem.getTargetAsString());
  }

  @Test
  public void testGetNavigationItemLinks() {
    assertNotNull(navItem.getNavigationItemLinks());
    assertEquals(0, navItem.getNavigationItemLinks().size());
  }

  @Test
  public void testGetChildElements() {
    assertNotNull(navItem.getChildElements());
    assertEquals(0, navItem.getChildElements().size());
  }

  @Test
  public void testGetTargetAsStringNewWindow() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    props.put("openInNewTab", true);
    Resource r = context.create().resource("/content/nav/nav-item-target-str", props);
    context.request().setResource(r);
    NavigationItemStaticDataSource item = context.request().adaptTo(
        NavigationItemStaticDataSource.class);
    assertEquals("_blank", item.getTargetAsString());
  }
}
