package makeshop;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MakeShopMain {

  public static void main(String[] args) throws Exception {
    int numberOfSrawlers = 1;
    CrawlConfig config = new CrawlConfig();
    // depth 가 곧 page number 수 이동도 연관된 것 같다.
    config.setMaxDepthOfCrawling(15);
    config.setCrawlStorageFolder("~/git/javatest/backup");
    config.setUserAgentString("sample-crawler");
    //config.setMaxPagesToFetch(100);
    config.setPolitenessDelay(1500);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
//    controller.addSeed("http://www.graymadonna.com");
    //controller.addSeed("http://www.heodak.com/");
//    controller.addSeed("http://www.ccoma-i.com");
    controller.addSeed("http://www.gozip28.com/");

    controller.start(MakeShopCrawler.class, numberOfSrawlers);

  }
}
