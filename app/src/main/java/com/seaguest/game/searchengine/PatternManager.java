package com.seaguest.game.searchengine;

import java.util.ArrayList;
import java.util.List;

class KmpPattern {
    int[] pattern;
    int[] lsp;

    public KmpPattern(int[] pattern) {
        this.pattern = pattern;
        computeLSP(pattern);
    }

    public void computeLSP(int[] pattern) {
        // Compute longest suffix-prefix table
        lsp = new int[pattern.length];
        if (lsp.length > 0)
            lsp[0] = 0; // Base case
        for (int i = 1; i < pattern.length; i++) {
            int j = lsp[i - 1]; // Start by assuming we're extending the previous LSP
            while (j > 0 && pattern[i] != pattern[j])
                j = lsp[j - 1];
            if (pattern[i] == pattern[j])
                j++;
            // assert 0 <= j && j < i;
            if (!(0 <= j && j <= i)) {
                System.out.println("********************** SYSTEM ERROR ------i:" + i + " j:" + j);
            }
            lsp[i] = j;
        }
    }
}

public class PatternManager {
    private static PatternManager instance = null;

    private static List<KmpPattern>[] patterns = new ArrayList[7];

    public static PatternManager getInstance() {
        if (instance == null) {
            instance = new PatternManager();
        }
        return instance;
    }

    public List<KmpPattern> getPattern(int index) {
        return patterns[index];
    }

    public int size() {
        return patterns.length;
    }

    {
        // five
        patterns[0] = new ArrayList<KmpPattern>();
        patterns[0].add(new KmpPattern(new int[]{1, 1, 1, 1, 1}));

        // four
        patterns[1] = new ArrayList<KmpPattern>();
        patterns[1].add(new KmpPattern(new int[]{0, 1, 1, 1, 1, 0}));

        // sfour
        patterns[2] = new ArrayList<KmpPattern>();
        patterns[2].add(new KmpPattern(new int[]{1, 1, 1, 1, 0}));
        patterns[2].add(new KmpPattern(new int[]{0, 1, 1, 1, 1}));
        patterns[2].add(new KmpPattern(new int[]{1, 1, 1, 0, 1}));
        patterns[2].add(new KmpPattern(new int[]{1, 0, 1, 1, 1}));
        patterns[2].add(new KmpPattern(new int[]{1, 1, 0, 1, 1}));

        // three
        patterns[3] = new ArrayList<KmpPattern>();
        patterns[3].add(new KmpPattern(new int[]{0, 1, 1, 1, 0, 0}));
        patterns[3].add(new KmpPattern(new int[]{0, 0, 1, 1, 1, 0}));
        patterns[3].add(new KmpPattern(new int[]{0, 1, 1, 0, 1, 0}));
        patterns[3].add(new KmpPattern(new int[]{0, 1, 0, 1, 1, 0}));

        // sthree
        patterns[4] = new ArrayList<KmpPattern>();
        patterns[4].add(new KmpPattern(new int[]{1, 1, 1, 0, 0}));
        patterns[4].add(new KmpPattern(new int[]{0, 0, 1, 1, 1}));
        patterns[4].add(new KmpPattern(new int[]{1, 1, 0, 1, 0}));
        patterns[4].add(new KmpPattern(new int[]{0, 1, 0, 1, 1}));
        patterns[4].add(new KmpPattern(new int[]{1, 0, 1, 1, 0}));
        patterns[4].add(new KmpPattern(new int[]{0, 1, 1, 0, 1}));
        patterns[4].add(new KmpPattern(new int[]{1, 0, 1, 0, 1}));
        patterns[4].add(new KmpPattern(new int[]{1, 1, 0, 0, 1}));
        patterns[4].add(new KmpPattern(new int[]{1, 0, 0, 1, 1}));
        patterns[4].add(new KmpPattern(new int[]{0, 1, 1, 1, 0}));

        // two
        patterns[5] = new ArrayList<KmpPattern>();
        patterns[5].add(new KmpPattern(new int[]{0, 0, 1, 1, 0, 0}));
        patterns[5].add(new KmpPattern(new int[]{0, 1, 1, 0, 0, 0}));
        patterns[5].add(new KmpPattern(new int[]{0, 0, 0, 1, 1, 0}));
        patterns[5].add(new KmpPattern(new int[]{0, 1, 0, 1, 0, 0}));
        patterns[5].add(new KmpPattern(new int[]{0, 0, 1, 0, 1, 0}));
        patterns[5].add(new KmpPattern(new int[]{0, 1, 0, 0, 1, 0}));

        // stwo
        patterns[6] = new ArrayList<KmpPattern>();
        patterns[6].add(new KmpPattern(new int[]{1, 1, 0, 0, 0}));
        patterns[6].add(new KmpPattern(new int[]{0, 0, 0, 1, 1}));
        patterns[6].add(new KmpPattern(new int[]{1, 0, 1, 0, 0}));
        patterns[6].add(new KmpPattern(new int[]{0, 0, 1, 0, 1}));
        patterns[6].add(new KmpPattern(new int[]{1, 0, 0, 1, 0}));
        patterns[6].add(new KmpPattern(new int[]{0, 1, 0, 0, 1}));
        patterns[6].add(new KmpPattern(new int[]{1, 0, 0, 0, 1}));
        patterns[6].add(new KmpPattern(new int[]{0, 1, 1, 0, 0}));
        patterns[6].add(new KmpPattern(new int[]{0, 0, 1, 1, 0}));
        patterns[6].add(new KmpPattern(new int[]{0, 1, 0, 1, 0}));

    }

}
