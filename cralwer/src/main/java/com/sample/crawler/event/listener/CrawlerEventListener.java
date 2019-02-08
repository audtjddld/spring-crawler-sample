package com.sample.crawler.event.listener;


import com.sample.crawler.component.CollectProductLinkServiceMap;
import com.sample.crawler.detail.service.CollectProductLinkService;
import com.sample.crawler.event.model.CrawlerEvent;
import com.sample.crawler.factory.CrawlerFactory;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import java.net.MalformedURLException;
import java.net.URL;
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
  private final CollectProductLinkServiceMap collectProductLinkServiceMap;

  @Autowired
  public CrawlerEventListener(CrawlerFactory crawlerFactory, ApplicationContext applicationContext, CollectProductLinkServiceMap collectProductLinkServiceMap) {
    this.crawlerFactory = crawlerFactory;
    this.applicationContext = applicationContext;
    this.collectProductLinkServiceMap = collectProductLinkServiceMap;
  }

  @EventListener
  public void crawlerStart(CrawlerEvent event) throws MalformedURLException {
    // TODO pipe 아이디에 대한 정규식 및 대상 위치 정보 얻어옴.
    Class<? extends WebCrawler> clazz = crawlerFactory.getCrawlerInfo(event.getCompany().getType());

    CrawlController controller = applicationContext.getBean(CrawlController.class);
    log.debug("controller hash : {}", controller);
    Asserts.notNull(controller, "controller");

    // detail page를 전달 받을 subscriber 생성.
    URL url = new URL(event.getCompany().getSeedURL());
    log.info("host: {}", url.getHost());
//    configurableListableBeanFactory.registerSingleton(url.getHost(), new CollectProductLinkService());
    collectProductLinkServiceMap.put(url.getHost(), new CollectProductLinkService());
    controller.addSeed(event.getCompany().getSeedURL());
    controller.start(clazz, 1);

//    ((DefaultListableBeanFactory) configurableListableBeanFactory).destroySingleton(url.getHost());
    collectProductLinkServiceMap.remove(url.getHost());

  }

}
