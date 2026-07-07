package io.kestros.cms.components.basic.api.data.sportsleague;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A demo site user. {@link #getFavouriteClub()} holds a club slug.
 */
public class AcplUser {

  private String username;
  private String avatarUrl;
  private String favouriteClub;
  private String email;
  private Prefs prefs;

  public String getUsername() {
    return username;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getFavouriteClub() {
    return favouriteClub;
  }

  public String getEmail() {
    return email;
  }

  public Prefs getPrefs() {
    return prefs;
  }

  /** Notification/visibility preferences. */
  public static class Prefs {
    private boolean email;
    private boolean push;
    @JsonProperty("public")
    private boolean publicProfile;

    public boolean isEmail() {
      return email;
    }

    public boolean isPush() {
      return push;
    }

    public boolean isPublicProfile() {
      return publicProfile;
    }
  }
}
