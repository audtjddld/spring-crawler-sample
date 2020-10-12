package com.sample.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ScrapingMessage implements Serializable {

  // 식별 값으로는 현재 이거 밖에 없음.
  private final String domain;

  private final String link;

  private final String category;

  @JsonCreator
  public ScrapingMessage(@JsonProperty("domain") String domain,
                         @JsonProperty("link") String link,
                         @JsonProperty("category") String category) {
    this.domain = domain;
    this.link = link;
    this.category = category;
  }

}
