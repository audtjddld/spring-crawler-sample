package com.sample.crawler.factory;

import common.company.Company;
import common.company.Hosting;
import org.springframework.stereotype.Component;

@Component
public class CrawlerFactory {

  public CrawlerFactory(Company company) {

    Hosting hosting = company.getType();



  }

}
