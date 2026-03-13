package dev.jaimerey.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator bancaReyRouteConfig(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r
						.path("/bancarey/accounts/**")
						.filters(f -> f
								.rewritePath("/bancarey/accounts/(?<segment>.*)",
										"/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(LocalDateTime.now()))
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")
								))
						.uri("lb://ACCOUNTS"))
				.route(r -> r
						.path("/bancarey/cards/**")
						.filters(f -> f
								.rewritePath("/bancarey/cards/(?<segment>.*)",
										"/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(LocalDateTime.now()))
						)
						.uri("lb://CARDS"))
				.route(r -> r
						.path("/bancarey/loans/**")
						.filters(f -> f
								.rewritePath("/bancarey/loans/(?<segment>.*)",
										"/${segment}")
								.addResponseHeader("X-Response-Time",
										String.valueOf(LocalDateTime.now()))
								.retry(retryConfig -> retryConfig.setRetries(3)
										.setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100),
												Duration.ofMillis(1000),
												2,
												true))
						)
						.uri("lb://LOANS"))
				.build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(
				id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}
}
