package com.betting.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomNumberGeneratorTest {

    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @Test
    public void generate() {
        Integer randomGeneratedNumber = randomNumberGenerator.generate(1, 100);
        Assertions.assertTrue(randomGeneratedNumber >= 1 && randomGeneratedNumber <= 100);
    }
}