package com.sample.crawler.detail.service;

import com.sample.common.RegexGenerator;
import com.sample.crawler.event.model.CollectProductLinkEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class CollectProductLinkService {

  private RegexGenerator regexGenerator;
  private Set<String> previousVisitURLs;

  public CollectProductLinkService() {
    previousVisitURLs = new HashSet<>();
    regexGenerator = new RegexGenerator();
  }

  public void collect(CollectProductLinkEvent collectProductLinkEvent) throws InterruptedException, IOException {
    TimeUnit.MILLISECONDS.sleep(1500);
    Document doc = Jsoup.connect(collectProductLinkEvent.getFullURL())
        .userAgent("SampleCrawler")
        .get();

    Elements elements = doc.select("a[href^=/shop/shopdetail.html]");
    elements.forEach(e -> {
      String link = String.format("http://%s%s", collectProductLinkEvent.getDomain(), e.attr("href"));
      //LOG.info("link : {}", link);
      regexGenerator.setLink(link);

      String filterLink = regexGenerator.generateLink();
      //LOG.info("filter link : {}", filterLink);

      if (previousVisitURLs.contains(filterLink)) {
        return;
      }

      previousVisitURLs.add(filterLink);

      //TODO detail link를 kafka를 통해서 보낸다.
      log.info("filter link : {}", filterLink);
    });

  }
}
