package makeshop.scraping.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import common.CrawlerEventBus;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import makeshop.regex.RegexGenerator;
import makeshop.scraping.event.model.ScrapProductDetailLinkEvent;
import makeshop.scraping.event.model.ScrapProductEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class ScrapProductDetailEventLinkListener {

  private RegexGenerator regexGenerator;
  private Set<String> previousVisitURLs;
  private AsyncEventBus asyncEventBus;

  public ScrapProductDetailEventLinkListener() {
    previousVisitURLs = new HashSet<>();
    regexGenerator = new RegexGenerator();
    asyncEventBus = CrawlerEventBus.getInstance();
  }

  //TODO detail page link 분리 후 detail page 내 form tag의 정보를 크롤링
  @Subscribe
  public void scrap(ScrapProductDetailLinkEvent event) throws IOException, InterruptedException {
    //    LOG.info("{} : {}", Thread.currentThread().getName(), event.getFullURL());

    TimeUnit.MILLISECONDS.sleep(1500);

    Document doc = Jsoup.connect(event.getFullURL()).get();

    Elements elements = doc.select("a[href^=/shop/shopdetail.html]");
    elements.forEach(e -> {
      String link = String.format("http://%s%s", event.getDomain(), e.attr("href"));
      //LOG.info("link : {}", link);
      regexGenerator.setLink(link);

      String filterLink = regexGenerator.generateLink();
      //LOG.info("filter link : {}", filterLink);

      if (previousVisitURLs.contains(filterLink)) {
        return;
      }

      previousVisitURLs.add(filterLink);

      //log.info("link : {}, category: {}, id : {}", regexGenerator.generateLink(), regexGenerator.generateCategory(), regexGenerator.generateId());

      asyncEventBus.post(new ScrapProductEvent(event.getDomain(),"a", regexGenerator.generateId(), regexGenerator.generateLink(), event.getCategoryMap().get(regexGenerator.generateCategory())));

    });
  }
}
