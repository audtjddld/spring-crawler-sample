package com.sample.crawler.factory;

import com.sample.common.company.Hosting;
import com.sample.crawler.extractor.MakeShopExtractor;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.springframework.stereotype.Component;

@Component
public class CrawlerFactory {

  //TODO 수집을 호출 하는 쪽에서 hosting 정보만 전달해준다. 이후 어떻게 크롤링 값이 다를지는 미지수.
  public Class<? extends WebCrawler> getCrawlerInfo(Hosting type) {
    if (type == Hosting.MAKESHOP) {
      return MakeShopExtractor.class;
    }
    //TODO cafe24 crawler 추가. 그외 기타 예외 갖는 부분들 추가.
    return null;
  }
}
