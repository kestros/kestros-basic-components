package io.kestros.cms.components.basic.api.data.sportsleague;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Retrieves sports-league (ACPL demo) records from JSON files stored in the JCR
 * (nt:file nodes, conventionally under /content/data/sports-league). Each method reads the
 * file at the given path and binds it with Jackson.
 */
public interface SportsLeagueDataService {

  /** Conventional root for the sample data files. */
  String DEFAULT_DATA_ROOT = "/content/data/sports-league";

  @Nonnull
  List<AcplClub> getClubs(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;

  /**
   * Club lookup by slug (club-typed fields in other records hold a club slug).
   *
   * @return the club, or null when the slug has no match.
   */
  @Nullable
  AcplClub getClub(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath,
      @Nonnull String slug) throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplStanding> getStandings(@Nonnull ResourceResolver resolver,
      @Nonnull String jsonDataPath) throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplResult> getResults(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplFixture> getFixtures(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplSquadMember> getSquad(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;

  @Nonnull
  AcplPlayerProfile getPlayerProfile(@Nonnull ResourceResolver resolver,
      @Nonnull String jsonDataPath) throws SportsLeagueDataRetrievalException;

  @Nonnull
  AcplMatchSummary getMatchSummary(@Nonnull ResourceResolver resolver,
      @Nonnull String jsonDataPath) throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplStory> getStories(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplForumCategory> getForumCategories(@Nonnull ResourceResolver resolver,
      @Nonnull String jsonDataPath) throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplForumThread> getThreads(@Nonnull ResourceResolver resolver,
      @Nonnull String jsonDataPath) throws SportsLeagueDataRetrievalException;

  @Nonnull
  AcplThreadDetail getThreadDetail(@Nonnull ResourceResolver resolver,
      @Nonnull String jsonDataPath) throws SportsLeagueDataRetrievalException;

  @Nonnull
  List<AcplUser> getUsers(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;

  @Nonnull
  AcplLeagueInfo getLeagueInfo(@Nonnull ResourceResolver resolver, @Nonnull String jsonDataPath)
      throws SportsLeagueDataRetrievalException;
}
