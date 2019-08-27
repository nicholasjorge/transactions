package com.george.transaction.transactionservice;

import com.george.transaction.transactionservice.dto.TransactionDto;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

public class ValidationTest {

    private static Validator validator;
    private TransactionDto dto;

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Before
    public void before() {
        dto = new TransactionDto();
        dto.setTransactionType("IBAN_TO_IBAN");
        dto.setName("George");
        dto.setIban(RandomStringUtils.randomAlphanumeric(25));
        dto.setCnp(RandomStringUtils.randomNumeric(13));
        dto.setAmount(new BigDecimal(RandomStringUtils.randomNumeric(4)));
        dto.setDescription(RandomStringUtils.randomAlphabetic(100));
    }

    @Test
    public void testTransactionType() {
        dto.setTransactionType(RandomStringUtils.randomAlphanumeric(5));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Invalid value for transaction type. This is not permitted.",
                Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testNameLength() {
        dto.setName(RandomStringUtils.randomAlphanumeric(256));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Name must not exceed 255 characters", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testNameEmpty() {
        dto.setName("");
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Name must not be empty", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testNameNull() {
        dto.setName(null);
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Name must not be empty", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testIbanLength() {
        dto.setIban(RandomStringUtils.randomAlphanumeric(26));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("IBAN must not exceed 25 characters", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testIbanEmpty() {
        dto.setIban("");
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("IBAN must not be empty", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testIbanNull() {
        dto.setIban(null);
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("IBAN must not be empty", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testCnpLength() {
        dto.setCnp(RandomStringUtils.randomAlphanumeric(14));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("CNP must not exceed 13 characters", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testCnpEmpty() {
        dto.setCnp("");
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("CNP must not be empty", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testCnpNull() {
        dto.setCnp(null);
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("CNP must not be empty", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testDescriptionLength() {
        dto.setDescription(RandomStringUtils.randomAlphanumeric(256));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Description must not exceed 255 characters", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testAmountZero() {
        dto.setAmount(BigDecimal.ZERO);
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Amount must be a positive number", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testAmountNegative() {
        dto.setAmount(new BigDecimal(-2));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Amount must be a positive number", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testAmountDecimals() {
        dto.setAmount(new BigDecimal(2.999));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Amount must have max 2 decimals, and max 9 integer", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testAmountIntegerValueLength() {
        dto.setAmount(new BigDecimal(RandomStringUtils.randomNumeric(10)));
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("Amount must have max 2 decimals, and max 9 integer", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

/*    @Test
    public void testAmount() {
        dto.setAmount("chars");
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("The value must be a valid number", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }

    @Test
    public void testAmountFloatNumber() {
        dto.setAmount("5.55");
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(0, Matchers.is(constraintViolations.size()));
    }

    @Test
    public void testAmountNegative() {
        dto.setAmount("-2");
        Set<ConstraintViolation<TransactionDto>> constraintViolations = validator.validate(dto);
        Assert.assertThat(1, Matchers.is(constraintViolations.size()));
        Assert.assertThat("The value must be a valid number", Matchers.is(constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().get()));
    }*/

}
