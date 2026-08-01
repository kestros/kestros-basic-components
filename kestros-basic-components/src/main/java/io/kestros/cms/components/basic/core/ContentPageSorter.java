package io.kestros.cms.components.basic.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Orders the pages behind a list component.
 *
 * <p>The four list data sources that sort pages carried an identical copy of these three
 * comparators as inline lambdas. Extracted here so the ordering rules live in one place and can
 * carry their own nullability contract, which a lambda body cannot.</p>
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public final class ContentPageSorter {

  private static final String CREATED_DATE = "createdDate";
  private static final String LAST_MODIFIED = "lastModified";
  private static final String NAME = "name";

  private ContentPageSorter() {
    // Utility class; not instantiable.
  }

  /**
   * Sorts the supplied pages in place, by the named property.
   *
   * <p>An unrecognised sort key falls back to the display title, which is the behaviour the
   * inline comparators had.</p>
   *
   * @param pages Pages to sort, modified in place.
   * @param sortBy Sort key: createdDate, lastModified, name, or anything else for display title.
   */
  public static void sort(@Nonnull final List<BaseContentPage> pages,
      @Nonnull final String sortBy) {
    if (sortBy.isEmpty()) {
      return;
    }
    switch (sortBy) {
      case CREATED_DATE:
        pages.sort(Comparator.comparing(ContentPageSorter::createdDate));
        break;
      case LAST_MODIFIED:
        pages.sort(Comparator.comparing(ContentPageSorter::lastModifiedDate));
        break;
      case NAME:
        pages.sort(Comparator.comparing(ContentPageSorter::name));
        break;
      default:
        pages.sort(Comparator.comparing(ContentPageSorter::displayTitle));
        break;
    }
  }

  /**
   * The page's creation date, as epoch milliseconds.
   *
   * @param page Page to read from.
   * @return The creation date as epoch milliseconds, or 0 when it is absent.
   */
  @Nonnull
  private static Long createdDate(@Nonnull final BaseContentPage page) {
    return contentDate(page, "jcr:created");
  }

  /**
   * The page's last-modified date, as epoch milliseconds.
   *
   * @param page Page to read from.
   * @return The last-modified date as epoch milliseconds, or 0 when it is absent.
   */
  @Nonnull
  private static Long lastModifiedDate(@Nonnull final BaseContentPage page) {
    return contentDate(page, "jcr:lastModified");
  }

  /**
   * A date property from the page's jcr:content, as epoch milliseconds.
   *
   * @param page Page to read from.
   * @param propertyName Date property to read.
   * @return The property as epoch milliseconds, or 0 when the page has no jcr:content or the
   *     property is absent.
   */
  @Nonnull
  private static Long contentDate(@Nonnull final BaseContentPage page,
      @Nonnull final String propertyName) {
    final Resource content = page.getResource().getChild("jcr:content");
    if (content == null) {
      return 0L;
    }
    final Calendar calendar = content.getValueMap().get(propertyName, Calendar.class);
    return calendar == null ? 0L : calendar.getTimeInMillis();
  }

  /**
   * The page's node name, never null.
   *
   * @param page Page to read from.
   * @return The page's node name, or the empty string when it has none.
   */
  @Nonnull
  private static String name(@Nonnull final BaseContentPage page) {
    final String pageName = page.getName();
    return pageName == null ? "" : pageName;
  }

  /**
   * The page's display title, falling back to its node name.
   *
   * @param page Page to read from.
   * @return The display title, the node name when there is no title, or the empty string when
   *     there is neither.
   */
  @Nonnull
  private static String displayTitle(@Nonnull final BaseContentPage page) {
    final String title = page.getDisplayTitle();
    return title == null ? name(page) : title;
  }
}
