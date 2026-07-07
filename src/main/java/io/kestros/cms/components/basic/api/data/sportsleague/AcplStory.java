package io.kestros.cms.components.basic.api.data.sportsleague;

import java.util.ArrayList;
import java.util.List;

/**
 * A news story. {@code body} is a list of paragraphs.
 */
public class AcplStory {

  private String slug;
  private String headline;
  private String dek;
  private String author;
  private String date;
  private String image;
  private String category;
  private boolean featured;
  private List<String> body = new ArrayList<>();

  public String getSlug() {
    return slug;
  }

  public String getHeadline() {
    return headline;
  }

  public String getDek() {
    return dek;
  }

  public String getAuthor() {
    return author;
  }

  public String getDate() {
    return date;
  }

  public String getImage() {
    return image;
  }

  public String getCategory() {
    return category;
  }

  public boolean isFeatured() {
    return featured;
  }

  public List<String> getBody() {
    return new ArrayList<>(body);
  }
}
