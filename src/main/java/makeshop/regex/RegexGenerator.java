package makeshop.regex;

import static makeshop.regex.MakeShop.BRAND_UID_REGEX;
import static makeshop.regex.MakeShop.CATEGORY_REGEX;
import static makeshop.regex.MakeShop.PRODUCT_LINK_REGEX;

import com.google.common.base.Strings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 정규식을 이용한 생성기
 */
public class RegexGenerator {

  private Pattern idPattern;
  private Pattern linkPattern;
  private Pattern categoryPattern;
  private String link;

  public RegexGenerator() {
    idPattern = Pattern.compile(BRAND_UID_REGEX);
    linkPattern = Pattern.compile(PRODUCT_LINK_REGEX);
    categoryPattern = Pattern.compile(CATEGORY_REGEX);
  }

  public void setLink(String link) {
    if (Strings.isNullOrEmpty(link)) {
      throw new IllegalArgumentException("link is null or empty!!");
    } else {
      this.link = link;
    }
  }

  public String generateId() {
    Matcher matcher = idPattern.matcher(link);
    return matcher.find() ? matcher.group(2) : null;
  }

  public String generateLink() {
    Matcher matcher = linkPattern.matcher(link);
    return matcher.find() ? matcher.group() : null;
  }

  public String generateCategory() {
    Matcher matcher = categoryPattern.matcher(link);
    return matcher.find() ? matcher.group(2) : null;
  }
}
