package io.kestros.cms.components.basic.core.content.videoembed;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class VideoEmbedYouTubeDataSourceTest extends BaseDataSourceTest {

  private VideoEmbedYouTubeDataSource videoEmbedYouTubeDataSource
          = new VideoEmbedYouTubeDataSource();

  private int counter;

  @Override
  public void doComponentSetup() {
    context.create().resource("/content/page/jcr:content", new HashMap<>());
  }

  @Override
  public void testToSyntheticResource() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "abcdefghijk");

    assertNotNull(adaptWith(properties)
        .toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content"));
  }

  @Test
  public void testisValidVideoInput() {
    assertTrue(videoEmbedYouTubeDataSource.isValidVideoInput(
            "https://www.youtube.com/watch?v=zzzzzzzzzzzz"));
  }

  @Test
  public void testisValidVideoInputWhenHtml() {
    assertFalse(videoEmbedYouTubeDataSource.isValidVideoInput(
            "<p>https://www.youtube.com/watch?v=zzzzzzzzzzzz</p>"));
    assertFalse(videoEmbedYouTubeDataSource.isValidVideoInput(
            "https://www.youtube.com/watch?v=zzzzzzzzzz<p>test</p>zz"));
  }

  @Test
  public void testisValidVideoInputWithFullYoutubeUrl() {
    assertTrue(videoEmbedYouTubeDataSource.isValidVideoInput(
            "https://www.youtube.com/watch?v=zzzzzzzzzzz"));
  }

  @Test
  public void testisValidVideoInputWithShortYoutubeUrl() {
    assertTrue(videoEmbedYouTubeDataSource.isValidVideoInput(
            "https://youtu.be/zzzzzzzzzzz"));
  }

  @Test
  public void testisValidVideoInputWithEmbedUrl() {
    assertTrue(videoEmbedYouTubeDataSource.isValidVideoInput(
            "https://www.youtube.com/embed/zzzzzzzzzzz"));
  }

  @Test
  public void testisValidVideoInputWithRawVideoId() {
    assertTrue(videoEmbedYouTubeDataSource.isValidVideoInput(
            "zzzzzzzzzzz"));
  }

  @Test
  public void testisValidVideoInputWhenImgXss() {
    assertFalse(videoEmbedYouTubeDataSource.isValidVideoInput(
            "<img src=x onerror=alert(1)>"));
  }

  @Test
  public void testisValidVideoInputWhenSvgXss() {
    assertFalse(videoEmbedYouTubeDataSource.isValidVideoInput(
            "<svg/onload=alert(1)>"));
  }

  @Test
  public void testisValidVideoInputWhenHtmlEntityEncoded() {
    assertFalse(videoEmbedYouTubeDataSource.isValidVideoInput(
            "&lt;script&gt;alert(1)&lt;/script&gt;"));
  }

  @Test
  public void testisValidVideoInputWhenIframeInjected() {
    assertFalse(videoEmbedYouTubeDataSource.isValidVideoInput(
            "<iframe src=\"https://evil.com\"></iframe>"));
  }

  // ---------------------------------------------------------------------------------------------
  // getVideoEmbedCode and the id extraction behind it had no coverage: the existing cases only
  // reach isValidVideoInput.
  // ---------------------------------------------------------------------------------------------

  private VideoEmbedYouTubeDataSource adaptWith(final Map<String, Object> properties) {
    final Resource resource =
        context.create().resource("/content/page/jcr:content/embed-" + (++counter), properties);
    context.request().setResource(resource);
    return context.request().adaptTo(VideoEmbedYouTubeDataSource.class);
  }

  @Test
  public void testGetVideoEmbedCodeFromAWatchUrl() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "https://www.youtube.com/watch?v=abcdefghijk");

    final String embed = adaptWith(properties).getVideoEmbedCode();

    assertNotNull(embed);
    assertTrue(embed.contains("https://www.youtube.com/embed/abcdefghijk"));
  }

  /** A watch URL with trailing parameters keeps only the id. */
  @Test
  public void testGetVideoEmbedCodeStripsTrailingParameters() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "https://www.youtube.com/watch?v=abcdefghijk&t=30s");

    assertTrue(adaptWith(properties).getVideoEmbedCode()
        .contains("https://www.youtube.com/embed/abcdefghijk"));
  }

  @Test
  public void testGetVideoEmbedCodeFromAShortUrl() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "https://youtu.be/abcdefghijk");

    assertTrue(adaptWith(properties).getVideoEmbedCode()
        .contains("https://www.youtube.com/embed/abcdefghijk"));
  }

  /** A bare id is passed through as the id. */
  @Test
  public void testGetVideoEmbedCodeFromARawVideoId() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "abcdefghijk");

    assertTrue(adaptWith(properties).getVideoEmbedCode()
        .contains("https://www.youtube.com/embed/abcdefghijk"));
  }

  @Test
  public void testGetVideoEmbedCodeAppendsTheMuteParameter() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "abcdefghijk");
    properties.put("mute", true);

    assertTrue(adaptWith(properties).getVideoEmbedCode().contains("?mute=1"));
  }

  @Test
  public void testGetVideoEmbedCodeAppendsAllowFullscreen() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "abcdefghijk");
    properties.put("allowFullscreen", true);

    assertTrue(adaptWith(properties).getVideoEmbedCode().contains("allowfullscreen"));
  }

  @Test
  public void testGetVideoEmbedCodeWhenNothingIsConfigured() {
    assertNull(adaptWith(new HashMap<>()).getVideoEmbedCode());
  }

  @Test
  public void testGetVideoEmbedCodeWhenTheInputIsBlank() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "   ");

    assertNull(adaptWith(properties).getVideoEmbedCode());
  }

  @Test
  public void testGetVideoEmbedCodeWhenTheInputIsHtml() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "<iframe src=\"evil\"></iframe>");

    assertNull(adaptWith(properties).getVideoEmbedCode());
  }

  /** A youtube URL with neither a v= parameter nor a youtu.be path has no id to extract. */
  @Test
  public void testGetVideoEmbedCodeWhenTheYoutubeUrlHasNoVideoId() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("youtubeVideo", "https://www.youtube.com/feed/subscriptions");

    assertNull(adaptWith(properties).getVideoEmbedCode());
  }
}
