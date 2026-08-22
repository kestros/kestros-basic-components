package io.kestros.cms.components.basic.core.content.link;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosLinkImpl extends BaseSyntheticResource implements KestrosLink {

  private String text;
  private String href;
  private String title;
  private AnchorTarget target;
  private String rel;
  private String ariaLabel;
  private String ariaDescribedBy;
  private String lang;


  public KestrosLinkImpl(@Nonnull BaseContentPage page,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = page.getDisplayTitle();
    this.href = LinkUtils.getLink(page.getPath());
    // The six assignments that were here read the fields back into themselves, so they were
    // no-ops that left every one of them null. What a page-derived link should carry for title,
    // rel and the aria attributes is a product decision, not a cleanup, so those stay null.
    // target is not in that class: getTargetAsString has always answered SAME_WINDOW for a null
    // target, so the default is stated in the code rather than guessed at, and setting it is what
    // lets getTarget honour the @Nonnull the interface declares.
    this.target = AnchorTarget.SAME_WINDOW;
  }


  public KestrosLinkImpl(String text, String href, String title, AnchorTarget target, String rel,
      String ariaLabel, String ariaDescribedBy, String lang,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = text;
    this.href = href;
    this.title = title;
    // A link with no target opens in the same window. getTargetAsString already said so; keeping
    // the field null instead made getTarget break the @Nonnull the interface declares.
    this.target = target == null ? AnchorTarget.SAME_WINDOW : target;
    this.rel = rel;
    this.ariaLabel = ariaLabel;
    this.ariaDescribedBy = ariaDescribedBy;
    this.lang = lang;
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }

  @Nullable
  @Override
  public String getHref() {
    return href;
  }

  @Nullable
  @Override
  public String getTitle() {
    return title;
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return target;
  }

  @Nullable
  @Override
  public String getRel() {
    return rel;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return ariaLabel;
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return ariaDescribedBy;
  }

  @Nullable
  @Override
  public String getLang() {
    return lang;
  }

}
