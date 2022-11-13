package com.sky.pro.application.service;

import com.sky.pro.application.exception.InccorrectSurnameException;
import com.sky.pro.application.exception.IncorrectNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import java.util.Locale;

@Service
public class ValidatorService {

    public String validateName(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectNameException();
        }
        return StringUtils.capitalize(name.toLowerCase());
    }

    public String validateSurname(String surname) {
        String[] surnames = surname.split("-");
        for (int i = 0; i < surnames.length; i++) {
            if (!StringUtils.isAlpha(surnames[i])) {
                throw new InccorrectSurnameException();
            }
            surnames[i] = StringUtils.capitalize(surnames[i].toLowerCase());
        }
        return String.join("-", surnames);
    }

}
