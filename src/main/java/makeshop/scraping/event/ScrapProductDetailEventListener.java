package makeshop.scraping.event;

import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import makeshop.scraping.event.model.ScrapProductDetailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProductDetailEventListener {

  public static final Logger LOG = LoggerFactory.getLogger(ScrapProductDetailEventListener.class);
  private Set<String> previousVisitURLs;

  public ScrapProductDetailEventListener() {
    previousVisitURLs = new HashSet<>();
  }

  //TODO detail page link 분리 후 detail page 내 form tag의 정보를 크롤링
  @Subscribe
  public void scrap(ScrapProductDetailEvent event) throws IOException {
    LOG.info("{}", event);

    //Document doc = Jsoup.connect(event).get();

    //Elements elements = doc.select("a[href^=brandDetail]");

  }
}
