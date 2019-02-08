package com.sample.crawler.event.model;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollectProductLinkEvent {

  private String host;
  private String domain;
  private String link;
  private Map<String, String> categoryMap;
  public String getFullURL() {
    return String.format("http://%s%s", domain, link);
  }
}
