package graymadonna;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class GrayMadonnaMain {

  public static void main(String[] args) throws Exception {
    int numberOfSrawlers = 1;
    CrawlConfig config = new CrawlConfig();
    config.setMaxDepthOfCrawling(1);
    config.setCrawlStorageFolder("/Users/we/git/javatest/backup");
    config.setUserAgentString("wonder-shopping-crawler");
    config.setMaxPagesToFetch(50);
    config.setPolitenessDelay(2000);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
    controller.addSeed("http://www.graymadonna.com");

    System.out.println("id\ttitle\tprice_pc\tprice_mobile\tnormal_price\tlink\tmobile_link\timage_link\tcategory_name1");

    controller.start(GrayMadonnaCrawler.class, numberOfSrawlers);


  }
}
