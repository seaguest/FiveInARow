package com.seaguest.game.searchengine;

/**
 * Evaluation strategy. <br>
 * <p/>
 * Rules:
 * <ul>
 * <li>1, five consecutive X, +100000</li>
 * <li>2, 4 consecutive X with two side open, +10000</li>
 * <li>3, 4 consecutive X with one side open, +1000</li>
 * <li>4, 3 consecutive X with two side open, +1000</li>
 * <li>5, 3 consecutive X with one side open, +100</li>
 * <li>6, 2 consecutive X with two side open, +100</li>
 * <li>7, 2 consecutive X with one side open, +10</li>
 * <li>8, 1 consecutive X with two side open, +10</li>
 * <li>9, 1 consecutive X with one side open, +1</li>
 * <p/>
 * <li>1, five consecutive O, -100</li>
 * <li>2, 4 consecutive O with two side open, -100</li>
 * <li>3, 4 consecutive O with one side open, -80</li>
 * <li>4, 3 consecutive O with two side open, -80</li>
 * <li>5, 3 consecutive O with one side open, -60</li>
 * <li>6, 2 consecutive O with two side open, -60</li>
 * <li>7, 2 consecutive O with one side open, -40</li>
 * <li>8, 1 consecutive O with two side open, -40</li>
 * <li>9, 1 consecutive O with one side open, -20</li>
 * </ul>
 *
 * @author tyhk1987
 */


interface GameSetting {
    int EVA_ZERO = 0;
    int EVA_ONE_S = 1;
    int EVA_ONE = 10;
    int EVA_TWO_S = 10;
    int EVA_TWO = 100;
    int EVA_THREE_S = 100;
    int EVA_THREE = 1000;
    int EVA_FOUR_S = 1000;
    int EVA_FOUR = 10000;
    int EVA_FIVE = 100000;
}


public class Evaluator {

