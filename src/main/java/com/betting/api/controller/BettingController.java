package com.betting.api.controller;


import com.betting.api.dto.BettingRequest;
import com.betting.api.errors.HttpErrorResponse;
import com.betting.api.service.BettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Creates a new bet based on player bet amount and bet number")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            schema = @Schema(
                    implementation = BettingRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(
                            implementation = HttpErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(schema = @Schema(
                            implementation = HttpErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content(schema = @Schema(
                            implementation = HttpErrorResponse.class)))})
    @PostMapping(value = "/play", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createBet (@RequestBody @Validated BettingRequest bettingRequest) {
        log.debug("creating bet for [{}], [{}]", bettingRequest.getBetNumber(), bettingRequest.getBetAmount());
        return ResponseEntity.ok(String.valueOf(bettingService.calculateWinning(bettingRequest)));
    }
}
