package com.seaguest.game.minimax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Evaluation strategy. <br>
 * <p/>
 * Rules:
 * <ul>
 * <li>1, five consecutive X, +100</li>
 * <li>2, 4 consecutive X with two side open, +100</li>
 * <li>3, 4 consecutive X with one side open, +80</li>
 * <li>4, 3 consecutive X with two side open, +80</li>
 * <li>5, 3 consecutive X with one side open, +60</li>
 * <li>6, 2 consecutive X with two side open, +60</li>
 * <li>7, 2 consecutive X with one side open, +40</li>
 * <li>8, 1 consecutive X with two side open, +40</li>
 * <li>9, 1 consecutive X with one side open, +20</li>
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

public class Evaluator {

    public int size = 0;

    public Evaluator(int size) {
        this.size = size;
    }

    public int evaluate(MiniMaxBoard miniMaxBoard) {
        int result = 0;

        String[] rows = getRows(miniMaxBoard);
        for (String row : rows) {
            result += PatternRecordManager.getInstance().getScore(row);
        }

        String[] columns = getColumns(miniMaxBoard);
        for (String column : columns) {
            result += PatternRecordManager.getInstance().getScore(column);
        }

        return result;
    }

    public String[] getRows(MiniMaxBoard miniMaxBoard) {
        String[] rows = new String[miniMaxBoard.getSize()];

        for (int i = 0; i < miniMaxBoard.getSize(); i++) {
            rows[i] = new String(miniMaxBoard.getBoard()[i]);
        }
        return rows;
    }

    public static String[] getColumns(MiniMaxBoard miniMaxBoard) {
        String[] columns = new String[miniMaxBoard.getSize()];
        for (int i = 0; i < miniMaxBoard.getSize(); i++) {
            char[] res = new char[miniMaxBoard.getSize()];
            for (int j = 0; j < miniMaxBoard.getSize(); j++) {
                res[j] = miniMaxBoard.getBoard()[j][i];
            }
            columns[i] = new String(res);
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
    public String[] getRightDiagonals(MiniMaxBoard miniMaxBoard) {
        String[] diagonals = new String[2 * (size - Config.targetSize) + 1];

        for (int i = 0; i < (size - Config.targetSize + 1); i++) {
            char[] res = new char[size-i];
            for (int j = 0; j < (size - i); j++) {
                res[j] = miniMaxBoard.getBoard()[j][j + i];
            }
            diagonals[i] = new String(res);
        }

        for (int i = 0; i < (size - Config.targetSize); i++) {
            char[] res = new char[size-i-1];
            for (int j = 0; j < (size - i - 1); j++) {
                res[j] = miniMaxBoard.getBoard()[i + j + 1][j];
            }
            diagonals[i + size - Config.targetSize + 1] = new String(res);
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
    public String[] getLeftDiagonals(MiniMaxBoard miniMaxBoard) {
        String[] diagonals = new String[2 * (size - Config.targetSize) + 1];

        for (int i = 0; i < (size - Config.targetSize + 1); i++) {
            char[] res = new char[size-i];
            for (int j = 0; j < (size - i); j++) {
                res[j] = miniMaxBoard.getBoard()[j][size - 1 - i - j];
            }
            diagonals[i] = new String(res);
        }

        for (int i = 0; i < (size - Config.targetSize); i++) {
            char[] res = new char[size-i-1];
            for (int j = 0; j < (size - i - 1); j++) {
                res[j] = miniMaxBoard.getBoard()[i + 1 + j][size - 1 - j];
            }
            diagonals[i + size - Config.targetSize + 1] = new String(res);
        }
        return diagonals;
    }

    public int isXWin(MiniMaxBoard miniMaxBoard) {

        String[] rows = getRows(miniMaxBoard);
        String[] coloumns = getColumns(miniMaxBoard);
        String[] leftDiagonals = getLeftDiagonals(miniMaxBoard);
        String[] rightDiagonals = getRightDiagonals(miniMaxBoard);

        String[] merge = merge(rows, coloumns, leftDiagonals, rightDiagonals);

        for (String str : merge) {
            if (PatternRecordManager.getInstance().getScore(str) == 100) {
                return 1;
            } else if (PatternRecordManager.getInstance().getScore(str) == -100) {
                return -1;
            }
        }
        return 0;
    }

    public String[] merge(String[] a, String[] b, String[] c, String[] d) {
        int aLen = a.length;
        int bLen = b.length;
        int cLen = c.length;
        int dLen = d.length;
        String[] e = new String[aLen + bLen + cLen + dLen];
        System.arraycopy(a, 0, e, 0, aLen);
        System.arraycopy(b, 0, e, aLen, bLen);
        System.arraycopy(c, 0, e, aLen + bLen, cLen);
        System.arraycopy(d, 0, e, aLen + bLen + cLen, dLen);
        return e;
    }

    public void main(String[] args) {
        System.out.println(PatternRecordManager.getInstance().repeat('X', 6));
    }
}

class PatternRecord {
    public String pattern;
    public int score;

    public PatternRecord(String pattern, int score) {
        this.pattern = pattern;
        this.score = score;
    }
}

class PatternRecordManager {

    public static PatternRecordManager instance = new PatternRecordManager();

    public static List<PatternRecord> patterns;

    public PatternRecordManager() {
        patterns = new ArrayList<PatternRecord>();
        initializa();
    }

    public static PatternRecordManager getInstance() {
        return instance;
    }

    public void initializa() {
        patterns.add(new PatternRecord(repeat(Config.X, 5), 100));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 4) + Config.EMPTY, 99));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 4) + Config.O, 80));
        patterns.add(new PatternRecord(Config.O + repeat(Config.X, 4) + Config.EMPTY, 80));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 3) + Config.EMPTY, 80));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 3) + Config.O, 60));
        patterns.add(new PatternRecord(Config.O + repeat(Config.X, 3) + Config.EMPTY, 60));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 2) + Config.EMPTY, 60));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 2) + Config.O, 40));
        patterns.add(new PatternRecord(Config.O + repeat(Config.X, 2) + Config.EMPTY, 40));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 1) + Config.EMPTY, 40));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.X, 1) + Config.O, 20));
        patterns.add(new PatternRecord(Config.O + repeat(Config.X, 1) + Config.EMPTY, 20));

        patterns.add(new PatternRecord(repeat(Config.O, 5), -100));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 4) + Config.EMPTY, -99));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 4) + Config.X, -80));
        patterns.add(new PatternRecord(Config.X + repeat(Config.O, 4) + Config.EMPTY, -80));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 3) + Config.EMPTY, -80));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 3) + Config.X, -60));
        patterns.add(new PatternRecord(Config.X + repeat(Config.O, 3) + Config.EMPTY, -60));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 2) + Config.EMPTY, -60));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 2) + Config.X, -40));
        patterns.add(new PatternRecord(Config.X + repeat(Config.O, 2) + Config.EMPTY, -40));

        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 1) + Config.EMPTY, -40));
        patterns.add(new PatternRecord(Config.EMPTY + repeat(Config.O, 1) + Config.X, -20));
        patterns.add(new PatternRecord(Config.X + repeat(Config.O, 1) + Config.EMPTY, -20));

    }

    public int getScore(String target) {
        for (PatternRecord pattern : patterns) {
            if (target.indexOf(pattern.pattern) != -1) {
                return pattern.score;
            }
        }
        return 0;
    }

    public String repeat(char in, int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, in);
        return new String(chars);
    }

}
