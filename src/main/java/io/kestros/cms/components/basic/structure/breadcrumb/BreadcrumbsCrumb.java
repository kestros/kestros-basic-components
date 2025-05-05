package io.kestros.cms.components.basic.structure.breadcrumb;

import io.kestros.commons.structuredslingmodels.BasePage;

public class BreadcrumbsCrumb {

  private String href;
  private String text;
  private String ariaLabel;
  private boolean currentPage;

  public BreadcrumbsCrumb(BasePage page, boolean currentPage) {
    this.href = page.getPath() + ".html";
    this.text = page.getTitle();
    // TODO verify the aria label.
    this.ariaLabel = page.getTitle();
    this.currentPage = currentPage;
  }

  public String getHref() {
    return href;
  }

  public String getText() {
    return text;
  }

  public String getAriaLabel() {
    return ariaLabel;
  }

  public boolean isCurrentPage() {
    return currentPage;
  }

}
