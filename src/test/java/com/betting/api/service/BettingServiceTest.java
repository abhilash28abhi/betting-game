package com.betting.api.service;

import com.betting.api.dto.BettingRequest;
import com.betting.api.util.RandomNumberGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BettingServiceTest {

    @Mock
    private RandomNumberGenerator randomNumberGeneratorMock;

    @InjectMocks
    private BettingService bettingService;

    @Test
    public void shouldCalculateCorrectWinnings() {
        when(randomNumberGeneratorMock.generate(any(), any())).thenReturn(50);
        BettingRequest request = BettingRequest.builder()
                .betNumber(20)
                .betAmount(50.0)
                .build();
        Double winnings = bettingService.calculateWinning(request);
        Assertions.assertEquals(Double.valueOf(61.875), winnings);
    }

    @Test
    public void shouldCalculateZeroWinnings() {
        when(randomNumberGeneratorMock.generate(any(), any())).thenReturn(50);
        BettingRequest request = BettingRequest.builder()
                .betNumber(60)
                .betAmount(50.0)
                .build();
        Double winnings = bettingService.calculateWinning(request);
        Assertions.assertEquals(Double.valueOf(0.0), winnings);
    }

}