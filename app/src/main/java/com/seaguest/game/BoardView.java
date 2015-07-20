package com.seaguest.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.seaguest.game.minimax.Config;
import com.seaguest.game.minimax.MiniMaxBoard;

/**
 * Created by tyhk1987 on 2015/7/16.
 */
public class BoardView extends View {

    Bitmap blackBitmap, whiteBitmap;
    Paint paint;
    Point coordinate;

    //多余的像素
    int modx, mody;

    boolean isGmaeOver = false;

    // black always first
    int numberOfBlackPoints = 0;
    int numberOfWhiltePoints = 0;


    BoardPoint[][] boardPoints;
    int boardSize;
    int sideLength;

    //x1、x2、y1、y2开始和结束点
    int startx1, endx1;
    int starty1, endy1;

    //五个黑点位置
    int fivePointsLocal[][];

    boolean isMachineFirst = false;


    MediaPlayer mediaPlayerWin;
    MediaPlayer mediaPlayerLost;
    MediaPlayer mediaPlayerDraw;
    MediaPlayer mediaPlayerPush;
    MediaPlayer mediaPlayerRemove;



    public boolean isBlackTurn() {
        return numberOfBlackPoints == numberOfWhiltePoints;
    }


    /*初始化所有点方法*/
    public void initBoardPoints() {
        coordinate = new Point(-100, -100);

        //二维数组分配
        boardPoints = new BoardPoint[boardSize][];
        for (int i = 0; i < boardSize; i++) {
            boardPoints[i] = new BoardPoint[boardSize];
            for (int j = 0; j < boardSize; j++) {
                int x, y;
                x = startx1 + sideLength * j;
                y = starty1 + sideLength * i;
                boardPoints[i][j] = new BoardPoint(x, y);
            }
        }
    }

