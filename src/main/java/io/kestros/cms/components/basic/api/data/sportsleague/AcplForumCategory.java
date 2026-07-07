package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * A discussion-board category.
 */
public class AcplForumCategory {

  private String slug;
  private String name;
  private String desc;
  private int threadCount;
  private int postCount;

  public String getSlug() {
    return slug;
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public int getThreadCount() {
    return threadCount;
  }

  public int getPostCount() {
    return postCount;
  }
}
