package com.sample.crawler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

  @PostMapping(path = "/{pipeId}")
  public ResponseEntity<Void> crawlerByPipeId(@PathVariable("pipeId") String pipeId) {
    return ResponseEntity.ok().build();
  }
}
