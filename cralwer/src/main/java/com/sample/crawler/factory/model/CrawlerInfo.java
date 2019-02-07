package com.sample.crawler.factory.model;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CrawlerInfo {

  private String seedURL;

  private WebCrawler crawler;
}
