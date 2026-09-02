package com.jono.transactionlab.dto;

import java.math.BigDecimal;

public record AccountSummaryDTO(
        Long id,
        String accountHolderName,
        BigDecimal balance
) {
}
