package com.sample.crawler.parser;


import com.google.common.base.Strings;
import com.sample.common.regex.MakeShop;
import com.sample.crawler.factory.CrawlerFactory;
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
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MakeShopCrawler extends WebCrawler {

  // 중복 URL 수집 불가
  private Set<String> menuURLs = new HashSet<>();
  private Set<String> preventURLs = new HashSet<>();
  private Map<String, String> categoryMap = new HashMap<>();
  private String domain;
  private MakeShopProductListScrap makeShopProductListScrap;

  @Autowired
  public MakeShopCrawler(CrawlerFactory crawlerFactory) {

    this.makeShopProductListScrap = new MakeShopProductListScrap();
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

    String domain = page.getWebURL().getDomain();

    elements.forEach(e -> {
      String href = e.attr("href");
      makeShopProductListScrap.matcher(href);
      if (makeShopProductListScrap.find()) {
        String link = makeShopProductListScrap.group();
        if (!menuURLs.contains(link)) {
          menuURLs.add(link);
          //asyncEventBus.post(new ScrapProductDetailLinkEvent(domain, link, categoryMap));
          //TODO kafka
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

class MakeShopProductListScrap {

  private Pattern pattern;
  private Matcher matcher = null;

  public MakeShopProductListScrap() {
    pattern = Pattern.compile(MakeShop.MAKE_SHOP_PAGE_REGEX);
  }

  public void matcher(String text) {
    this.matcher = pattern.matcher(text);
  }

  public boolean find() {
    return this.matcher.find();
  }

  public String group() {
    return this.matcher.group();
  }
}