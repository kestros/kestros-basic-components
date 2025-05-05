package io.kestros.cms.components.basic.content;

public enum AnchorTargetOptions {
  SELF("_self", "Self"),
  BLANK("_blank", "Blank");

  private String displayName;
  private String value;

  AnchorTargetOptions(String displayName, String value) {
    this.displayName = displayName;
    this.value = value;
  }

  public static AnchorTargetOptions lookup(String value) {
    for (AnchorTargetOptions option : AnchorTargetOptions.values()) {
      if (option.getValue().equals(value)) {
        return option;
      }
    }
    return SELF;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getValue() {
    return value;
  }
}
