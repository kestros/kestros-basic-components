package io.kestros.cms.components.basic.api.data.sportsleague;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * An ACPL club. Other records reference clubs by {@link #getSlug() slug}.
 */
public class AcplClub {

  private String slug;
  private String name;
  @JsonProperty("short")
  private String shortName;
  private String city;
  private int founded;
  private String stadium;
  private int stadiumCapacity;
  @JsonProperty("mgr")
  private String manager;
  @JsonProperty("primary")
  private String primaryColor;
  @JsonProperty("secondary")
  private String secondaryColor;
  private String crest;
  private List<String> honours = new ArrayList<>();

  public String getSlug() {
    return slug;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public String getCity() {
    return city;
  }

  public int getFounded() {
    return founded;
  }

  public String getStadium() {
    return stadium;
  }

  public int getStadiumCapacity() {
    return stadiumCapacity;
  }

  public String getManager() {
    return manager;
  }

  public String getPrimaryColor() {
    return primaryColor;
  }

  public String getSecondaryColor() {
    return secondaryColor;
  }

  public String getCrest() {
    return crest;
  }

  public List<String> getHonours() {
    return new ArrayList<>(honours);
  }
}
