package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * An upcoming fixture. {@link #getHome()} / {@link #getAway()} hold club slugs; the venue is
 * derived from the home club's stadium.
 */
public class AcplFixture {

  private String date;
  private String day;
  private String time;
  private String home;
  private String away;
  private int mw;

  public String getDate() {
    return date;
  }

  public String getDay() {
    return day;
  }

  public String getTime() {
    return time;
  }

  public String getHome() {
    return home;
  }

  public String getAway() {
    return away;
  }

  public int getMw() {
    return mw;
  }
}
