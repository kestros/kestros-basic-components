package io.kestros.cms.components.basic.core;

import static org.junit.Assert.assertEquals;

import io.kestros.cms.components.basic.BaseComponentTest;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * The four page-backed list data sources shared an identical copy of these comparators as inline
 * lambdas. Tested directly now that they live in one place.
 */
public class ContentPageSorterTest extends BaseComponentTest {

  @Override
  public void doComponentSetup() {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");

    final Map<String, Object> titled = new HashMap<>();
    titled.put("jcr:primaryType", "nt:unstructured");
    titled.put("jcr:title", "Zebra");
    context.create().resource("/content/alpha", pageProperties);
    context.create().resource("/content/alpha/jcr:content", titled);

    final Map<String, Object> dated = new HashMap<>();
    dated.put("jcr:primaryType", "nt:unstructured");
    dated.put("jcr:title", "Apple");
    dated.put("jcr:created", Calendar.getInstance());
    dated.put("jcr:lastModified", Calendar.getInstance());
    context.create().resource("/content/zulu", pageProperties);
    context.create().resource("/content/zulu/jcr:content", dated);

    // No jcr:content at all: the date comparators must treat this as 0 rather than throwing.
    context.create().resource("/content/mike", pageProperties);
  }

  @Override
  public void testToSyntheticResource() {
    // Not a component; nothing to synthesize.
  }

  private List<BaseContentPage> pages() {
    final List<BaseContentPage> pages = new ArrayList<>();
    for (final String name : Arrays.asList("zulu", "alpha", "mike")) {
      pages.add(context.resourceResolver().getResource("/content/" + name)
          .adaptTo(BaseContentPage.class));
    }
    return pages;
  }

  @Test
  public void testSortByName() {
    final List<BaseContentPage> pages = pages();
    ContentPageSorter.sort(pages, "name");

    assertEquals("alpha", pages.get(0).getName());
    assertEquals("mike", pages.get(1).getName());
    assertEquals("zulu", pages.get(2).getName());
  }

  /** An unrecognised key falls back to the display title, which is what the lambdas did. */
  @Test
  public void testSortByAnythingElseUsesTheDisplayTitle() {
    final List<BaseContentPage> pages = pages();
    ContentPageSorter.sort(pages, "title");

    assertEquals("zulu", pages.get(0).getName());
  }

  @Test
  public void testSortByCreatedDateToleratesAPageWithNoContent() {
    final List<BaseContentPage> pages = pages();
    ContentPageSorter.sort(pages, "createdDate");

    // The two pages without a created date sort first, both scoring 0.
    assertEquals(3, pages.size());
    assertEquals("zulu", pages.get(2).getName());
  }

  @Test
  public void testSortByLastModifiedToleratesAPageWithNoContent() {
    final List<BaseContentPage> pages = pages();
    ContentPageSorter.sort(pages, "lastModified");

    assertEquals(3, pages.size());
    assertEquals("zulu", pages.get(2).getName());
  }

  @Test
  public void testSortByEmptyLeavesTheOrderAlone() {
    final List<BaseContentPage> pages = pages();
    ContentPageSorter.sort(pages, "");

    assertEquals("zulu", pages.get(0).getName());
    assertEquals("alpha", pages.get(1).getName());
  }
}
