package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Chessman;

import java.util.Arrays;

public class Eveluation {
	public final static String TAG = "Eveluation";
	
	public final static int RESULT = 100;
	public final static int MAX = 9999;
	public final static int MIN = -9999;

	public final static int STWO = 1; //眠二
	public final static int STHREE = 2; //眠三
	public final static int SFOUR = 3; //冲四
	public final static int TWO = 4; //活二
	public final static int THREE = 5; //活三
	public final static int FOUR = 6; //活四 
	public final static int FIVE = 7; //五连
	public final static int ANALYSISED = 255; //已分析
	public final static int TOBEANALYSIS = 0; //已分析
	public final static int OTHERS = 10; //其他类型
	//棋子位置价值表
	public static int posValue11[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0},
			{0, 1, 2, 3, 3, 3, 3, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 4, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 4, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 3, 3, 3, 3, 2, 1, 0},
			{0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
	public static int posValue15[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
			{0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
			{0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	};
	public static int posValue17[][] = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
			{0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 7, 8, 7, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
			{0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
			{0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	};
	
	//保存anlysisLine分析结果的数组
	int lineRecord[];
	//存放所有的分析结果的数组
	//三维数组，存放水平、垂直、左斜、右斜方向上的棋型
	int record[][][];
	//存放统计过的分析结果的数组
	int counted[][];
	int count = 0;
	
	public Eveluation(int size) {
		// TODO Auto-generated constructor stub
		lineRecord = new int[RESULT];
		
		record = new int[size][][];
		for (int i = 0; i < size; i++) {
			record[i] = new int[size][];
			for (int j = 0; j < size; j++) {
				record[i][j] = new int[4];
			}
		}
		
		//黑1白2两个
		counted = new int[3][];
		for (int i = 0; i < counted.length; i++) {
			counted[i] = new int[20];
		}
	}
	
	/*
	 * 估值函数,对传入的棋盘打分
	 * chessTable 棋盘
	 * type 棋子类型,当前轮到谁下棋
	 * */
	public 	int	Eveluate(int[][] chessTable, int type) {
		int nStoneType;
		
		//累加计数器
		count++;
		clearRecord();
		
		//分析所有棋子在4个方向上的棋型
		for (int l = 0; l < chessTable.length; l++) {
			for (int l2 = 0; l2 < chessTable[l].length; l2++) {
				if (chessTable[l][l2] != Chessman.NOSTONE) {
					//如果水平方向没有分析过
					if (record[l][l2][0] == TOBEANALYSIS) {
						analysisHorizon(chessTable, l, l2);
					}
					//如垂直方向没有分析过
					if (record[l][l2][1] == TOBEANALYSIS) {
						analysisVertical(chessTable, l, l2);
					}
					//如果左斜方向没有分析过
					if (record[l][l2][2] == TOBEANALYSIS) {
						analysisLeft(chessTable, l, l2);
					}
					//如果右斜方向没有分析过
					if (record[l][l2][3] == TOBEANALYSIS) {
						analysisRight(chessTable, l, l2);
					}
				}
			}
		}
		
		//统计分析的结果，获得每种棋型数量
		for (int l = 0; l < record.length; l++) {
			for (int l2 = 0; l2 < record[l].length; l2++) {
				for (int m = 0; m < record[l][l2].length; m++) {
					//棋子颜色类型
					nStoneType = chessTable[l][l2];
					if (nStoneType != Chessman.NOSTONE) {
						switch (record[l][l2][m]) {
							//五连
							case FIVE:
								counted[nStoneType][FIVE]++;
								break;
							
							//活四
							case FOUR:
								counted[nStoneType][FOUR]++;
								break;
							
							//冲四
							case SFOUR:
								counted[nStoneType][SFOUR]++;
								break;
								
							//活三
							case THREE:
								counted[nStoneType][THREE]++;
								break;
							
							//眠三
							case STHREE:
								counted[nStoneType][STHREE]++;
								break;
							
							//活二
							case TWO:
								counted[nStoneType][TWO]++;
								break;
							
							//眠二
							case STWO:
								counted[nStoneType][STWO]++;
								break;	
								
							default:
								break;
						}
					}
				}
			}
		}
		
		//五连,直接返回极值
		if (type == Chessman.WHITE) {
			if (counted[Chessman.WHITE][FIVE] > 0) {
				return MAX;
			}
			
			if (counted[Chessman.BLACK][FIVE] > 0) {
				return MIN;
			}
			
		} else {
			if (counted[Chessman.BLACK][FIVE] > 0) {
				return MAX;
			}
			
			if (counted[Chessman.WHITE][FIVE] > 0) {
				return MIN;
			}
		}
		
		// 两个冲四等于一个活四
		if (counted[Chessman.WHITE][SFOUR] > 1) {
			counted[Chessman.WHITE][FOUR]++;
		}
		if (counted[Chessman.BLACK][SFOUR] > 1) {
			counted[Chessman.BLACK][FOUR]++;
		}
		
		//黑白估值
		int wValue = 0, bValue = 0;
		//轮到白方下棋
		if (type == Chessman.WHITE) {
			//活四，白起胜 返回极值
			if (counted[Chessman.WHITE][FOUR] > 0) {
				return 9990;
			}
			
			//冲四,白胜 返回极值
			if (counted[Chessman.WHITE][SFOUR ] > 0) {
				return 9980;
			}
			
			//白棋无冲四活四，而黑棋有活四，黑棋胜返回极值
			if (counted[Chessman.BLACK][FOUR] > 0) {
				return -9970;
			}
			
			//白棋无冲四活四，而黑棋有冲四和活三，黑棋胜返回极值
			if (counted[Chessman.BLACK][SFOUR] > 0
					&& counted[Chessman.BLACK][THREE] > 0) {
				return -9960;
			}
			
			//白棋有活三黑棋没有冲四，白棋胜并返回极值
			if (counted[Chessman.WHITE][THREE] > 0
					&& counted[Chessman.BLACK][SFOUR] == 0) {
				return 9950;
			}
			
			//黑的活三多于一个，而白无冲四、活三、冲三，黑棋胜返回极值
			if (counted[Chessman.BLACK][THREE] > 1
					&& counted[Chessman.WHITE][SFOUR] == 0
					&& counted[Chessman.WHITE][THREE] == 0
					&& counted[Chessman.WHITE][STHREE] == 0) {
				return -9940;
			}
			
			//白活三多于一个，白棋价值加2000
			if (counted[Chessman.WHITE][THREE] > 1) {
				wValue += 2000;
			} else {
				//否则白棋价值加200
				if (counted[Chessman.WHITE][THREE] > 0) {
					wValue += 200;
				}
			}
			
			//黑的活三多于一个，黑棋价值加500
			if (counted[Chessman.BLACK][THREE] > 1) {
				bValue += 500;
			} else {
				//否则黑棋价值加100
				if (counted[Chessman.BLACK][THREE] > 0) {
					bValue += 100;
				}
			}
			
			//每个眠三加10 白棋
			if (counted[Chessman.WHITE][STHREE] > 0) {
				wValue += counted[Chessman.WHITE][STHREE] * 10;
			}
			//每个眠三加10  黑棋
			if (counted[Chessman.BLACK][STHREE] > 0) {
				bValue += counted[Chessman.BLACK][STHREE] * 10;
			}
			//每个活二加4 白棋
			if (counted[Chessman.WHITE][TWO] > 0) {
				wValue += counted[Chessman.WHITE][TWO] * 4;
			}
			//每个活二加4  黑棋
			if (counted[Chessman.BLACK][TWO] > 0) {
				bValue += counted[Chessman.BLACK][TWO] * 4;
			}
			//每个眠二加1 白棋
			if (counted[Chessman.WHITE][STWO] > 0) {
				wValue += counted[Chessman.WHITE][STWO];
			}
			//每个眠二加1  黑棋
			if (counted[Chessman.BLACK][STWO] > 0) {
				bValue += counted[Chessman.BLACK][STWO];
			}
		} else { //黑棋走
			//黑棋活四
			if (counted[Chessman.BLACK][FOUR] > 0) {
				return 9990;
			}
			
			//冲四,黑棋胜 返回极值
			if (counted[Chessman.BLACK][SFOUR] > 0) {
				return 9980;
			}
			
			//活四,白棋胜 返回极值
			if (counted[Chessman.WHITE][FOUR] > 0) {
				return -9970;
			}
			
			//冲四并活三,白棋胜
			if (counted[Chessman.WHITE][SFOUR] > 0
				&& counted[Chessman.WHITE][THREE] > 0) {
				return -9960;
			}
			
			//黑棋活三，白棋无四，黑棋胜
			if (counted[Chessman.BLACK][THREE] > 0
					&& counted[Chessman.WHITE][SFOUR] == 0) {
				return 9950;
			}
			
			//白的活三多于一个,而黑棋无四,白棋胜并返回
			if (counted[Chessman.WHITE][THREE] > 1
					&& counted[Chessman.BLACK][SFOUR] == 0
					&& counted[Chessman.BLACK][THREE] == 0
					&& counted[Chessman.BLACK][STHREE] == 0) {
				return -9940;
			}
			
			//黑棋的活三多于一个，黑棋价值加2000
			if (counted[Chessman.BLACK][THREE] > 1) {
				bValue += 2000;
			} else {
				//否则黑棋价值加200
				if (counted[Chessman.BLACK][THREE] > 0) {
					bValue += 200;
				}
			}
			
			//白棋的活三多于一个，白棋价值加500
			if (counted[Chessman.WHITE][THREE] > 1) {
				wValue += 500;
			} else {
				//否则黑棋价值加100
				if (counted[Chessman.WHITE][THREE] > 0) {
					wValue += 100;
				}
			}
			
			//每个眠三加10 白棋
			if (counted[Chessman.WHITE][STHREE] > 0) {
				wValue += counted[Chessman.WHITE][STHREE] * 10;
			}
			//每个眠三加10  黑棋
			if (counted[Chessman.BLACK][STHREE] > 0) {
				bValue += counted[Chessman.BLACK][STHREE] * 10;
			}
			//每个活二加4 白棋
			if (counted[Chessman.WHITE][TWO] > 0) {
				wValue += counted[Chessman.WHITE][TWO] * 4;
			}
			//每个活二加4  黑棋
			if (counted[Chessman.BLACK][TWO] > 0) {
				bValue += counted[Chessman.BLACK][TWO] * 4;
			}
			//每个眠二加1 白棋
			if (counted[Chessman.WHITE][STWO] > 0) {
				wValue += counted[Chessman.WHITE][STWO];
			}
			//每个眠二加1  黑棋
			if (counted[Chessman.BLACK][STWO] > 0) {
				bValue += counted[Chessman.BLACK][STWO];
			}
		}
		
		//统计所有棋子的位置价值
		for (int l = 0; l < chessTable.length; l++) {
			for (int l2 = 0; l2 < chessTable[l].length; l2++) {
				nStoneType = chessTable[l][l2];
				if (nStoneType != Chessman.NOSTONE) {
					if (nStoneType == Chessman.BLACK) {
						switch (chessTable.length) {
							case 11:
								bValue += posValue11[l][l2];
								break;
								
							case 13:
								bValue += posValue13[l][l2];
								break;
								
							case 15:
								bValue += posValue15[l][l2];
								break;
								
							case 17:
								bValue += posValue17[l][l2];
								break;
							default:
								break;
						}
					} else {
						switch (chessTable.length) {
							case 11:
								wValue += posValue11[l][l2];
								break;
								
							case 13:
								wValue += posValue13[l][l2];
								break;
								
							case 15:
								wValue += posValue15[l][l2];
								break;
								
							case 17:
								wValue += posValue17[l][l2];
								break;
							default:
								break;
						}
					}
				}
			}
		}
		
		
		//返回估值
		if (type == Chessman.BLACK) {
			return bValue - wValue;
		} else {
			return wValue - bValue;
		}
		
	}
	
	/*
	 * 清空棋型分析结果
	 * */
	public void clearRecord() {
		for (int i = 0; i < record.length; i++) {
			for (int j = 0; j < record[i].length; j++) {
				for (int j2 = 0; j2 < record[i][j].length; j2++) {
					record[i][j][j2] = TOBEANALYSIS;
				}
			}
		}
		
		for (int i = 0; i < counted.length; i++) {
			for (int j = 0; j < counted[i].length; j++) {
				counted[i][j] = 0;
			}
		}
	}
	
	/*
	 * 清空直线分析临时结果
	 * */
	public void clearTempLineResult() {
		for (int i = 0; i < lineRecord.length; i++) {
			lineRecord[i] = TOBEANALYSIS;
		}
	}
	
	/*
	 * 设置直线分析临时结果为已分析
	 * */
	public void setLineRecordToAnalysised(int[] r, int type) {
		for (int i = 0; i < r.length; i++) {
			r[i] = type;
		}
	}
	
	/*
	 * 水平方向上某点及其周边的棋型
	 * chessTable 要分析的棋盘
	 * i 某点在数组的行下标 
	 * j 某点在数组的列下标 
	 * 返回棋型
	 * */
	public int analysisHorizon(int[][] chessTable, int i, int j) {
		//调用直线分析方法,chessTable[i]行数组中的棋子，当前是第j列
		analysisLine(chessTable[i], chessTable[i].length, j);
		//提取分析结果
		for (int s = 0; s < chessTable[i].length; s++) {
			//保存已分析的棋型
			if (lineRecord[s] != TOBEANALYSIS) {
				record[i][s][0] = lineRecord[s];
			}
		}
		return record[i][j][0];
	}
	
	/*
	 * 垂直方向上某点及其周边的棋型
	 * chessTable 要分析的棋盘
	 * i 某点在数组的行下标 
	 * j 某点在数组的列下标 
	 * 返回棋型
	 * */
	public int analysisVertical(int[][] chessTable, int i, int j) {
		int[] tempArray = new int[chessTable.length];
		
		//将垂直方向上的棋子转到临时数组
		for (int k = 0; k < tempArray.length; k++) {
			tempArray[k] = chessTable[k][j];
		}
		//调用直线分析方法, 垂直数组 第i个
		analysisLine(tempArray, tempArray.length, i);
		//提取分析结果
		for (int s = 0; s < chessTable.length; s++) {
			//排除未分析
			if (lineRecord[s] != TOBEANALYSIS) {
				record[s][j][1] = lineRecord[s];
			}
		}
		return record[i][j][1];
	}
	
	/*
	 * 左斜45度方向上某点及其周边的棋型
	 * chessTable 要分析的棋盘
	 * i 某点在数组的行下标 
	 * j 某点在数组的列下标 
	 * 返回棋型
	 * */
	public int analysisLeft(int[][] chessTable, int i, int j) {
		int[] tempArray = new int[chessTable.length];
		//x表示在数组的列下标，y表示在数组的行下标
		int x, y;
		if (i < j) { //主上三角
			y = 0;
			x = j - i;
		} else { //主下三角
			x = 0;
			y = i - j;
		}
		
		//将左斜方向上的棋子转到临时数组
		int k;
		for (k = 0; k < tempArray.length; k++) {
			//确保不越界
			if ((x + k > (chessTable.length - 1))
					|| (y + k) > (chessTable.length - 1)) {
				break;
			}
			//注意行列
			tempArray[k] = chessTable[y + k][x + k];
		}
		
		//调用直线分析方法, k个棋子，在临时数组中的第j-x个
		analysisLine(tempArray, k, j - x);
		//提取分析结果
		for (int s = 0; s < k; s++) {
			//排除未分析
			if (lineRecord[s] != TOBEANALYSIS) {
				record[y + s][x + s][2] = lineRecord[s];
			}
		}
		return record[i][j][2];
	}
	
	/*
	 * 右斜45度方向上某点及其周边的棋型
	 * chessTable 要分析的棋盘
	 * i 某点在数组的行下标 
	 * j 某点在数组的列下标 
	 * 返回棋型
	 * */
	public int analysisRight(int[][] chessTable, int i, int j) {
		int[] tempArray = new int[chessTable.length];
		//x表示在数组的列下标，y表示在数组的行下标, realnum
		int x, y, realnum;
		
		//次上三角
		if ((i + j) < (chessTable.length - 1)) {
			y = i + j; //左下，底部
			x = 0; 
			//realnum = j;
		}else {//次下三角
			y = chessTable.length - 1; //底行
			x = j - (chessTable.length - 1) + i;
			//realnum = (chessTable.length - 1) - j;
		}
		
		//将右斜方向上的棋子转到临时数组
		int k;
		for (k = 0; k < tempArray.length; k++) {
			//越界就不处理
			if ((x + k) > (chessTable.length - 1)
					|| (y - k) < 0) {
				break;
			}
			tempArray[k] = chessTable[y - k][x + k];
		}
		
		//调用直线分析方法, k个棋子，在临时数组中的第j-x个
		analysisLine(tempArray, k, j - x);
		//提取分析结果
		for (int s = 0; s < k; s++) {
			//排除未分析
			if (lineRecord[s] != TOBEANALYSIS) {
				record[y - s][x + s][3] = lineRecord[s];
			}
		}
		return record[i][j][3];
	}
	
	/*
	 * 分析给定行上某店及其周边的棋型 五、四、三、二等棋型
	 * chessArray 棋盘某行、列、左斜、右斜的数组 引用类型
	 * gridNum 数组长度
	 * stonePos 待分析棋子位置
	 * 返回棋型
	 * */
	public int analysisLine(int[] chessArray, int gridNum, int stonePos) {
		//当前棋子类型
		int stoneType;
		int analyLine[];
		//当前棋子在数组中的位置
		int analyPos;
		int leftEdge, rightEdge;
		int leftRange, rightRange;
		
		//数组长度小于5 放你下都下不赢
		if (gridNum < 5) {
			//设置为已分析
			setLineRecordToAnalysised(lineRecord, ANALYSISED);
			return 0;
		}
		
		analyPos = stonePos;
		//设置为未分析
		setLineRecordToAnalysised(lineRecord, TOBEANALYSIS);
		//设置为0
		//setLineRecordToAnalysised(analyLine, 0);
		//复制要分析的数组
		analyLine = Arrays.copyOf(chessArray, chessArray.length);
		gridNum--;
		stoneType = analyLine[analyPos];
		leftEdge = analyPos;
		rightEdge = analyPos;
		
		//计算连续棋子左边界
		while (leftEdge > 0
				&& analyLine[leftEdge - 1] == stoneType) {
			leftEdge--;
		}
		//计算连续棋子右边界
		while (rightEdge < gridNum
				&& analyLine[rightEdge + 1] == stoneType) {
			rightEdge++;
		}
		
		leftRange = leftEdge;
		rightRange = rightEdge;
		//两个循环计算棋子可下的范围 (包含已下的棋子)
		while (leftRange > 0
				&& (analyLine[leftRange - 1] == Chessman.NOSTONE
					|| analyLine[leftRange - 1] == stoneType)) {
			leftRange--;
		}
		while (rightRange < gridNum
				&& (analyLine[rightRange + 1] == Chessman.NOSTONE
					|| analyLine[rightRange + 1] == stoneType)) {
			rightRange++;
		}
		
		//如果此范围小于4（下标相减），说明这行最多能下四个棋子，就不必分析了
		if ((rightRange - leftRange) < 4) {
			for (int k = leftRange; k <= rightRange ; k++) {
				lineRecord[k] = ANALYSISED;
			}
			return 0;
		}
		
		//将连续区域设为已分析，防止重复分析
		for (int k = leftRange; k <= rightRange ; k++) {
			lineRecord[k] = ANALYSISED;
		}
		
		//如果待分析棋子棋型为5连
		//(下标相减 大于3表示至少有5个棋子)
		if ((rightEdge - leftEdge) > 3) {
			lineRecord[analyPos] = FIVE;
			return FIVE;
		}
		//如果待分析棋子棋型为五连
		//(下标相减 等于3表示有4个棋子相连)
		if ((rightEdge - leftEdge) == 3) {
			//左边有对方的棋或者到边界
			boolean leftFour = true;
			
			if ((leftEdge > 0) 
					&& analyLine[leftEdge - 1] == Chessman.NOSTONE) {
				//左边没有对方的棋
				leftFour = false;
			}
			
			if (rightEdge < gridNum) {//右边未到边界
				if (analyLine[rightEdge + 1] == Chessman.NOSTONE) {//右边没有对方的棋
					if (!leftFour) {//左右两边到都没有棋
						//活四
						lineRecord[analyPos] = FOUR;
					}else {
						//冲四
						lineRecord[analyPos] = SFOUR;
					}
				} else {//右边要么有对方的棋要么到边界
					//左边没有棋
					if (!leftFour) {
						//冲四
						lineRecord[analyPos] = SFOUR;
					}
					
					//右边有棋，在前面已处理，不会执行到这里
				}
			} else {//右边到边界了
				//左边没有棋
				if (!leftFour) {
					//冲四
					lineRecord[analyPos] = SFOUR;
				}
				
			}
			
			return lineRecord[analyPos];
		}
		
		//如果待分析棋子棋型为三连
		//(下标相减 等于2表示有3个棋子相连)
		if ((rightEdge - leftEdge) == 2) {
			boolean leftThree = false;
			
			if (leftEdge > 1) {
				if (analyLine[leftEdge - 1] == Chessman.NOSTONE) {
					if (leftEdge > 1
							&& analyLine[leftEdge - 2] == analyLine[leftEdge]) {
						//左边隔一个空白有一个己方棋子
						//跳冲四
						lineRecord[leftEdge] = SFOUR;
						lineRecord[leftEdge - 2] = ANALYSISED;
					} else {
						//左边有对方棋子
						leftThree = true;
					}
				}
			}
			if (rightEdge < gridNum) {
				if (analyLine[rightEdge + 1] == Chessman.NOSTONE) {
					//右边没有对方棋子
					if (rightEdge < gridNum - 1
							&& analyLine[rightEdge + 2] == analyLine[rightEdge]	) {
						//右边隔一个空白有一个己方棋子
						//冲四
						lineRecord[rightEdge] = SFOUR;
						lineRecord[rightEdge + 2] = ANALYSISED;
					} else {
						if (!leftThree) {//左边没有对方的棋子
							//活三
							lineRecord[rightEdge] = THREE;
						} else {
							//冲三
							lineRecord[rightEdge] = STHREE;
						}
					}
				} else {
					if (lineRecord[leftEdge] == SFOUR) {//左边有冲四
						return lineRecord[rightEdge];
					} 
					
					if (!leftThree) {
						lineRecord[analyPos] = STHREE;//眠三
					}
				}
				
				
			}
			return lineRecord[analyPos];
		}
		
		//待分析的棋子棋型为二连
		if ((rightEdge - leftEdge) == 1) {
			boolean leftTwo = false;
			boolean leftThree = false;
			
			if (leftEdge > 2) {
				if (analyLine[leftEdge - 1] == Chessman.NOSTONE) {
					//左边有对方的棋子
					if ((leftEdge-1) > 1
							&& analyLine[leftEdge - 2] == analyLine[leftEdge]) {
						if (analyLine[leftEdge - 3] == analyLine[leftEdge]) {
							//左边隔2个空白有己方棋子
							lineRecord[leftEdge - 3] = ANALYSISED;
							lineRecord[leftEdge - 2] = ANALYSISED;
							//冲四
							lineRecord[leftEdge] = SFOUR;
						} else {
							if (analyLine[leftEdge - 3] == Chessman.NOSTONE) {
								//左边隔1个空白有己方棋子
								lineRecord[leftEdge - 2] = ANALYSISED;
								//眠三
								lineRecord[leftEdge] = STHREE; 
							}
						}
					} else {
						leftTwo = true;
					}
				}
			}
			
			if (rightEdge < gridNum-2) {
				if (analyLine[rightEdge + 1] == Chessman.NOSTONE) {
					//右边有棋
					if ((rightEdge + 1) < gridNum - 1
							&& analyLine[rightEdge + 2] == analyLine[rightEdge]) {
						if(analyLine[rightEdge + 3] == analyLine[rightEdge])
						{
							//右边隔两个己方棋子
							lineRecord[rightEdge + 3] = ANALYSISED;
							lineRecord[rightEdge + 2] = ANALYSISED;
							//冲四 
							lineRecord[rightEdge] = SFOUR;
						}else {
							if(analyLine[rightEdge + 3] == Chessman.NOSTONE)
							{
								//右边隔 1 个己方棋子
								lineRecord[rightEdge + 2] = ANALYSISED;
								//眠三
								lineRecord[rightEdge] = STHREE;
							}
						}
					} else {
						//左边冲四
						if(lineRecord[leftEdge] == SFOUR)
							return lineRecord[leftEdge];
						//左边眠三
						if(lineRecord[leftEdge] == STHREE)					
							return lineRecord[leftEdge];

						if(leftTwo)
							//返回活二
							lineRecord[analyPos] = TWO;
						else
							//眠二
							lineRecord[analyPos] = STWO;
					}
				} else {
					//冲四返回
					if(lineRecord[leftEdge] == SFOUR)
						return lineRecord[leftEdge];
					//眠二
					if(leftTwo)
						lineRecord[analyPos] = STWO;
				}
			}
			
			return lineRecord[analyPos];
		}
		return 0;
	}
	
}
