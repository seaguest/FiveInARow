package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Chessman;
import com.seaguest.game.player.ComputerPlayer;
import com.seaguest.game.player.IPlayer;
import com.seaguest.game.player.Player;
import com.seaguest.game.transition.TransitionTable;

import java.util.List;

/**
 * Evaluation strategy. <br>
 *
 * @author tyhk1987
 */

public class Evaluator {
    public int[][] board;
    public int[][][] analyzedFlag;   // record analyzed status on all 4 directions
    EvaluationHelper helper;

    public final static int FIVE = 0;
    public final static int FOUR = 1;
    public final static int SFOUR = 2;
    public final static int THREE = 3;
    public final static int STHREE = 4;
    public final static int TWO = 5;
    public final static int STWO = 6;

    public Evaluator(int[][] board) {
        this.board = board;
        helper = new EvaluationHelper();
        analyzedFlag = new int[board.length][board.length][4];
    }

    /**
     * Evaluate the situation after player put down a chessman. next turn is otherplayer.
     *
     * @param counter
     * @param playerColor
     * @return
     */
    public int evaluate(int[][] counter, int playerColor) {
        int score = 0;
        int otherPlayerColor = 3 - playerColor;

        // if the other player has 5, must win
        if (counter[otherPlayerColor - 1][FIVE] > 0) {
            return -999999 + computeDifference(counter, playerColor);
        }

        // if the player has 5, must win
        if (counter[playerColor - 1][FIVE] > 0) {
            return 999999 + computeDifference(counter, playerColor);
        }

        // if the other player has 4 or s4, must win
        if (counter[otherPlayerColor - 1][FOUR] > 0) {
            return -290000 + computeDifference(counter, playerColor);
        }
        if (counter[otherPlayerColor - 1][SFOUR] > 0) {
            return -190000 + computeDifference(counter, playerColor);
        }

        // if the player has 4, must win
        if (counter[playerColor - 1][FOUR] > 0) {
            return 290000 + computeDifference(counter, playerColor);
        }

        // if the player has s4, could win, could be stopped
        // but if I have a s4 and a 3. must win
        if (counter[playerColor - 1][SFOUR] > 0) {
            if (counter[playerColor - 1][THREE] > 0) {
                return 90000 + computeDifference(counter, playerColor);
            }
            // to be calculated later , for only 1 S4
        }

        // if the other player has 3 or s3, must win
        if (counter[otherPlayerColor - 1][THREE] > 0) {
            return -90000 + computeDifference(counter, playerColor);
        }
        if (counter[otherPlayerColor - 1][STHREE] > 0) {
            return -70000 + computeDifference(counter, playerColor);
        }

        // if the player has more than one 3, must win
        if (counter[playerColor - 1][THREE] > 1) {
            return 90000 + computeDifference(counter, playerColor);
        }

        // if the player has more than one 3, must win
        if (counter[playerColor - 1][STHREE] > 1) {
            return 70000 + computeDifference(counter, playerColor);
        }

        score += computeDifference(counter, playerColor);
        return score;
    }

    public int computeDifference(int[][] counter, int playerColor) {
        int score = 0;
        int otherPlayerColor = 3 - playerColor;

        int[] difference = new int[7];
        difference[FIVE] = counter[playerColor - 1][FIVE] - counter[otherPlayerColor - 1][FIVE];
        difference[FOUR] = counter[playerColor - 1][FOUR] - counter[otherPlayerColor - 1][FOUR];
        difference[SFOUR] = counter[playerColor - 1][SFOUR] - counter[otherPlayerColor - 1][SFOUR];
        difference[THREE] = counter[playerColor - 1][THREE] - counter[otherPlayerColor - 1][THREE];
        difference[STHREE] = counter[playerColor - 1][STHREE] - counter[otherPlayerColor - 1][STHREE];
        difference[TWO] = counter[playerColor - 1][TWO] - counter[otherPlayerColor - 1][TWO];
        difference[STWO] = counter[playerColor - 1][STWO] - counter[otherPlayerColor - 1][STWO];

        score += difference[FIVE] * 99999;
        score += difference[FOUR] * 30000;
        score += difference[SFOUR] * 10000;
        score += difference[THREE] * 3000;
        score += difference[STHREE] * 1000;
        score += difference[TWO] * 300;
        score += difference[STWO] * 100;

        int nStoneType;
        for (int l = 0; l < board.length; l++) {
            for (int l2 = 0; l2 < board[l].length; l2++) {
                nStoneType = board[l][l2];
                if (nStoneType != Player.NONE) {
                    if (nStoneType == playerColor) {
                        score += LocationScoreArray.getInstance().getScore(board.length, l, l2);
                    } else {
                        score -= LocationScoreArray.getInstance().getScore(board.length, l, l2);
                    }
                }
            }
        }
        return score;
    }

    /**
     * Analyze the counter related to the position.
     *
     * @param chessman
     */
    public int[][] analyzePoint(Chessman chessman) {
        List<int[]> lines = MatrixHelper.getLines(board, chessman, analyzedFlag);
        int[][] counter = EvaluationHelper.getCounterForLines(lines);
        return counter;
    }


    public void cleanAnalyzedFlag(){
        analyzedFlag = new int[board.length][board.length][4];
    }


    public static int test=0;

