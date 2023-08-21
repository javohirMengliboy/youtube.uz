package com.example.service;

import com.example.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public String getMessage(String code, Language lang) {
        return messageSource.getMessage(code, null, new Locale(lang.name()));
    }
}
