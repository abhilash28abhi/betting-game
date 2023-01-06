package com.betting.api.service;

import com.betting.api.dto.BettingRequest;
import com.betting.api.util.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BettingService {

    private RandomNumberGenerator randomNumberGenerator;

    @Autowired
    public BettingService(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public Double calculateWinning(BettingRequest bettingRequest) {
        Integer randomNumber = randomNumberGenerator.generate(1, 100);
        if (randomNumber < bettingRequest.getBetNumber()) {
            return 0.0;
        }
        return (bettingRequest.getBetAmount() * (99.0 / (100.0 - bettingRequest.getBetNumber().doubleValue())));
    }

}
