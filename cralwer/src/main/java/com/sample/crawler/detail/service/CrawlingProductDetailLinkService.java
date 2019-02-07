package com.sample.crawler.detail.service;

import com.sample.common.RegexGenerator;
import com.sample.crawler.detail.model.CrawlingDetailLink;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CrawlingProductDetailLinkService {

  private RegexGenerator regexGenerator;
  private Set<String> previousVisitURLs;

  public CrawlingProductDetailLinkService() {
    previousVisitURLs = new HashSet<>();
    regexGenerator = new RegexGenerator();
  }

  public void scrap(CrawlingDetailLink crawlingDetailLink) throws InterruptedException, IOException {
    TimeUnit.MILLISECONDS.sleep(1500);
    Document doc = Jsoup.connect(crawlingDetailLink.getFullURL())
        .userAgent("SampleCrawler")
        .get();

    Elements elements = doc.select("a[href^=/shop/shopdetail.html]");
    elements.forEach(e -> {
      String link = String.format("http://%s%s", crawlingDetailLink.getDomain(), e.attr("href"));
      //LOG.info("link : {}", link);
      regexGenerator.setLink(link);

      String filterLink = regexGenerator.generateLink();
      //LOG.info("filter link : {}", filterLink);

      if (previousVisitURLs.contains(filterLink)) {
        return;
      }

      previousVisitURLs.add(filterLink);

      //TODO detail link를 kafka를 통해서 보낸다.
    });

  }
}
