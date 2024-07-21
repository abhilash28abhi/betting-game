package com.betting.api.service;

import com.betting.api.dto.BettingRequest;
import com.betting.api.util.RandomNumberGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BettingRTPTest {

    Logger LOGGER = LoggerFactory.getLogger(BettingRTPTest.class);

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUrl = "http://localhost:8090/api/v1/betting/bet";

    private static final Integer NO_OF_ROUNDS = 1000000;
    private static final Integer LOWER_BOUND = 1;
    private static final Integer UPPER_BOUND = 99;

    private static final int numberOfThreads = 24;

    @Test
    public void calculateRtp() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        AtomicReference<BigDecimal> totalSpent = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalWon = new AtomicReference<>(BigDecimal.ZERO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < NO_OF_ROUNDS; i++) {
            executorService.submit(() -> {
                double betAmount = 1.0;//generate random bet amount using Math.random() * 100;
                int betNumber = RandomNumberGenerator.generate(LOWER_BOUND, UPPER_BOUND);

                BettingRequest bettingRequest = BettingRequest.builder()
                        .betAmount(betAmount)
                        .betNumber(betNumber)
                        .build();
                HttpEntity<BettingRequest> request = new HttpEntity<>(bettingRequest, headers);

                try {
                    ResponseEntity<Double> response = restTemplate.exchange(
                            baseUrl, HttpMethod.POST, request, Double.class);
                    Double win = response.getBody();
                    if (win != null) {
                        totalSpent.updateAndGet(currentSpent -> currentSpent.add(BigDecimal.valueOf(betAmount)));
                        totalWon.updateAndGet(currentWon -> currentWon.add(BigDecimal.valueOf(win)));
                    }
                } catch (Exception e) {
                    LOGGER.error("Error during API call: ", e);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        BigDecimal rtp = totalWon.get()
                .divide(totalSpent.get(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
        System.out.println("Total won: " + totalWon.get());
        System.out.println("Total spent: " + totalSpent.get());

        LOGGER.info("RTP after [{}] rounds  --- [{}]", NO_OF_ROUNDS, rtp);
    }
}
