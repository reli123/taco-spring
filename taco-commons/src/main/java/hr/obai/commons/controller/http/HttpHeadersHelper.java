package hr.obai.commons.controller.http;

import hr.obai.commons.utils.JsonUtils;
import hr.obai.commons.model.outcome.RequestOutcome;

import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import pl.touk.throwing.ThrowingFunction;

/** Encapsulates data into {@link HttpHeaders}. */
@UtilityClass
public final class HttpHeadersHelper {
  private static final String CUSTOM_HEADERS_PREFIX = "X-";

  @CustomHeader.Expose
  public static final String X_CURRENT_PAGE = CUSTOM_HEADERS_PREFIX + "Current-Page";

  @CustomHeader.Expose
  public static final String X_NUM_CURRENT_PAGE_ELEMENTS =
      CUSTOM_HEADERS_PREFIX + "Num-Current-Page-Elements";

  @CustomHeader.Expose
  public static final String X_NUM_TOTAL_PAGES = CUSTOM_HEADERS_PREFIX + "Num-Total-Pages";

  @CustomHeader.Expose
  public static final String X_NUM_TOTAL_ELEMENTS = CUSTOM_HEADERS_PREFIX + "Num-Total-Elements";

  @CustomHeader.Expose
  public static final String X_REQUEST_OUTCOME = CUSTOM_HEADERS_PREFIX + "Request-Outcome";

  private static final HttpHeaders accessControlExposeHeaders;

  static {
    val headers = new HttpHeaders();
    headers.setAccessControlExposeHeaders(
        FieldUtils.getFieldsListWithAnnotation(HttpHeadersHelper.class, CustomHeader.Expose.class)
            .stream()
            .map(ThrowingFunction.unchecked(field -> (String) field.get(null)))
            .collect(Collectors.toList()));
    accessControlExposeHeaders = headers;
  }

  public static HttpHeaders generateHttpHeadersForAccessControlExposeHeaders() {
    return accessControlExposeHeaders;
  }

  public static HttpHeaders generateHttpHeadersForPagination(@NonNull Page<?> page) {
    val headers = new HttpHeaders();
    headers.set(X_CURRENT_PAGE, String.valueOf(page.getNumber()));
    headers.set(X_NUM_CURRENT_PAGE_ELEMENTS, String.valueOf(page.getNumberOfElements()));
    headers.set(X_NUM_TOTAL_PAGES, String.valueOf(page.getTotalPages()));
    headers.set(X_NUM_TOTAL_ELEMENTS, String.valueOf(page.getTotalElements()));
    return headers;
  }

  public static HttpHeaders generateHttpHeadersForRequestOutcome(
      @NonNull RequestOutcome requestOutcome) {
    val headers = new HttpHeaders();
    headers.set(X_REQUEST_OUTCOME, JsonUtils.toJsonString(requestOutcome));
    return headers;
  }
}
