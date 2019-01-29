package makeshop.scraping.event.model;

import java.util.Map;

public class ScrapProductDetailLinkEvent {

  private String domain;
  private String link;
  private Map<String, String> categoryMap;

  public ScrapProductDetailLinkEvent(String domain, String link, Map<String, String> categoryMap) {
    this.domain = domain;
    this.link = link;
    this.categoryMap = categoryMap;
  }

  public String getFullURL() {
    return String.format("http://%s%s", domain, link);
  }

  public String getDomain() {
    return domain;
  }

  public Map<String, String> getCategoryMap() {
    return categoryMap;
  }

  @Override
  public String toString() {
    return "ScrapProductDetailEvent{" +
        "link='" + link + '\'' +
        ", categoryMap=" + categoryMap +
        '}';
  }
}
