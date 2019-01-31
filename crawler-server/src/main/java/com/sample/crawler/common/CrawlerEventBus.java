package com.sample.crawler.common;

import com.google.common.eventbus.AsyncEventBus;
import com.sample.crawler.makeshop.scraping.event.ScrapProductDetailEventLinkListener;
import com.sample.crawler.makeshop.scraping.event.ScrapProductEventListener;
import java.util.concurrent.Executors;

public class CrawlerEventBus {

  private static AsyncEventBus asyncEventBus;

  public static AsyncEventBus getInstance() {
    if (asyncEventBus == null) {
      asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));
      asyncEventBus.register(new ScrapProductDetailEventLinkListener());
      asyncEventBus.register(new ScrapProductEventListener());
    }
    return asyncEventBus;
  }
}
