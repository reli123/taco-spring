package hr.obai.commons.controller.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Umbrella-annotation for annotations related to custom headers.
 *
 * @see Expose
 * @see HttpHeadersHelper
 * @see HttpHeadersHelper#generateHttpHeadersForAccessControlExposeHeaders()
 */
@Retention(RetentionPolicy.RUNTIME)
@interface CustomHeader {
  /**
   * Headers annotated with this annotation will be exposed to {@link
   * org.springframework.http.HttpHeaders#ACCESS_CONTROL_EXPOSE_HEADERS}.
   */
  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  @interface Expose {}
}
