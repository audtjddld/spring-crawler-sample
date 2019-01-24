package common;

public class Price {

  public Price(String text) {
    this.price = text.replaceAll(",", "");
  }

  private String price;

  @Override
  public String toString() {
    return "Price{" +
        "price='" + price + '\'' +
        '}';
  }
}
