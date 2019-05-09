package ru.ifmo.lab3.domain;

import ru.ifmo.lab3.exception.FileFormatException;
import ru.ifmo.lab3.exception.MyIOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Parser {

    Map<String, Map<String, String>> parse(Reader reader) {

        try (BufferedReader br = new BufferedReader(reader)) {
            Map<String, Map<String, String>> data = new HashMap<>();

            List<String> lines = br.lines().collect(Collectors.toList());

            String currentSection = "";
            for (String line : lines) {
                String trimmedLine = line.split(";")[0].trim();
                if (!trimmedLine.isEmpty()) {
                    if (trimmedLine.startsWith("[") && trimmedLine.endsWith("]")) {
                        currentSection = getSection(trimmedLine);
                        data.computeIfAbsent(currentSection, k -> new HashMap<>());
                    } else {
                        String[] keyAndValue = trimmedLine.split("=");
                        if (keyAndValue.length != 2) {
                            throw new FileFormatException("The key and value pair " + trimmedLine + " is incorrect!!!");
                        }
                        String key = getKey(keyAndValue[0]);
                        String value = getValue(keyAndValue[1]);
                        data.get(currentSection).put(key, value);
                    }
                }
            }
            return data;

        } catch (IOException e) {
            throw new MyIOException("Failed to read lines from reader");
        }
    }


    private String getSection(String section) {
        section = section.replace("[", "").replace("]", "");
        if (!isSectionValid(section)) {
            throw new FileFormatException("Section name " + section + "is incorrect!!!");
        }
        return section;
    }

    private boolean isSectionValid(String section) {
        for (int i = 0; i < section.length(); i++) {
            if (!Character.isLetterOrDigit(section.charAt(i)) && section.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }

    private String getKey(String string) {

        String key = string.trim();
        if (isValidKey(key)) {
            return key;
        } else {
            throw new FileFormatException("The key " + key + " is incorrect!!!");
        }
    }

    private String getValue(String string) {
        String value = string.trim();
        if (isValidValue(value)) {
            return value;
        } else {
            throw new FileFormatException("The value " + value + " is incorrect!!!");
        }
    }

    private boolean isValidKey(String key) {
        for (int i = 0; i < key.length(); i++) {
            if (!Character.isLetterOrDigit(key.charAt(i)) && key.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }

    private boolean isValidValue(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))
                    && value.charAt(i) != '_'
                    && value.charAt(i) != '/'
                    && value.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }
}