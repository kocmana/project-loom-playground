package at.kocmana.loomplayground.structuredconcurrency.support;

import java.util.Random;

public class MockMethods {

  private MockMethods() {}

  private static final Random RANDOM = new Random();

  private static void sleepForRandomDuration() {
    var duration = RANDOM.nextLong(5000);
    sleepFor(duration);
  }

  private static void sleepFor(long durationInMs) {
    try {
      Thread.sleep(durationInMs);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static String failUserRequest() {
    throw new RuntimeException("Something went wrong");
  }

  public static int failOrderRequest() {
    throw new RuntimeException("Something went wrong");
  }

  public static String findUser() {
    MockMethods.sleepForRandomDuration();
    return "foo";
  }

  public static int fetchOrder() {
    MockMethods.sleepForRandomDuration();
    return 1;
  }

}
