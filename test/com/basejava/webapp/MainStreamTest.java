package com.basejava.webapp;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class MainStreamTest {

    @Test
    public void minValue() {
        Assert.assertEquals(123, MainStream.minValue(new int[]{1, 2, 3, 3, 3, 2, 2, 2, 1}));
        Assert.assertEquals(89, MainStream.minValue(new int[]{9, 8, 8, 9, 8, 9, 8}));
        Assert.assertEquals(2345, MainStream.minValue(new int[]{0, 4, 2, 5, 3, 0, 0}));
        Assert.assertEquals(0, MainStream.minValue(new int[]{0}));
        Assert.assertEquals(9, MainStream.minValue(new int[]{9, 9, 9, 9, 9, 9, 9, 9}));
    }


    @Test
    public void oddOrEven() {
        Assert.assertEquals(Arrays.asList(1, 3), MainStream.oddOrEven(Arrays.asList(1, 2, 3, 4)));
        Assert.assertEquals(Arrays.asList(2, 4, 6), MainStream.oddOrEven(Arrays.asList(1, 2, 4, 6)));
        Assert.assertEquals(Collections.emptyList(), MainStream.oddOrEven(Arrays.asList(1)));
        Assert.assertEquals(Collections.emptyList(), MainStream.oddOrEven(Arrays.asList(2)));
    }
}