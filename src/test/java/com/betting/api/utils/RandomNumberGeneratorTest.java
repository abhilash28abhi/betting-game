package com.betting.api.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomNumberGeneratorTest {


    @Test
    public void generate() {
        Integer randomGeneratedNumber = RandomNumberGenerator.generate(1, 100);
        Assertions.assertTrue(randomGeneratedNumber >= 1 && randomGeneratedNumber <= 100);
    }
}