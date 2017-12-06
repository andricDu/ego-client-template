package bio.overture.egoclienttemplate.config;

import bio.overture.egoclienttemplate.retry.ClientRetryListener;
import bio.overture.egoclienttemplate.retry.DefaultRetryListener;
import bio.overture.egoclienttemplate.retry.RetryPolicies;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.retry.backoff.ExponentialBackOffPolicy.DEFAULT_MULTIPLIER;

@Configuration
public class RetryConfig {

  private static final int DEFAULT_MAX_RETRIES = 5;
  private static final long DEFAULT_INITIAL_BACKOFF_INTERVAL = SECONDS.toMillis(15L);

  @Value("${auth.connection.maxRetries}")
  private int maxRetries = DEFAULT_MAX_RETRIES;
  @Value("${auth.connection.initialBackoff}")
  private long initialBackoff = DEFAULT_INITIAL_BACKOFF_INTERVAL;
  @Value("${auth.connection.multiplier}")
  private double multiplier = DEFAULT_MULTIPLIER;

  @Bean
  public RetryTemplate retryTemplate() {
    val result = new RetryTemplate();
    result.setBackOffPolicy(defineBackOffPolicy());

    result.setRetryPolicy(new SimpleRetryPolicy(maxRetries, RetryPolicies.getRetryableExceptions(), true));
    result.registerListener(new DefaultRetryListener(clientRetryListener()));
    return result;
  }

  @Bean
  public ClientRetryListener clientRetryListener() {
    return new ClientRetryListener() {

      @Override
      public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
                                                   Throwable throwable) {
        if (throwable instanceof InvalidTokenException) {
          this.retry = false;
        }
      }
    };

  }

  private BackOffPolicy defineBackOffPolicy() {
    val backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(initialBackoff);
    backOffPolicy.setMultiplier(multiplier);

    return backOffPolicy;
  }

}
