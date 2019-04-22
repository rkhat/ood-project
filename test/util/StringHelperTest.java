package util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class StringHelperTest {
  // Testing checkAlphaNumeric
  @Test
  public void testCheckAlphaNumeric() {
    System.out.println("Run Test checkAlphaNumeric");
    String s = "abc123";
    // test with 0 for minLength, disregards length, should return true
    Assert.assertTrue(StringHelper.checkAlphaNumeric(s, 0));
    // test with 7 for minLength, should return false
    Assert.assertTrue(!StringHelper.checkAlphaNumeric(s, 7));
    // test with matchLength true and minLength 6, should return true
    Assert.assertTrue(StringHelper.checkAlphaNumeric(s, 6, true));
    // test with matchLength true and minLength 4, should return false
    Assert.assertTrue(!StringHelper.checkAlphaNumeric(s, 4, true));
    // test with non alphanumeric characters, should return false
    s = "a.b.c.1.2.3!";
    Assert.assertTrue(!StringHelper.checkAlphaNumeric(s, 0));
  }
}
