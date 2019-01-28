package scraping;

import common.JobQueue;
import common.Price;
import common.Product;
import common.Target;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import regex.CategoryPattern;
import regex.IDPattern;
import regex.MakeShop;
import regex.RegexGenerator;

public class MakeShopScrap implements Runnable {

  private JobQueue jobQueue;

  public MakeShopScrap() {
    System.out.println("start scrap job");
    this.jobQueue = JobQueue.getInstance();
  }

  @Override
  public void run() {
    try {
      Target target = jobQueue.dequeue();

      Document document = Jsoup.connect(target.getScrapUrl()).get();

      // recursive
      Elements elements = document.select("a[href^=/shop/shopdetail.html]");

      elements.forEach(e -> {
        String url = e.attr("href");
        Document doc = null;
        try {
          //System.out.println("url : " + "http://" + target.getDomain() + url);
          doc = Jsoup.connect( target.getDomain() + url).get();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        RegexGenerator regexGenerator = new RegexGenerator(IDPattern.MAKE_SHOP, MakeShop.MAKE_SHOP, CategoryPattern.MAKE_SHOP);
        String id = regexGenerator.generateId(url);
        Product product = new Product();

        product.setId(id);
        product.setLink(target.getDomain() + url);
        product.setTitle(doc.select("h3.tit-prd").text());
        product.setPrice(new Price(doc.select("input[name='price']").attr("value")));
        product.setImageLink(target.getDomain() + doc.select("div.thumb-wrap>.thumb img").attr("src"));
        product.setCategoryName1(target.getCategoryMap().get(regexGenerator.generateCategory(url)));

        //logger.info("{}", product);
        System.out.println(product.toString());
      });
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }
}