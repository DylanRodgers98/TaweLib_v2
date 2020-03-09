package com.crowvalley.tawelib.util;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {

    public static <T> List<T> castList(Class<T> clazz, List<?> listToBeCasted) {
        return listToBeCasted.stream().map(clazz::cast).collect(Collectors.toList());
    }

}
