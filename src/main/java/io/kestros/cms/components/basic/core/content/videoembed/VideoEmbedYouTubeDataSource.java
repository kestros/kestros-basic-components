package io.kestros.cms.components.basic.core.content.videoembed;

import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoEmbedYouTubeDataSource extends BaseSlingModelDataSource
    implements KestrosVideoEmbed {

  @Nullable
  @Override
  public String getVideoEmbedCode() {
    String videoId = extractVideoId(getYoutubeVideo());
    if (StringUtils.isBlank(videoId)) {
      return null;
    }

    String src = buildEmbedUrl(videoId);

    return String.format(
        "<iframe src=\"%s\" " +
        "        style=\"width:100%%;height:100%%;border:0;\" " +
        "        %s " +
        "        allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; "
        + "picture-in-picture; fullscreen\" "
        +
        "        referrerpolicy=\"strict-origin-when-cross-origin\">" +
        "</iframe>",
        src,
        isAllowFullscreen() ? "allowfullscreen" : ""
                        );
  }



    /* ------------------
       Config getters
       ------------------ */


  private String getYoutubeVideo() {
    return getResource().getValueMap().get("youtubeVideo", String.class);
  }

  private boolean isMute() {
    return getResource().getValueMap().get("mute", false);
  }

  private boolean isAllowFullscreen() {
    return getResource().getValueMap().get("allowFullScreen", true);
  }


    /* ------------------
       Helpers
       ------------------ */

  private String buildEmbedUrl(String videoId) {
    String base = "https://www.youtube.com/embed/";

    List<String> params = new ArrayList<>();

    if (isMute()) {
      params.add("mute=1");
    }

    return base + videoId + (params.isEmpty() ? "" : "?" + String.join("&", params));
  }

  private String extractVideoId(String value) {
    if (StringUtils.isBlank(value)) {
      return null;
    }

    if (!value.contains("youtube") && !value.contains("youtu.be")) {
      return value;
    }

    if (value.contains("youtu.be/")) {
      return value.substring(value.lastIndexOf("/") + 1);
    }

    int index = value.indexOf("v=");
    if (index != -1) {
      return value.substring(index + 2).split("&")[0];
    }

    return null;
  }

}
