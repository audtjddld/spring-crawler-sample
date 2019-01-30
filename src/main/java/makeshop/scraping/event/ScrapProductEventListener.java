package makeshop.scraping.event;

import com.google.common.eventbus.Subscribe;
import common.Price;
import common.Product;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import makeshop.scraping.event.model.ScrapProductEvent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
public class ScrapProductEventListener {

  private WebDriver webDriver;

  public ScrapProductEventListener() {
    System.setProperty("webdriver.chrome.driver", "/Users/we/Downloads/chromedriver");
    //System.setProperty("webdriver.chrome.verboseLogging", "true");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("headless");
    this.webDriver = new ChromeDriver(options);
  }

  @Subscribe
  public void scrapProduct(ScrapProductEvent event) throws InterruptedException, IOException {
    TimeUnit.SECONDS.sleep(1);


    webDriver.get(event.getLink());

    JavascriptExecutor executor = (JavascriptExecutor) webDriver;
    String productName = executor.executeScript("return product_name").toString();
    String expireCheck = executor.executeScript(
        "var t = []; var wonderOptions = optionJsonData.basic[0];  for (var prop in wonderOptions) {t.push(wonderOptions[prop]['sto_state'])}; return t.every(function(state){return state == 'SOLDOUT'});")
        .toString();
    String productPrice = executor.executeScript("return product_price").toString();
    String imageLink = executor.executeScript("return $(\"meta[property='og:image']\").attr('content')").toString();
    String link = event.getLink();

    // 품절 체크
    if (Boolean.valueOf(expireCheck)) {
      log.info("품절 입니당~ {}", link);
      return;
    }

    Product product = Product.builder()
        .id(event.getId())
        .categoryName1(event.getCategory())
        .link(link)
        .title(productName)
        .mobileLink(link)
        .imageLink(imageLink)
        .price(new Price(productPrice))
        .mobilePrice(new Price(productPrice))
        .normalPrice(new Price(productPrice))
        .build();
    log.info("{}", product);
    /*
    String price = document.select("input[name='price']").attr("value");
    Product product = Product.builder()
        .id(event.getId())
        .categoryName1(event.getCategory())
        .link(link)
        .title(productName)
        .mobileLink(link)
        .imageLink("http://" + event.getDomain() + document.select("div.thumb-wrap>.thumb img").attr("src"))
        .price(new Price(price))
        .mobilePrice(new Price(price))
        .normalPrice(new Price(price))
        .build();

    log.info("{}", product);
    */
  }
}
