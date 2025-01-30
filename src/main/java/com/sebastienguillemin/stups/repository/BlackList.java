package com.sebastienguillemin.stups.repository;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * List of all samples that must not be fetched.
 */
public class BlackList {
    private final static ArrayList<Integer> LIST = new ArrayList<>(Arrays.asList(331, 332, 334,965, 1799, 2431, 2433, 2441, 2468, 2470, 2510, 2511, 2512, 2546, 3162, 3358, 3401, 3730, 3798, 4019, 4020, 4152, 4408, 4428, 4429, 4430, 4847, 4848, 4849, 4949, 4950, 5561, 5562, 5563, 5564, 5565, 5566, 5567, 5568, 6006, 6195, 6196, 6197, 6198, 6199, 6572, 6573, 7380, 7645, 7654, 7675, 7676, 7677, 7828, 8039, 8040, 8041, 8042, 8113, 8114, 8115, 8116, 8117, 8118, 8119, 8243, 8552, 9194, 9195, 9196, 9197, 9274, 9275, 9276, 9277, 9291, 9542, 9543, 9544, 9545, 9546, 10266, 10267, 10268, 10515, 10703, 10704, 10705, 10706, 10707, 10764, 10993, 11613, 12439, 12503, 12666, 12670, 12816, 13319, 13687, 23857, 23858, 23859, 23860, 23861));

    public static boolean inBlackList(int id) {
        return LIST.contains(id);
    }
}
