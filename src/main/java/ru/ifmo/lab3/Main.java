package ru.ifmo.lab3;

import ru.ifmo.lab3.domain.IniConfig;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        String fileName = "build/resources/main/configuration.ini";
        try {
            IniConfig storage = IniConfig.fromFile(fileName);
            System.out.println(storage.getValue(Integer.class, "COMMON", "LogNCMD"));
            System.out.println(storage.getValue(String.class, "NCMD", "SampleRate"));
            System.out.println(storage.getValue(Double.class, "NCMD", "SampleRate"));
            System.out.println(storage.getValue(String.class, "COMMON", "OpenMPThreadsCount"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid type of requested value");
        } catch (FileNotFoundException e) {
            System.out.println("File with filename = " + fileName + " not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}