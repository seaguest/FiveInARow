package com.seaguest.game.searchengine;

import com.seaguest.game.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

import static com.seaguest.game.searchengine.LineSplitter.isAvailableForAnalyze;
import static com.seaguest.game.searchengine.LineSplitter.splitSection;

public class EvaluationHelper {
    public int[][] counter = new int[2][7];

    // counter for current line
    public int[][] currentCounter = new int[2][7];

    /**
     * count the atomic line.
     *
     * @param line
     * @param patternIndex
     * @return
     */
    public int countPattern(int[] line, int patternIndex, int color) {
        int count = 0;
        int index = -1;
        if (line.length < 5) { // we need at least 5 chessman to evaluate, no value for line less than 5 chessman
            return 0;
        }

        List<KmpPattern> currentPattern = PatternManager.getInstance().getPattern(patternIndex);
        if (currentPattern == null || currentPattern.size() == 0) {
            return 0;
        }

        for (KmpPattern pattern : currentPattern) {
            index = KmpIntArrayMatcher.search(line, pattern, color);
            if (index != -1) {
                count++;
                int[] leftArray = new int[index];
                int[] rightArray = new int[line.length - index - pattern.pattern.length];
                System.arraycopy(line, 0, leftArray, 0, index);
                System.arraycopy(line, index + pattern.pattern.length, rightArray, 0, line.length - index
                        - pattern.pattern.length);
                count += countPattern(leftArray, patternIndex, color);
                count += countPattern(rightArray, patternIndex, color);
                currentCounter[color - 1][patternIndex] = count;
                break;
            }
        }
        if (count == 0) {
            if (patternIndex >= PatternManager.getInstance().size() - 1) {
                currentCounter[color - 1][patternIndex] = 0;
            } else {
                currentCounter[color - 1][patternIndex + 1] = countPattern(line, patternIndex + 1, color);
            }
        }

        return count;
    }

    public void updteCounterTable() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                counter[i][j] += currentCounter[i][j];
                // reset currentCounter
                currentCounter[i][j] = 0;
            }
        }
    }

    // add srcCounter to toCounter
    public static int[][] addCounter(int[][] counter1, int[][] counter2) {
        int[][] res = new int[2][7];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                res[i][j] =  counter1[i][j] + counter2[i][j];
            }
        }
        return res;
    }

    // compute difference srcCounter to toCounter
    public static int[][] subCounter(int[][] counter1, int[][] counter2) {
        int[][] res = new int[2][7];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                res[i][j] =  counter1[i][j] - counter2[i][j];
            }
        }
        return res;
    }


    public int getColor(int[] line) {
        int i = 0;
        while (line[i] == 0) {
            i++;
        }
        return line[i];
    }

    public void printCounter() {
        for (int i = 0; i < 2; i++) {
            System.out.println("-----------------Player:" + i + "-----------------");
            for (int j = 0; j < 7; j++) {
                System.out.println("-Pattern:" + j + " - count: " + counter[i][j]);
            }
        }
    }

    public void analyzeLine(int[] line) {
        ArrayList<int[]>[] newLines = splitSection(line);
        for (int i = 0; i < 2; i++) {
            for (int[] li : newLines[i]) {
                analyzeAtomicLine(li, i + 1);
                // update the counter
                updteCounterTable();
            }
        }
    }


    public static int[][] getCounterForLines(List<int[]> lines) {
        int[][] mycounters = new int[2][7];
        for(int[] line: lines){
            int[][] mycounter = getCounterForLine(line);
            mycounters = addCounter(mycounters, mycounter);
        }
        return mycounters;
    }


    public static int[][] getCounterForLine(int[] line) {
        EvaluationHelper newhelp = new EvaluationHelper();
        newhelp.analyzeLine(line);
        return newhelp.counter;
    }

    public boolean isGameOver(int[][] board, IPlayer player) {
        int[][] lines = MatrixHelper.getLines(board);

        for (int[] line : lines) {
            if (isAvailableForAnalyze(line)) {
                int index = KmpIntArrayMatcher.search(line, PatternManager.getInstance().getPattern(0).get(0),
                        player.getChessmanColor());
                if (index != -1) {
                    player.setWinState(IPlayer.WIN);
                    return true;
                }
                int otherPlayerColor = 3 - player.getChessmanColor();
                index = KmpIntArrayMatcher.search(line, PatternManager.getInstance().getPattern(0).get(0),
                        otherPlayerColor);
                if (index != -1) {
                    player.setWinState(IPlayer.LOST);
                    return true;
                }
            }
        }

        if (isDraw(board)) {
            player.setWinState(IPlayer.DRAW);
            return true;
        }
        return false;
    }

    public boolean isDraw(int[][] board) {
        int i = 0;
        while (i < board.length) {
            int j = 0;
            while (j < board.length && board[i][j] != IPlayer.NONE) {
                j++;
            }
            if (j != board.length) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Analyze the atomic line separated by the other player color. in this line, we have only one color.
     */
    public void analyzeAtomicLine(int[] line, int color) {
        countPattern(line, 0, color);
    }


    public static void main(String[] args) {
        // int[] line = { 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1 };
        // int[] line = {0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 2, 0, 2, 2, 2, 0, 0, 2, 2, 2, 2, 0, 0, 1, 1, 1,
        // 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0};

        //int[] line = {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] line = {0,1, 1, 0, 0,0,0,0,0};

        // ArrayList<int[]>[] newLines = splitSection(line);
        //
        // for (int i = 0; i < 2; i++) {
        // System.out.println("********************Player:" + i + "********************");
        // for (int[] li : newLines[i]) {
        // System.out.println(Arrays.toString(li));
        // }
        // }
        EvaluationHelper helper = new EvaluationHelper();

        helper.analyzeLine(line);
        helper.printCounter();

        // int[] pat = {1, 1, 0, 1, 1};
        // int[] test = {0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1};
        // int index = KmpIntArrayMatcher.search(test, pat);
        // System.out.println(index);

    }
}
