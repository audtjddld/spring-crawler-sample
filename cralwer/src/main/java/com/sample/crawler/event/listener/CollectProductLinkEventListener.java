package com.sample.crawler.event.listener;

import com.sample.crawler.detail.service.CollectProductLinkService;
import com.sample.crawler.event.model.CollectProductLinkEvent;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CollectProductLinkEventListener {

  private final ConfigurableListableBeanFactory configurableListableBeanFactory;

  @Autowired
  public CollectProductLinkEventListener(GenericApplicationContext genericApplicationContext) {
    configurableListableBeanFactory = genericApplicationContext.getBeanFactory();
  }

  @EventListener
  public void collect(CollectProductLinkEvent event) throws IOException, InterruptedException {
    CollectProductLinkService collectProductLinkService = (CollectProductLinkService) configurableListableBeanFactory.getSingleton(event.getHost());
    if (collectProductLinkService == null) {
      log.error("host: {}", event.getHost());
      return;
    }
    collectProductLinkService.collect(event);
  }
}
