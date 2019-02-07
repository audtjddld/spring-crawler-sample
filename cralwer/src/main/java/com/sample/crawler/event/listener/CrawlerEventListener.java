package com.sample.crawler.event.listener;

import com.sample.crawler.event.model.CrawlerEvent;
import com.sample.crawler.factory.CrawlerFactory;
import com.sample.crawler.factory.model.CrawlerInfo;
import com.sample.crawler.parser.MakeShopCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrawlerEventListener {

  private final CrawlerFactory crawlerFactory;
  private final ApplicationContext applicationContext;

  @Autowired
  public CrawlerEventListener(CrawlerFactory crawlerFactory, ApplicationContext applicationContext) {
    this.crawlerFactory = crawlerFactory;
    this.applicationContext = applicationContext;
  }

  @EventListener
  public void crawlerStart(CrawlerEvent event) {
    // TODO pipe 아이디에 대한 정규식 및 대상 위치 정보 얻어옴.
    // 변경되는 영역으로 찾은 곳은 품절 체크를 위한 버튼 영역과, 상품 이미지 영역을 추출하는 곳이다.

    CrawlController controller = applicationContext.getBean(CrawlController.class);
    log.info("controller hash : {}", controller);
    Asserts.notNull(controller, "controller");

    log.info("crawler Controller : {}", controller);
    CrawlerInfo crawlerInfo = crawlerFactory.getCrawlerInfo(event.getPipeId());

    controller.addSeed(crawlerInfo.getSeedURL());
    controller.start(crawlerInfo.getCrawler(), 1);
  }

}
