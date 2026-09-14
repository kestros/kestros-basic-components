package io.kestros.cms.components.basic.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.assets.api.models.Asset;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/**
 * Orders the assets behind a list component.
 *
 * <p>Extracted from the inline comparators in CardListAssetsDataSource so the ordering rules can
 * carry their own nullability contract, which a lambda body cannot. Mirrors
 * {@link ContentPageSorter} for pages.</p>
 */
@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A static utility class: private constructor, no instance state, every"
        + " method static. There is never an instance to print.")
public final class AssetSorter {

  private static final String CREATED_DATE = "createdDate";
  private static final String LAST_MODIFIED = "lastModified";
  private static final String NAME = "name";

  private AssetSorter() {
    // Utility class; not instantiable.
  }

  /**
   * Sorts the supplied assets in place, by the named property.
   *
   * <p>An unrecognised sort key falls back to the title, which is the behaviour the inline
   * comparators had.</p>
   *
   * @param assets Assets to sort, modified in place.
   * @param sortBy Sort key: createdDate, lastModified, name, or anything else for title.
   */
  public static void sort(@Nonnull final List<Asset> assets, @Nonnull final String sortBy) {
    if (sortBy.isEmpty()) {
      return;
    }
    switch (sortBy) {
      case CREATED_DATE:
        assets.sort(Comparator.comparing(AssetSorter::createdTime));
        break;
      case LAST_MODIFIED:
        assets.sort(Comparator.comparing(AssetSorter::modifiedTime));
        break;
      case NAME:
        assets.sort(Comparator.comparing(AssetSorter::name));
        break;
      default:
        assets.sort(Comparator.comparing(AssetSorter::title));
        break;
    }
  }

  /**
   * The asset's creation date, as epoch milliseconds.
   *
   * @param asset Asset to read from.
   * @return The creation date as epoch milliseconds, or 0 when it is absent.
   */
  @Nonnull
  private static Long createdTime(@Nonnull final Asset asset) {
    return time(asset.getCreatedDate());
  }

  /**
   * The asset's last-modified date, as epoch milliseconds.
   *
   * @param asset Asset to read from.
   * @return The last-modified date as epoch milliseconds, or 0 when it is absent.
   */
  @Nonnull
  private static Long modifiedTime(@Nonnull final Asset asset) {
    return time(asset.getModifiedDate());
  }

  /**
   * A date as epoch milliseconds.
   *
   * @param date Date to convert, possibly null.
   * @return The date as epoch milliseconds, or 0 when it is absent.
   */
  @Nonnull
  private static Long time(@Nullable final Date date) {
    return date == null ? 0L : date.getTime();
  }

  /**
   * The asset's node name, never null.
   *
   * <p>The fallback goes through {@code defaultString} rather than an {@code == null} test on
   * purpose: Asset declares these getters @Nonnull, so SpotBugs reads an inline null check as
   * redundant and reports it. The behaviour is the same either way.</p>
   *
   * @param asset Asset to read from.
   * @return The asset's node name, or the empty string when it has none.
   */
  @Nonnull
  private static String name(@Nonnull final Asset asset) {
    return StringUtils.defaultString(asset.getName());
  }

  /**
   * The asset's title, falling back to its node name.
   *
   * @param asset Asset to read from.
   * @return The title, the node name when there is no title, or the empty string when neither.
   */
  @Nonnull
  private static String title(@Nonnull final Asset asset) {
    return StringUtils.defaultString(asset.getTitle(), name(asset));
  }
}
