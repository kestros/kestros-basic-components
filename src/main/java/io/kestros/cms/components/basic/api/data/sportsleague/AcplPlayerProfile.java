package io.kestros.cms.components.basic.api.data.sportsleague;

import java.util.ArrayList;
import java.util.List;

/**
 * The featured player profile. {@link #getClub()} holds a club slug.
 */
public class AcplPlayerProfile {

  private String name;
  private String club;
  private String position;
  private String positionShort;
  private int number;
  private int age;
  private String height;
  private String nationality;
  private int joined;
  private String portrait;
  private String bio;
  private Stats season;
  private Stats career;
  private List<MatchRecord> last5 = new ArrayList<>();

  public String getName() {
    return name;
  }

  public String getClub() {
    return club;
  }

  public String getPosition() {
    return position;
  }

  public String getPositionShort() {
    return positionShort;
  }

  public int getNumber() {
    return number;
  }

  public int getAge() {
    return age;
  }

  public String getHeight() {
    return height;
  }

  public String getNationality() {
    return nationality;
  }

  public int getJoined() {
    return joined;
  }

  public String getPortrait() {
    return portrait;
  }

  public String getBio() {
    return bio;
  }

  public Stats getSeason() {
    return season;
  }

  public Stats getCareer() {
    return career;
  }

  public List<MatchRecord> getLast5() {
    return new ArrayList<>(last5);
  }

  /** Aggregate stats (season or career; career omits minutes/cards). */
  public static class Stats {
    private int apps;
    private int goals;
    private int assists;
    private int minutes;
    private int yellows;
    private int reds;

    public int getApps() {
      return apps;
    }

    public int getGoals() {
      return goals;
    }

    public int getAssists() {
      return assists;
    }

    public int getMinutes() {
      return minutes;
    }

    public int getYellows() {
      return yellows;
    }

    public int getReds() {
      return reds;
    }
  }

  /** A last-5-matches row. {@code opp} holds a club slug. */
  public static class MatchRecord {
    private String opp;
    private String venue;
    private int g;
    private int a;
    private double rating;

    public String getOpp() {
      return opp;
    }

    public String getVenue() {
      return venue;
    }

    public int getG() {
      return g;
    }

    public int getA() {
      return a;
    }

    public double getRating() {
      return rating;
    }
  }
}
