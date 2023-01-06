package com.betting.api.service;

import com.betting.api.dto.BettingRequest;
import com.betting.api.util.RandomNumberGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class BettingServiceRTPTest {

    Logger LOGGER = LoggerFactory.getLogger(BettingServiceRTPTest.class);

    private static final Integer NO_OF_ROUNDS = 1000000;
    private static final Integer LOWER_BOUND = 1;
    private static final Integer UPPER_BOUND = 99;
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
    private BettingService bettingService = new BettingService(randomNumberGenerator);

    @Test
    public void shouldCalculateRTP() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(24);

        Double betAmount = 1.0;
        BettingRequest request = BettingRequest.builder()
                .betAmount(betAmount)
                .betNumber(randomNumberGenerator.generate(LOWER_BOUND, UPPER_BOUND))
                .build();
        Callable<Double> betting = () -> bettingService.calculateWinning(request);
        List<Callable<Double>> bets = new ArrayList<>();
        IntStream.range(1, NO_OF_ROUNDS).forEach(i -> bets.add(betting));
        //storing the bet results in list
        List<Double> winnings = new ArrayList<>();
        executorService.invokeAll(bets).forEach(f ->
                {
                    try {
                        winnings.add(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.error("Exception occurred during the game", e);
                    }
                }
        );

        BigDecimal rtp = calculateRTP(winnings, NO_OF_ROUNDS, betAmount);
        LOGGER.info("RTP after [{}] rounds with each bet amount [{}] is --- [{}]", NO_OF_ROUNDS, betAmount, rtp);
    }

    private BigDecimal calculateRTP(List<Double> winnings, int numberOfRounds, Double betAmount) {
        BigDecimal totalWinnings = winnings.stream().map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal rtp = (new BigDecimal(100).multiply(totalWinnings.divide(new BigDecimal(numberOfRounds * betAmount))));
        return rtp.setScale(3, BigDecimal.ROUND_HALF_EVEN);
    }
}
