package graymadonna;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GrayMadonnaCrawler extends WebCrawler {
  private String host = "http://www.graymadonna.com";
  private static String TAB = "\t";
  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL();

    return href.startsWith(host + "/shop/shopbrand.html");
  }

  @Override
  public void visit(Page page) {
    HtmlParseData data = (HtmlParseData) page.getParseData();
    Document doc = Jsoup.parse(data.getHtml());
    Elements elements = doc.select(".item-wrap>.item-cont>.item-list");

    // 할인 가격 삭제
    elements.select(".consumer_p").remove();
    elements.select("div.prd-list").remove();

    Pattern pattern = Pattern.compile("(branduid=([0-9]+))");
    Matcher matcher = null;
    StringBuilder sb = null;
    for (Element element : elements) {
      sb = new StringBuilder();

      String link = host + element.getElementsByTag("a").attr("href");
      String image = host + element.getElementsByTag("img").attr("src");
      String title = element.select(".prd-name").text();
      String price = element.select(".prd-price").text().replace(",", "").replace("원", "");

      if (title != null && title.indexOf("품절") > 0) {
        continue;
      }

      matcher = pattern.matcher(link);
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
