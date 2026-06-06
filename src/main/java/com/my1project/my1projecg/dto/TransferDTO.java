package com.my1project.my1projecg.dto;

import java.math.BigDecimal;

public class TransferDTO {
    private String receiverUsername;
    private Double amount;

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
