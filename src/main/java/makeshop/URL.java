package makeshop;

public class URL {

  private String host;
  private String subURL;
  private String combineUrl;

  public URL(String parentUrl, String subUrl) {
    this.host = parentUrl.substring(0, parentUrl.length()-1);
    this.subURL = subUrl;
  }

  public String getCombineUrl() {
    return host + subURL;
  }

  public String getHost() {
    return host;
  }

  public String getSubURL() {
    return subURL;
  }
}
