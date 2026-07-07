package io.kestros.cms.components.basic.api.data.sportsleague;

import java.util.ArrayList;
import java.util.List;

/**
 * The featured match with goals, cards, stats, and lineups.
 * {@link #getHome()} / {@link #getAway()} hold club slugs.
 */
public class AcplMatchSummary {

  private String home;
  private String away;
  private int hg;
  private int ag;
  private int mw;
  private String date;
  private String dayLong;
  private String kickoff;
  private String venue;
  private int attendance;
  private String referee;
  private String weather;
  private List<Goal> goals = new ArrayList<>();
  private List<Card> cards = new ArrayList<>();
  private List<Stat> stats = new ArrayList<>();
  private Lineups lineups;

  public String getHome() {
    return home;
  }

  public String getAway() {
    return away;
  }

  public int getHg() {
    return hg;
  }

  public int getAg() {
    return ag;
  }

  public int getMw() {
    return mw;
  }

  public String getDate() {
    return date;
  }

  public String getDayLong() {
    return dayLong;
  }

  public String getKickoff() {
    return kickoff;
  }

  public String getVenue() {
    return venue;
  }

  public int getAttendance() {
    return attendance;
  }

  public String getReferee() {
    return referee;
  }

  public String getWeather() {
    return weather;
  }

  public List<Goal> getGoals() {
    return new ArrayList<>(goals);
  }

  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }

  public List<Stat> getStats() {
    return new ArrayList<>(stats);
  }

  public Lineups getLineups() {
    return lineups;
  }

  /** A goal event. {@code team} holds a club slug. */
  public static class Goal {
    private int minute;
    private String scorer;
    private String team;
    private String note;

    public int getMinute() {
      return minute;
    }

    public String getScorer() {
      return scorer;
    }

    public String getTeam() {
      return team;
    }

    public String getNote() {
      return note;
    }
  }

  /** A booking event. {@code team} holds a club slug. */
  public static class Card {
    private int minute;
    private String player;
    private String team;
    private String card;

    public int getMinute() {
      return minute;
    }

    public String getPlayer() {
      return player;
    }

    public String getTeam() {
      return team;
    }

    public String getCard() {
      return card;
    }
  }

  /** A comparative stat bar (h/a display values, hp/ap percentages). */
  public static class Stat {
    private String label;
    private String h;
    private String a;
    private int hp;
    private int ap;

    public String getLabel() {
      return label;
    }

    public String getH() {
      return h;
    }

    public String getA() {
      return a;
    }

    public int getHp() {
      return hp;
    }

    public int getAp() {
      return ap;
    }
  }

  /** One side's lineup. */
  public static class Lineup {
    private String formation;
    private String gk;
    private List<String> def = new ArrayList<>();
    private List<String> mid = new ArrayList<>();
    private List<String> att = new ArrayList<>();
    private String st;

    public String getFormation() {
      return formation;
    }

    public String getGk() {
      return gk;
    }

    public List<String> getDef() {
      return new ArrayList<>(def);
    }

    public List<String> getMid() {
      return new ArrayList<>(mid);
    }

    public List<String> getAtt() {
      return new ArrayList<>(att);
    }

    public String getSt() {
      return st;
    }
  }

  /** Both lineups. */
  public static class Lineups {
    private Lineup home;
    private Lineup away;

    public Lineup getHome() {
      return home;
    }

    public Lineup getAway() {
      return away;
    }
  }
}
