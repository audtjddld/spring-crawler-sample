package com.sample.crawler.event.listener;

import com.sample.crawler.event.model.CrawlerEvent;
import com.sample.crawler.factory.CrawlerFactory;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
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
    log.info(" hosting : {}", event.getCompany().getType());
    Class<? extends WebCrawler> clazz = crawlerFactory.getCrawlerInfo(event.getCompany().getType());

    CrawlController controller = applicationContext.getBean(CrawlController.class);
    log.info("controller hash : {}", controller);
    Asserts.notNull(controller, "controller");

    log.info("crawler Controller : {}", controller);

    controller.addSeed(event.getCompany().getSeedURL());
    controller.start(clazz, 1);
  }

}
