package makeshop;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.time.Duration;
import java.time.Instant;
import makeshop.crawling.MakeShopCrawler;

public class MakeShopMain {

  public static void main(String[] args) throws Exception {
    int numberOfSrawlers = 1;
    CrawlConfig config = new CrawlConfig();

    config.setMaxDepthOfCrawling(15); // depth 가 곧 page number 수 이동도 연관된 것 같다.

    config.setCrawlStorageFolder("~/git/javatest/backup");
    config.setUserAgentString("sample-crawler");
    config.setPolitenessDelay(1500);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    controller.addSeed("http://www.bnbshop.co.kr");
    Instant start = Instant.now();

    // crawler start
    controller.start(MakeShopCrawler.class, numberOfSrawlers);


    Instant end = Instant.now();
    System.out.println(Duration.between(start, end));
  }
}
