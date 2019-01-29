package regex;

public class MakeShop {
  public static String MAKE_SHOP = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?(branduid=([0-9]+))";
  public static final String MAKE_SHOP_PAGE_REGEX = "/shop/shopbrand.html\\?type=(X|Y)&xcode=([0-9]{3})&mcode=([0-9]{3})&sort=&page=([0-9]{0,3})";
  // /shop/shopbrand.html?xcode=001&type=X
  public static final String MAKE_SHOP_SHOULD_VISIT_URL_REGEX = "/shop/shopbrand.html\\?xcode=([0-9]{3})&type=(X)";

  private static final String SUB_REGEX = "(type=(X)|xcode=([0-9]{3})|mcode=([0-9]{3})|sort=|page=([0-9]{0,3}))";
  public static final String MAKE_SHOP_PAGE_REGEX2 = "/shop/shopbrand.html\\?" + SUB_REGEX + "&" + SUB_REGEX + "&" + SUB_REGEX;


  /*
  public static void main(String[] args) {
    String url = "http://www.graymadonna.com/shop/shopbrand.html?type=X&xcode=001&sort=&page=4";

    Pattern pattern = Pattern.compile(MAKE_SHOP_PAGE_REGEX2);
    Matcher matcher = pattern.matcher(url);

    if (matcher.find()) {
      System.out.println(matcher.group());
    }
  }
  */
}
