package util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RandomGeneratorTest {
  // Testing generateRandomString
  @Test
  public void testGenerateRandomString() {
    System.out.println("Run Test generateRandomString");
    String s1 = RandomGenerator.generateRandomString(10);
    String s2 = RandomGenerator.generateRandomString(10);
    String s3 = RandomGenerator.generateRandomString(5);
    Assert.assertTrue(s1.length() == 10 && s2.length() == 10 && s3.length() == 5);
    Assert.assertTrue(!s1.contentEquals(s2));
  }
}
