/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.LogUtils;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidThemeException;
import io.kestros.cms.uiframeworks.api.exceptions.ThemeRetrievalException;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;

/**
 * Shared behaviour for the card list data sources that build one card per page.
 *
 * <p>Between them these two methods are what lets a card list drop a single broken page instead of
 * rendering empty. The skip in the loop is only safe because the prerequisites have already been
 * checked: everything the card constructor reads from the data source is known good by then, so
 * anything still thrown inside the loop depends on the page being skipped.
 */
final class CardListSupport {

  private CardListSupport() {
    // static helpers only.
  }

  /**
   * Makes, once and uncaught, the calls that the card constructor makes back against the data
   * source. These inputs are identical for every card in the list, so a failure here describes the
   * whole component rather than any one page and must reach the caller.
   *
   * <p>This restates by hand what {@code BaseSyntheticResource}'s constructor asks the data source
   * for. It is a classifier, not an optimisation - the loop still builds each card the same way.
   * A card list test that asserts a whole-component failure still propagates is the guard against
   * this drifting out of step with that constructor.
   *
   * <p>Only the UI framework is checked for null afterwards. The other four either return a value
   * or throw: the resolver and the path come off the data source's own resource, the variations
   * are always a list, and the layout falls back to "default". {@code BaseSyntheticResource}
   * null-checks all five, and if one of those ever does start returning null the card list would
   * drop every page for the same reason - which is what the whole-component test is there to
   * catch.
   *
   * <p>The failure is returned rather than thrown. {@code getCardElements()} cannot declare the
   * checked exceptions {@code getUiFramework()} raises, because {@code KestrosCardList} does not
   * declare them, so they have to become unchecked somewhere. Building the unchecked exception here
   * and throwing it in the caller keeps the caller's throw out of a catch block, which is the shape
   * SpotBugs reads as softening. The trade is that the stack trace starts at this method rather
   * than at the throw; the cause is attached, so nothing is lost.
   *
   * @param dataSource Data source the cards will be built from.
   * @return The failure that stops every card being built, or null when the prerequisites hold.
   */
  @Nullable
  static IllegalStateException componentPrerequisiteFailure(
          @Nonnull final BaseSlingModelDataSource dataSource) {
    final ResourceResolver resourceResolver = dataSource.getResourceResolver();
    final String parentPath = dataSource.getResource().getPath();
    final List<ComponentVariation> variations = dataSource.getElementVariations("cardVariations",
            KestrosCard.RESOURCE_TYPE);
    final String layout = dataSource.getLayout("card");
    final UiFramework uiFramework;
    try {
      uiFramework = dataSource.getUiFramework();
    } catch (final ResourceNotFoundException | InvalidThemeException | ThemeRetrievalException
            | UiFrameworkRetrievalException e) {
      return new IllegalStateException(String.format(
              "Card list at %s cannot build any card: its page's theme cannot be read.",
              LogUtils.forLog(parentPath)), e);
    }
    return uiFrameworkMissingFailure(uiFramework, parentPath, resourceResolver != null,
            variations.size(), layout);
  }

  /**
   * Failure for a page that resolved to no UI framework, or null when it resolved to one.
   *
   * <p>The value arrives as a parameter rather than being checked where it is read, for the same
   * reason {@code BaseSyntheticResource} routes its five inputs through {@code requireConfigured}:
   * {@code getUiFramework()} is annotated {@code @Nonnull}, so a null check written against it
   * reads as dead code, and the card list tests build a data source whose theme carries no UI
   * framework and require this to fail. The annotation is a claim about the contract; this is the
   * guard for a caller who breaks it.
   *
   * @param uiFramework UI framework the page resolved to, if any.
   * @param parentPath Path of the card list component.
   * @param hasResourceResolver Whether the data source produced a resource resolver.
   * @param variationCount Number of card variations the data source produced.
   * @param layout Card layout the data source produced.
   * @return The failure to raise, or null when there is a UI framework.
   */
  @Nullable
  private static IllegalStateException uiFrameworkMissingFailure(
          @Nullable final UiFramework uiFramework, @Nullable final String parentPath,
          final boolean hasResourceResolver, final int variationCount,
          @Nullable final String layout) {
    if (uiFramework != null) {
      return null;
    }
    return new IllegalStateException(String.format(
            "Card list at %s cannot build any card: its page resolves to no UI framework. "
                    + "hasResourceResolver=%s, cardVariations=%s, cardLayout=%s.",
            LogUtils.forLog(parentPath), hasResourceResolver, variationCount,
            LogUtils.forLog(layout)));
  }

  /**
   * Records a page whose card could not be built and was therefore left out of the list.
   *
   * <p>The exception class is named before its message because the message is often null, and
   * "Skipping the card for /x: null" says nothing about what went wrong.
   *
   * <p>Nothing read here may throw. The page is the object that has just failed, so asking it for
   * its path can fail as well, and an exception raised while logging the skip would blank the whole
   * list again - which is the defect this method exists to prevent.
   *
   * @param log Logger of the data source doing the skipping.
   * @param page Page whose card could not be built.
   * @param dataSourcePath Path of the card list component.
   * @param cause Failure that stopped the card being built.
   */
  static void logSkippedCard(@Nonnull final Logger log, @Nullable final BaseContentPage page,
          @Nullable final String dataSourcePath, @Nonnull final Exception cause) {
    log.warn("Skipping the card for {} in the card list at {}. {}: {} Every other page in the list "
                    + "still renders.", describePath(page), LogUtils.forLog(dataSourcePath),
            cause.getClass().getName(), describeMessage(cause), cause);
  }

  @Nullable
  private static String describePath(@Nullable final BaseContentPage page) {
    if (page == null) {
      return "an unknown page";
    }
    try {
      return LogUtils.forLog(page.getPath());
    } catch (final RuntimeException e) {
      return "a page whose path could not be read";
    }
  }

  @Nullable
  private static String describeMessage(@Nonnull final Exception cause) {
    try {
      return LogUtils.forLog(cause.getMessage());
    } catch (final RuntimeException e) {
      return "a message that could not be read";
    }
  }
}
