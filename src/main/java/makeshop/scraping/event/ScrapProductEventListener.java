package makeshop.scraping.event;

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

  @Subscribe
  public void scrapProduct(ScrapProductEvent event) throws InterruptedException, IOException {
    TimeUnit.SECONDS.sleep(1);

    Document document = Jsoup.connect(event.getLink()).get();

    //FIXME javascript 정규식으로 추출 예정.
    Elements element = document.select("div.prd-btns a");
    if (element.size() == 0) {
      log.info("품절상품 입니다.");
      return;
    }

//    log.info("link : {}", event.getLink());

    String link = event.getLink();
    String price = document.select("input[name='price']").attr("value");
    Product product = Product.builder()
        .id(event.getId())
        .categoryName1(event.getCategory())
        .link(link)
        .title(document.select("h3.tit-prd").text())
        .mobileLink(link)
        .imageLink("http://" + event.getDomain() + document.select("div.thumb-wrap>.thumb img").attr("src"))
        .price(new Price(price))
        .mobilePrice(new Price(price))
        .normalPrice(new Price(price))
        .build();

    log.info("{}", product);
  }
}
