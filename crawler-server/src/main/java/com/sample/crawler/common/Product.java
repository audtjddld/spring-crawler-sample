package com.sample.crawler.common;

import lombok.Builder;

@Builder
public class Product {


  private String id;
  private String title;
  private Price price;
  private Price mobilePrice;
  private Price normalPrice;
  private String link;
  private String mobileLink;
  private String categoryName1;
  private String imageLink;

  // "id\ttitle\tprice_pc\tprice_mobile\tnormal_price\tlink\tmobile_link\timage_link\tcategory_name1"
  public String toString() {
    String TAB = "\t";
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
}
