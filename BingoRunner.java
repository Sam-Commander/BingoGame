public class BingoRunner
{
  public static void main(String[] args)
  {

    // Starts the game

    BingoController bc = new BingoController();
    bc.run();
    System.out.println(Toolkit.GOODBYEMESSAGE);
  }
}