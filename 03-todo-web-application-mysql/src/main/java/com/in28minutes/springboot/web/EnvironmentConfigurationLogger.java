package com.in28minutes.springboot.web;

import java.util.Arrays;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnvironmentConfigurationLogger {

  @SuppressWarnings("rawtypes")
  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    final var environment = event.getApplicationContext().getEnvironment();
    log.info("====== Environment and configuration ======");
    log.info("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
    final var sources = ((AbstractEnvironment) environment).getPropertySources();
    StreamSupport.stream(sources.spliterator(), false)
        .filter(ps -> ps instanceof EnumerablePropertySource)
        .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
        .flatMap(Arrays::stream)
        .distinct()
        .forEach(
            prop -> {
              log.info("{}", prop);
              //					Object resolved = environment.getProperty(prop, Object.class);
              //					if (resolved instanceof String) {
              //						LOGGER.info("{}", environment.getProperty(prop));
              //					}
            });
    log.info("===========================================");
  }
}
