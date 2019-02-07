package com.sample.common;

public class Price {

  private String price;

  public Price(String text) {
    this.price = text.replaceAll(",", "");
  }

  @Override
  public String toString() {
    return "Price{" +
        "price='" + price + '\'' +
        '}';
  }

  public String getPrice() {
    return price;
  }
}
