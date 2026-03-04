package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

/**
 * Datasource that queries session pages filtered by a day tag and generates a
 * schedule table with columns: time, session title, presenter name.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableScheduleDataSource extends BaseContainerSlingModelDataSource implements
    KestrosTable {

  @OSGiService
  private TagRetrievalService tagRetrievalService;

  @Nonnull
  @Override
  public List<KestrosTableHeader> getHeaderElements() {
    List<KestrosTableHeader> headers = new ArrayList<>();
    try {
      headers.add(new KestrosTableHeaderImpl("Time", this, "header", "time"));
      headers.add(new KestrosTableHeaderImpl("Session", this, "header", "session"));
      headers.add(new KestrosTableHeaderImpl("Presenter", this, "header", "presenter"));
    } catch (Exception e) {
      // Return empty headers on failure
    }
    return headers;
  }

  @Nonnull
  @Override
  public List<KestrosTableRow> getRowElements() {
    List<KestrosTableRow> rows = new ArrayList<>();

    String sessionsPath = getResource().getValueMap().get("sessionsPath", String.class);
    String dayTag = getResource().getValueMap().get("dayTag", String.class);

    if (sessionsPath == null) {
      return rows;
    }

    Resource sessionsRoot = getResourceResolver().getResource(sessionsPath);
    if (sessionsRoot == null) {
      return rows;
    }

    int rowIndex = 0;
    for (Resource sessionResource : sessionsRoot.getChildren()) {
      BaseContentPage sessionPage = sessionResource.adaptTo(BaseContentPage.class);
      if (sessionPage == null) {
        continue;
      }

      if (dayTag != null && tagRetrievalService != null) {
        List<KestrosTag> tags = tagRetrievalService.getTagsOnResource(sessionResource);
        boolean hasTag = false;
        for (KestrosTag tag : tags) {
          if (tag.getPath().equals(dayTag) || tag.getPath().endsWith("/" + dayTag)) {
            hasTag = true;
            break;
          }
        }
        if (!hasTag) {
          continue;
        }
      }

      Resource contentResource = sessionResource.getChild("jcr:content");
      String time = "";
      String presenterName = "";
      if (contentResource != null) {
        time = contentResource.getValueMap().get("time", "");
        presenterName = contentResource.getValueMap().get("presenterName", "");
        if (presenterName.isEmpty()) {
          String presenterPath = contentResource.getValueMap().get("presenterPath", String.class);
          if (presenterPath != null) {
            Resource presenterResource = getResourceResolver().getResource(presenterPath);
            if (presenterResource != null) {
              BaseContentPage presenterPage = presenterResource.adaptTo(BaseContentPage.class);
              if (presenterPage != null) {
                presenterName = presenterPage.getDisplayTitle();
              }
            }
          }
        }
      }

      try {
        List<KestrosTableCell> cells = new ArrayList<>();
        cells.add(new KestrosTableCellImpl(time, this, "cell", "time-" + rowIndex));
        cells.add(new KestrosTableCellImpl(sessionPage.getDisplayTitle(), this, "cell",
            "session-" + rowIndex));
        cells.add(new KestrosTableCellImpl(presenterName, this, "cell",
            "presenter-" + rowIndex));
        rows.add(new KestrosTableRowImpl(cells, this, "row", "row-" + rowIndex));
        rowIndex++;
      } catch (Exception e) {
        // Skip rows that fail to construct
      }
    }

    return rows;
  }
}
