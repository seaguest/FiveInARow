package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Point;

public class MoveAndScore implements Comparable<MoveAndScore> {
	public Point position;
	public int score;

	MoveAndScore() {
	}



	public MoveAndScore(int score, Point point) {
		this.score = score;
		this.position = point;
	}


	@Override
	public int compareTo(MoveAndScore another) {
		// TODO Auto-generated method stub
		return this.score - another.score;
	}
}
