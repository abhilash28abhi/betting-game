package com.betting.api.controller;

import com.betting.api.dto.BettingRequest;
import com.betting.api.service.BettingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BettingControllerTest {

    private final Double result = 48.385;

    @Mock
    private BettingService bettingService;

    @InjectMocks
    private BettingController bettingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBet_Successful() {
        BettingRequest bettingRequest = BettingRequest.builder()
                .betNumber(52)
                .betAmount(75.0)
                .build();

        when(bettingService.calculateWinning(any())).thenReturn(result);
        ResponseEntity<?> responseEntity = bettingController.createBet(bettingRequest);
        Assertions.assertEquals(Double.valueOf((String)responseEntity.getBody()), result);
    }

    @Test
    public void testBet_InvalidBetNumber_BadRequest() {
        BettingRequest bettingRequest = BettingRequest.builder()
                .betNumber(101)
                .betAmount(75.0)
                .build();

        // Mock the BindingResult to simulate validation error
        BindingResult bindingResult = org.mockito.Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(new FieldError("betNumber", "betNumber",
                "Bet number must be less than or equal to 100")));

        try {
            bettingController.createBet(bettingRequest);
        } catch (Exception e) {
            Assertions.assertEquals("Bet number must be less than or equal to 100",
                    ((MethodArgumentNotValidException) e).getBindingResult().getFieldError("betNumber").getDefaultMessage());
        }
    }
}
