package com.seaguest.game.searchengine;

import com.seaguest.game.player.IPlayer;

import java.util.ArrayList;

/**
 * Created by tyhk1987 on 2015/8/26.
 */
public class LineSplitter {


    public static boolean isLastOccurence(int[] lines, int playerIndex) {
        int i = playerIndex + 1;
        if (i == lines.length) {
            return true;
        }

        while (lines[i] == IPlayer.NONE) {
            i++;
            if (i == lines.length) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if the playerColor is the first occurence since last otherPlayerLastIndex.
     *
     * @param lines
     * @param otherPlayerLastIndex
     * @param playerIndex
     * @param playerColor
     * @return
     */
    public static boolean isFirstOccurence(int[] lines, int otherPlayerLastIndex, int playerIndex, int playerColor) {
        if (otherPlayerLastIndex == -1) {
            return false;
        }
        int i = otherPlayerLastIndex + 1;
        while (lines[i] != playerColor) {
            i++;
        }
        return i == playerIndex;
    }


    /**
     * check if the line cotains at least two chessmans.
     *
     * @param line
     * @return
     */
    public static boolean isAvailableForAnalyze(int[] line) {
        int leftIndex = 0;
        while (leftIndex < line.length && line[leftIndex] == IPlayer.NONE) {
            leftIndex++;
        }
        if (leftIndex == line.length) { // if we don't have any chessman
            return false;
        }
        int rightIndex = line.length - 1;
        while (rightIndex >= 0 && line[rightIndex] == IPlayer.NONE) {
            rightIndex--;
        }
        if (rightIndex == -1) { // if we don't have any chessman
            return false;
        }

        if (leftIndex == rightIndex) { // if we have only one chessman
            return false;
        }
        return true;
    }

    public static ArrayList<int[]>[] splitSection(int[] lines) {
        ArrayList<int[]>[] result = new ArrayList[2];
        ArrayList<int[]> blackSplits = new ArrayList<int[]>();
        ArrayList<int[]> whiteSplits = new ArrayList<int[]>();
        result[0] = blackSplits;
        result[1] = whiteSplits;

        int len = lines.length;
        int lastBlackSplitIndex = -1;
        int lastWhiteSplitIndex = -1;

        for (int i = 0; i < len; i++) {
            if (lines[i] == IPlayer.BLACK) {
                int tmp = i;
                boolean firstOccurence = isFirstOccurence(lines, lastWhiteSplitIndex, tmp, IPlayer.BLACK);
                if (firstOccurence) {
                    int length = tmp - lastBlackSplitIndex - 1;
                    if (length >= 5) {
                        int[] res = new int[length];
                        System.arraycopy(lines, lastBlackSplitIndex + 1, res, 0, length);
                        if (isAvailableForAnalyze(res)) {
                            whiteSplits.add(res);
                        }
                    }
                }
                boolean lastOccurence = isLastOccurence(lines, tmp);
                if (lastOccurence) {
                    int length = lines.length - lastWhiteSplitIndex - 1;
                    if (length >= 5) {
                        int[] res = new int[length];
                        System.arraycopy(lines, lastWhiteSplitIndex + 1, res, 0, length);
                        if (isAvailableForAnalyze(res)) {
                            blackSplits.add(res);
                        }
                    }
                    break;
                }
                lastBlackSplitIndex = tmp;
            } else if (lines[i] == IPlayer.WHITE) {
                int tmp = i;
                boolean firstOccurence = isFirstOccurence(lines, lastBlackSplitIndex, tmp, IPlayer.WHITE);
                if (firstOccurence) {
                    int length = tmp - lastWhiteSplitIndex - 1;
                    if (length >= 5) {
                        int[] res = new int[length];
                        System.arraycopy(lines, lastWhiteSplitIndex + 1, res, 0, length);
                        if (isAvailableForAnalyze(res)) {
                            blackSplits.add(res);
                        }
                    }
                }
                boolean lastOccurence = isLastOccurence(lines, tmp);
                if (lastOccurence) {
                    int length = lines.length - lastBlackSplitIndex - 1; // to be checked -1
                    if (length >= 5) {
                        int[] res = new int[length];
                        System.arraycopy(lines, lastBlackSplitIndex + 1, res, 0, length);
                        if (isAvailableForAnalyze(res)) {
                            whiteSplits.add(res);
                        }
                    }
                    break;
                }
                lastWhiteSplitIndex = tmp;
            }
        }
        return result;
    }
}
