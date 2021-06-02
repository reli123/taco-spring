package hr.obai.commons.model.outcome;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;

@Getter
public class RequestOutcome {
  @JsonProperty private RequestOutcomeStatus status;
  @JsonProperty private List<RequestOutcomeEntry<?>> entries;

  private RequestOutcome(RequestOutcomeStatus status, List<RequestOutcomeEntry<?>> entries) {
    validateArgs(status, entries);
    this.status = status;
    this.entries = entries;
  }

  private static void validateArgs(
      RequestOutcomeStatus status, List<RequestOutcomeEntry<?>> entries) {
    if (status == RequestOutcomeStatus.OK) {
      Validate.isTrue(entries.isEmpty(), "Expected no entries");
    } else {
      Validate.notEmpty(entries, "Expected at least one entry");
    }
  }

  public static RequestOutcome of(@NonNull Exception e, @NonNull String errorText) {
    return of(RequestOutcomeStatus.KO, RequestOutcomeEntry.of(e, errorText));
  }

  public static RequestOutcome of(
      @NonNull RequestOutcomeStatus status, @NonNull RequestOutcomeEntry<?>... entries) {
    return of(status, Arrays.asList(entries));
  }

  public static RequestOutcome of(
      @NonNull RequestOutcomeStatus status, @NonNull List<RequestOutcomeEntry<?>> entries) {
    return new RequestOutcome(status, entries);
  }

  public static RequestOutcome ok() {
    return of(RequestOutcomeStatus.OK, Collections.emptyList());
  }

  public static RequestOutcome ko(@NonNull String koMessage) {
    return of(
        RequestOutcomeStatus.KO, RequestOutcomeEntry.of(RequestOutcomeEntryType.ERROR, koMessage));
  }
}
