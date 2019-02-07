package com.sample.crawler.factory;

import com.sample.crawler.factory.model.CrawlerInfo;
import com.sample.crawler.parser.MakeShopCrawler;
import org.springframework.stereotype.Component;

@Component
public class CrawlerFactory {

  //TODO 수집 대상 정보는 호출하는 곳에서 제공해준다. 이건 예시이다.
  public CrawlerInfo getCrawlerInfo(String pipeId) {
    switch (pipeId) {
      case "graymadonna" :
        return new CrawlerInfo("http://www.graymadonna.com", MakeShopCrawler.class);
      case "gozip28" :
        return new CrawlerInfo("http://www.gozip28.com", MakeShopCrawler.class);
      case "sezwick" :
        return new CrawlerInfo("http://www.sezwick.com", MakeShopCrawler.class);
      default:
        return null;
    }
  }
}
