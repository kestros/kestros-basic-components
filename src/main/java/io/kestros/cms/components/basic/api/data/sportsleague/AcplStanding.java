package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * A league-table row. {@link #getClub()} holds a club slug. Rows arrive pre-sorted by position.
 */
public class AcplStanding {

  private int pos;
  private String club;
  private int p;
  private int w;
  private int d;
  private int l;
  private int gf;
  private int ga;
  private String gd;
  private int pts;
  private String form;

  public int getPos() {
    return pos;
  }

  public String getClub() {
    return club;
  }

  public int getP() {
    return p;
  }

  public int getW() {
    return w;
  }

  public int getD() {
    return d;
  }

  public int getL() {
    return l;
  }

  public int getGf() {
    return gf;
  }

  public int getGa() {
    return ga;
  }

  public String getGd() {
    return gd;
  }

  public int getPts() {
    return pts;
  }

  /** Recent form, most recent last (e.g. "WWDLW"). */
  public String getForm() {
    return form;
  }
}
