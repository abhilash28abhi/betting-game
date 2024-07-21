package com.betting.api.service;

import com.betting.api.dto.BettingRequest;
import com.betting.api.util.RandomNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class BettingService {


    public Double calculateWinning(BettingRequest bettingRequest) {
        Integer randomNumber = RandomNumberGenerator.generate(1, 99);
        log.debug("Random number generated is : [{}]", randomNumber);
        if (randomNumber < bettingRequest.getBetNumber()) {
            return 0.0;
        }
        return calculateWin(bettingRequest.getBetAmount(), bettingRequest.getBetNumber());
    }

    private Double calculateWin (double bet, int betNumber) {
        // Calculate win based on the given formula
        Double win = bet * (99 / (100.0 - betNumber));
        log.debug("Win value is : [{}]", win);
        // Round to 2 decimal places
        BigDecimal roundedWin = BigDecimal.valueOf(win).setScale(2, RoundingMode.HALF_UP);
        log.debug("Win value after rounding is : [{}]", roundedWin);
        return roundedWin.doubleValue();
    }
}
