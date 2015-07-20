import android.graphics.Point;

import com.seaguest.tyhk1987.canvas1.BoardPoint;
import com.seaguest.tyhk1987.canvas1.PointState;
import com.seaguest.tyhk1987.canvas1.Square;

/**
 * Created by tyhk1987 on 2015/7/18.
 */
public class TestBoardView {
    int boardSize = 13;
    BoardPoint[][] boardPoints;

    public TestBoardView() {
        boardPoints = new BoardPoint[boardSize][boardSize];
        initBoardPoints();

        boardPoints[12][0].state = PointState.BLACK;
    }

    public boolean isLineEmpty(BoardPoint[] line) {
        for (BoardPoint p : line) {
            if (!p.getPointSate().equals(PointState.VACANT)) {
                return false;
            }
        }
        return true;
    }

    public void initBoardPoints() {
        boardPoints = new BoardPoint[boardSize][];
        for (int i = 0; i < boardSize; i++) {
            boardPoints[i] = new BoardPoint[boardSize];
            for (int j = 0; j < boardSize; j++) {
                boardPoints[i][j] = new BoardPoint(100, 100);
            }
        }
    }

    public Square findCoverSquare() {
        int lefty = 0, righty = 0;
        int topx = 0, bottomx = 0;

        for (int i = 0; i < boardSize; i++) {
            BoardPoint[] line = boardPoints[i];
            if (!isLineEmpty(line)) {
                topx = Math.max(i - 1, 0);
                break;
            }
        }

        for (int i = boardSize - 1; i >= 0; i--) {
            BoardPoint[] line = boardPoints[i];
            if (!isLineEmpty(line)) {
                bottomx = Math.min(i + 1, boardSize - 1);
                break;
            }
        }

        // scan from left to right
        for (int i = 0; i < boardSize; i++) {
            BoardPoint[] column = new BoardPoint[boardSize];
            for (int j = 0; j < boardSize; j++) {
                column[j] = boardPoints[j][i];
            }
            if (!isLineEmpty(column)) {
                lefty = Math.max(i - 1, 0);
                break;
            }
        }

        for (int i = boardSize - 1; i >= 0; i--) {
            BoardPoint[] column = new BoardPoint[boardSize];
            for (int j = 0; j < boardSize; j++) {
                column[j] = boardPoints[j][i];
            }
            if (!isLineEmpty(column)) {
                righty = Math.min(i + 1, boardSize - 1);
                break;
            }
        }

        int xlen = bottomx - topx;
        int ylen = righty - lefty;

        if (xlen > ylen && ((righty + xlen - ylen) > (boardSize - 1))) {
            lefty -= (xlen - ylen);
        }

        if (xlen < ylen && ((bottomx + ylen - xlen) > (boardSize - 1))) {
            topx -= (ylen - xlen);
        }

        // we need a minimum square with 5 points on each side
        // expand to left, bottom
        if (xlen < 4 && ylen < 4) {
            if (bottomx + (4 - xlen) > (boardSize - 1)) {    // if to bottom not possible, then up
                topx -= (4 - xlen);
            } else {
                bottomx += (4 - xlen);
            }

            if (righty + (4 - xlen) > (boardSize - 1)) {
                lefty -= (4 - xlen);
            } else {
                righty += (4 - xlen);
            }
            xlen = 4;
            ylen = 4;
        }

        Point start = new Point(topx, lefty);
        start.x = topx;
        start.y = lefty;

        System.out.println("111 topx:x="+topx+"-lefty:"+lefty);
        System.out.println("222 Start:x="+start.x+"-y:"+start.y);


        int width = Math.max(xlen, ylen);

        System.out.println("777777777777777 lefty:=" + lefty + "-righty:" + righty + "-topx:" + topx + "-bottomx:" + bottomx);

        return new Square(start, width); // 4 cases, 5 points
    }
}
