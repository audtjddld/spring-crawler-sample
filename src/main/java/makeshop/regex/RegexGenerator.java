package makeshop.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 정규식을 이용한 생성기
 */
public class RegexGenerator {
  private Pattern idPattern;
  private Pattern urlPattern;
  private Pattern categoryPattern;

  public RegexGenerator(String idRegex, String urlRegex, String categoryRegex) {
    idPattern = Pattern.compile(idRegex);
    urlPattern = Pattern.compile(urlRegex);
    categoryPattern = Pattern.compile(categoryRegex);
  }

  public String generateId(String text) {
    Matcher matcher = idPattern.matcher(text);
    return matcher.find() ? matcher.group(2) : null;
  }

  public String generateUrl(String text) {
    Matcher matcher = urlPattern.matcher(text);
    return matcher.find() ? matcher.group(2) : null;
  }

  public String generateCategory(String text) {
    Matcher matcher = categoryPattern.matcher(text);
    return matcher.find() ? matcher.group(2) : null;
  }
}
