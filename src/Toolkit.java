import java.util.Scanner;

public class Toolkit
{
  private static final Scanner stdIn = new Scanner(System.in);
  public static final String GOODBYEMESSAGE = "Thank you for playing";

  // Gets input for message

  public static String getInputForMessage(String message)
  {
    System.out.println(message);
    String output = stdIn.nextLine().trim();
    return output;
  }

  // Prints current bingo card numbers

  public static String printArray(String[] array)
  {
    StringBuilder sb = new StringBuilder();
    String commaSpace = "";

    for (String i : array)
    {
      sb.append(commaSpace).append(i);
      commaSpace = ", ";
    }
    return sb.toString();
  }
}
