package com.sebastienguillemin.stups.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;

import org.yaml.snakeyaml.Yaml;

public class PropertiesReader {
    private static PropertiesReader propertiesReader;

    public static PropertiesReader getInstance() {
        if (propertiesReader == null)
            propertiesReader = new PropertiesReader();

        return propertiesReader;
    }

    private HashMap<String, String> properties;

    public String getPropertyValue(String propertyName) {
        if (this.properties == null)
            this.loadProperties();

        return Optional.of(this.properties.get(propertyName)).orElse("");
    }

    private void loadProperties() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("properties.yml");
        this.properties = yaml.load(inputStream);

        System.out.println("Properties loaded.");
    }
}
