package makeshop.scraping;

import static makeshop.regex.MakeShop.MAKE_SHOP_PAGE_REGEX;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeShopProductListScrap {

  private Pattern pattern;
  private Matcher matcher = null;

  public MakeShopProductListScrap() {
    pattern = Pattern.compile(MAKE_SHOP_PAGE_REGEX);
  }

  public void matcher(String text) {
    this.matcher = pattern.matcher(text);
  }

  public boolean find() {
    return this.matcher.find();
  }

  public String group() {
    return this.matcher.group();
  }
}
