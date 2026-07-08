package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.sitebuilding.api.models.DynamicPageContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data-driven {@link KestrosTable} datasource: renders a table from a collection of structured data
 * nodes, mirroring {@link io.kestros.cms.components.basic.core.lists.cardlist.CardListChildPagesDataSource}
 * (which renders cards from child pages).
 *
 * <p>Configured on the component with:
 * <ul>
 *   <li>{@code dataPath} — path to the container whose child nodes are the rows.</li>
 *   <li>{@code columns} — ordered property names read from each data node into the row's cells.</li>
 *   <li>{@code headers} — ordered header labels (optional).</li>
 * </ul>
 * Rows/cells/headers are built programmatically as synthetic elements
 * ({@link KestrosTableRowImpl}/{@link KestrosTableCellImpl}/{@link KestrosTableHeaderImpl}) and fed to
 * the stock table component.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableChildNodesDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTable {

  private static final Logger LOG = LoggerFactory.getLogger(TableChildNodesDataSource.class);

  private static final Pattern PLACEHOLDER = Pattern.compile("\\{([^}]+)}");

  /**
   * Substitutes {@code {name}} placeholders in the data path with dynamic-route parameters, so a
   * single table component on a dynamic page (e.g. {@code teams/{team}}) can resolve
   * per-request data (e.g. {@code dataPath = /content/data/sports-league/teams/{team}/results}).
   * Placeholders with no matching route parameter resolve to empty (the path then misses and the
   * table renders no rows).
   *
   * @param dataPath configured data path, possibly containing placeholders.
   * @return the resolved data path.
   */
  @Nonnull
  private String resolveDataPath(@Nonnull final String dataPath) {
    if (!dataPath.contains("{")) {
      return dataPath;
    }
    final DynamicPageContext context =
        getRequest() != null ? getRequest().adaptTo(DynamicPageContext.class) : null;
    final Matcher matcher = PLACEHOLDER.matcher(dataPath);
    final StringBuffer resolved = new StringBuffer();
    while (matcher.find()) {
      final String value = context != null ? context.getParameter(matcher.group(1)) : null;
      matcher.appendReplacement(resolved, Matcher.quoteReplacement(value != null ? value : ""));
    }
    matcher.appendTail(resolved);
    return resolved.toString();
  }

  @Nonnull
  @Override
  public List<KestrosTableHeader> getHeaderElements() {
    final List<KestrosTableHeader> headers = new ArrayList<>();
    final String[] labels = getResource().getValueMap().get("headers", String[].class);
    if (labels != null) {
      for (int i = 0; i < labels.length; i++) {
        try {
          headers.add(new KestrosTableHeaderImpl(labels[i], this, "header" + i, "header" + i));
        } catch (final ComponentConfigurationException e) {
          LOG.warn("Unable to build table header '{}': {}", labels[i], e.getMessage());
        }
      }
    }
    return headers;
  }

  @Nonnull
  @Override
  public List<KestrosTableRow> getRowElements() {
    final List<KestrosTableRow> rows = new ArrayList<>();
    final String configuredDataPath = getResource().getValueMap().get("dataPath", String.class);
    final String[] columns = getResource().getValueMap().get("columns", String[].class);
    if (configuredDataPath == null || columns == null) {
      return rows;
    }
    final String dataPath = resolveDataPath(configuredDataPath);
    final Resource dataResource = getResourceResolver().getResource(dataPath);
    if (dataResource == null) {
      LOG.warn("Table dataPath {} not found.", dataPath);
      return rows;
    }
    int r = 0;
    for (final Resource node : dataResource.getChildren()) {
      final List<KestrosTableCell> cells = new ArrayList<>();
      for (int c = 0; c < columns.length; c++) {
        final Object value = node.getValueMap().get(columns[c]);
        final String text = value != null ? String.valueOf(value) : "";
        try {
          cells.add(new KestrosTableCellImpl(text, this, "r" + r + "c" + c, "r" + r + "c" + c));
        } catch (final ComponentConfigurationException e) {
          LOG.warn("Unable to build table cell: {}", e.getMessage());
        }
      }
      try {
        rows.add(new KestrosTableRowImpl(cells, this, "row" + r, "row" + r));
      } catch (final ComponentConfigurationException e) {
        LOG.warn("Unable to build table row {}: {}", r, e.getMessage());
      }
      r++;
    }
    return rows;
  }
}
