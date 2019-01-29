package makeshop.crawling;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import makeshop.scraping.MakeShopProductListScrap;
import makeshop.scraping.event.ScrapProductDetailEventListener;
import makeshop.scraping.event.model.ScrapProductDetailEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MakeShopCrawler extends WebCrawler {

  // 중복 URL 수집 불가
  private Set<String> menuURLs = new HashSet<>();
  private Set<String> preventURLs = new HashSet<>();
  private Map<String, String> categoryMap = new HashMap<>();
  private String domain;
  private MakeShopProductListScrap makeShopProductListScrap;
  private EventBus eventBus;


  public MakeShopCrawler() {
    logger.info("new");
    makeShopProductListScrap = new MakeShopProductListScrap();
    eventBus = new EventBus();
    eventBus.register(new ScrapProductDetailEventListener());
  }

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {

    if (Strings.isNullOrEmpty(domain)) {
      domain = referringPage.getWebURL().getDomain();
      logger.info("domain : {}", domain);
    }

    if (!url.getDomain().equals(domain)) {
      return false;
    }

    if (categoryMap.size() == 0) {
      collectCategory(referringPage);
      return true;
    }

    if (preventURLs.contains(url.getURL())) {
      return false;
    }
    preventURLs.add(url.getURL());

    return url.getPath().startsWith("/shop/shopbrand.html");
  }

  @Override
  public void visit(Page page) {

    // html 파싱
    HtmlParseData data = (HtmlParseData) page.getParseData();
    Document document = Jsoup.parse(data.getHtml());

    // 페이지 링크 및 detail page를 추출한다.
    Elements elements = document.select("a[href^=/shop/shopbrand.html]");

    //logger.info("visit url : {}", url);
    String domain = page.getWebURL().getDomain();
    elements.forEach(e -> {
      String href = e.attr("href");
      makeShopProductListScrap.matcher(href);
      if (makeShopProductListScrap.find()) {
        String url = makeShopProductListScrap.group();
        if (!menuURLs.contains(url)) {
          // 페이지 detail link 추출.
//          logger.info("visit : {}", url);
          // queue 저장.
          menuURLs.add(url);
          // detail page 링크 파싱 후 처리.
          eventBus.post(new ScrapProductDetailEvent("http://" + domain, categoryMap));
        }
      }
    });
  }

  /**
   * 상품에 맵핑 시키기 위한 카테고리 정보를 생성한다.
   */
  private void collectCategory(Page page) {
    if (categoryMap.size() == 0) {
      HtmlParseData data = (HtmlParseData) page.getParseData();
      //logger.info("data {}", data);
      if (data != null) {
        Document document = null;
        try {
          document = Jsoup.connect(page.getWebURL().toString()).get();
        } catch (IOException e) {
          e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("(xcode=([0-9]+))");
        Matcher codeMatcher;
        assert document != null;

        Elements elements = document.select("a[href^=/shop/shopbrand.html]");
        for (Element el : elements) {
          //System.out.println(el);
          if (!Strings.isNullOrEmpty(el.text()) && el.attr("href").indexOf("mcode") <= 0) {
            String link = el.attr("href");
            codeMatcher = pattern.matcher(link);
            String key = codeMatcher.find() ? codeMatcher.group(2) : "Crawling";
            String categoryName = el.text();
            categoryMap.put(key, categoryName);
          }
        }
        logger.info("category map : {}", categoryMap);
      }
    }
  }
}