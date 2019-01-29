package makeshop.regex;

public class MakeShop {

  public static final String PRODUCT_LINK_REGEX = "^(https?|http?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?(branduid=([0-9]+))";

  public static final String MAKE_SHOP_PAGE_REGEX = "/shop/shopbrand.html\\?type=(X|Y)&xcode=([0-9]{3})&mcode=([0-9]{3})&sort=&page=([0-9]{0,3})";

  public static final String CATEGORY_REGEX = "(xcode=([0-9]+))";

  public static final String BRAND_UID_REGEX = "(branduid=([0-9]+))";

  //private static final String SUB_REGEX = "(type=(X)|xcode=([0-9]{3})|mcode=([0-9]{3})|sort=|page=([0-9]{0,3}))";
  //public static final String MAKE_SHOP_PAGE_REGEX2 = "/shop/shopbrand.html\\?" + SUB_REGEX + "&" + SUB_REGEX + "&" + SUB_REGEX;
}
