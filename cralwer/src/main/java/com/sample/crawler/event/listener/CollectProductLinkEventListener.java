package com.sample.crawler.event.listener;

import com.sample.crawler.component.CollectProductLinkServiceMap;
import com.sample.crawler.detail.service.CollectProductLinkService;
import com.sample.crawler.event.model.CollectProductLinkEvent;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CollectProductLinkEventListener {


  private final CollectProductLinkServiceMap collectProductLinkServiceMap;

  @Autowired
  public CollectProductLinkEventListener(CollectProductLinkServiceMap collectProductLinkServiceMap) {
    this.collectProductLinkServiceMap = collectProductLinkServiceMap;
  }

  @EventListener
  public void collect(CollectProductLinkEvent event) throws IOException, InterruptedException {
    CollectProductLinkService collectProductLinkService = collectProductLinkServiceMap.get(event.getHost());

    if (collectProductLinkService == null) {
      log.error("host: {}", event.getHost());
      return;
    }
    collectProductLinkService.collect(event);
  }
}
