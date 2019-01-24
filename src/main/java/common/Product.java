package common;

public class Product {

  private String TAB = "\t";
  private String id;
  private String title;
  private Price price;
  private Price mobilePrice;
  private Price normalPrice;
  private String link;
  private String mobileLink;
  private String categoryName1;
  private String imageLink;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
    this.mobileLink = this.link;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategoryName1() {
    return categoryName1;
  }

  public void setCategoryName1(String categoryName1) {
    if (categoryName1 == null) {
      this.categoryName1 = "CRAWLING";
      return;
    }
    this.categoryName1 = categoryName1;
  }

  public String getMobileLink() {
    return mobileLink;
  }

  // "id\ttitle\tprice_pc\tprice_mobile\tnormal_price\tlink\tmobile_link\timage_link\tcategory_name1"
  public String toString() {
    StringBuilder sb = new StringBuilder();
    return sb.append(id)
        .append(TAB)
        .append(title)
        .append(TAB)
        .append(price.getPrice())
        .append(TAB)
        .append(normalPrice.getPrice())
        .append(TAB)
        .append(mobilePrice.getPrice())
        .append(TAB)
        .append(link)
        .append(TAB)
        .append(mobileLink)
        .append(TAB)
        .append(imageLink)
        .append(TAB)
        .append(categoryName1)
        .toString();
  }

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = price;
    this.mobilePrice = price;
    this.normalPrice = price;
  }

  public Price getMobilePrice() {
    return mobilePrice;
  }

  public Price getNormalPrice() {
    return normalPrice;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
}
