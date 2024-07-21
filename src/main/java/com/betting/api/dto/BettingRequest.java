package com.betting.api.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Schema(name = "BettingRequest", description = "Request object for placing bet")
public class BettingRequest {

    @NotNull
    Double betAmount;

    @NotNull
    @Min(1)
    @Max(99)
    Integer betNumber;
}
