package io.kestros.cms.components.basic.structure.navigation;

public class NavigationPage {

  private String title;
  private String description;
  private String url;
  private boolean isActive;

  public NavigationPage(String title, String description, String url, boolean isActive) {
    this.title = title;
    this.description = description;
    this.url = url;
    this.isActive = isActive;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getUrl() {
    return url;
  }

  public boolean isActive() {
    return isActive;
  }
}
