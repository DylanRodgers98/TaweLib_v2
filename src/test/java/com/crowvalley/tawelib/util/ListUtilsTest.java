package com.crowvalley.tawelib.util;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ListUtilsTest {

    private static final Object LONG_AS_OBJECT = 1L;

    private static final Long LONG_AS_LONG = 1L;

    private static final Object STRING_AS_OBJECT = "TEST_STRING";

    @Test
    public void testCastList() throws NoSuchFieldException {
        assertThat(LONG_AS_OBJECT)
                .as("Long objects are equal")
                .isEqualTo(LONG_AS_LONG);

        Type longAsObjectType = ListUtilsTest.class.getDeclaredField("LONG_AS_OBJECT").getType();
        Type longAsLongType = ListUtilsTest.class.getDeclaredField("LONG_AS_LONG").getType();

        assertThat(longAsObjectType)
                .as("Long objects have different types")
                .isNotEqualTo(longAsLongType);

        List rawList = Collections.singletonList(LONG_AS_OBJECT);
        List<Long> castedList = ListUtils.castList(Long.class, rawList);

        assertThat(castedList)
                .as("Casted list matches the original raw list")
                .isEqualTo(rawList);

        assertThat(castedList)
                .as("Casted list contains the Long object casted to a Long")
                .containsExactly(LONG_AS_LONG);
    }

    @Test
    public void testCastList_ListContainsObjectsOfWrongType() {
        List rawList = Arrays.asList(LONG_AS_OBJECT, STRING_AS_OBJECT);
        assertThatCode(() -> ListUtils.castList(Long.class, rawList))
                .as("Attempt to cast List containing an object of the wrong type throws ClassCastException")
                .isInstanceOf(ClassCastException.class);
    }

}
