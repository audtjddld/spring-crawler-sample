package makeshop.scraping.event.model;

import java.util.Map;

public class ScrapProductDetailEvent {

  private String url;
  private Map<String, String> categoryMap;

  public ScrapProductDetailEvent(String url, Map<String, String> categoryMap) {
    this.url = url;
    this.categoryMap = categoryMap;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, String> getCategoryMap() {
    return categoryMap;
  }

  @Override
  public String toString() {
    return "ScrapProductDetailEvent{" +
        "url='" + url + '\'' +
        ", categoryMap=" + categoryMap +
        '}';
  }
}
