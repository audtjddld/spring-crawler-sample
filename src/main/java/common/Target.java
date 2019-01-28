package common;

import java.util.Map;

public class Target {

  private Hosting hosting;
  private String domain;
  private String subPath;
  private Map<String, String> categoryMap;

  public Target(Hosting hosting, String domain, String subPath, Map<String, String> categoryMap) {
    this.hosting = hosting;
    this.domain = domain;
    this.subPath = subPath;
    this.categoryMap = categoryMap;
  }

  public Hosting getHosting() {
    return hosting;
  }


  public String getDomain() {
    return domain;
  }


  public String getSubPath() {
    return subPath;
  }


  public String getScrapUrl() {
    return getDomain() + getSubPath();
  }

  public Map<String, String> getCategoryMap() {
    return categoryMap;
  }
}