    /**
     *
     * This is based on the transition table, take the previous record and add the difference .
     *
     * @param chessmansRecords
     * @return
     */
    public int[][] getCurrentCounter(List<Chessman> chessmansRecords){
        test++;
        int[][] addtionalCounterForRecordsPoints = new int[2][7];
        for(int i=0; i<chessmansRecords.size(); i++){
            int[][] counterForPoint =  analyzePoint(chessmansRecords.get(i));
            addtionalCounterForRecordsPoints = EvaluationHelper.addCounter(addtionalCounterForRecordsPoints, counterForPoint);
        }
        cleanAnalyzedFlag();

       // System.out.println("------------------------------------------------- BEFORE UNMOVE:" + test);
        //System.out.println("addtionalCounterForRecordsPoints:\n" + Arrays.deepToString(addtionalCounterForRecordsPoints));
       // displayBoard();


        // the original board, unmake the point;
        unmoveChessmans(chessmansRecords);
        int[][] initialCounter = TransitionTable.getInstance().getCounter(board);
        if(initialCounter == null){
            initialCounter = Evaluator.getCounter(board);
        }
  //      System.out.println("initialCounter:\n" + Arrays.deepToString(initialCounter));

/*
        System.out.println("------------------------------------------------- ORIGINAL BOARD");
        displayBoard();

*/

        int[][] additionalCounterWithoutRecordsPoints = new int[2][7];
        for(int i=0; i<chessmansRecords.size(); i++){
            int[][] counterForPoint =  analyzePoint(chessmansRecords.get(i));
            additionalCounterWithoutRecordsPoints = EvaluationHelper.addCounter(additionalCounterWithoutRecordsPoints, counterForPoint);
        }
        cleanAnalyzedFlag();
        moveChessmans(chessmansRecords);

        //System.out.println("additionalCounterWithoutRecordsPoints:\n" + Arrays.deepToString(additionalCounterWithoutRecordsPoints));

        int[][] difference = EvaluationHelper.subCounter(addtionalCounterForRecordsPoints, additionalCounterWithoutRecordsPoints);
        int[][] result = EvaluationHelper.addCounter(initialCounter, difference);

//        System.out.println("difference:\n" + Arrays.deepToString(difference));

  //      System.out.println("final result:\n" + Arrays.deepToString(result));

        return result;
    }


    public void moveChessman(Chessman chessman){
        if(board[chessman.x][chessman.y] == IPlayer.NONE){
            board[chessman.x][chessman.y] = chessman.color;
        }
    }

    public void unmoveChessman(Chessman chessman){
        if(board[chessman.x][chessman.y] != IPlayer.NONE){
            board[chessman.x][chessman.y] = IPlayer.NONE;
        }
    }

    public void moveChessmans(List<Chessman> chessmans){
        for(Chessman chessman: chessmans){
            moveChessman(chessman);
        }
    }

    public void unmoveChessmans(List<Chessman> chessmans){
        for(Chessman chessman: chessmans){
            unmoveChessman(chessman);
        }
    }

    public static int[][] getCounter(int[][] currentBoard) {
        EvaluationHelper newhelper = new EvaluationHelper();
        int[][] lines = MatrixHelper.getLines(currentBoard);
//        System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
 //       System.out.println(Arrays.deepToString(lines));

        for (int[] line : lines) {
            newhelper.analyzeLine(line);
        }
        return newhelper.counter;
    }


    public int evaluate(IPlayer player, List<Chessman> chessmansRecords) {
        int playerColor = 3 - currentColor();

        int score = evaluate(getCurrentCounter(chessmansRecords), playerColor);
        return -score;
    }


    public int evaluate(IPlayer player) {
        int score = 0;
        int[][] lines = MatrixHelper.getLines(board);
        // System.out.println(Arrays.deepToString(lines));

        for (int[] line : lines) {
            helper.analyzeLine(line);
        }
        // helper.printCounter();

        // we evaluate the score based on the last chessman, thus for the last player
        int playerColor = 3 - currentColor();

        score = evaluate(helper.counter, playerColor);

        return -score;
    }

    public void displayBoard() {
        System.out.println("------------------borad----------------");
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------borad----------------");
    }

    public static void main(String[] args) {
        int[][] board1 = {{0, 1, 1, 0, 0, 0, 0}, {0, 1, 0, 0, 2, 0, 0}, {0, 0, 0, 2, 2, 0, 1},
                {0, 0, 0, 0, 2, 0, 1}, {0, 2, 2, 2, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 1, 1, 0, 0, 0, 0},};

        int[][] board9 = {{0, 0, 1, 0, 0, 0, 0, 1, 1}, {0, 0, 1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 2, 0}, {0, 0, 0, 0, 0, 0, 2, 2, 0}, {0, 0, 0, 0, 0, 2, 0, 2, 0},
                {0, 0, 2, 0, 2, 2, 2, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},};

        int[][] board13 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 2, 0, 0, 0, 0, 0}, {0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};

        int[][] board11 = {{0, 0, 0, 0, 1, 0, 2, 0, 0, 1, 1}, {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};


        int[][] board130 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };


        //
        // try {
        // System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("D:\\tmp\\out.log")), true));
        // } catch (FileNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        //
        //
        ComputerPlayer ai = new ComputerPlayer(IPlayer.WHITE, 2);

        // Evaluator evaluator = new Evaluator(board9);
        // evaluator.move(3,2, 1);
        // evaluator.move(6,7, 2);
        // evaluator.move(3,3, 1);
        // evaluator.move(6,8, 2);
        // evaluator.displayBoard();
        // int score = evaluator.evaluate(ai);
        // System.out.println("score:" + score);
        //
        Evaluator evaluator = new Evaluator(board130);
//	evaluator.move(5, 6, 2);
        // evaluator.move(5,5, 2);
        // evaluator.move(2,8, 1);
        // evaluator.move(7,3, 2);

        evaluator.displayBoard();
        int score = evaluator.evaluate(ai);
        System.out.println("score:" + score);

    }

    public boolean isGameOver(IPlayer player) {
        return helper.isGameOver(board, player);
    }


    public int currentColor() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    count++;
                }
            }
        }

        return (count % 2 == 0) ? IPlayer.BLACK : IPlayer.WHITE;

    }

}
