package com.example.mvt_tracker.util;

import com.example.mvt_tracker.exception.IncorrectFormatException;

public interface FormatValidator {
    boolean isValidBasketballFormat(String[] fields) throws IncorrectFormatException;

    boolean isValidHandballFormat(String[] fields) throws IncorrectFormatException;
}
