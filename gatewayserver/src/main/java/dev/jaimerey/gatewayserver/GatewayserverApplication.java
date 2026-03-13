package dev.jaimerey.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}
