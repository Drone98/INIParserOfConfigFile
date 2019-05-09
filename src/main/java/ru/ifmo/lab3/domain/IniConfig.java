package ru.ifmo.lab3.domain;

import ru.ifmo.lab3.exception.FileFormatException;
import ru.ifmo.lab3.exception.PairOfKeyAndSectionIsNotExist;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IniConfig {

    private final Map<String, Map<String, String>> data;

    private IniConfig(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public static IniConfig fromFile(String filename) throws FileNotFoundException {
        return IniConfig.fromReader(new InputStreamReader(new FileInputStream(filename)));
    }

    public static IniConfig fromReader(Reader reader) {
        Parser parser = new Parser();
        return new IniConfig(parser.parse(reader));
    }

    public <T> T getValue(Class<T> cls, String section, String key) {
        if (data.get(section) == null || data.get(section).get(key) == null) {
            throw new PairOfKeyAndSectionIsNotExist("Pair of " + section + " and " + key + " does not exist!!!");
        }
        String value = data.get(section).get(key);
        if (Double.class.isAssignableFrom(cls)) {
            return (T) Double.valueOf(value);
        } else if (Integer.class.isAssignableFrom(cls)) {
            return (T) Integer.valueOf(value);
        } else if (Boolean.class.isAssignableFrom(cls)) {
            return (T) Boolean.valueOf(value);
        } else {
            return (T) value;
        }
    }
}