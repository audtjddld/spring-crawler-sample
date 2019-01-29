package makeshop.scraping.event;

import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import makeshop.regex.RegexGenerator;
import makeshop.scraping.event.model.ScrapProductDetailLinkEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProductDetailEventLinkListener {

  public static final Logger LOG = LoggerFactory.getLogger(ScrapProductDetailEventLinkListener.class);
  private RegexGenerator regexGenerator;
  private Set<String> previousVisitURLs;

  public ScrapProductDetailEventLinkListener() {
    previousVisitURLs = new HashSet<>();
    regexGenerator = new RegexGenerator();
  }

  //TODO detail page link 분리 후 detail page 내 form tag의 정보를 크롤링
  @Subscribe
  public void scrap(ScrapProductDetailLinkEvent event) throws IOException {
    LOG.info("{} : {}", Thread.currentThread().getName(), event.getFullURL());

    Document doc = Jsoup.connect(event.getFullURL()).get();

    Elements elements = doc.select("a[href^=/shop/shopdetail.html]");
    elements.forEach(e -> {
      String link = String.format("http://%s%s", event.getDomain(), e.attr("href"));
      LOG.info("link : {}", link);
      regexGenerator.setLink(link);

      String filterLink = regexGenerator.generateLink();
      LOG.info("filter link : {}", filterLink);

      if (previousVisitURLs.contains(filterLink)) {
        return;
      }

      LOG.info("link : {}, category: {}, id : {}", regexGenerator.generateLink(), regexGenerator.generateCategory(), regexGenerator.generateId());

    });
  }
}
