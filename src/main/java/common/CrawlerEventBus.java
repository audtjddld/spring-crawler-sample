package common;

import com.google.common.eventbus.AsyncEventBus;
import java.util.concurrent.Executors;
import makeshop.scraping.event.ScrapProductDetailEventLinkListener;
import makeshop.scraping.event.ScrapProductEventListener;

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
