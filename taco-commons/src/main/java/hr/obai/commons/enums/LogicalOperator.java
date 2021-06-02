package hr.obai.commons.enums;

public enum LogicalOperator {
  AND,
  OR;

  public String value() {
    return name();
  }

  public static LogicalOperator fromValue(String v) {
    return valueOf(v);
  }
}
