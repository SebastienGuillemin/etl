package com.sebastienguillemin.stups.repository;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * List of all samples that must not be fetched.
 */
public class BlackList {
    private final static ArrayList<Integer> LIST = new ArrayList<>(Arrays.asList(33634, 33633, 33133, 33136, 33135, 33134, 33635, 30916, 31910, 32407, 32406, 32405, 31815, 32774));

    public static boolean inBlackList(int id) {
        return LIST.contains(id);
    }
}
