package io.kestros.cms.components.basic.core.content.videoembed;

import javax.annotation.Nonnull;
import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoEmbedYouTubeDataSource extends BaseSlingModelDataSource
        implements KestrosVideoEmbed {

  private static final Pattern YOUTUBE_VIDEO_ID =
          Pattern.compile("^[a-zA-Z0-9_-]{11}$");

  private static final Pattern YOUTUBE_URL_ID =
          Pattern.compile(
                  "(?:youtu\\.be/|youtube\\.com/(?:watch\\?v=|embed/))([a-zA-Z0-9_-]{11})"
          );

  // very conservative: any angle brackets = HTML
  private static final Pattern HTML_PATTERN =
          Pattern.compile("[<>]");

  boolean isValidVideoInput(String input) {
    if (input == null) {
      return Boolean.FALSE;
    }

    String value = input.trim();

    // 🚫 reject anything that looks like HTML
    if (HTML_PATTERN.matcher(value).find()) {
      return Boolean.FALSE;
    }

    // ✅ raw video ID
    if (YOUTUBE_VIDEO_ID.matcher(value).matches()) {
      return Boolean.TRUE;
    }

    // ✅ known YouTube URL formats
    Matcher matcher = YOUTUBE_URL_ID.matcher(value);
    if (matcher.find()) {
      return Boolean.TRUE;
    }

    return Boolean.FALSE;
  }


  @Nullable
  @Override
  public String getVideoEmbedCode() {
    if (!isValidVideoInput(getYoutubeVideo())) {
      return null;
    }

    String videoId = extractVideoId(getYoutubeVideo());
    if (StringUtils.isBlank(videoId)) {
      return null;
    }

    String src = buildEmbedUrl(videoId);
    if (StringUtils.isBlank(src)) {
      return null;
    }

    return String.format(
            "<iframe src=\"%s\" " +
                    "        style=\"width:100%%;height:100%%;border:0;\" " +
                    "        %s " +
                    "        allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; "
                    + "gyroscope; "
                    + "picture-in-picture; fullscreen\" "
                    +
                    "        referrerpolicy=\"strict-origin-when-cross-origin\">" +
                    "</iframe>",
            src,
            isAllowFullscreen() ? "allowfullscreen" : ""
    );
  }

  @Nonnull
  private String getYoutubeVideo() {
    return getResource().getValueMap().get("youtubeVideo", String.class);
  }

  private boolean isMute() {
    return getResource().getValueMap().get("mute", Boolean.FALSE);
  }


    /* ------------------
       Helpers
       ------------------ */

  private boolean isAllowFullscreen() {
    return getResource().getValueMap().get("allowFullScreen", Boolean.TRUE);
  }

  @Nonnull
  private String buildEmbedUrl(String videoId) {


    String base = "https://www.youtube.com/embed/";
    List<String> params = new ArrayList<>();

    if (isMute()) {
      params.add("mute=1");
    }

    return base + videoId + (params.isEmpty() ? "" : "?" + String.join("&", params));
  }


  @Nullable
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
