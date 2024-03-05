package ru.kata.spring.boot_security.demo.exception_handling;

import java.util.HashMap;
import java.util.Map;

public class UserIncorrectData {
    private String info;
    private final Map<String, String> fieldErrors;

    public UserIncorrectData() {
        fieldErrors = new HashMap<>();
    }

    public void addError(String fieldName, String errorMessage) {
        fieldErrors.put(fieldName, errorMessage);
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
