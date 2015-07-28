package com.seaguest.game.player;

import android.content.Intent;

/**
 * Player Interface
 */
public interface IPlayer {
	public final static int WIN = 1;
	public final static int LOST = -1;
	public final static int DRAW = 0;

	public static final int X = 11;
	public static final int O = 22;
	public static final int EMPTY = 10;

	public static final int NONE = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;


	public boolean isX();

	public boolean play();

	public int canIWin();

	// send message to the other player.
	public boolean sendMessage(Intent message);
}