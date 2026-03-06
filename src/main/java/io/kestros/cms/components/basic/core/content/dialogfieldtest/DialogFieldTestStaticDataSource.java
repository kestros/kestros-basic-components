package io.kestros.cms.components.basic.core.content.dialogfieldtest;

import io.kestros.cms.components.basic.api.content.KestrosDialogFieldTest;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class DialogFieldTestStaticDataSource extends BaseSlingModelDataSource
    implements KestrosDialogFieldTest {

  @Override
  @Nullable
  public String getSampleText() {
    return getResource().getValueMap().get("sampleText", String.class);
  }

  @Override
  @Nullable
  public String getSampleTextarea() {
    return getResource().getValueMap().get("sampleTextarea", String.class);
  }

  @Override
  @Nullable
  public String getSampleRichtext() {
    return getResource().getValueMap().get("sampleRichtext", String.class);
  }

  @Override
  public boolean getSampleCheckbox() {
    return getResource().getValueMap().get("sampleCheckbox", false);
  }

  @Override
  @Nullable
  public String getSamplePath() {
    return getResource().getValueMap().get("samplePath", String.class);
  }

  @Override
  @Nullable
  public String getSampleSelect() {
    return getResource().getValueMap().get("sampleSelect", String.class);
  }

  @Override
  @Nullable
  public String getSampleTag() {
    return getResource().getValueMap().get("sampleTag", String.class);
  }

  @Override
  @Nullable
  public String getSampleNumber() {
    return getResource().getValueMap().get("sampleNumber", String.class);
  }

  @Override
  @Nullable
  public String getSampleDate() {
    return getResource().getValueMap().get("sampleDate", String.class);
  }

  @Override
  @Nullable
  public String getSampleDatetime() {
    return getResource().getValueMap().get("sampleDatetime", String.class);
  }

  @Override
  public boolean getSampleToggle() {
    return getResource().getValueMap().get("sampleToggle", false);
  }

  @Override
  @Nullable
  public String getSampleRadio() {
    return getResource().getValueMap().get("sampleRadio", String.class);
  }

  @Override
  @Nullable
  public String getSampleHidden() {
    return getResource().getValueMap().get("sampleHidden", String.class);
  }

  @Override
  @Nullable
  public String getSampleImage() {
    return getResource().getValueMap().get("sampleImage", String.class);
  }
}
