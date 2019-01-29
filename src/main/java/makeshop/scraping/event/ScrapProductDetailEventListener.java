package makeshop.scraping.event;

import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapProductDetailEventListener {

  public static final Logger LOG = LoggerFactory.getLogger(ScrapProductDetailEventListener.class);
  private Set<String> previousVisitURLs;

  public ScrapProductDetailEventListener() {
    previousVisitURLs = new HashSet<>();
  }

  @Subscribe
  public void scrap(String event) throws IOException {
    LOG.info("{}", event);

    Document doc = Jsoup.connect(event).get();

    Elements elements = doc.select("a[href^=brandDetail]");

    for (Element element : elements) {
      
    }

  }
}
