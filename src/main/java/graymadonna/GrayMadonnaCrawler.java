package graymadonna;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import regex.IDPattern;
import regex.URLPattern;

public class GrayMadonnaCrawler extends WebCrawler {

  private String host = "http://www.graymadonna.com";
  private static String TAB = "\t";

  // 중복 URL 수집 불가
  private Set<String> urls = new HashSet<>();

  public GrayMadonnaCrawler() {
    logger.info("new");
  }

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL();
    boolean result = false;
    if (href.startsWith(host + "/shop/shopbrand.html")) {
      result = true;
      //logger.info("url : {}", href);
    }
    return result;
  }

  @Override
  public void visit(Page page) {
    String idRegex = IDPattern.GRAY_MADONNA;

    HtmlParseData data = (HtmlParseData) page.getParseData();

    Pattern idPattern = Pattern.compile(idRegex);
    Pattern urlPattern = Pattern.compile(String.format(URLPattern.URL_AND_ID, idRegex));

    Document doc = Jsoup.parse(data.getHtml());
    Elements elements = doc.select(".item-wrap>.item-cont>.item-list");

    // 할인 가격 삭제
    elements.select(".consumer_p").remove();
    // 상단 plus item 삭제
    // elements.select("div.prd-list").remove();

    Matcher matcher = null;
    StringBuilder sb = null;
    for (Element element : elements) {
      sb = new StringBuilder();

      String link = host + element.getElementsByTag("a").attr("href");
      String image = host + element.getElementsByTag("img").attr("src");
      String title = element.select(".prd-name").text();
      String price = element.select(".prd-price").text().replace(",", "").replace("원", "");

      Matcher m = urlPattern.matcher(link);
      link = m.find() ? m.group() : link;

      if (urls.contains(link)) {
        continue;
      }

      urls.add(link);

      if (title != null && title.indexOf("품절") > 0) {
        continue;
      }

      matcher = idPattern.matcher(link);
      sb.append(matcher.find() ? matcher.group(2) : null)
          .append(TAB)
          .append(title)
          .append(TAB)
          .append(price)
          .append(TAB)
          .append(price)
          .append(TAB)
          .append(price)
          .append(TAB)
          .append(link)
          .append(TAB)
          .append(link)
          .append(TAB)
          .append(image)
          .append(TAB)
          .append("CRAWLING")
          .append(TAB);

      System.out.println(sb.toString());
    }
  }
}
