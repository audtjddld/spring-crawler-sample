package com.sample.crawler.makeshop.scraping.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ScrapProductEvent {

  private String domain;
  private String shopId;
  private String id;
  private String link;
  private String category;

}
