package com.sample.crawler.makeshop.scraping.event;

import com.google.common.eventbus.Subscribe;
import common.Price;
import common.Product;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import makeshop.scraping.event.model.ScrapProductEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class ScrapProductEventListener {

  private String btnArea = "div.prd-btns a";
  private String imageArea = "div.thumb-wrap>.thumb img";

  @Subscribe
  public void scrapProduct(ScrapProductEvent event) throws InterruptedException, IOException {
    TimeUnit.SECONDS.sleep(1);

    Document document = Jsoup.connect(event.getLink())
        .userAgent("SampleCrawler")
        .get();

    //FIXME javascript 정규식으로 추출 예정.
    // 변경되는 지점 : 버튼 영역
    Elements element = document.select(btnArea);
    if (element.size() == 0) {
      log.info("품절상품 입니다. {}", event.getLink());
      return;
    }
    //    log.info("link : {}", event.getLink());

    String link = event.getLink();
    String price = document.select("input[name='price']").attr("value");
    // 변경되는 지점 : 이미지 링크 위치
    String imageLink = document.select(imageArea).attr("src");
    imageLink = imageLink.startsWith("http") ? imageLink : "http://" + event.getDomain() + imageLink;

    Product product = Product.builder()
        .id(event.getId())
        .categoryName1(event.getCategory())
        .link(link)
        .title(document.select("form[name='form1'] h3").text())
        .mobileLink(link)
        .imageLink(imageLink)
        .price(new Price(price))
        .mobilePrice(new Price(price))
        .normalPrice(new Price(price))
        .build();

    log.info("{}", product);
  }
}
