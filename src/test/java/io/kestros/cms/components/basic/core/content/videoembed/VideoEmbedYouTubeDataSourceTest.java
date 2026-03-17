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
package io.kestros.cms.components.basic.core.content.videoembed;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VideoEmbedYouTubeDataSourceTest {


  private VideoEmbedYouTubeDataSource videoEmbedYouTubeDataSource
          = new VideoEmbedYouTubeDataSource();

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

}