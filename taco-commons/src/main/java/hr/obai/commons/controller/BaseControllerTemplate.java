package hr.obai.commons.controller;

import hr.obai.commons.repository.criteria.BaseCriteria;
import hr.obai.commons.validation.Validatable;
import hr.obai.commons.controller.http.HttpHeadersHelper;
import hr.obai.commons.dto.BaseDto;
import hr.obai.commons.model.outcome.RequestOutcome;

import java.util.Optional;
import java.util.concurrent.Callable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/** Template for a controller */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseControllerTemplate<DTO extends BaseDto, CRITERIA extends BaseCriteria> {

  public static final String SEARCH = "/searches";
  public static final String ACCEPT_APPLICATION_JSON_UTF8_VALUE =
      HttpHeaders.ACCEPT + "=" + MediaType.APPLICATION_JSON_VALUE;

  public static final class RequestHandler<R> {
    public ResponseEntity<R> handle(@NonNull Callable<?> operation) {
      R payload;
      val builder =
          ResponseEntity
              // Always HTTP STATUS 200!
              .status(HttpStatus.OK)
              .headers(HttpHeadersHelper.generateHttpHeadersForAccessControlExposeHeaders())
              // "OK" is only a default: the request outcome will be overridden if an error occurs.
              .headers(HttpHeadersHelper.generateHttpHeadersForRequestOutcome(RequestOutcome.ok()));
      try {
        val result = operation.call();
        updateHttpHeaders(builder, result);
        payload = toPayload(result);
      } catch (Exception ex) {
        handleError(builder, ex);
        payload = null;
      }
      return builder.body(payload);
    }

    private void updateHttpHeaders(ResponseEntity.BodyBuilder builder, Object result) {
      if (result instanceof Page) {
        val page = (Page<?>) result;
        val headers = HttpHeadersHelper.generateHttpHeadersForPagination(page);
        builder.headers(headers);
      } else if (result instanceof Validatable) {
        val validatable = (Validatable) result;
        val requestOutcome =
            validatable.isValid() ? RequestOutcome.ok() : RequestOutcome.ko("validation_error");
        builder.headers(HttpHeadersHelper.generateHttpHeadersForRequestOutcome(requestOutcome));
      }
    }

    @SuppressWarnings("unchecked")
    private R toPayload(Object result) {
      R payload;
      if (result instanceof Page) {
        payload = (R) ((Page<?>) result).getContent();
      } else if (result instanceof Optional) {
        payload = ((Optional<R>) result).orElse(null);
      } else {
        payload = (R) result;
      }
      return payload;
    }

    private void handleError(ResponseEntity.BodyBuilder builder, Exception ex) {
      log.error("Error handling request: " + ex.getMessage(), ex);
      updateHttpHeadersWithError(builder, ex);
    }

    private void updateHttpHeadersWithError(ResponseEntity.BodyBuilder builder, Exception ex) {
      val requestOutcome = RequestOutcome.ko(ex.getMessage());
      builder.headers(HttpHeadersHelper.generateHttpHeadersForRequestOutcome(requestOutcome));
    }
  }
}
