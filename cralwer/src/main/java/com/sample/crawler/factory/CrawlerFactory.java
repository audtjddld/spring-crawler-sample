package com.sample.crawler.factory;

import com.sample.crawler.factory.model.CrawlerInfo;
import com.sample.crawler.parser.MakeShopCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CrawlerFactory {

  private final ApplicationContext applicationContext;

  @Autowired
  public CrawlerFactory(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public CrawlerInfo getCrawlerInfo(String pipeId) {
    switch (pipeId) {
      case "graymadonna" :
        return new CrawlerInfo("http://www.graymadonna.com", applicationContext.getBean(MakeShopCrawler.class));
      case "gozip28" :
        return new CrawlerInfo("http://www.gozip28.com", applicationContext.getBean(MakeShopCrawler.class));
      case "sezwick" :
        return new CrawlerInfo("http://www.sezwick.com", applicationContext.getBean(MakeShopCrawler.class));
      default:
        return null;
    }
  }
}
