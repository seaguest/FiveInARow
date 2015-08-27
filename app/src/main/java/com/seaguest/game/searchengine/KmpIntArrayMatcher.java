package com.seaguest.game.searchengine;

/**
 *
 * The goal is to try to check if one int[] is subset of another taking into account the order.
 *
 * @author yohuang
 *
 */
public final class KmpIntArrayMatcher {

    public static int search(int[] text, KmpPattern kmppattern, int color) {
	int[] pattern = kmppattern.pattern;

	if (text == null)
	    throw new NullPointerException();
	if (pattern.length == 0)
	    return 0; // Immediate match

	// Walk through text string
	int j = 0; // Number of chars matched in pattern
	for (int i = 0; i < text.length; i++) {
	    while (j > 0 && text[i] != pattern[j] * color)
		j = kmppattern.lsp[j - 1]; // Fall back in the pattern
	    if (text[i] == pattern[j] * color) {
		j++; // Next char matched, increment position
		if (j == pattern.length)
		    return i - (j - 1);
	    }
	}
	return -1; // Not found
    }

    public static void main(String[] args) {
	// int[] pattern = { 0, 1, 0,1, 1, 1, 0 };
	// int[] test = { 0, 0, 2, 2, 2, 0, 0, 2, 2, 2, 2, 0 };

	int[] test = { 0, 2, 2, 2, 2, 2, 0 };
	KmpPattern pat = new KmpPattern(new int[] { 1, 1, 1, 1, 1 });

	int x = KmpIntArrayMatcher.search(test, pat, 2);
	System.out.println(x);

	x = KmpIntArrayMatcher.search(test, pat, 1);
	System.out.println(x);

    }

}
