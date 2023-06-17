package com.example.mvt_tracker.util.impl;

import com.example.mvt_tracker.exception.IncorrectFormatException;
import com.example.mvt_tracker.util.FormatValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FormatValidatorImpl implements FormatValidator {
    private static final int PLAYER_NAME = 0;
    private static final int NICK_NAME = 1;
    private static final int PLAYER_NUMBER = 2;
    private static final int TEAM_NAME = 3;

    @Override
    public boolean isValidBasketballFormat(String[] fields) throws IncorrectFormatException {
        if (fields.length != 7 || namingValidation(fields)) {
            throw new IncorrectFormatException("The file has an incorrect format");
        }
        return true;
    }

    @Override
    public boolean isValidHandballFormat(String[] fields) throws IncorrectFormatException {
        if (fields.length != 6 || namingValidation(fields)) {
            throw new IncorrectFormatException("The file has an incorrect format");
        }
        return true;
    }

    public boolean namingValidation(String[] fields) {
        if (fields[PLAYER_NAME].isEmpty()) {
            return true;
        }

        if (fields[NICK_NAME].isEmpty()) {
            return true;
        }

        try {
            Integer.parseInt(fields[PLAYER_NUMBER]);
        } catch (NumberFormatException e) {
            return true;
        }
        return fields[TEAM_NAME].isEmpty();
    }
}
