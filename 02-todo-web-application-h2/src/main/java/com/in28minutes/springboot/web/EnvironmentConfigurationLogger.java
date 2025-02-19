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
    log.debug("====== Environment and configuration ======");
    log.debug("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
    final var sources = ((AbstractEnvironment) environment).getPropertySources();
    StreamSupport.stream(sources.spliterator(), false)
        .filter(ps -> ps instanceof EnumerablePropertySource)
        .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
        .flatMap(Arrays::stream)
        .distinct()
        .forEach(prop -> log.debug("{}", prop)); // environment.getProperty(prop)
    log.debug("===========================================");
  }
}
