package com.fed.file.api;

import java.util.Map;
import java.util.SortedSet;

public interface ISortedOnFrequency {
    SortedSet<Map.Entry<String,Integer>> sort();
}
