package com.baiwangmaoyi.luckydraw.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class RemoteRandomProviderTest {

    private RemoteRandomProvider privider;
    private List<String> parList = new ArrayList<>();
    private HashMap<String, AtomicLong> resultMap = new HashMap<>();

    private static final int TOTAL_ROUND = 100;
    private static final int EACH_ROUND = 10;

    @Before
    public void init() {
        privider = new RemoteRandomProvider();
        privider.init();
        for (int i = 0; i < 110; i++) {
            parList.add(String.valueOf(i));
        }
        resultMap.clear();
    }

    @Test
    public void testVariance() {
        for (int i = 0; i < TOTAL_ROUND; i++) {
            List<String> pickedList = privider.pickRandomly(parList, EACH_ROUND);
            System.out.println("Round " + i);
            for (String par : pickedList) {
                resultMap.putIfAbsent(par, new AtomicLong(0L));
                AtomicLong count = resultMap.get(par);
                count.getAndIncrement();
            }
        }


        long sum = resultMap.entrySet().stream().mapToLong(e -> e.getValue().intValue()).sum();
        double mean = ((double) TOTAL_ROUND * EACH_ROUND) / ((double) parList.size());
        double squareSum = resultMap.entrySet().stream().map(e -> {
            double tmp = ((double) e.getValue().longValue());
            return Math.pow(tmp, 2.0);
        }).mapToDouble(Double::valueOf).sum();
        double varSquare = squareSum / ((double) parList.size()) - Math.pow(mean, 2.0);
        double var = Math.sqrt(varSquare);
        System.out.println(var);
        assertTrue("Variance:" + var + "  is too high!", var < 5);
    }

}
