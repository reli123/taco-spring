package hr.obai.taco.commons.validation;

import hr.obai.taco.commons.dto.BaseDto;

/** Always {@code true} {@link ServiceValidator}. */
public final class PassThroughValidator<DTO extends BaseDto & Validatable>
    extends ServiceValidator<DTO> {
  @Override
  protected final boolean doValidate(DTO item) {
    return true;
  }
}
