package io.kestros.cms.components.basic.api.data.sportsleague;

/**
 * Thrown when sports-league JSON data cannot be resolved or parsed.
 */
public class SportsLeagueDataRetrievalException extends Exception {

  private static final long serialVersionUID = 1L;

  public SportsLeagueDataRetrievalException(String message) {
    super(message);
  }

  public SportsLeagueDataRetrievalException(String message, Throwable cause) {
    super(message, cause);
  }
}
