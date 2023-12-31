package org.example.config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CacheProperties {

    private static final String PROPERTY_FILE_NAME = "src/main/resources/applicationCache.yml";

    public static String getProperty(String nameOfProperty) {
        Map<String, String> data = new HashMap<>();
        try (InputStream inputStream = new FileInputStream(PROPERTY_FILE_NAME)) {
            Yaml yaml = new Yaml();
            data = yaml.load(inputStream);
        } catch (IOException ex) {
        }
        return data.get(nameOfProperty);
    }
}
