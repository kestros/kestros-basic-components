package io.kestros.cms.components.basic.api.data.sportsleague;

import java.util.ArrayList;
import java.util.List;

/**
 * A full thread with its replies.
 */
public class AcplThreadDetail {

  private String id;
  private String title;
  private String author;
  private String authorAvatar;
  private String posted;
  private String body;
  private List<Reply> replies = new ArrayList<>();

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getAuthorAvatar() {
    return authorAvatar;
  }

  public String getPosted() {
    return posted;
  }

  public String getBody() {
    return body;
  }

  public List<Reply> getReplies() {
    return new ArrayList<>(replies);
  }

  /** A reply post. */
  public static class Reply {
    private String author;
    private String avatar;
    private String ago;
    private String body;
    private int up;
    private int down;

    public String getAuthor() {
      return author;
    }

    public String getAvatar() {
      return avatar;
    }

    public String getAgo() {
      return ago;
    }

    public String getBody() {
      return body;
    }

    public int getUp() {
      return up;
    }

    public int getDown() {
      return down;
    }
  }
}
