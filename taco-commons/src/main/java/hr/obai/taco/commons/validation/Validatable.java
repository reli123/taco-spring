package hr.obai.taco.commons.validation;

/** Contract for an object that can be validated. */
public interface Validatable {
  boolean isValid();

  void setValid(boolean valid);

  default void invalidate() {
    setValid(false);
  }
}
