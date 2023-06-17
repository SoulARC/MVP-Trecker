package com.example.mvt_tracker.util;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.util.impl.FormatValidatorImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FormatValidatorImplTest {

    private FormatValidatorImpl formatValidator;

    @BeforeEach
    public void setUp() {
        formatValidator = new FormatValidatorImpl();
    }

    @SneakyThrows
    @Test
    public void isValidBasketballFormat_ok() {
        String[] fields = {"player 1", "nick1", "4", "Team A", "10", "2", "7"};
        boolean isValid = formatValidator.isValidBasketballFormat(fields);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void isInvalidBasketballFormat_notOk() {
        String[] fields = {"player 1", "nick1", "4", "Team A", "10", "2"};
        Assertions.assertThrowsExactly(IncorrectFormatException.class, () -> {
            boolean isValid = formatValidator.isValidBasketballFormat(fields);
        });
    }

    @SneakyThrows
    @Test
    public void isValidHandballFormat_ok() {
        String[] fields = {"player 1", "nick1", "4", "Team A", "0", "20"};
        boolean isValid = formatValidator.isValidHandballFormat(fields);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void isInvalidHandballFormat_notOk() {
        String[] fields = {"player 1", "nick1", "4", "Team A", "0"};
        Assertions.assertThrowsExactly(IncorrectFormatException.class, () -> {
            boolean isValid = formatValidator.isValidHandballFormat(fields);
        });
    }

}
