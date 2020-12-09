package com.sample.scraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ScrapingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScrapingApplication.class, args);
  }

}

