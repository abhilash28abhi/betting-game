package com.betting.api.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class BettingRequest {

    @NotNull
    Double betAmount;

    @NotNull
    @Min(1)
    @Max(100)
    Integer betNumber;
}
