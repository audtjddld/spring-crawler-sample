package com.sample.crawler.extractor;


import static com.sample.crawler.kafka.service.KafkaService.kafkaService;

import com.google.common.base.Strings;
import com.sample.common.RegexGenerator;
import com.sample.common.message.ScrapingMessage;
import com.sample.common.regex.MakeShop;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 상품 리스트만 조회
 */
@Slf4j
public class MakeShopExtractor extends WebCrawler {

  // 중복 URL 수집 불가
  private Set<String> menuURLs = new HashSet<>();
  private Set<String> visitURLs = new HashSet<>();
  private Set<String> extractDetailURLs = new HashSet<>();

  private String domain;
  private ExtractedMakeShopProductLink extractedMakeShopProductLink;
  private RegexGenerator regexGenerator;
  private CategoryExtractor categoryExtractor;

  public MakeShopExtractor() {
    this.extractedMakeShopProductLink = new ExtractedMakeShopProductLink();
    this.regexGenerator = new RegexGenerator();
    this.categoryExtractor = new CategoryExtractor();
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

    if (categoryExtractor.isEmpty()) {
      categoryExtractor.extractCategory(referringPage);
      return true;
    }

    if (visitURLs.contains(url.getURL())) {
      return false;
    }

    visitURLs.add(url.getURL());

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
      extractedMakeShopProductLink.matcher(href);
      if (extractedMakeShopProductLink.find()) {
        String link = extractedMakeShopProductLink.group();
        if (!menuURLs.contains(link)) { // 중복 링크 제거
          menuURLs.add(link);

          Elements detailLinks = document.select("a[href^=/shop/shopdetail.html]");
          detailLinks.forEach(detailLink -> {
            String newDetailLink = String.format("http://%s%s", domain, detailLink.attr("href"));
            regexGenerator.setLink(newDetailLink);

            String category = categoryExtractor.getCategory(link);

            String filterLink = regexGenerator.generateLink();

            if (extractDetailURLs.contains(filterLink)) {  // detail 중복 링크 체크
              return;
            }

            extractDetailURLs.add(filterLink);
            log.debug("filter link : {}, category : {}", filterLink, category);

            kafkaService.sendMessage(new ScrapingMessage(domain, filterLink, category));
          });
        }
      }
    });
  }

  @Override
  public void onBeforeExit() {
    super.onBeforeExit();
    log.info("product count : {}", extractDetailURLs.size());
  }
}

class ExtractedMakeShopProductLink {

  private Pattern pattern;
  private Matcher matcher = null;

  ExtractedMakeShopProductLink() {
    pattern = Pattern.compile(MakeShop.MAKE_SHOP_PAGE_REGEX);
  }

  void matcher(String text) {
    this.matcher = pattern.matcher(text);
  }

  boolean find() {
    return this.matcher.find();
  }

  String group() {
    return this.matcher.group();
  }
}