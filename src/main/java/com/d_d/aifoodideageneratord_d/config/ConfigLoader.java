package com.d_d.aifoodideageneratord_d.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            System.out.println("Nie mogę otworzyć pliku konfiguracyjnego : " + filePath);
            throw new RuntimeException(e);
        }
        return properties;
    }
}