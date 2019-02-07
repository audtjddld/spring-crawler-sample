package com.sample.crawler.detail.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CrawlingDetailLink {
  private String domain;
  private String link;
  private Map<String, String> categoryMap;

  public String getFullURL() {
    return String.format("http://%s%s", domain, link);
  }
}
