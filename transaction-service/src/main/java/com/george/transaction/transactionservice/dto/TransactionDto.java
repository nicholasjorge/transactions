package com.george.transaction.transactionservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.george.transaction.transactionservice.validation.Enum;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TransactionDto implements Serializable {

    private static final long serialVersionUID = -916709137486141281L;

    @NotBlank(message = "Transaction type must not be empty")
    @Enum(enumClass = TransactionTypeDto.class)
    private String transactionType;
    @NotBlank(message = "Name must not be empty")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;
    @NotBlank(message = "CNP must not be empty")
    @Size(max = 13, message = "CNP must not exceed 13 characters")
    private String cnp;
    @NotBlank(message = "IBAN must not be empty")
    @Size(max = 25, message = "IBAN must not exceed 25 characters")
    private String iban;
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    @Digits(integer = 9, fraction = 2, message = "Amount must have max 2 decimals, and max 9 integer")
    @Positive(message = "Amount must be a positive number")
    private BigDecimal amount;
    //    @Pattern(regexp = "^[+]?([0-9]+([.][0-9]*)?|[.][0-9]+)$", message = "The value must be a valid number")
    //    private String amount;

}
