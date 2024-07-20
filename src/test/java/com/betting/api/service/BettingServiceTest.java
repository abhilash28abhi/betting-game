package com.betting.api.service;

import com.betting.api.dto.BettingRequest;
import com.betting.api.util.RandomNumberGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;

@RunWith(MockitoJUnitRunner.class)
public class BettingServiceTest {

    @InjectMocks
    private BettingService bettingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void calculateWinning_successful() {
        try (MockedStatic<RandomNumberGenerator> mockedStatic = mockStatic(RandomNumberGenerator.class)) {
            mockedStatic.when(() -> RandomNumberGenerator.generate(anyInt(), anyInt())).thenReturn(50);
            BettingRequest request = BettingRequest.builder()
                    .betNumber(20)
                    .betAmount(50.0)
                    .build();
            Double winnings = bettingService.calculateWinning(request);
            Assertions.assertEquals(Double.valueOf(61.88), winnings);
        }
    }

    @Test
    public void calculateWinning_betNumberLessThanRandom_zeroWin() {
        try (MockedStatic<RandomNumberGenerator> mockedStatic = mockStatic(RandomNumberGenerator.class)) {
            mockedStatic.when(() -> RandomNumberGenerator.generate(anyInt(), anyInt())).thenReturn(50);
            BettingRequest request = BettingRequest.builder()
                    .betNumber(60)
                    .betAmount(50.0)
                    .build();
            Double winnings = bettingService.calculateWinning(request);
            Assertions.assertEquals(Double.valueOf(0.0), winnings);
        }
    }

}