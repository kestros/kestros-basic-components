package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * A played match result. {@link #getHome()} / {@link #getAway()} hold club slugs.
 */
public class AcplResult {

  private String date;
  private String day;
  private String home;
  private int hg;
  private int ag;
  private String away;
  private int mw;

  public String getDate() {
    return date;
  }

  public String getDay() {
    return day;
  }

  public String getHome() {
    return home;
  }

  public int getHg() {
    return hg;
  }

  public int getAg() {
    return ag;
  }

  public String getAway() {
    return away;
  }

  public int getMw() {
    return mw;
  }
}
