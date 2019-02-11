package com.sample.common.message;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ScrapingMessage implements Serializable {

  // 식별 값으로는 현재 이거 밖에 없음.
  private String domain;

  private String link;

  private String category;
}
