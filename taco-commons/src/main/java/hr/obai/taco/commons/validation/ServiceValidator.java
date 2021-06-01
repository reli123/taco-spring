package hr.obai.taco.commons.validation;

import hr.obai.taco.commons.dto.BaseDto;

import java.util.List;

import lombok.NonNull;

/**
 * Contract for a validation service.
 *
 * @param <T> The type of the validatable object.
 * @see BaseDto
 * @see Validatable
 */
public abstract class ServiceValidator<T extends Validatable> {

  public final boolean validateAll(List<T> items) {

    boolean allValid = true;
    for (T item : items) {
      allValid &= validate(item);
    }

    return allValid;
  }

  public final boolean validate(@NonNull T item) {
    boolean valid = doValidate(item);
    item.setValid(valid);
    return valid;
  }

  protected abstract boolean doValidate(T item);
}
