package com.sample.crawler.controller;

import com.sample.common.company.Company;
import com.sample.crawler.event.model.CrawlerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CrawlerController {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public CrawlerController(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping(path = "/crawling")
  public ResponseEntity<Void> crawlerByPipeId(@RequestBody Company company) {
    log.info("크롤링이 시작되었습니다. pipeId : {}", company);
    applicationEventPublisher.publishEvent(new CrawlerEvent(company));
    return ResponseEntity.ok().build();
  }
}
