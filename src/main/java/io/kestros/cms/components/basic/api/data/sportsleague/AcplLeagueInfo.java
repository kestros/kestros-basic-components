package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * League-level values (current matchweek, hero/promo copy) so pages don't hardcode them.
 */
public class AcplLeagueInfo {

  private String name;
  private String shortName;
  private String season;
  private int currentMatchweek;
  private int totalMatchweeks;
  private String heroTitle;
  private String heroText;
  private String promoText;

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public String getSeason() {
    return season;
  }

  public int getCurrentMatchweek() {
    return currentMatchweek;
  }

  public int getTotalMatchweeks() {
    return totalMatchweeks;
  }

  public String getHeroTitle() {
    return heroTitle;
  }

  public String getHeroText() {
    return heroText;
  }

  public String getPromoText() {
    return promoText;
  }
}