    public void resetBoard() {
        isGmaeOver = false;
        numberOfBlackPoints = 0;
        numberOfWhiltePoints = 0;
        coordinate = new Point(-100, -100);

        //二维数组分配
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                BoardPoint point = boardPoints[i][j];
                point.state = PointState.VACANT;
            }
        }
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        Log.v("attr", "attr");

        //设置棋盘这个View是可以获得焦点的
        setFocusable(true);

        // set sound effect
        mediaPlayerWin = MediaPlayer.create(context, R.raw.win);
        mediaPlayerLost = MediaPlayer.create(context, R.raw.lost);
        mediaPlayerDraw = MediaPlayer.create(context, R.raw.draw);
        mediaPlayerPush = MediaPlayer.create(context, R.raw.put);
        mediaPlayerRemove = MediaPlayer.create(context, R.raw.remove);


    }


    /*重写父类大小改变方法*/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        Log.v("attr", "sizechanged");

        //初始化成员部分变量
        initMembers(13, 3);
        //初始化所有的点
        initBoardPoints();
    }

    /*初始化所有成员变量值
     *size 棋盘大小
     *bPSize 五个大黑点的大小
     */
    public void initMembers(int size, int bPSize) {
        //初始化棋盘大小
        boardSize = size;
        //初始化黑点的大小

        //x余数
        modx = this.getWidth() % boardSize;
        //获取小正方形边长
        sideLength = this.getWidth() / boardSize;
        //获取x1点， 外层
        //获取x2点， 内层
        startx1 = (modx + sideLength) / 2;
        //获取endx1点， 外层
        endx1 = this.getWidth() - startx1;
        //获取endx2点， 内层
        //y余数
        mody = (this.getHeight() - this.getWidth()) / 2;
        //获取y1点, 最外层
        starty1 = mody;
        //获取y2点， 内层
        //获取endy1点， 外层
        endy1 = this.getHeight() - starty1;
        //获取endy2点， 内层

        //初始化画笔
        //paint = new Paint();
        //初始化黑白棋图片资源
        blackBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.black);
        whiteBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.white);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        print(canvas);
    }


    public void print(Canvas canvas) {
        //初始化画笔
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        //画棋盘
        for (int i = 0; i < boardSize; i++) {
            //画横线
            canvas.drawLine(boardPoints[i][0].x,
                    boardPoints[i][0].y,
                    boardPoints[i][boardSize - 1].x,
                    boardPoints[i][boardSize - 1].y,
                    paint);
            //画竖线
            canvas.drawLine(boardPoints[0][i].x,
                    boardPoints[0][i].y,
                    boardPoints[boardSize - 1][i].x,
                    boardPoints[boardSize - 1][i].y,
                    paint);

        }


        // draw points
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                BoardPoint point = boardPoints[i][j];
                Rect rect = new Rect(
                        (int) (point.x - sideLength / 2),
                        (int) (point.y - sideLength / 2),
                        (int) (point.x + sideLength / 2),
                        (int) (point.y + sideLength / 2));

                if (point.getPointSate().equals(PointState.BLACK)) {
                    canvas.drawBitmap(blackBitmap, null, rect, null);
                } else if (point.getPointSate().equals(PointState.WHITE)) {
                    canvas.drawBitmap(whiteBitmap, null, rect, null);
                }
            }

        }

        if (!isGmaeOver) {
            if (0 <= coordinate.x && coordinate.x < boardSize && 0 <= coordinate.y && coordinate.y < boardSize) {
                // draw the current point
                BoardPoint point = boardPoints[coordinate.x][coordinate.y];
                if (point.getPointSate().equals(PointState.VACANT)) {
                    Rect rect = new Rect(
                            (int) (point.x - sideLength / 2),
                            (int) (point.y - sideLength / 2),
                            (int) (point.x + sideLength / 2),
                            (int) (point.y + sideLength / 2));

                    if (isBlackTurn()) {
                        point.state = PointState.BLACK;
                        numberOfBlackPoints++;
                        canvas.drawBitmap(blackBitmap, null, rect, null);
                    } else {
                        point.state = PointState.WHITE;
                        numberOfWhiltePoints++;
                        canvas.drawBitmap(whiteBitmap, null, rect, null);
                    }
                    mediaPlayerPush.start();

                    MiniMaxBoard board = new MiniMaxBoard();
                    Square square = findCoverSquare();
                    board.setBoard(getCharBoard(square));

                    if (isMachineWin(board) == 1) {
                        isGmaeOver = true;
                        showGameOver("Machine win!", Config.X);
                    } else if (isMachineWin(board) == -1) {
                        isGmaeOver = true;
                        showGameOver("You win!", Config.O);
                    }
                }
            }
        }

        // after human, then it is machine's turn
        if (!isGmaeOver && !isBlackTurn()) {
            callMachineMiniMax(canvas);
        }
    }


    public int isMachineWin(MiniMaxBoard board) {
        return board.isXWon();
    }

    public void showGameOver(String msg, char winPayer) {

        if (winPayer == Config.X) {
            mediaPlayerLost.start();
        } else if (winPayer == Config.O) {
            mediaPlayerWin.start();
        }

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(msg)
                .setTitle("Game Over!")
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        // show it
        dialog.show();
    }

    public void callMachineMiniMax(Canvas canvas) {
        MiniMaxBoard board = new MiniMaxBoard();
        Square square = findCoverSquare();
        board.setBoard(getCharBoard(square));
        board.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Config.X);
        Point bestMove = board.returnBestMove();
        bestMove.x += square.start.x;
        bestMove.y += square.start.y;

        BoardPoint point = boardPoints[bestMove.x][bestMove.y];
        Rect rect = new Rect(
                (int) (point.x - sideLength / 2),
                (int) (point.y - sideLength / 2),
                (int) (point.x + sideLength / 2),
                (int) (point.y + sideLength / 2));

        if (isBlackTurn()) {
            point.state = PointState.BLACK;
            numberOfBlackPoints++;
            canvas.drawBitmap(blackBitmap, null, rect, null);
        } else {
            point.state = PointState.WHITE;
            numberOfWhiltePoints++;
            canvas.drawBitmap(whiteBitmap, null, rect, null);
        }

        mediaPlayerPush.start();

        // update new boards and check
        MiniMaxBoard newBoard = new MiniMaxBoard();
        Square square1 = findCoverSquare();
        newBoard.setBoard(getCharBoard(square1));

        if (isMachineWin(newBoard) == 1) {
            isGmaeOver = true;
            showGameOver("Machine win!",Config.X);
        } else if (isMachineWin(board) == -1) {
            isGmaeOver = true;
            showGameOver("You win!", Config.O);
        }
    }

    public void newGame() {
        resetBoard();
        invalidate();
    }

    public Point findLocation(float x, float y) {
        Point location = new Point();
        // the array ndex and the real XY are inverse.
        location.y = Math.round((x - startx1) / sideLength);
        location.x = Math.round((y - starty1) / sideLength);
        return location;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        coordinate = findLocation(x, y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                invalidate();
                break;
/*
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
*/
        }
        return true;
    }

    public boolean isLineEmpty(BoardPoint[] line) {
        for (BoardPoint p : line) {
            if (!p.getPointSate().equals(PointState.VACANT)) {
                return false;
            }
        }
        return true;
    }

    /**
     * y
     * ------>
     * |
     * | x
     * |
     *
     * @return
     */
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
        int width = Math.max(xlen, ylen);

//        System.out.println("777777777777777 lefty:=" + lefty + "-righty:" + righty + "-topx:" + topx + "-bottomx:" + bottomx);

        return new Square(start, width); // 4 cases, 5 points
    }

    public char[][] getCharBoard(Square square) {
        int size = square.width + 1;
        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
//                System.out.println("9999999999999999Start:x="+square.start.x+"-y:"+square.start.y+"-size:"+square.width +"ij:"+i+j);
                BoardPoint point = boardPoints[square.start.x + i][square.start.y + j];

                if (point.getPointSate().equals(PointState.BLACK)) {   // BLACK ALWAYS FIRST
                    if (isMachineFirst) {
                        board[i][j] = Config.X;
                    } else {
                        board[i][j] = Config.O;
                    }
                } else if (point.getPointSate().equals(PointState.WHITE)) {   // BLACK ALWAYS FIRST
                    if (isMachineFirst) {
                        board[i][j] = Config.O;
                    } else {
                        board[i][j] = Config.X;
                    }
                } else {
                    board[i][j] = Config.EMPTY;
                }
            }
        }
        return board;
    }
}
