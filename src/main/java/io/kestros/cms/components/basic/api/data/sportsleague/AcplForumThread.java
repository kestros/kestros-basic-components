package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * A discussion-board thread list row. {@code cat} holds a forum-category slug.
 */
public class AcplForumThread {

  private String id;
  private String title;
  private String cat;
  private String author;
  private int replies;
  private String lastBy;
  private String lastAgo;
  private boolean pinned;
  private boolean hot;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getCat() {
    return cat;
  }

  public String getAuthor() {
    return author;
  }

  public int getReplies() {
    return replies;
  }

  public String getLastBy() {
    return lastBy;
  }

  public String getLastAgo() {
    return lastAgo;
  }

  public boolean isPinned() {
    return pinned;
  }

  public boolean isHot() {
    return hot;
  }
}
