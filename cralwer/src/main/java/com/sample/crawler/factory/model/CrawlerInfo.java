package com.sample.crawler.factory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Deprecated
public class CrawlerInfo {

  private String seedURL;

  private Class crawler;
}
