package com.betting.api.controller;


import com.betting.api.dto.BettingRequest;
import com.betting.api.service.BettingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/betting")
@Slf4j
public class BettingController {

    private BettingService bettingService;

    @Autowired
    public BettingController(BettingService bettingService) {
        this.bettingService = bettingService;
    }

    @PostMapping(value = "/bet", consumes = APPLICATION_JSON_VALUE) public ResponseEntity<String> createBet (
            @RequestBody @Validated BettingRequest bettingRequest) {
        log.debug("creating bet for [{}], [{}]", bettingRequest.getBetNumber(), bettingRequest.getBetAmount());
        return ResponseEntity.ok(String.valueOf(bettingService.calculateWinning(bettingRequest)));
    }
}
