package makeshop;

import com.google.common.base.Strings;
import common.Price;
import common.Product;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import regex.CategoryPattern;
import regex.IDPattern;
import regex.RegexGenerator;
import regex.URLPattern;

public class MakeShopCrawler extends WebCrawler {

  private String host = "http://www.graymadonna.com";

  // 중복 URL 수집 불가
  private Set<String> urls = new HashSet<>();
  private Map<String, String> categoryMap = new HashMap<>();

  public MakeShopCrawler() {
    logger.info("new");
  }

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL();

    if (categoryMap.size() == 0) {
      Document document = null;
      try {
        document = Jsoup.connect(href).get();
      } catch (IOException e) {
        e.printStackTrace();
      }

      Pattern pattern = Pattern.compile("(xcode=([0-9]+))");
      Matcher codeMatcher;
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
    }
    return href.startsWith(host + "/shop/shopdetail.html");
  }

  @Override
  public void visit(Page page) {
    HtmlParseData data = (HtmlParseData) page.getParseData();

    logger.info("page url : {}", page.getWebURL());

    Document doc = Jsoup.parse(data.getHtml());

    //Elements elements = doc.select("form[name='form1']>input[type='hidden']");

    // branduid   상품 ID
    // xcode      카테고리 ID
    // disprice_wh 가격 정보
    // h3.tit-prd 상품명
    // link       상품 링크
    // div.thumb  이미지
    // 품절 체크 div.prd-btns에 버튼이 없으면 품절.

    // 1. 품절 체크
    Elements element = doc.select("div.prd-btns");
    if (element.size() == 0) {
      logger.error("품절상품 입니다.");
      return;
    }

    // 2. 중복 체크
    RegexGenerator regexGenerator = new RegexGenerator(IDPattern.MAKE_SHOP, URLPattern.MAKE_SHOP, CategoryPattern.MAKE_SHOP);
    String url = page.getWebURL().toString();
    if (urls.contains(regexGenerator.generateUrl(url))) {
      return;
    }

    urls.add(regexGenerator.generateUrl(url));

    Product product = new Product();
    product.setId(regexGenerator.generateId(url));
    product.setLink(regexGenerator.generateUrl(url));
    product.setTitle(doc.select("h3.tit-prd").text());
    product.setPrice(new Price(doc.select("input[name='price']").text()));
    product.setImageLink(doc.select("div.thum>img").attr("href"));
    product.setCategoryName1(categoryMap.get(regexGenerator.generateCategory(url)));

    logger.info("{}", product);
  }


   /*/
  public static void main(String[] args) throws IOException {
    // ^(https?):\/\/([^:\/\s]+)(:([^\/]*))?((\/[^\s/\/]+)*)?\/?%s
    // (branduid=([0-9]+))
    Document doc = Jsoup.connect("http://www.graymadonna.com").get();
    Pattern pattern = Pattern.compile("(xcode=([0-9]+))");
    Matcher codeMatcher;
    Elements elements = doc.select("a[href^=/shop/shopbrand.html]");
    for (Element el : elements) {
      //System.out.println(el);
      if (!Strings.isNullOrEmpty(el.text()) && el.attr("href").indexOf("mcode") <= 0) {
        String link = el.attr("href");
        codeMatcher = pattern.matcher(link);
        String code = codeMatcher.find() ? codeMatcher.group(2) : "Crawling";
        String categoryName = el.text();
        System.out.println("code : " + code + ", " + categoryName);
      }
    }

    String url = "/shop/shopbrand.html?xcode=001&type=X&mcode=001";

    Pattern pattern = Pattern.compile("^(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?(xcode=([0-9]+))");
    Matcher matcher = pattern.matcher(url);

    System.out.println(matcher.find() ? matcher.group() : "없음");
  }
  /**/
}