    public static int posValue7[][] = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0},
    };

    public static int posValue13[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
            {0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
            {0, 1, 2, 3, 4, 4, 4, 4, 4, 3, 2, 1, 0},
            {0, 1, 2, 3, 4, 5, 5, 5, 4, 3, 2, 1, 0},
            {0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1, 0},
            {0, 1, 2, 3, 4, 5, 5, 5, 4, 3, 2, 1, 0},
            {0, 1, 2, 3, 4, 4, 4, 4, 4, 3, 2, 1, 0},
            {0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
            {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    int[][] board;

    public boolean isWin = false;

    public Evaluator(int[][] board) {
        this.board = board;
    }


    public int evaluate(int player, boolean isX) {
        int score = 0, score4Opponent =0;
        score += evaluateLines(getRows(), player);
        score += evaluateLines(getColumns(), player);
        score += evaluateLines(getLeftDiagonals(), player);
        score += evaluateLines(getRightDiagonals(), player);

        int otherPlayer = 3 -player;
        score4Opponent += evaluateLines(getRows(), otherPlayer);
        score4Opponent += evaluateLines(getColumns(), otherPlayer);
        score4Opponent += evaluateLines(getLeftDiagonals(), otherPlayer);
        score4Opponent += evaluateLines(getRightDiagonals(), otherPlayer);


        if(isX){        // computation is based on X
            if(score >=100000){
                return score;
            }else if(score4Opponent >=100000){
                return -score4Opponent;
            }
            return score-score4Opponent;
        }else{
            if(score >=100000){
                return -score;
            }else if(score4Opponent >=100000){
                return score4Opponent;
            }
            return score4Opponent-score;
        }
    }

    public boolean isPlayerWin(int player) {
        return isWin;
    }

    public int[][] getRows() {
        int[][] rows = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, rows[i], 0, board.length);
        }
        return rows;
    }

    public int[][] getColumns() {
        int[][] columns = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            int[] res = new int[board.length];
            for (int j = 0; j < board.length; j++) {
                res[j] = board[j][i];
            }
            columns[i] = res;
        }
        return columns;
    }

    /**
     * This Diagonal starts from the right top side to left down side. <br>
     * \ direction <br>
     * <p/>
     * the starting point should be <br>
     * {(0, squareSize-targetSize). (targetSize-1, squareSize-1)} <br>
     * then end point is <br>
     * {(squareSize-targetSize, 0). (squareSize-1, targetSize-1)}
     * <p/>
     * the total size will be 2*(squareSize-targetSize)+1
     *
     * @return
     */
    public int[][] getRightDiagonals() {
        int size = board.length;
        int[][] diagonals = new int[2 * (size - Config.targetSize) + 1][size];

        for (int i = 0; i < (size - Config.targetSize + 1); i++) {
            int[] res = new int[size - i];
            for (int j = 0; j < (size - i); j++) {
                res[j] = board[j][j + i];
            }
            diagonals[i] = res;
        }

        for (int i = 0; i < (size - Config.targetSize); i++) {
            int[] res = new int[size - i - 1];
            for (int j = 0; j < (size - i - 1); j++) {
                res[j] = board[i + j + 1][j];
            }
            diagonals[i + size - Config.targetSize + 1] = res;
        }
        return diagonals;
    }

    /**
     * This Diagonal starts from the left top side to right down side. <br>
     * / direction <br>
     * the starting point should be <br>
     * {(0, targetSize-1). (targetSize-1, 0)} <br>
     * then end point is <br>
     * {(squareSize-targetSize, squareSize-1). (squareSize-1, squareSize-targetSize)}
     * <p/>
     * the total size will be 2*(squareSize-targetsize)+1
     *
     * @return
     */
    public int[][] getLeftDiagonals() {
        int size = board.length;
        int[][] diagonals = new int[2 * (size - Config.targetSize) + 1][size];

        for (int i = 0; i < (size - Config.targetSize + 1); i++) {
            int[] res = new int[size - i];
            for (int j = 0; j < (size - i); j++) {
                res[j] = board[j][size - 1 - i - j];
            }
            diagonals[i] = res;
        }

        for (int i = 0; i < (size - Config.targetSize); i++) {
            int[] res = new int[size - i - 1];
            for (int j = 0; j < (size - i - 1); j++) {
                res[j] = board[i + 1 + j][size - 1 - j];
            }
            diagonals[i + size - Config.targetSize + 1] = res;
        }
        return diagonals;
    }


    public int evaluateLines(int[][] lines, int player) {
        int score = 0;
        for (int[] line : lines) {
            score += evaluateLine(line, player);
        }
        return score;
    }


    /**
     * evaluation is always for computer.
     *
     * @param line
     * @param player
     * @return
     */
    private int evaluateLine(int[] line, int player) {
        int value = 0;
        int cnt = 0; // connecting points
        int blk = 0; // close sides

        int otherplayer = 3 - player;   //1 or 2

        // scan
        for (int i = 0; i < line.length; ++i) {
            if (line[i] == player) // first my chessman
            {
                cnt = 1;
                blk = 0;

                if (i == 0 || i  == line.length-1) {
                    ++blk;
                } else if (line[i - 1] == otherplayer) {
                    ++blk;
                }


                for (i = i + 1; i < line.length && line[i] == player; ++i, ++cnt) ;

                if (i  >= line.length) {
                    ++blk;
                } else if (line[i] == otherplayer) {
                    ++blk;
                }

                value += getValue(cnt, blk);
            }
        }
        return value;
    }

    private int getValue(int cnt, int blk) {
        if (blk == 0) {
            switch (cnt) {
                case 1:
                    return GameSetting.EVA_ONE;
                case 2:
                    return GameSetting.EVA_TWO;
                case 3:
                    return GameSetting.EVA_THREE;
                case 4:
                    return GameSetting.EVA_FOUR;
                default:
                    return GameSetting.EVA_FIVE;
            }
        } else if (blk == 1) // one side closed
        {
            switch (cnt) {
                case 1:
                    return GameSetting.EVA_ONE_S;
                case 2:
                    return GameSetting.EVA_TWO_S;
                case 3:
                    return GameSetting.EVA_THREE_S;
                case 4:
                    return GameSetting.EVA_FOUR_S;
                default:
                    return GameSetting.EVA_FIVE;
            }
        } else // two side closed
        {
            if (cnt >= 5) {
                isWin = true;
                return GameSetting.EVA_FIVE;
            } else {
                return GameSetting.EVA_ZERO;
            }
        }
    }
}
