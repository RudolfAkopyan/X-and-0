// Gamer always plays first
// Gamer always puts  "X" when bot puts "0"
// Bot choose empty plays
// index colums start from 0;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int ROW_COUNT = 3;
    private static int COL_COUNT = 3;
    private static String CELL_STATE_EMPTY = " ";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_0 = "O";
    private static Random random = new Random();

    private static String GAME_STATE_X_WON = "X won!";

    private static String GAME_STATE_0_WON = " won!";

    private static String GAME_STATE_DRAW = "Draw";

    private static String GAME_STATE_NOT_FINISHED = "Game is not Finished";

    private static Scanner scanner = new Scanner (System.in);

    public static void main(String[] args) {

        while (true) {
            startGameRound();
            System.out.println( "wer");
        }
    }

    public static void startGameRound() {
        System.out.println();
        System.out.println("Start of new round");
        String[][] board = createBoard();
        startGameLoop(board);

    }
    public static String[][] createBoard() {
        String[][] board = new String[ROW_COUNT][COL_COUNT];
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                board[row][col] = CELL_STATE_EMPTY;
            }
        }
        return board;
    }
    public static void startGameLoop(String[][] board) {
        boolean playerTurn = true;

        do {
            if (playerTurn) {


                makePlayerTurn(board);
                printBoard(board);
            } else {
                makeBotTurn(board);
                printBoard(board);
            }
            playerTurn = !playerTurn;

            System.out.println();

            String gameState = CheckGameState(board);
            if (!Objects.equals(gameState, GAME_STATE_NOT_FINISHED)) {
                System.out.println(gameState);
                return;
            }

        }   while (true);

    }
    public static void makePlayerTurn(String[][] board) {
        //get input
        int[] coordinates = inputCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_X;
        // validate input
        // place x on board
    }

    public static int[] inputCellCoordinates(String[][] board) {
        System.out.println("Please type coordinates from 0 to 2 and use space beetwen them");
        //exception don't check for Space and correct nums
       do {
           String[] input = scanner.nextLine().split(" ");

           int row = Integer.parseInt(input[0]);
           int col = Integer.parseInt(input[1]);

           if ((row < 0) || (row >= ROW_COUNT) || (col >= COL_COUNT)) {
               System.out.println("Wrong number! Please type num from 0 to 2");
           } else if (!Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
               System.out.println("This cell already has number");
           } else {
               return new int[]{row, col};
           }
       } while(true);

        }

        //makeBot turn
    public static void makeBotTurn(String[][] board){
        System.out.println("bot's move");
        int[] coordinates = getRandomEmptyCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_0;
    }
    public static int[] getRandomEmptyCellCoordinates(String[][] board) {
        do {
            int row = random.nextInt(ROW_COUNT);
            int col = random.nextInt(COL_COUNT);
            //empty
            if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                return new int[] {row, col};
            }
        } while (true);
        //get random empty cell
        //place 0 on board
    }
    public static String CheckGameState(String[][] board) {
        ArrayList<Integer> sums = new ArrayList<>();

        for (int row = 0; row < ROW_COUNT; row++){
            int rowSum = 0;
            for (int col = 0; col < COL_COUNT; col++){
                rowSum += calculateNumValue(board[row][col]);
            }
            sums.add(rowSum);
        }
         for(int col = 0; col< COL_COUNT; col++){
             int colSum = 0;
             for (int row = 0; row < ROW_COUNT; row++){
                 colSum += calculateNumValue(board[row][col]);
             }
             sums.add(colSum);
         }

        //diaganal from top-left to bottom-right
        int leftDiagonalSum = 0;
        for (int  i = 0; i < ROW_COUNT; i++)
        {
            leftDiagonalSum += calculateNumValue(board[i][i]);
        }
        sums.add(leftDiagonalSum);
        //diaganal from top-right to bottom-left
        int rightDiagonalSum = 0;
        for (int  i = 0; i < ROW_COUNT; i++)
        {
            leftDiagonalSum += calculateNumValue(board[i][(ROW_COUNT - 1) - i]);
        }
        sums.add(rightDiagonalSum);

        if (sums.contains(3)) {
            return GAME_STATE_X_WON;
        } else if (sums.contains(-3)) {
            return GAME_STATE_0_WON;
        }   else if  (areAllCellsTaken(board)) {
            return  GAME_STATE_DRAW;
        }   else {
            return GAME_STATE_NOT_FINISHED;
        }
    }

    private static int calculateNumValue(String cellState) {
        if(Objects.equals(cellState, CELL_STATE_X)) {
            return 1;
        } else if (Objects.equals(cellState, CELL_STATE_0)){
            return  -1;
        } else {
            return 0;
        }
    }

    public static boolean areAllCellsTaken(String[][] board) {
        for (int row = 0; row < ROW_COUNT; row++) {
            String Line = "| ";
            for (int col = 0; col < ROW_COUNT; col++) {
                if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                    return false;
                }
            }
        }
            return true;
    }
    public static void printBoard(String[][] board) {
        System.out.println("---------");
        for (int row = 0; row < ROW_COUNT; row++) {
            String Line = "| ";
            for ( int col = 0; col < ROW_COUNT; col++) {
                Line += board[row][col] + " ";
            }
            Line += "|";
            System.out.println(Line);
            }
        System.out.println("---------");

        }
    }
