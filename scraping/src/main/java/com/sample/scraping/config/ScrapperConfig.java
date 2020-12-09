package com.sample.scraping.config;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperConfig {

  @Bean
  public Semaphore stopper() {
    return new Semaphore(1);
  }

  @Bean
  public ReentrantLock lock() {
    return new ReentrantLock();
  }

}
