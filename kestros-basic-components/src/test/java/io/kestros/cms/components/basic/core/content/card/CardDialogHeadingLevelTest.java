package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * A hand-built card picks its own heading level, so none of the card dialogs offers to take the
 * list's. Danny, 2026-10-05: "remove that option. if building an adhoc card list, you can choose
 * the header per card."
 */
public class CardDialogHeadingLevelTest {

  private static final String[] DIALOGS = {"default", "page-card", "asset-card"};

  private static String read(String dialog) throws IOException {
    String path = "/libs/kestros/commons/components/content/card/datasources/" + dialog + ".json";
    try (InputStream in = CardDialogHeadingLevelTest.class.getResourceAsStream(path)) {
      assertNotNull(path, in);
      return IOUtils.toString(in, StandardCharsets.UTF_8);
    }
  }

  @Test
  public void testNoDialogOffersToInheritFromTheList() throws IOException {
    for (String dialog : DIALOGS) {
      assertFalse(dialog, read(dialog).contains("Inherit from list"));
    }
  }

  @Test
  public void testEveryDialogStartsOnH2() throws IOException {
    Pattern selected = Pattern.compile(
            "\"value\"\\s*:\\s*\"(h[1-6])\"\\s*,\\s*\"selected\"\\s*:\\s*true");
    for (String dialog : DIALOGS) {
      Matcher matcher = selected.matcher(read(dialog));
      int count = 0;
      String value = null;
      while (matcher.find()) {
        count++;
        value = matcher.group(1);
      }
      assertEquals(dialog + ": exactly one heading level is selected", 1, count);
      assertEquals(dialog, "h2", value);
    }
  }
}
