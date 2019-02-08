package com.sample.crawler.component;

import com.sample.crawler.detail.service.CollectProductLinkService;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class CollectProductLinkServiceMap extends ConcurrentHashMap<String, CollectProductLinkService> {

}
