package com.betting.api.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNumberGenerator {

    public Integer generate(Integer lowerBound, Integer upperBound) {
        return new Random().nextInt(upperBound) + lowerBound;
    }
}
