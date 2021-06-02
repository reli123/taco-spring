package hr.obai.commons.model.outcome;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
public class RequestOutcomeEntry<T> {
  @JsonProperty private RequestOutcomeEntryType type;
  @JsonProperty private String title;
  @JsonProperty private String text;
  @JsonProperty private T data;

  private RequestOutcomeEntry(RequestOutcomeEntryType type, String title, String text, T data) {
    this.type = type;
    this.title = title;
    this.text = text;
    this.data = data;
  }

  public static RequestOutcomeEntry of(
      @NonNull RequestOutcomeEntryType type, @NonNull String text) {
    return of(type, text, null);
  }

  public static <T> RequestOutcomeEntry of(
      @NonNull RequestOutcomeEntryType type, @NonNull String text, T data) {
    return of(type, StringUtils.EMPTY, text, data);
  }

  public static <T> RequestOutcomeEntry of(
      @NonNull RequestOutcomeEntryType type, @NonNull String title, @NonNull String text, T data) {
    return new RequestOutcomeEntry<>(type, title, text, data);
  }

  public static RequestOutcomeEntry of(@NonNull Exception e, @NonNull String text) {
    return of(RequestOutcomeEntryType.ERROR, StringUtils.EMPTY, text, e.getMessage());
  }
}
