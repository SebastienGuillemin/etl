package com.sebastienguillemin.stups.repository;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * List of all samples that must not be fetched.
 */
public class BlackList {
    private final static ArrayList<Integer> LIST = new ArrayList<>(Arrays.asList());

    public static boolean inBlackList(int id) {
        return LIST.contains(id);
    }
}
