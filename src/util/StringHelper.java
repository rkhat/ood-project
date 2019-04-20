package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to check that strings satisfy certain conditions.
 * 
 * @author Alec Agnese, Rami El Khatib
 *
 */
public class StringHelper {
  /**
   * Checks if string is alphanumeric with minimum of minLength characters.
   * 
   * @param str       The string.
   * @param minLength The minimum length of the string.
   * @return true if valid, false otherwise.
   */
  public static boolean checkAlphaNumeric(String str, int minLength) {
    if (str.length() < minLength)
      return false;
    // check if alphanumeric
    String regex = "^[a-zA-Z0-9]+$";
    Pattern pattern = Pattern.compile(regex);
    Matcher strMatcher = pattern.matcher(str);
    if (!strMatcher.matches())
      return false;

    return true;
  }

  /**
   * Checks if string is alphanumeric with minimum of minLength characters. If
   * matchLength is true, it checks if the string has exactly minLength
   * characters.
   * 
   * @param str         The string.
   * @param minLength   The minimum length of the string.
   * @param matchLength true if string should have exactly minLength characters,
   *                    false otherwise.
   * @return            true if valid, false otherwise.
   */
  public static boolean checkAlphaNumeric(String str, int minLength,
      boolean matchLength) {
    if (matchLength)
      return ((str.length() == minLength) && checkAlphaNumeric(str, 1));
    return checkAlphaNumeric(str, minLength);
  }

}
