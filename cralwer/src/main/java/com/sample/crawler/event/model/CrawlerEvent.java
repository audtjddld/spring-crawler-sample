package com.sample.crawler.event.model;

import com.sample.common.company.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CrawlerEvent {

  private Company company;
}
