package io.kestros.cms.components.basic.core.content.videoembed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class VideoEmbedDataSourceComponentTest extends BaseDataSourceComponentTest {

  private VideoEmbedDataSourceComponent videoEmbed;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosVideoEmbed.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() {
    properties.put("sling:resourceType", KestrosVideoEmbed.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    properties.put("youtubeVideo", "https://www.youtube.com/watch?v=zzzzzzzzzzzz");
  }

  @Override
  public void testToSyntheticResource() {
    resource = context.create().resource("/content/video-embed", properties);

    context.request().setResource(resource);
    videoEmbed = context.request().adaptTo(VideoEmbedDataSourceComponent.class);

    assertNotNull(videoEmbed);
    assertEquals("/synthetics/test/path/video-embed",
            videoEmbed.toSyntheticResource(context.resourceResolver(), "/test/path").getPath());
  }

  @Test
  public void testGetVideoEmbedCode() {
    resource = context.create().resource("/content/video-embed", properties);

    context.request().setResource(resource);
    videoEmbed = context.request().adaptTo(VideoEmbedDataSourceComponent.class);

    assertEquals(
            "<iframe src=\"https://www.youtube.com/embed/zzzzzzzzzzzz\"         "
                    + "style=\"width:100%;height:100%;border:0;\"         allowfullscreen        "
                    + " allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; "
                    + "gyroscope; picture-in-picture; fullscreen\"         "
                    + "referrerpolicy=\"strict-origin-when-cross-origin\"></iframe>",
            videoEmbed.getVideoEmbedCode());
  }

  @Test
  public void testGetVideoEmbedCodeWhenFullScreen() {
    properties.put("allowFullScreen", true);
    resource = context.create().resource("/content/video-embed", properties);

    context.request().setResource(resource);
    videoEmbed = context.request().adaptTo(VideoEmbedDataSourceComponent.class);

    assertEquals(
            "<iframe src=\"https://www.youtube.com/embed/zzzzzzzzzzzz\"         "
                    + "style=\"width:100%;height:100%;border:0;\"         allowfullscreen        "
                    + " allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; "
                    + "gyroscope; picture-in-picture; fullscreen\"         "
                    + "referrerpolicy=\"strict-origin-when-cross-origin\"></iframe>",
            videoEmbed.getVideoEmbedCode());
  }

  @Test
  public void testGetVideoEmbedCodeWhenFullScreenWhenMute() {
    properties.put("mute", true);
    resource = context.create().resource("/content/video-embed", properties);

    context.request().setResource(resource);
    videoEmbed = context.request().adaptTo(VideoEmbedDataSourceComponent.class);

    assertEquals(
            "<iframe src=\"https://www.youtube.com/embed/zzzzzzzzzzzz?mute=1\"         "
                    + "style=\"width:100%;height:100%;border:0;\"         allowfullscreen        "
                    + " allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; "
                    + "gyroscope; picture-in-picture; fullscreen\"         "
                    + "referrerpolicy=\"strict-origin-when-cross-origin\"></iframe>",
            videoEmbed.getVideoEmbedCode());
  }

  @Test
  public void testGetVideoEmbedCodeWhenFullScreenWhenAll() {
    properties.put("mute", true);
    properties.put("allowFullScreen", true);
    resource = context.create().resource("/content/video-embed", properties);

    context.request().setResource(resource);
    videoEmbed = context.request().adaptTo(VideoEmbedDataSourceComponent.class);

    assertEquals(
            "<iframe src=\"https://www.youtube.com/embed/zzzzzzzzzzzz?mute=1\"         "
                    + "style=\"width:100%;height:100%;border:0;\"         allowfullscreen        "
                    + " allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; "
                    + "gyroscope; picture-in-picture; fullscreen\"         "
                    + "referrerpolicy=\"strict-origin-when-cross-origin\"></iframe>",
            videoEmbed.getVideoEmbedCode());
  }

  @Test
  public void testGetVideoEmbedCodeWhenFullScreenWhenInvalidUrl() {
    properties.put("youtubeVideo", "<text>invalidUrl</text>");
    resource = context.create().resource("/content/video-embed", properties);

    context.request().setResource(resource);
    videoEmbed = context.request().adaptTo(VideoEmbedDataSourceComponent.class);

    assertNull(videoEmbed.getVideoEmbedCode());
  }


}