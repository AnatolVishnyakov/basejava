package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (one, two) -> one * 10 + two);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, (one, two) -> one + two);

        return (sum % 2 == 0)
                ? integers.stream().filter(value -> value % 2 != 0).collect(Collectors.toList())
                : integers.stream().filter(value -> value % 2 == 0).collect(Collectors.toList());
    }
}
