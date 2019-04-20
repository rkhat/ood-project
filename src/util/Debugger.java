package util;

public class Debugger {
  public static void printLineNumber(String msg) {
    String className = Thread.currentThread().getStackTrace()[2].getClassName();
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
    System.out.println(className + "." + methodName + "-" + lineNumber + ": " + msg);
  }
}
