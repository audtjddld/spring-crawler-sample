package com.sample.crawler.extractor;

import com.google.common.base.Strings;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class CategoryExtractor {

  private Map<String, String> categoryMap = new HashMap<>();
  private Pattern pattern;
  private Matcher codeMatcher;
  public CategoryExtractor() {
    this.pattern = Pattern.compile("(xcode=([0-9]+))");
  }

  /**
   * 상품에 맵핑 시키기 위한 카테고리 정보를 생성한다.
   */
  public void extractCategory(Page page) {
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
        log.info("category map : {}", categoryMap);
        //TODO 추출된 category map은 별도의 저장소에 저장 한다 or 휘발성으로 가지고 있는다.
      }
    }
  }

  boolean isEmpty() {
    return this.categoryMap.isEmpty();
  }

  String getCategory(String link) {

    codeMatcher = pattern.matcher(link);

    String code = codeMatcher.find() ? codeMatcher.group(2) : "NotFound";

    return this.categoryMap.getOrDefault(code, "Crawling");
  }
}
