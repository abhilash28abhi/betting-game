package com.betting.api.dto;

public class PlayerRequest {

    Double betAmount;

    Integer betNumber;

    public Double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }

    public Integer getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(Integer betNumber) {
        this.betNumber = betNumber;
    }
}
