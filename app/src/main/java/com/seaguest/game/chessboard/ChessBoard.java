package com.seaguest.game.chessboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.seaguest.game.R;
import com.seaguest.game.player.ComputerPlayer;
import com.seaguest.game.player.IPlayer;
import com.seaguest.game.resource.ResourceManager;
import com.seaguest.game.searchengine.Evaluator;
import com.seaguest.game.searchengine.MoveAndScore;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends View {

    ResourceManager resourceManager;

    Paint paint;

    boolean isGmaeOver = false;

    // the chessboard
    Chessman[][] chessboard;

    Point[][] boardPoints;

    // used by search engine, contains 0,1,2
    public int[][] searchEngineBoard;

    // the list which contains all chessman on board
    public List<Chessman> onBoardChessman;

    public int chessBoardSize = 13;

    int sideLength;

    // board boundary
    int startx1, endx1;
    int starty1, endy1;

    // five stars
    Point[] fiveStars = new Point[5];

    boolean isMachineFirst = false;

    // computer player is white
    ComputerPlayer computerPlayer;

    public ChessBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        Log.v("attr", "attr");

        setFocusable(true);

        if (!isInEditMode()) {
            resourceManager = new ResourceManager(context, getResources());
        }

        // init boards points
        boardPoints = new Point[chessBoardSize][chessBoardSize];
        resetChessBoard();
        computerPlayer = new ComputerPlayer(this, IPlayer.WHITE);
    }

    public void resetChessBoard() {
        isGmaeOver = false;
        chessboard = new Chessman[chessBoardSize][chessBoardSize];
        searchEngineBoard = createSearchEngineBoard();
        onBoardChessman = new ArrayList<Chessman>();
        invalidate();
    }

    // set board boundary
    public void initBoundBoundary(int size) {
        chessBoardSize = size;
        int modx = this.getWidth() % chessBoardSize;
        sideLength = this.getWidth() / chessBoardSize;
        startx1 = (modx + sideLength) / 2;
        endx1 = this.getWidth() - startx1;
        int mody = (this.getHeight() - this.getWidth()) / 2;
        starty1 = startx1;
        endy1 = this.getHeight() - starty1;

        // find five starts
        fiveStars[0] = new Point(3, 3);
        fiveStars[1] = new Point(3, chessBoardSize - 4);
        fiveStars[2] = new Point((chessBoardSize - 1) / 2, (chessBoardSize - 1) / 2);
        fiveStars[3] = new Point(chessBoardSize - 4, 3);
        fiveStars[4] = new Point(chessBoardSize - 4, chessBoardSize - 4);

    }


    // init board points with coordiantes
    public void initBoardPoints() {

        // init all points
        for (int i = 0; i < chessBoardSize; i++) {
            boardPoints[i] = new Point[chessBoardSize];
            for (int j = 0; j < chessBoardSize; j++) {
                int x, y;
                x = startx1 + sideLength * j;
                y = starty1 + sideLength * i;
                boardPoints[i][j] = new Point(x, y);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        Log.v("attr", "sizechanged");

        initBoundBoundary(chessBoardSize);

        initBoardPoints();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        displayChessBoard(canvas);
    }

    public void placeChessman(Chessman chessman) {
        // record the on board chessmans
        onBoardChessman.add(chessman);
        chessboard[chessman.x][chessman.y] = chessman;
        searchEngineBoard[chessman.x][chessman.y] = chessman.color;
        resourceManager.mediaPlayerPush.start();
        checkIsGameOver();

    }

    public boolean checkIsGameOver() {
        Evaluator evaluator = new Evaluator(searchEngineBoard);
        int score = evaluator.evaluate(IPlayer.BLACK, false);
        if (score > 100000) {  // black win, human
            isGmaeOver = true;
            showGameOver("You win!", IPlayer.O);
        } else {
            score = evaluator.evaluate(IPlayer.WHITE, true);
            if (Math.abs(score) > 100000) {  // black win, human
                isGmaeOver = true;
                showGameOver("Machine win!", IPlayer.X);
            }
        }
        return isGmaeOver;
    }


    public void showGameOver(String msg, int winPayer) {

        if (winPayer == IPlayer.X) {
            resourceManager.mediaPlayerLost.start();
        } else if (winPayer == IPlayer.O) {
            resourceManager.mediaPlayerWin.start();
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

    public void displayChessBoard(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < chessBoardSize; i++) {
            canvas.drawLine(boardPoints[i][0].x,
                    boardPoints[i][0].y,
                    boardPoints[i][chessBoardSize - 1].x,
                    boardPoints[i][chessBoardSize - 1].y,
                    paint);
            canvas.drawLine(boardPoints[0][i].x,
                    boardPoints[0][i].y,
                    boardPoints[chessBoardSize - 1][i].x,
                    boardPoints[chessBoardSize - 1][i].y,
                    paint);
        }

        // draw five stars
        for (Point p : fiveStars) {
            paint.setStyle(Paint.Style.FILL);
            Point star = boardPoints[p.x][p.y];
            canvas.drawCircle(star.x, star.y, 10, paint);
        }

        // draw chessman
        for (int i = 0; i < chessBoardSize; i++) {
            for (int j = 0; j < chessBoardSize; j++) {
                Chessman chessman = chessboard[i][j];
                if (chessman != null) {
                    // get absolute coordiante
                    Point point = boardPoints[i][j];
                    Rect rect = new Rect(
                            (int) (point.x - sideLength / 2),
                            (int) (point.y - sideLength / 2),
                            (int) (point.x + sideLength / 2),
                            (int) (point.y + sideLength / 2));

                    if (chessman.color == IPlayer.BLACK) {
                        canvas.drawBitmap(resourceManager.blackBitmap, null, rect, null);
                    } else if (chessman.color == IPlayer.WHITE) {
                        canvas.drawBitmap(resourceManager.whiteBitmap, null, rect, null);
                    }
                }

            }
        }
    }

    public Point findLocation(float x, float y) {
        Point location = new Point(-1, -1);
        // the array ndex and the real XY are inverse.
        location.y = Math.round((x - startx1) / sideLength);
        location.x = Math.round((y - starty1) / sideLength);
        return location;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        Point coordinate = findLocation(x, y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // click should be in boundary
                if (!isGmaeOver && (0 <= coordinate.x && coordinate.x < chessBoardSize && 0 <= coordinate.y && coordinate.y < chessBoardSize)) {
                    // BLACK is alwyas first
                    int currentColor = IPlayer.NONE;
                    if (onBoardChessman.size() % 2 == 0) {
                        currentColor = IPlayer.BLACK;
                    } else {
                        currentColor = IPlayer.WHITE;
                    }

                    Chessman chessman = new Chessman(coordinate.x, coordinate.y, IPlayer.BLACK);
                    placeChessman(chessman);
                    invalidate();
                }

                break;
/*
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
*/
            case MotionEvent.ACTION_UP:

                if (!isGmaeOver) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            long startTime = System.currentTimeMillis();
                            computerPlayer.searchEngine.setBoard(searchEngineBoard);
                            computerPlayer.searchEngine.searchBestMove();
                            long endTime = System.currentTimeMillis();
                            if ((endTime - startTime) < 500) {
                                try {
                                    this.sleep(500);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    thread.start();

                    try {
                        thread.join();
                        MoveAndScore bestMove = computerPlayer.searchEngine.getBestMove();
                        Chessman aiChessman = new Chessman(bestMove.position.x, bestMove.position.y, IPlayer.WHITE);
                        placeChessman(aiChessman);
                        invalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }

        return true;
    }

    public int[][] getSearchEngineBoard() {
        return searchEngineBoard;
    }


    public int[][] createSearchEngineBoard() {
        int[][] board = new int[chessBoardSize][chessBoardSize];
        for (int i = 0; i < chessBoardSize; i++) {
            for (int j = 0; j < chessBoardSize; j++) {
                Chessman point = chessboard[i][j];
                if (point != null) {
                    if (point.color == IPlayer.BLACK) {   // BLACK ALWAYS FIRST
                        board[i][j] = IPlayer.BLACK;
                    } else if (point.color == IPlayer.WHITE) {   // BLACK ALWAYS FIRST
                        board[i][j] = IPlayer.WHITE;
                    }
                } else {
                    board[i][j] = IPlayer.NONE;
                }

            }
        }
        return board;
    }

}