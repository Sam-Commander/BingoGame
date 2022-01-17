import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Integer.parseInt;

public class BingoController
{
    private final String[] mainMenuItems =
            {"Exit", "Play bingo",
            "Set number separator",
            "Create a bingo card",
            "List existing cards",
            "Set bingo card size"};

    // Constants defined according to assignment specs

    private final String OPTION_EXIT = "0";
    private final String OPTION_PLAY = "1";
    private final String OPTION_SEPARATOR = "2";
    private final String OPTION_CREATE_CARD = "3";
    private final String OPTION_LIST_CARDS = "4";
    private final String OPTION_SIZE = "5";

    private int currentRowSize = Defaults.DEFAULT_NUMBER_OF_ROWS;
    private int currentColumnSize = Defaults.DEFAULT_NUMBER_OF_COLUMNS;

    // An arraylist of all bingo cards active in the game

    ArrayList<BingoCard> bingoCards = new ArrayList<>();

    // Getters and setters

    public int getCurrentRowSize() {return currentRowSize;}
    public void setCurrentRowSize(int currentRowSize) {this.currentRowSize = currentRowSize;}
    public int getCurrentColumnSize() {return currentColumnSize;}
    public void setCurrentColumnSize(int currentColumnSize) {this.currentColumnSize = currentColumnSize;}

    // Adds a bingo card

    public void addNewCard(BingoCard card)
    {
        bingoCards.add(card);
    }

    // MENU OPTION 5 - Sets size of bingo card

    public void setSize()
    {
        setCurrentRowSize(parseInt(Toolkit.getInputForMessage("Enter the number of rows for the card")));
        setCurrentColumnSize(parseInt(Toolkit.getInputForMessage("Enter the number of columns for the card")));
        System.out.printf("The bingo card size is set to %d rows X %d columns%n", getCurrentRowSize(), getCurrentColumnSize());
    }

    // MENU OPTION 3 - Creates a bingo card

    public void createCard()
    {
        int numbersRequired = getCurrentColumnSize() * getCurrentRowSize();

        String[] numbers;

        boolean correctAmountOfNumbersEntered = false;

        do {
            numbers = Toolkit.getInputForMessage(String.format("Enter %d numbers for your card (separated by " + "'%s')",
                    numbersRequired, Defaults.getNumberSeparator())).trim().split(Defaults.getNumberSeparator());

            // Verifies if the correctAmountOfNumbersEntered is true or false depending on the numbersRequired calculation

            int check = numbers.length;

            if (check == numbersRequired)
            {
               correctAmountOfNumbersEntered = true;
            }

            if (check != numbersRequired)
            {
                System.out.printf("Try again: you entered %d numbers instead of %d%n", check, numbersRequired);
                continue;
            }
        } while (!correctAmountOfNumbersEntered);

        System.out.printf("You entered%n" + Toolkit.printArray(numbers).trim() + "%n");

        BingoCard card = new BingoCard(getCurrentRowSize(), getCurrentColumnSize());
        card.setCardNumbers(numbers);
        addNewCard(card);
    }

    // MENU OPTION 4 - Lists the cards

    public void listCards()
    {
        int index = 0;
        for (BingoCard xyz : bingoCards)
        {
            System.out.printf("Card%1$3s numbers:%n", index++);
            printCardAsGrid(xyz.getCardNumbers());
        }
    }

    // Prints card as grid

    public void printCardAsGrid(String numbers)
    {
        String[] array = numbers.split(Defaults.getNumberSeparator());
        int[][] rowsAndColumns = new int[getCurrentRowSize()][getCurrentColumnSize()];
        int[] numbersList = Arrays.stream(array).mapToInt(Integer::parseInt).toArray();

        for(int i = 0; i < getCurrentColumnSize(); i++) {
            for (int j = 0; j < getCurrentRowSize(); j++)
                rowsAndColumns[j][i] = numbersList[(j * getCurrentColumnSize()) + i];}

        String temp = Arrays.deepToString(rowsAndColumns).replaceAll("\\[|\\||\\s", "")
        .replace("]]","")
        .replace("],", "\n")
        .replace(",", Defaults.getNumberSeparator())
        .replaceAll("\\b(\\d)\\b", "x$1")
        .replaceAll("x", " ");
        System.out.println(temp);
    }

    // MENU OPTION 2 - Sets separator to separate numbers on bingo card

    public void setSeparator()
    {
        String sep = Toolkit.getInputForMessage("Enter the new separator");
        Defaults.setNumberSeparator(sep);
        System.out.printf("Separator is " + "'%s'%n", Defaults.getNumberSeparator());
    }

    // Resets all bingo cards

    public void resetAllCards()
    {
        BingoCard reset = new BingoCard(currentRowSize, currentColumnSize);
        reset.resetMarked();
    }

    // Marks called numbers from card

    public void markNumbers(int number)
    {
        int index = 0;
        for (BingoCard xyz : bingoCards)
        {
            System.out.printf("Checking card %d for %d%n", index++, number);
            xyz.markNumber(number);
        }
    }

    // Returns winner Id

    public int getWinnerId()
    {
        int index = -1;

        for (BingoCard xyz : bingoCards)
        {
            index++;
            if (xyz.isWinner() == true)
                return index;
        }
        return Defaults.NO_WINNER;
    }

    // MENU OPTION 1 - Starts game

    public void play()
    {
        System.out.println("Eyes down, look in!");
        resetAllCards();

        boolean weHaveAWinner;

        do {
            markNumbers(parseInt(Toolkit.getInputForMessage("Enter the next number").trim()));
            int winnerID = getWinnerId();
            weHaveAWinner = winnerID != Defaults.NO_WINNER;
            if (weHaveAWinner)
                System.out.printf("And the winner is card %d%n", winnerID);
        } while (!weHaveAWinner);
    }

    // Prints menu

    public String getMenu(String[] menuItems)
    {
        StringBuilder menuText = new StringBuilder();

        for(int i = 0; i < menuItems.length; i++)
        {
            menuText.append(" ").append(i).append(": ").append(menuItems[i]).append("\n");
        }

        return menuText.toString();
    }

    // Runs the menu

    public void run()
    {
        BingoController menuControl = new BingoController();
        boolean finished = false;
        do {
            switch (Toolkit.getInputForMessage(getMenu(mainMenuItems)))
            {
                case OPTION_EXIT:
                    finished = true;
                    break;

                case OPTION_PLAY:
                    menuControl.play();
                    break;

                case OPTION_SEPARATOR:
                    menuControl.setSeparator();
                    break;

                case OPTION_CREATE_CARD:
                    menuControl.createCard();
                    break;

                case OPTION_LIST_CARDS:
                    menuControl.listCards();
                    break;

                case OPTION_SIZE:
                    menuControl.setSize();
                    break;
            }
        } while (!finished);
    }
}