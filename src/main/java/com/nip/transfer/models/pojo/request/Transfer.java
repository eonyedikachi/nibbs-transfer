package com.nip.transfer.models.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private String sourceUserId;
    private String destinationUserId;
    private BigDecimal amount;

}
