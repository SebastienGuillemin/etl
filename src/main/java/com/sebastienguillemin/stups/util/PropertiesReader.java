package com.sebastienguillemin.stups.util;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class PropertiesReader {
    private static PropertiesReader propertiesReader;

    public static PropertiesReader getInstance() {
        if (propertiesReader == null)
            propertiesReader = new PropertiesReader();

        return propertiesReader;
    }

    private Map<String, Object> properties;

    @SuppressWarnings("unchecked")
    public String getPropertyValue(String propertyName) {
        if (this.properties == null)
            this.loadProperties();

        String parts[] = propertyName.split("\\.");

        String value = "";
        Map<String, Object> nextNode = this.properties;
        Object next;
        for (int i = 0; i < parts.length; i ++) {
            next = nextNode.get(parts[i]);
            if (next == null) {
                return "";
            }
            else if (next instanceof Map)
                nextNode = (Map<String, Object>) next;
            else if (next instanceof String) {
                if (i < parts.length - 1)
                    return "";
    
                value = (String) next;
            }
        }

        return value;
    }

    public boolean getPropertyValueBoolean(String propertyName) {
        return Boolean.valueOf(this.getPropertyValue(propertyName));
    }

    private void loadProperties() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("properties.yml");
        this.properties = yaml.load(inputStream);
        
        System.out.println("Properties loaded.");
    }
}
