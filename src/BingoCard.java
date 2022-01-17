import java.util.Arrays;

public class BingoCard
{
  // The two arrays are private to enact information hiding, getCardNumbers returns array as string

  private int[][]     numbers;
  private boolean[][] markedOff;
  private int numberOfRows;
  private int numberOfColumns;

  public BingoCard(int numberOfRows, int numberOfColumns)
  {
    setNumberOfRows(numberOfRows);
    setNumberOfColumns(numberOfColumns);

    numbers   = new int[numberOfRows][numberOfColumns];
    markedOff = new boolean[numberOfRows][numberOfColumns];
    resetMarked();
  }

  // Resets structure to false

  public void resetMarked()
  {
    for (boolean[] booleans : markedOff) {
      Arrays.fill(booleans, false);
    }
  }

  public int getNumberOfRows() {return numberOfRows;}
  public void setNumberOfRows(int numberOfRows) {this.numberOfRows = numberOfRows;}
  public int getNumberOfColumns() {return numberOfColumns;}
  public void setNumberOfColumns(int numberOfColumns) {this.numberOfColumns = numberOfColumns;}

  public String getCardNumbers()
  {
    String temp = Arrays.deepToString(numbers).replaceAll("\\[|\\]||\\s", "");
    String temp2;
    temp2 = temp.replace(",", "").trim();
    temp2 = temp2.replace(" ", Defaults.getNumberSeparator());
    return temp2;
  }

  // Sets bingo card numbers in correct grid format

  public void setCardNumbers(String[] numbersAsStrings)
  {
    int[] numbersList = Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();

    for(int i = 0; i < getNumberOfColumns(); i++) {
      for (int j = 0; j < getNumberOfRows(); j++)
        numbers[j][i] = numbersList[(j * getNumberOfColumns()) + i];
    }
  }

  // Marks called numbers from bingo card

  public void markNumber(int number)
  {
    boolean matchFound = false;

    for(int i = 0; i < numbers.length; i++)
    {
      for (int j = 0; j < numbers[0].length; j++)
      {
        if (numbers[i][j] == number)
        {
          System.out.printf("Marked off %d%n", number);
          markedOff[i][j] = true;
          matchFound = true;
        }
      }
    }
    if (!matchFound)
    {
      System.out.printf("Number %d not on this card%n", number);
    }
  }

  // Checks to see if there is a winner

  public boolean isWinner()
  {
    for (int i = 0; i < markedOff.length; i++){
      for (int j = 0; j < markedOff[0].length; j++){
        if (!markedOff[i][j]) return false;
      }
    }
    return true;
  }
}