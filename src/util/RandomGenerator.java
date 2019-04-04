package util;

import java.util.Random;

/**
 * This class is a utility class to generate random strings
 * @author Alec Agnese, Rami El Khatib
 */
public class RandomGenerator {
  private RandomGenerator() {
    characters = new char[NUM_CHAR];
    //integers ascii
    for(int i=0;i<10;i++) {
      characters[i] = (char) (48 + i);
    }
    
    //uppercase letters ascii
    for(int i=0;i<26;i++) {
      characters[i] = (char) (65 + i);
    }
    
    //lowercase letters ascii
    for(int i=0;i<26;i++) {
      characters[i] = (char) (97 + i);
    }
  }
  
  /**
   * Generates a string of random alphanumeric characters with a specific length
   * @param length Length of the string to generate
   * @return String of random alphanumeric string
   */
  public static String generateRandomString(int length) {
    Random random = new Random();
    StringBuilder builder = new StringBuilder(length);
    for(int i = 0;i < length; i++) {
      int randomInteger = random.nextInt(NUM_CHAR);
      builder.append(instance.characters[randomInteger]);
    }
    return builder.toString();
  }
  
  private static final RandomGenerator instance = new RandomGenerator();
  
  //Array of ascii of allowed characters
  private final char[] characters;
  //Total number of character choices(0-9,a-z,A-Z)
  private static final int NUM_CHAR = 62;
}
