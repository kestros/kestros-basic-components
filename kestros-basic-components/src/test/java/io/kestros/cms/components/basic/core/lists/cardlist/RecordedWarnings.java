/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.lists.cardlist;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Captures the WARN lines a class writes while a test runs.
 *
 * <p>The card lists are required to say which page they dropped and why, so that line is part of
 * the behaviour under test rather than a side effect of it - a skip nobody can attribute is how
 * the tag search list came to render empty in silence.
 */
final class RecordedWarnings implements AutoCloseable {

  private final ch.qos.logback.classic.Logger logger;
  private final ListAppender<ILoggingEvent> appender = new ListAppender<>();

  RecordedWarnings(final Class<?> recordedClass) {
    logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(recordedClass);
    appender.start();
    logger.addAppender(appender);
  }

  /**
   * The WARN lines written since this recorder was opened, already rendered.
   *
   * @return Formatted WARN messages, in the order they were written.
   */
  List<String> messages() {
    final List<String> warnings = new ArrayList<>();
    for (final ILoggingEvent event : appender.list) {
      if (Level.WARN.equals(event.getLevel())) {
        warnings.add(event.getFormattedMessage());
      }
    }
    return warnings;
  }

  @Override
  public void close() {
    logger.detachAppender(appender);
  }
}
