package com.betting.api.utils;


import java.util.Random;

public class RandomNumberGenerator {

    public static Integer generate(Integer lowerBound, Integer upperBound) {
        return new Random().nextInt(upperBound) + lowerBound;
    }
}