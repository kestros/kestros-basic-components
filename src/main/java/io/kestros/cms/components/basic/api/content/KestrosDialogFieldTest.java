package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nullable;

public interface KestrosDialogFieldTest extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/dialog-field-test";

  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getSampleText();

  @Nullable
  String getSampleTextarea();

  @Nullable
  String getSampleRichtext();

  boolean getSampleCheckbox();

  @Nullable
  String getSamplePath();

  @Nullable
  String getSampleSelect();

  @Nullable
  String getSampleTag();

  @Nullable
  String getSampleNumber();

  @Nullable
  String getSampleDate();

  @Nullable
  String getSampleDatetime();

  boolean getSampleToggle();

  @Nullable
  String getSampleRadio();

  @Nullable
  String getSampleHidden();

  @Nullable
  String getSampleImage();
}
