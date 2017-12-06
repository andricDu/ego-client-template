package bio.overture.egoclienttemplate.retry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.retry.listener.RetryListenerSupport;

import static lombok.AccessLevel.PROTECTED;

/**
 * ClientRetryListener allows to inject client logic which will be executed before any statements of
 * {@link DefaultRetryListener}. If after a call to the ClientRetryListener {@code isRetry()} returns {@code FALSE} the
 * default retry logic will not be executed.
 */
@Data
@FieldDefaults(level = PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class ClientRetryListener extends RetryListenerSupport {

  boolean retry = true;

}